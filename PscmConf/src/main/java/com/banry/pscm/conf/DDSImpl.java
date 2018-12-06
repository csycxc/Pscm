package com.banry.pscm.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.banry.pscm.persist.dds.DDLI;
import com.banry.pscm.persist.dds.DynamicDataSourceConfiguration;
import com.banry.pscm.service.conf.DDSI;

@Service
public class DDSImpl implements DDSI {

	private static Logger log = LoggerFactory.getLogger(DDSImpl.class);

	@Autowired
	DDLI ddl;

	@Autowired
	Environment ev;	

	/**
	 * 初始化租户
	 * 
	 * 输入：
	 * String tenantAccount 租户账号
	 * 
	 * 处理流程和逻辑：
	 * 1. 检查输入是否为空，或者非法租户代码名（TODO）
	 * 2. 使用Mybatis运行DDL创建database
	 * 3. 加载初始化sql创建表
	 * 4. 创建运行时数据源
	 * 
	 */
	@Override
	public void initTenant(String tenantAccount) throws Exception {

		if (tenantAccount == null)
			throw new Exception("tenant account is null!");
		
		//1. create database into db server
		log.info("creating database for tenant " + tenantAccount + "...");
		ddl.createDataBase("tenant_" + tenantAccount);
		log.info("database tenant_ + " + tenantAccount + " created for tenant " + tenantAccount + "...");
		
		//2. load tables
		runMysqlScript(tenantAccount);
		
		//3. create datasource
		DataSource dataSource = createDS(tenantAccount);
		
		//4. add tenant data source into runtime DDS list
		DynamicDataSourceConfiguration.addNewDS(dataSource, tenantAccount);
		
	}

	public DataSource createDS(String tenantAccount) {
		log.info("creating datasource for tenant " + tenantAccount + "...");
		
		String dbhost_port = ev.getProperty("pscm.tenant.database.host_port");
		String urlTmpl = "jdbc:mysql://" + dbhost_port + "/tenant_";
		String username = ev.getProperty("spring.datasource.username");
		String password = ev.getProperty("spring.datasource.password");
		String driverClassName = ev.getProperty("spring.datasource.driverClassName");
		//String initSQLFile = ev.getProperty("pscm.tenant.database.initsql");


		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driverClassName);
		druidDataSource.setUrl(urlTmpl + tenantAccount);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);
		return druidDataSource;
	}

	private void runMysqlScript(String dbname) {

		ApplicationHome home = new ApplicationHome(this.getClass());
		log.info("home path:" + home.getDir());
		
		String dbhost_port = ev.getProperty("pscm.tenant.database.host_port");
		String urlTmpl = "jdbc:mysql://" + dbhost_port + "/tenant_";
		String username = ev.getProperty("spring.datasource.username");
		String password = ev.getProperty("spring.datasource.password");
		String driverClassName = ev.getProperty("spring.datasource.driverClassName");
		String initSQLFile = ev.getProperty("pscm.tenant.database.initsql");

		try {
			// Create MySQL Connection
			Class.forName(driverClassName);
			Connection connection = DriverManager.getConnection(urlTmpl + dbname, username, password);

			// Initialize object for ScriptRunner
			ScriptRunner runner = new ScriptRunner(connection);
			// Give the input file to Reader
			Reader br = new BufferedReader(new FileReader(home.getDir() + "/" + initSQLFile));
			// Execute script
			runner.runScript(br);

		} catch (Exception e) {
			log.info("load tables for '" + dbname + "'  failed: " + e);
			e.printStackTrace();
		}
	}

}
