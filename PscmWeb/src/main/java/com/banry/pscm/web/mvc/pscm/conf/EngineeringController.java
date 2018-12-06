package com.banry.pscm.web.mvc.pscm.conf;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.schedule.Engineering;
import com.banry.pscm.service.schedule.EngineeringService;
import com.banry.pscm.service.schedule.ScheduleException;


@Controller
@RequestMapping("/engineering")
public class EngineeringController {

	@Autowired
	private EngineeringService engineeringService;

	private static Logger log = LoggerFactory.getLogger(EngineeringController.class);

	@Autowired
	private Environment ev;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 编辑工程信息
	 * 
	 * @param engineering
	 * @param m
	 * @param session
	 * @return
	 */
	@RequestMapping("/editengineering")
	public String editengineering(Engineering engineering, Model m, HttpSession session) {
		String parentTenantAccount = (String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");//COMPANY_LEVEL_TENANT_ACCOUNT
		DynamicDataSourceContextHolder.set(parentTenantAccount);
		// 从session中获取当前租户的项目信息
		String engCode = (String) session.getAttribute("ENG_CODE");
		String tenantCode = (String) session.getAttribute("CURRENT_TENANT_CODE");

		m.addAttribute("tenantCode", tenantCode);
		m.addAttribute("PARENT_TENANT_CODE", "company_t5");
		m.addAttribute("TENANT_TYPE", "0");
		
		if (engCode != null) {
			m.addAttribute("engCode", engCode);
			try {
				Engineering existEngineering = engineeringService.selectEngineeringByPrimaryKey(engCode);				
				if (existEngineering != null) {
					log.info("existing engineering:"+existEngineering);
					m.addAttribute("engCode", existEngineering.getEngCode());
					m.addAttribute("engName", existEngineering.getEngName());
					m.addAttribute("budget", existEngineering.getBudget());
					m.addAttribute("confSource", existEngineering.getConfSource());
					m.addAttribute("contractPrice", existEngineering.getContractPrice());
					m.addAttribute("engAddress", existEngineering.getEngAddress());
					m.addAttribute("engFullName", existEngineering.getEngFullName());
					m.addAttribute("engSurvey", existEngineering.getEngSurvey());
					m.addAttribute("engType", existEngineering.getEngType());
					m.addAttribute("payRationThreshold", existEngineering.getPayRationThreshold());
				}
			} catch (ScheduleException e) {
				// TODO Auto-generated catch block
				log.error(e.toString());
				e.printStackTrace();
			}
		}
		
		
		/*
		 * 
		 *调用公共服务API，获取专业来源列表
		 * 
		 */
		JSONArray majorSourceJson = restTemplate
				.getForEntity(ev.getProperty("pscm.commonbase.rest.url") + "/dds/getCommonbaseCatalog", JSONArray.class)
				.getBody(); 	
		log.debug("majorSourceJson" + majorSourceJson);
		m.addAttribute("MajorSourceArray", majorSourceJson);
		
		return "conf/engineering";
	}
	
	@RequestMapping(value = "/saveengineering")
	public ResponseEntity<Object> saveenginerring(Engineering engineering, HttpSession session) {
		log.info("saveenginerring start...");
		
		log.info(session.toString());
		// 在session中获取公司级别租户代码
		String parentTenantCode = (String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");//COMPANY_LEVEL_TENANT_ACCOUNT

		// 如果未登录或者登陆超时，获取不到session中的信息，返回给前端通知用户重新登陆
		if (parentTenantCode == null)
			return new ResponseEntity<>("未登录，或者登陆超市，请重新登陆", HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500

		DynamicDataSourceContextHolder.set(parentTenantCode);

		log.debug("engineering" + engineering.toString());

		try {
			String engCode = engineeringService.saveEngineering(engineering);
			
			session.setAttribute("ENG_CODE", engCode);
			
			return new ResponseEntity<>(engCode, HttpStatus.OK);// 保存成功返回200
		} catch (Exception e) {
			e.printStackTrace();
			log.error("save engineer error:" + e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		}
	}
}
