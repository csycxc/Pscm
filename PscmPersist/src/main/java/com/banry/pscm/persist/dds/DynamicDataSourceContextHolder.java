package com.banry.pscm.persist.dds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Haomeng
 * @version 1.0
 * @since 2018/05/24
 */
public class DynamicDataSourceContextHolder {

	private static Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
	private static final ThreadLocal<String> currentDatesource = new ThreadLocal<>();

	/**
	 * 清除当前数据源
	 */
	public static void clear() {
		currentDatesource.remove();
	}

	/**
	 * 获取当前使用的数据源
	 *
	 * @return 当前使用数据源的ID
	 */
	public static String get() {
		return currentDatesource.get();
	}

	/**
	 * 设置当前使用的数据源
	 *
	 * @param value
	 *            需要设置的数据源ID
	 */
	public static void set(String value) {
		currentDatesource.set(value);
	}

	/**
	 * 设置从从库读取数据
	 */
	public static void setSlave() {
		DynamicDataSourceContextHolder.set("test1db");
	}
}
