package com.banry.pscm.web.mvc.pscm.schedule;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.Role;
import com.banry.pscm.service.account.SysRolesService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.RoleDivisionService;
import com.banry.pscm.web.mvc.model.DataTableModel;
/**
 * 角色划分中间表controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/roledivision")
public class RoleDivisionController {
	
	@Autowired
	RoleDivisionService roleDivisionService;
	@Autowired
	EngDivisionService engDivisionService;
	@Autowired
	SysRolesService rolesService;
	
	@RequestMapping("/getRoleList")
	@ResponseBody
	public Object getRoleList() {
		DataTableModel data = new DataTableModel();
		try {
			List<Role> listGrid = rolesService.findAllRole("");
			data.setData(listGrid);
			return data;
		} catch (AccountException e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * 获取角色拥有的 划分模块
	 * url : '$ctx/division/findEngDivision', sys_resource---->eng_division 
	 * */
	@RequestMapping("/findRoleDivisions")
	@ResponseBody
	public List<EngDivision> findRoleDivisions(String roleCode) {
		try {
			//查询当前角色拥有的划分
			List<EngDivision> engDivisionList = roleDivisionService.findRoleDivisions(roleCode);
			return engDivisionList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存角色对应的划分
	 * url : '$ctx/division/saveEngDivisions',
	 */
	@RequestMapping("/saveRoleDivisions")
	@ResponseBody
	public String saveRoleDivisions(HttpServletRequest request, String roleCode, String ids) {  //ids是划分的id集合
		try {
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			roleDivisionService.saveRoleDivisions(roleCode, ids, tenantCode);
			return "{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
	/**
	 * 当全不选时，删除该角色的所有划分
	 */
	@RequestMapping("/deleteRoleDivisions")
	@ResponseBody
	public String deleteRoleDivisions(HttpServletRequest request, String roleCode) {
		try {
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			int i = roleDivisionService.deleteRoleDivisions(roleCode, tenantCode);
			//System.out.println(i+"======================================");
			return "{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	

}
