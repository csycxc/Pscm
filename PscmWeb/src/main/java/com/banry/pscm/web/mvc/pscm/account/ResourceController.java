package com.banry.pscm.web.mvc.pscm.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysResource;
import com.banry.pscm.service.account.SysResourceService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.model.TreeNode;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	@Autowired
	SysResourceService resourceService;
	@Autowired
	EngDivisionService engDivisionService;
	
	@RequestMapping("/roleresource")
	@ResponseBody
	public Object roleresource() {
		ModelAndView mv = new ModelAndView("account/roleresource");
		DataTableModel data = new DataTableModel();
		try {
			List<TreeNode> menuLst1 = new ArrayList<TreeNode>();//系统模块
			
			List<TreeNode> menuLst2 = new ArrayList<TreeNode>();//工程划分
			
			List<TreeNode> menuLst3 = new ArrayList<TreeNode>();//操作模块
			
			List<EngDivision> engList = engDivisionService.findAllEngDivision();//查询所有划分
			
			DynamicDataSourceContextHolder.set("DEFAULT");
			List<SysResource> resLst = resourceService.findAll();//查询所有模块
			
			for (SysResource res : resLst) {
				if(res.getResourceCode().startsWith("1")) {
					//系统模块
					TreeNode tree = new TreeNode();
					tree.setId(res.getResourceCode());
					tree.setpId(res.getParentResourceCode());
					tree.setName(res.getResourceName());
					tree.setOpen(true);
					tree.setDraggable(false);
					menuLst1.add(tree);
				}else {
					//操作 模块
					TreeNode tree = new TreeNode();
					tree.setId(res.getResourceCode());
					tree.setpId(res.getParentResourceCode());
					tree.setName(res.getResourceName());
					tree.setOpen(true);
					tree.setDraggable(false);
					menuLst3.add(tree);
				}
			}
			for (EngDivision engDivision : engList) {
				if(engDivision.getDivLevel().intValue() < 7) {        //level数据库里的类型为char(2) 类里为String
					//工程划分
					TreeNode tree = new TreeNode();
					tree.setId(engDivision.getDivisionSnCode());
					if (engDivision.getDivisionSnCode().indexOf(".") > -1) {//有.
						tree.setpId(engDivision.getDivisionSnCode().substring(0, engDivision.getDivisionSnCode().lastIndexOf(".")));
					}
					tree.setName(engDivision.getDivName());
					tree.setOpen(true);
					tree.setDraggable(false);
					//tree.setDivlevel(engDivision.getDivlevel());
					menuLst2.add(tree);
				}
				
			}
			
			//系统模块
//			String jsonString1 = JSON.toJSONString(menuLst1);
			mv.addObject("menuNode1", menuLst1);
			
			//划分
//			String jsonString2 = JSON.toJSONString(menuLst2);
			mv.addObject("menuNode2", menuLst2);
			
			//操作模块
//			String jsonString3 = JSON.toJSONString(menuLst3);
			mv.addObject("menuNode3", menuLst3);
			
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ScheduleException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
}
