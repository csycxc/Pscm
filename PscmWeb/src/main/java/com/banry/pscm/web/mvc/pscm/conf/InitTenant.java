package com.banry.pscm.web.mvc.pscm.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banry.pscm.conf.DDSImpl;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.conf.PscmConfException;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.ScheduleException;

//import com.banry.pscm.conf.biz.DDSImpl;
//import com.banry.pscm.conf.biz.PSCMConfServiceImpl;
//import com.banry.pscm.conf.model.PscmConfException;
//import com.banry.pscm.conf.persist.dds.DynamicDataSourceContextHolder;

/**
 * 初始化租户REST RPC控制器 REST RPC API： inittenant 用来初始化租户数据库上下文，输入参数就是租户号名称tenantcode
 * 提供login api来模拟租户登录 提供findAllEngDivision来测试租户数据库上下文
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/conf")
public class InitTenant {

	private static Logger log = LoggerFactory.getLogger(InitTenant.class);

	@Autowired
	DDSImpl ddsImpl;

	@Autowired
	EngDivisionService engDivisionService;
	/*@Autowired
	PSCMConfServiceImpl pscmConfServiceImpl;*/

	/**
	 * 模拟一个租户登陆，设置session上下文 登陆模块做好后，删除这个模拟api(TODO)
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(String tenantcode, HttpSession session) {
		session.setAttribute("CURRENT_TENANT_ACCOUNT", tenantcode);
		log.info(tenantcode + " login ...");
		return tenantcode;
	}

	/**
	 * 测试方法，用来测试多租户环境业务api返回不同租户数据
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/findAllEngDivision")
	public ResponseEntity<List> findAllEngDivision(HttpSession session) {
		log.info("findAllEngDivision start...");
		/*try {
			setCurrentSessionDS(session);
			try {
				List<EngDivision> allList = engDivisionService.findAllEngDivision();
				return new ResponseEntity<>(allList, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			} catch (ScheduleException e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
		} catch (PscmConfException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		}*/
		try {
			setCurrentSessionDS(session);
			return new ResponseEntity<>(engDivisionService.findAllEngDivision(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		} catch (ScheduleException e) {
			e.printStackTrace();
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		} catch (PscmConfException e) {
			e.printStackTrace();
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		}
	}

	/**
	 * 初始化租户数据库上下文<BR>
	 * 处理逻辑： 创建租户数据库 加载表结构 初始化租户管理员用户和组 添加租户库数据源到动态数据源运行上下文
	 * 
	 * @param tenantcode
	 *            租户代码名<BR>
	 * @param request
	 *            HTTP Request<BR>
	 * @param response
	 *            HTTP Response<BR>
	 */
	@RequestMapping(value = "/inittenant", produces = "application/json")
	public void inittenant(String tenantcode, HttpServletRequest request, HttpServletResponse response) {
		// 使用默认数据源连接，运行ddl来创建数据库和表结构，加载初始数据
		DynamicDataSourceContextHolder.set("DEFAULT");// 设置使用当前数据源
		try {
			log.info("call restapi initTenant with tenantcode=" + tenantcode);
			ddsImpl.initTenant(tenantcode);
			log.info("tenant " + tenantcode + " init ok!");

		} catch (Exception e) {
			log.error("tenant " + tenantcode + " init error:" + e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * 设置当前回话租户使用的数据源 这个需要手工设置，使用后最好设置成null
	 * 
	 * @param session
	 */
	public static void setCurrentSessionDS(HttpSession session) throws PscmConfException {
		String tenantcode = (String) session.getAttribute("CURRENT_TENANT_ACCOUNT");
		if (tenantcode == null) {
			log.error("can not get CURRENT_TENANT_ACCOUNT from session.");
			throw new PscmConfException("can not get 'CURRENT_TENANT_ACCOUNT' from session, Session timeout or not login");
		}
		log.info("tenantcode = " + tenantcode);
		DynamicDataSourceContextHolder.set(tenantcode);
	}

	/**
	 * 设置公司级的数据源
	 * @param session
	 * @throws PscmConfException
	 */
	public static void setCurrentSessionCompDS(HttpSession session) throws PscmConfException {
		String tenantcode = (String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");
		if (tenantcode == null) {
			log.error("can not get COMPANY_LEVEL_TENANT_ACCOUNT from session.");
			throw new PscmConfException("can not get 'COMPANY_LEVEL_TENANT_ACCOUNT' from session, Session timeout or not login");
		}
		log.info("tenantcode = " + tenantcode);
		DynamicDataSourceContextHolder.set(tenantcode);
	}
}