package com.banry.pscm.persist.dds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Haomeng
 * @version 1.0
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
	private static Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

	@Override
	protected Object determineCurrentLookupKey() {
		log.info("当前数据源：" + DynamicDataSourceContextHolder.get());
		if (DynamicDataSourceContextHolder.get() == null) {
			DynamicDataSourceContextHolder.set("DEFAULT");
		}
		return DynamicDataSourceContextHolder.get();
	}
}
