package com.banry.pscm;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.banry.pscm.conf.DDSImpl;
import com.banry.pscm.persist.dds.DynamicDataSourceConfiguration;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;

/**
 * 自定义启动类，可以在此类中运行一些需要启动时候做的动作<BR>
 * 
 * 1 初始化动态数据源，会调用tenant service中的getTenantList restapi获取已有的租户列表，为每隔租户创建动态数据源 *<BR> 
 * 
 * 2  * 用户登录后悔把当前数据源名称放在session中，以后的page/restapi调用会在当前session中获取数据源名称并使用
 * DynamicDataSourceContextHolder.set("数据库名")进行数据源动态切换<BR>
 * 
 * 3 建立公共库缓冲commonDBCache<BR>
 *   访问cache方式 ，例如访问cache中的municipal_bridge_housing_dept公共库的engdivision表信息
 *   ((Hashtable)CommonDBCache.commonDBCache.get("municipal_bridge_housing_dept")).get("engdivisionList")
 * 
 * TODO: 增加刷新公共库缓冲的方法
 * 
 * @author Haomeng, Wang
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static boolean initFlag = false;	

	private static Logger log = LoggerFactory.getLogger(ApplicationStartup.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		Environment ev = event.getApplicationContext().getEnvironment();
		ev.getProperty("pscm.commonbase.rest.url");

		RestTemplate restTemplate = (RestTemplate) event.getApplicationContext().getBean("restTemplate");

		if (!initFlag) {
			log.info("init dds...");
			initDDSContext(event.getApplicationContext());

			// build commondb cache
			CommonDBCache.buildCommonDBCache(ev, restTemplate);
			log.debug("commonDBCache:" + CommonDBCache.commonDBCache);

			initFlag = true;
		}

	}

	/**
	 * 根据公共目录库中的公共业务库列表，初始化每个公共业务库
	 * 
	 * @param appCtx
	 */
	private void initDDSContext(ApplicationContext appCtx) {

		Environment ev = appCtx.getEnvironment();

		// for debuging to check ctx beans
		// printCtx(appCtx);

		RestTemplate restTemplate = (RestTemplate) appCtx.getBean("restTemplate");
		DDSImpl ddsImpl = (DDSImpl) appCtx.getBean("DDSImpl");

		// 设置默认数据源
		DynamicDataSourceContextHolder.set("DEFAULT");

		// 调用 租户服务 tenant的 getTenantList RestAPI方法，获取租户列表
		JSONObject tenantList = restTemplate
				.getForEntity(ev.getProperty("pscm.tenant.rest.url") + "/tenant/getTenantList", JSONObject.class)
				.getBody();

		if (tenantList != null) {
			ArrayList<?> tenantListData = (ArrayList<?>) tenantList.get("data");

			log.info("tenantList" + tenantList);

			for (int i = 0; i < tenantListData.size(); i++) {
				LinkedHashMap<?, ?> tenant = (LinkedHashMap<?, ?>) tenantListData.get(i);
				String tenantAccount = tenant.get("account").toString();
				DataSource ds = ddsImpl.createDS(tenantAccount);
				DynamicDataSourceConfiguration.addNewDS(ds, tenantAccount);
			}
		} else {
			log.info("can not get tenant list, check tenant service status");
		}

	}

	private static void printCtx(ApplicationContext appCtx) {
		String[] beans = appCtx.getBeanDefinitionNames();
		for (int i = 0; i < beans.length; i++) {
			log.debug(beans[i]);
		}
	}

	/**
	 * 创建公共业务库的数据源
	 * 
	 * @param appCtx
	 * @param dsname
	 * @return
	 */

	public DataSource createDS(ApplicationContext appCtx, String dsname) {
		log.info("creating datasource for dsname " + dsname + "...");

		Environment ev = appCtx.getEnvironment();

		String dbhost_port = ev.getProperty("pscm.tenant.database.host_port");
		String urlTmpl = "jdbc:mysql://" + dbhost_port + "/";
		String username = ev.getProperty("spring.datasource.username");
		String password = ev.getProperty("spring.datasource.password");
		String driverClassName = ev.getProperty("spring.datasource.driverClassName");
		// String initSQLFile = ev.getProperty("pscm.tenant.database.initsql");

		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driverClassName);
		druidDataSource.setUrl(urlTmpl + dsname);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);

		return druidDataSource;
	}

	
}
