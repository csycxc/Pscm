package com.banry.pscm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;

@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {

	@Pointcut("execution(* com.banry.pscm.web.mvc..*Controller.*(..))")
	public void mybatisExecutionSqlPointcut() {
	}

	@Before("mybatisExecutionSqlPointcut()")
	public void beforeMybatisExecutionSql(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String tenantAccount = SessionUtil.getSessionValue(request, SessionUtil.TENANT_ACCOUNT);
		if (tenantAccount != null && !"".equals(tenantAccount)) {
			// 切换数据源
			DynamicDataSourceContextHolder.set(tenantAccount);
		} else {
			DynamicDataSourceContextHolder.set("DEFAULT");
		}
	}

	/**
	 * 在SwitchDataSource注解的方法之后执行
	 * 
	 * @see com.micro.fast.common.annotation.SwitchDataSource
	 */
	@After("mybatisExecutionSqlPointcut()")
	public void afterMybatisExecutionSql() {
		// 清除进程中数据源的名字
		DynamicDataSourceContextHolder.clear();
	}
}
