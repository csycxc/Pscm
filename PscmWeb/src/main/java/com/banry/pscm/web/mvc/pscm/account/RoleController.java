package com.banry.pscm.web.mvc.pscm.account;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.Role;
import com.banry.pscm.service.account.RoleKey;
import com.banry.pscm.service.account.SysDepts;
import com.banry.pscm.service.account.SysDeptsService;
import com.banry.pscm.service.account.SysResource;
import com.banry.pscm.service.account.SysRolesService;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.RoleDivisionService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.web.mvc.model.DataTableModel;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	SysRolesService roleService;
	@Autowired
	SysDeptsService deptService;
	@Autowired
	EngDivisionService engDivisionService;
	@Autowired
	RoleDivisionService roleDivisionService;
	
	@RequestMapping("/getRoleList")
	@ResponseBody
	public Object getRoleList(HttpServletRequest request) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<Role> listGrid = roleService.findAllRole("tenant_code='" + tenantCode + "'");
			data.setData(listGrid);
			return data;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	@RequestMapping("/edit")
	public Object edit(HttpServletRequest request, String roleCode) {
		ModelAndView mv = new ModelAndView("account/role-edit");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//编辑时获取角色信息
			if (roleCode != null && !"".equals(roleCode)) {
				RoleKey key = new RoleKey(roleCode, tenantCode);
				Role r = roleService.getById(key);
				mv.addObject("r", r);
				mv.addObject("flag", "U");
			} else {
				mv.addObject("flag", "I");
			}
			//获取部门信息
			List<SysDepts> deptLst = deptService.findAllDept(tenantCode);
			mv.addObject("deptLst", deptLst);
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveRole")
	@ResponseBody
	public Map saveRole(HttpServletRequest request, Role role, String flag) {
		Map map = new HashMap();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			role.setTenantCode(tenantCode);
			//新增
			if ("I".equals(flag)) {
				Role r = roleService.getById(role);
				if (r != null) {
					map.put("result", false);
					map.put("retMsg", "编号已经存在");
					return map;
				}
				role.setCreateDate(new Date());
				role.setStatus(1);
				String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
				role.setCreateUser(userCode);
			}
			roleService.saveRole(role);
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
	
	@RequestMapping("/deleteRole")
	@ResponseBody
	public String deleteRole(HttpServletRequest request, String roleCode) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			RoleKey key = new RoleKey(roleCode, tenantCode);
			roleService.deleteRole(key);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
	@RequestMapping("/saveUsersRoles")
	@ResponseBody
	public String saveUsersRoles(HttpServletRequest request, String roleCode, String ids) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			roleService.saveUsersRoles(roleCode, ids, userCode, tenantCode);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
	@RequestMapping("/delUserInRolesList")
	@ResponseBody
	public String delUserInRolesList(HttpServletRequest request, String roleCode, String ids) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			roleService.delUserInRolesList(roleCode, tenantCode, ids);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
	@RequestMapping("/findUsersByRole")
	@ResponseBody
	public Object findUsersByRole(HttpServletRequest request, String roleCode) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<SysUsers> listGrid = roleService.findUsersByRole(roleCode, tenantCode);
			data.setData(listGrid);
			return data;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	@RequestMapping("/findWaitUsersByRole")
	@ResponseBody
	public Object findWaitUsersByRole(HttpServletRequest request, String roleCode) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<SysUsers> listGrid = roleService.findWaitUsersByRole(roleCode, tenantCode);
			data.setData(listGrid);
			return data;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	@RequestMapping("/saveRoleRes")
	@ResponseBody
	public String saveRoleRes(HttpServletRequest request, String roleCode, String ids) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			roleService.saveRoleRes(roleCode, ids, userCode, tenantCode);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
	@RequestMapping("/findRoleRes")
	@ResponseBody
	public List<SysResource> findRoleRes(HttpServletRequest request, String roleCode) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<SysResource> resList = roleService.findRoleRes(roleCode, tenantCode);
			return resList;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据角色查询划分
	 * @author chenshiyu
	 * @param roleCode
	 * @return
	 * @throws ScheduleException 
	 */
	@RequestMapping("/findRoleResAndEngs")
	@ResponseBody
	public Map findRoleResAndEngs(HttpServletRequest request, String roleCode){
		try {
			Map map = new HashMap();
			List<Object> list1 = new ArrayList<Object>();
			List<Object> list2 = new ArrayList<Object>();
			Integer leafLevel = 0;//叶子节点的划分级别
			List<Integer> levelLst = engDivisionService.selectDivLevel();
			if (levelLst.size() > 0) {
				for (int i = 0; i < levelLst.size(); i++) {
					if (i == 0) {
						leafLevel = levelLst.get(i);
					} else {
						break;
					}
				}
			}
			//查询出勾选划分
			List<EngDivision> engList = roleDivisionService.findRoleDivisions(roleCode);
			//过滤掉工序
			if(engList.size()>0) {
				for (EngDivision ed : engList) {
					if(ed.getDivLevel()==null) {
						continue;
					}else {
						if(ed.getDivLevel().intValue() < leafLevel.intValue()) {
							list2.add(ed);
						}
					}
				}
			}
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//查询出勾选模块
			List<SysResource> resList = roleService.findRoleRes(roleCode, tenantCode);
			if(resList.size()>0) {
				for (SysResource sysResource : resList) {
					list1.add(sysResource);
				}
			}
			map.put("div", list2);
			map.put("res", list1);
			return map;
		} catch (AccountException e) {
			e.printStackTrace();
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存 模块和划分        ids2是划分
	 * @author chenshiyu
	 */
	@RequestMapping("/saveRoleResAndEngs")
	@ResponseBody
	public String saveRoleResAndEngs(HttpServletRequest request, String roleCode, String ids1,String ids2) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			if(ids2 != null && !"".equals(ids2)) {
				roleDivisionService.saveRoleDivisions(roleCode, ids2, tenantCode);
			}else {
				roleDivisionService.deleteRoleDivisions(roleCode, tenantCode);
			}
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			if(ids1 != null && !"".equals(ids1)) {
				roleService.saveRoleRes(roleCode, ids1, userCode, tenantCode);
			}else {
				roleService.deleteRoleRes(roleCode, tenantCode);
			}
			return "{success:true}";
		} catch (AccountException e) {
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	/**
	 * 删除模块和划分
	 * @author chenshiyu
	 */
	@RequestMapping("/delRoleResAndEngs")
	@ResponseBody
	public String delRoleResAndEngs(HttpServletRequest request, String roleCode) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			roleDivisionService.deleteRoleDivisions(roleCode, tenantCode);
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			roleService.deleteRoleRes(roleCode, tenantCode);
			return "{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
}
