package com.banry.pscm.web.mvc.pscm.account;

import java.util.Date;
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
import com.banry.pscm.service.account.SysDepts;
import com.banry.pscm.service.account.SysDeptsKey;
import com.banry.pscm.service.account.SysDeptsService;
import com.banry.pscm.web.mvc.model.DataTableModel;

@Controller
@RequestMapping("/dept")
public class DeptController {
	
	@Autowired
	SysDeptsService deptService;
	@Autowired
	RelateEnterpriseService entService;
	
	@RequestMapping("/getDeptList")
	@ResponseBody
	public Object getDeptList(HttpServletRequest request) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<SysDepts> listGrid = deptService.findAllDept(tenantCode);
			data.setData(listGrid);
			return data;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	@RequestMapping("/edit")
	public Object edit(HttpServletRequest request, String deptCode) {
		ModelAndView mv = new ModelAndView("account/dept-edit");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
//			List tenantList = (List) request.getSession().getAttribute("TENANT_LIST");
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//编辑时获取角色信息
			if (deptCode != null && !"".equals(deptCode)) {
				SysDeptsKey key = new SysDeptsKey(deptCode, tenantCode);
				SysDepts d = deptService.getByDeptCode(key);
				mv.addObject("d", d);
				mv.addObject("flag", "U");
			} else {
				mv.addObject("flag", "I");
			}
			//获取部门信息
			List<RelateEnterprise> reLst = entService.findRelateEnterpriseList();
			mv.addObject("reLst", reLst);
//			mv.addObject("tenantList", tenantList);
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveDept")
	@ResponseBody
	public Map saveDept(HttpServletRequest request, SysDepts dept, String flag) {
		Map map = new HashMap();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			dept.setTenantCode(tenantCode);
			//新增
			if ("I".equals(flag)) {
				SysDepts r = deptService.getByDeptCode(dept);
				if (r != null) {
					map.put("result", false);
					map.put("retMsg", "编号已经存在");
					return map;
				}
				dept.setDeptOrganizeDate(new Date());
			}
			deptService.saveDept(dept);
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
	
	@RequestMapping("/deleteDept")
	@ResponseBody
	public String deleteDept(HttpServletRequest request, String deptCode) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			SysDeptsKey key = new SysDeptsKey(deptCode, tenantCode);
			deptService.deleteDept(key);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
}
