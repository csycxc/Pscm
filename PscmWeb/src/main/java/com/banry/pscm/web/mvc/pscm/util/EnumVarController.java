package com.banry.pscm.web.mvc.pscm.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.EnumVarService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;

@Controller
@RequestMapping("/enumVar")
public class EnumVarController {
	
	@Autowired
	EnumVarService enumVarService;
	
	/**
	 * 依据枚举名称查询枚举
	 * @param enumName
	 * @return
	 */
	@RequestMapping("/getEnumVarList")
	@ResponseBody
	public Object getEnumVarList(HttpServletRequest request, String enumName) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<EnumVar> listGrid = enumVarService.findByEnumName(enumName);
			data.setData(listGrid);
			return data;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
}
