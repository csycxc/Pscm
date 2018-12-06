package com.banry.pscm.service.conf;

public interface DDSI {
	
	/**
	 * 初始化租户动态数据源创建接口：<br>
	 * 
	 * 输入参数<br>
	 * 
	 *  @param tenantCode 租户代码<br>
	 * 
	 * 处理步骤：<br>
	 * 
	 * 1. 根据租户代码创建新的租户数据库实例<br>
	 * 2. 加载租户表到新创建的租户数据库<br>
	 * 3. 在新的租户库中，为租户创建默认的管理员账号<br>
	 * 4. 在新的租户库中，为租户创建默认的配置员账号<br>
	 * 

	 * 异常：<br>
	 * 
	 *  Exception<br>
	 *  1. 输入tenantCode为空<br>
	 *  2. 创建新的租户数据处理出错<br>
	 *  3. 步骤1或者2出错<br>
	 *  4. 其他错误<br>
	 *    
	 * 
	 */
	public void initTenant(String tenantCode) throws Exception;
}
