package com.banry.pscm.persist.dds;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * @author Haomeng
 * @version 1.0
 * @since 2018/05/24
 */
// @MapperScan(basePackages = "com.banry.pscm.conf.dao")
@Configuration
public class DynamicDataSourceConfiguration {

	private static Logger log = LoggerFactory.getLogger(DynamicDataSourceConfiguration.class);

	public static Map<Object, Object> dataSourceMap = new HashMap<>();
	public static DynamicRoutingDataSource _DDS = new DynamicRoutingDataSource();

	@Bean("defaultDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource defaultDataSource() {
		log.debug("creating DefaultDS...");
		DataSource ds = DruidDataSourceBuilder.create().build();
		log.info("new ds created:" + ds.toString());
		return ds;
	}

	/**
	 * 核心动态数据源
	 *
	 * @return 数据源实例
	 */
	@Bean("dynamicDataSource")
	public DataSource dynamicDataSource() {
		log.debug("creating dynamicDataSource...");
		DataSource defaultDs = defaultDataSource();
		// _DDS.setDefaultTargetDataSource(defaultDs);
		dataSourceMap.put("DEFAULT", defaultDs);
		_DDS.setTargetDataSources(dataSourceMap);
		DynamicDataSourceContextHolder.set("DEFAULT");
		return _DDS;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		log.debug("creating sqlSessionFactory...");
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		//设置动态数据源
		sqlSessionFactoryBean.setDataSource(dynamicDataSource());
		// 此处设置为了解决找不到mapper文件的问题
		// sqlSessionFactoryBean.setMapperLocations(new
		// PathMatchingResourcePatternResolver()
		// .getResources("classpath:com/banry/pscm/conf/persist/mapper/*.xml"));
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setMapUnderscoreToCamelCase(true);//-自动使用驼峰命名属性映射字段   userId    user_id
		sqlSessionFactoryBean.setConfiguration(configuration);
		sqlSessionFactoryBean.setFailFast(true);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:com/banry/pscm/persist/mapper/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	public static void addNewDS(DataSource ds, String name) {
		log.info("add " + name + " datasource into dynamicDataSource ...");
		dataSourceMap.put(name, ds);
		_DDS.setTargetDataSources(dataSourceMap);
		_DDS.afterPropertiesSet();
		log.info("dynamicDataSource list=" + dataSourceMap);
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}
}
