package com.banry.pscm.web.mvc.pscm.account;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysDepts;
import com.banry.pscm.service.account.SysDeptsService;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.account.SysUsersKey;
import com.banry.pscm.service.account.SysUsersService;
import com.banry.pscm.web.mvc.model.DataTableModel;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	SysUsersService userService;
	@Autowired
	SysDeptsService deptService;
	
	/**
	 * 重置密码
	 * @param userCode 用户编号
	 * @return
	 */
	@RequestMapping("/reNewPwd")
	@ResponseBody
	public boolean reNewPwd(HttpServletRequest request, String userCode){
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			SysUsersKey key = new SysUsersKey(userCode, tenantCode);
			SysUsers user = userService.getByCode(key);
			if(user!=null){
				BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
//				String MdNewPwd=EncoderByMd5("123");
				user.setUserPassword(encode.encode("123"));
				userService.saveUser(user);
				return true;
			}else{
				return false;
			}
		} catch (AccountException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * 
	 * @param userCode 登录名
	 * @param oldPwd  原密码
	 * @param newPwd 修改的新密码
	 * @return  Boolean
	 * @throws Exception
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public boolean updatePwd(HttpServletRequest request, String userCode, String oldPwd, String newPwd) throws Exception{
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			SysUsersKey key = new SysUsersKey(userCode, tenantCode);
			SysUsers user = userService.getByCode(key);
			String Pwd = user.getUserPassword();
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			//oldPwd 加密去对比数据库中的Pwd是否一致
			if(encode.encode(oldPwd).equals(Pwd)){
				String MdNewPwd=encode.encode(newPwd);
				user.setUserPassword(MdNewPwd);
				userService.saveUser(user);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 对加密前和加密后的字符串进行比较
	 * @param oldPwd 用户输入的原密码，已加密 
	 * @param Pwd  数据库中的密码
	 * @return  boolean
	 */
	@RequestMapping("/checkPwd")
	@ResponseBody
	public boolean checkPwd(HttpServletRequest request, String userCode, String oldPwd){
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			SysUsersKey key = new SysUsersKey(userCode, tenantCode);
			SysUsers user = userService.getByCode(key);
			String Pwd = user.getUserPassword();
			BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
			//oldPwd 加密去对比数据库中的Pwd是否一致
			if(encode.encode(oldPwd).equals(Pwd)){
				return true;
			}else{
				return false;
			}
		} catch (AccountException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 利用MD5对字符串进行加密
	 * @param str  加密前
	 * @return newStr  加密后
	 * @throws Exception
	 */
	public String EncoderByMd5(String str){
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			//计算md5函数
			md5.update(str.getBytes());
			String newStr=new BigInteger(1, md5.digest()).toString(16);
			return newStr;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@RequestMapping("/getUserList")
	@ResponseBody
	public Object getUserList(HttpServletRequest request) {
		DataTableModel data = new DataTableModel();
		try {
			//父租户
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<SysUsers> listGrid = userService.findAllUser(tenantCode);
			data.setData(listGrid);
			return data;
		} catch (AccountException e) {
			e.printStackTrace();
			return data;
		}
	}
	
	@RequestMapping("/edit")
	public Object edit(HttpServletRequest request, String userCode) {
		ModelAndView mv = new ModelAndView("account/user-edit");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
//			List tenantList = (List) request.getSession().getAttribute("TENANT_LIST");
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//编辑时获取角色信息
			if (userCode != null && !"".equals(userCode)) {
				SysUsersKey key = new SysUsersKey(userCode, tenantCode);
				SysUsers u = userService.getByCode(key);
				mv.addObject("u", u);
				mv.addObject("flag", "U");
			} else {
				mv.addObject("flag", "I");
			}
//			mv.addObject("tenantList", tenantList);
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
	@RequestMapping("/saveUser")
	@ResponseBody
	public Map saveUser(HttpServletRequest request, SysUsers user, String flag) {
		Map map = new HashMap();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			user.setTenantCode(tenantCode);
			//新增
			if ("I".equals(flag)) {
				SysUsers u = userService.getByCode(user);
				if (u != null) {
					map.put("result", false);
					map.put("retMsg", "编号已经存在");
					return map;
				}
				user.setCreateDate(new Date());
				user.setStatus(1);
				String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
				user.setCreateUser(userCode);
			}
			userService.saveUser(user);
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
	
	@RequestMapping("/deleteUser")
	@ResponseBody
	public String deleteUser(HttpServletRequest request, String userCode) {
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			SysUsersKey key = new SysUsersKey(userCode, tenantCode);
			userService.deleteUser(key);
			return "{success:true}";
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	

	
}
