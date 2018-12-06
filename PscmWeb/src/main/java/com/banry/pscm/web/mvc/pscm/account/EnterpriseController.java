package com.banry.pscm.web.mvc.pscm.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.RelateEnterprise;
import com.banry.pscm.service.account.RelateEnterpriseService;
import com.banry.pscm.web.mvc.model.DataTableModel;

@Controller
@RequestMapping("/enterprise")
public class EnterpriseController {
	
	@Autowired
	RelateEnterpriseService enterpriseService;
	
	@RequestMapping("/getEnterpriseList")
	@ResponseBody
	public Object getEnterpriseList(HttpServletRequest request) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantCode = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantCode);
			List<RelateEnterprise> listGrid = enterpriseService.findRelateEnterpriseList();
			data.setData(listGrid);
			data.setDraw(1L);
			data.setRecordsTotal(1L);
			data.setRecordsFiltered(1L);
			return data;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	@RequestMapping("/edit")
	public Object edit(HttpServletRequest request, String enterpriseCode) {
		ModelAndView mv = new ModelAndView("account/enterprise-edit");
		try {
			//编辑时获取角色信息
			if (enterpriseCode != null && !"".equals(enterpriseCode)) {
				String parentTenantCode = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				DynamicDataSourceContextHolder.set(parentTenantCode);
				RelateEnterprise ent = enterpriseService.findRelateEnterpriseByPrimaryKey(enterpriseCode);
				mv.addObject("ent", ent);
				mv.addObject("flag", "U");
			} else {
				mv.addObject("flag", "I");
			}
		} catch (AccountException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveRelateEnterprise")
	@ResponseBody
	public Map saveRelateEnterprise(HttpServletRequest request, RelateEnterprise ent, String flag) {
		Map map = new HashMap();
		try {
			String parentTenantCode = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantCode);
			//新增
			if ("I".equals(flag)) {
				RelateEnterprise r = enterpriseService.findRelateEnterpriseByPrimaryKey(ent.getEnterpriseCode());
				if (r != null) {
					map.put("result", false);
					map.put("retMsg", "编号已经存在");
					return map;
				}
			}
			enterpriseService.saveRelateEnterprise(ent);
			map.put("result", true);
			map.put("retMsg", "");
			return map;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping("/deleteRelateEnterprise")
	@ResponseBody
	public String deleteRelateEnterprise(HttpServletRequest request, String enterpriseCode) {
		try {
			String parentTenantCode = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantCode);
			enterpriseService.deleteRelateEnterprise(enterpriseCode);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
}
