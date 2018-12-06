package com.banry.pscm.web.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysResource;
import com.banry.pscm.service.account.SysResourceService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionPlanService;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.Engineering;
import com.banry.pscm.service.schedule.EngineeringService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.SupplierBidItemRate;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlan;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.web.mvc.model.TreeNode;

@Controller
public class IndexController {

	@Autowired
	private EngDivisionService engDivisionService;
	@Autowired
	SysResourceService resourceService;
	@Autowired
	EngDivisionPlanService engPlanService;
	@Autowired
	private EngineeringService engineeringService;
	@Autowired
	TenderPlanService tenderPlanService;
	@Autowired
	BidSupplierService bidSupplierService;
	@Autowired
	SupplierBidItemRateService supplierBidItemRateService;
	
	/**
	 * 登录成功后(没有的划分灰色显示，不能拖拽)chenshiyu
	 * @author chenshiyu
	 * @return
	 */
	@RequestMapping("/index")
	public Object index(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("index");
		CustomUserDetails cud = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userCode = cud.getUserCode();
		String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);	//company_t5
		String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
		//是否供方用户
		String isSupplier = SessionUtil.getSessionValue(request, SessionUtil.IS_SUPPLIER);
		//租户类别
		String tenantType = SessionUtil.getSessionValue(request, SessionUtil.TENANT_TYPE);
		//配置用户
		if ("conf".equals(userCode)) {
			
			//把tenantCode放入session中。
	        DynamicDataSourceContextHolder.set(parentTenantAccount);
			Engineering engineering = null;
			try {
				engineering = engineeringService.getEngineeringByTenantCode(tenantCode);
			} catch (ScheduleException e) {
				e.printStackTrace();
			}
			String engCode = "";
			String confSource = "";
			if(engineering != null) {
				engCode = engineering.getEngCode();
				confSource = engineering.getConfSource();
			}
			SessionUtil.setSessionValue(request, SessionUtil.ENG_CODE, engCode);
			SessionUtil.setSessionValue(request, SessionUtil.MAJOR_SOURCE, confSource);
			
			mv = new ModelAndView("conf/index");
		} else {
			//供方用户
			if ("Y".equals(isSupplier)) {
				mv = new ModelAndView("bidSupplier/index");
				try {
					//供方编号
					String supplierCreditCode = SessionUtil.getSessionValue(request, SessionUtil.SUPPLIER_CREDIT_CODE);
					//招标计划编码
					String tenderPlanCode = SessionUtil.getSessionValue(request, SessionUtil.TENDER_PLAN_CODE);;
					if (tenderPlanCode != null && !"".equals(tenderPlanCode)) {
						Map<String, String> queryBidSup = new HashMap<String, String>();
						queryBidSup.put("supplierCreditCode", supplierCreditCode);
						queryBidSup.put("tenderPlanCode", tenderPlanCode);
						mv.addObject("supplierCreditCode", supplierCreditCode);
						BidSupplier bs = bidSupplierService.selectBySupplierCreditCodeAndTenderPlanCode(queryBidSup);
						if (bs != null) {
							mv.addObject("bs", bs);
							mv.addObject("supplierName", SessionUtil.getSessionValue(request, SessionUtil.SUPPLIER_NAME));
							if (bs.getBidStartDate() != null && bs.getBidEndDate() != null &&
									bs.getBidStartDate().before(new Date()) && bs.getBidEndDate().after(new Date())) {
								//查询招标计划
								Map queryMap = new HashMap();
								queryMap.put("parentTenantAccount", parentTenantAccount);
								queryMap.put("tenderPlanCode", bs.getTenderPlanCode());
								TenderPlan tp = tenderPlanService.selectByPrimaryKey(queryMap);
								mv.addObject("tp", tp);
								//招标清单
								Map<String, String> map = new HashMap<String, String>();
								map.put("tenderPlanCode", bs.getTenderPlanCode());
								map.put("supplierCreditCode", supplierCreditCode);
								List<SupplierBidItemRate> rateList = supplierBidItemRateService.getSupBidItemRateByTpCodeAndSupCode(map);
								mv.addObject("rateList", rateList);
								//公司+项目部
								mv.addObject("title", SessionUtil.getSessionValue(request, SessionUtil.COMPANY_NAME)+
										SessionUtil.getSessionValue(request, SessionUtil.PROJECT_DEPARTMENT_NAME));
								//工程
						        DynamicDataSourceContextHolder.set(parentTenantAccount);
								try {
									Engineering engineering = engineeringService.getEngineeringByTenantCode(tenantCode);
									if(engineering != null) {
										mv.addObject("engName", engineering.getEngName());
									}
								} catch (ScheduleException e) {
									e.printStackTrace();
								}
							} else {
								mv.addObject("retMsg", "当前日期不可投标");
							}
						} else {
							mv.addObject("retMsg", "没有可以投标的招标计划");
						}
					} else {
						mv.addObject("retMsg", "请单击首页招标计划（公告），单击其中一条招标计划再登陆");
					}
				} catch (TenderPlanException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				//公司级用户
				if ("1".equals(tenantType)) {
					mv = new ModelAndView("comp/index");
				} else if ("0".equals(tenantType)) {
					mv = new ModelAndView("index");
					try {
						Integer leafLevel = 0;//叶子节点的划分级别
						Integer leafUpLevel = 0;//叶子节点的上一节划分级别
						List<Integer> levelLst = engDivisionService.selectDivLevel();
						if (CollectionUtils.isNotEmpty(levelLst)) {
							leafLevel = levelLst.get(0);
							if (levelLst.size() > 1) {
								leafUpLevel = levelLst.get(1);
							}
						}
						//默认打开的叶子节点父的划分
						mv.addObject("defaultDiv", "");
						//所有的划分
						List<EngDivision> lst1 = engDivisionService.findAllEngDivision();
						//有权限的划分
						List<String> lst = cud.getEngDiv();
						//当前正在施工的划分 已经交底或者计划日期已经到达 
						String sqlWhere = "div_level=" + leafLevel 
								//未完工
								+ " and exists (select 1 from sub_div_work_bill d where a.division_sn_code = d.division_sn_code and d.raw_con_map_quan + d.cons_map_sum_vary_quan > getSumFinishByDivSnCode(a.division_sn_code)) "
								//已经交底
								+ " and (exists (select 1 from tech_disclosure b where a.division_sn_code = b.division_sn_code and b.dis_date is not null)"
								//计划日期已经到达
								+ " or exists (select 1 from eng_division_plan c where a.division_sn_code = c.division_sn_code and c.start_date <= now()))"
								//权限
								+ " and checkEngDivAuthority('" + userCode + "', a.division_sn_code) = 'Y'";
						//查询正在施工的划分的所有父划分
						List<EngDivision> engDivList = engDivisionService.selectBySqlWhere("getParentList(a.division_sn_code) division_sn_code", sqlWhere);
						//需要展开的划分
						Set<String> divSet = new LinkedHashSet<String>();
						String[] arr=null;
						for(EngDivision engdivision : engDivList){
							arr=engdivision.getDivisionSnCode().split(",");
							for (String s : arr) {
								divSet.add(s);
							}
						}
					    List<TreeNode> treeLst = new ArrayList<TreeNode>();
						for (EngDivision eng : lst1) {
							TreeNode tree = new TreeNode();
							tree.setId(eng.getDivisionSnCode());
							if (eng.getDivisionSnCode().indexOf(".") > -1) {
								tree.setpId(eng.getDivisionSnCode().substring(0, eng.getDivisionSnCode().lastIndexOf(".")));
							}
							tree.setName(eng.getDivName());
							
							//tree.setOpen(true);
							if(divSet.contains(eng.getDivisionSnCode())){
								tree.setOpen(true);
							}else{
								tree.setOpen(false);
							}
	
							tree.setDivlevel(String.valueOf(eng.getDivLevel()));
							//父划分
							String parentDiv = "";
							if (eng.getDivisionSnCode().lastIndexOf(".") > -1) {
								parentDiv = eng.getDivisionSnCode().substring(0, eng.getDivisionSnCode().lastIndexOf("."));
							}
							//划分有权限
							if (lst.contains(eng.getDivisionSnCode())
								//或者叶子节点的父划分有权限
								||(lst.contains(parentDiv) && leafLevel.intValue() == eng.getDivLevel().intValue())) {
								tree.setDraggable(true);
								//设置默认第一个有权限且正在施工的
								if (leafUpLevel.intValue() == eng.getDivLevel().intValue() && tree.isOpen()) {
									if ("".equals(mv.getModelMap().get("defaultDiv"))) {
										mv.addObject("defaultDiv", eng.getDivisionSnCode());
										mv.addObject("divlevel", leafUpLevel);
									}
								}
							} else {
								tree.setDraggable(false);
							}
							//叶子节点或者叶子节点的上一级节点
							if (leafLevel.intValue() == eng.getDivLevel().intValue() || leafUpLevel.intValue() == eng.getDivLevel().intValue()) {
								tree.setTarget("mainframe");
								tree.setUrl("gantt?divisionsncode=" + eng.getDivisionSnCode() + "&divlevel=" + eng.getDivLevel().intValue());
								//tree.setDraggable(true);
								if (leafLevel == eng.getDivLevel()) {
									tree.setIsLeaf("Y");
								} else {
									tree.setIsLeaf("N");
								}
							} else {
								tree.setUrl("");
								//tree.setDraggable(false);
								tree.setIsLeaf("N");
							}
							treeLst.add(tree);
						}
						
	//					String jsonString = JSON.toJSONString(treeLst);
						mv.addObject("treeNode", treeLst);
						DynamicDataSourceContextHolder.set(parentTenantAccount);
						//菜单tree
						List<TreeNode> menuLst = new ArrayList<TreeNode>();
						List<SysResource> resLst = resourceService.findSysResourceByUserCode(userCode, "-1", "1", tenantCode);
						for (SysResource res : resLst) {
							TreeNode tree = new TreeNode();
							tree.setId(res.getResourceCode());
							tree.setpId(res.getParentResourceCode());
							tree.setName(res.getResourceName());
							tree.setOpen(false);
							tree.setTarget("");
							tree.setUrl("");
							tree.setDataUrl(res.getResourceUrl());
							tree.setDraggable(false);
							menuLst.add(tree);
						}
						mv.addObject("menuNode", menuLst);
						
					} catch (ScheduleException e) {
						e.printStackTrace();
					} catch (AccountException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mv;
	}
	
	@RequestMapping("/menu")
	public Object menu(HttpServletRequest request, String prCode) {
		ModelAndView mv = new ModelAndView("menu");
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//菜单tree
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			List<TreeNode> menuLst = new ArrayList<TreeNode>();
			List<SysResource> resLst = resourceService.findSysResourceByUserCode(userCode, prCode, "1", tenantCode);
			for (SysResource res : resLst) {
				TreeNode tree = new TreeNode();
				tree.setId(res.getResourceCode());
				tree.setpId(res.getParentResourceCode());
				tree.setName(res.getResourceName());
				tree.setOpen(false);
				tree.setUrl(res.getResourceUrl());
				tree.setDraggable(false);
				tree.setTarget("mainframe" + prCode);
				menuLst.add(tree);
			}
//			String jsonString1 = JSON.toJSONString(menuLst);
			mv.addObject("menuNode", menuLst);
			mv.addObject("prCode", prCode);
			//默认打开的页面
			if (resLst.size() > 0) {
				SysResource res = resLst.get(0);
				mv.addObject("defmenuUrl", res.getResourceUrl());
			} else {
				mv.addObject("defmenuUrl", "");
			}
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/gantt")
	public Object gantt(String divisionsncode, String divlevel) {
		ModelAndView mv = new ModelAndView("gantt");
		try {
			//叶子节点的划分级别
			Integer leafLevel = 0;
			//叶子节点的上一节划分级别
			Integer leafUpLevel = 0;
			List<Integer> levelLst = engDivisionService.selectDivLevel();
			if (CollectionUtils.isNotEmpty(levelLst) && levelLst.size() > 1) {
				leafUpLevel = levelLst.get(1);
			}
			if (divisionsncode != null && !"".equals(divisionsncode)) {
				if (String.valueOf(leafUpLevel).equals(divlevel)) {
					//分项工程编码
					mv.addObject("itemcode", divisionsncode);
					//工序编码
					mv.addObject("workcode", "");
				} else {
					//工序
					EngDivision div = engDivisionService.findEngDivisionByKey(divisionsncode);
					//分项工程编码
					mv.addObject("itemcode", div.getDivisionSnCode().substring(0, div.getDivisionSnCode().lastIndexOf(".")));
					//工序编码
					mv.addObject("workcode", divisionsncode);
				}
				//操作权限
				mv.addObject("readOnly", checkEngDivAuthority(divisionsncode));
			} else {
				//默认取第一个分项工程
				String sqlWhere = "a.div_level=" + leafUpLevel;
				List<EngDivision> divList = engDivisionService.selectBySqlWhere("a.*", sqlWhere);
				if (divList.size() > 0) {
					EngDivision div = divList.get(0);
					//分项工程编码
					mv.addObject("itemcode", div.getDivisionSnCode());
					//工序编码
					mv.addObject("workcode", "");
					//操作权限
					mv.addObject("readOnly", checkEngDivAuthority(div.getDivisionSnCode()));
				}
			}
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/to")
	public Object tourl(String url) {
		System.out.println("欢迎您url"+url);
		ModelAndView mv = new ModelAndView(url);
		return mv;
	}
	
	/**
	 * 校验当前划分是否有操作权限
	 * @author Xu Dingkui
	 * @date 2017年11月29日
	 * @param divisionsncode
	 * @return
	 */
	public boolean checkEngDivAuthority(String divisionsncode) {
		try {
			Integer leafLevel = 0;//叶子节点的划分级别
			List<Integer> levelLst = engDivisionService.selectDivLevel();
			if (levelLst.size() > 0) {
				for (int i = 0; i < levelLst.size(); i++) {
					if (i == 0) {
						leafLevel = levelLst.get(i);
						break;
					}
				}
			}
			EngDivision engDivision = engDivisionService.findEngDivisionByKey(divisionsncode);
			List<String> engDiv = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEngDiv();
			//如果是叶子节点
			if (leafLevel.intValue() == engDivision.getDivLevel().intValue()) {
				//父划分
				String parentDiv = "";
				if (divisionsncode.lastIndexOf(".") > -1) {
					parentDiv = divisionsncode.substring(0, divisionsncode.lastIndexOf("."));
					return !engDiv.contains(parentDiv);
				}
			} else {
				return !engDiv.contains(divisionsncode);
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
	}
	
	@RequestMapping("/login")
	public Object login(String account, String tenderPlanCode, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/login");
		if (account != null) {
			mv.addObject("account", account);
			mv.addObject("tenderPlanCode", tenderPlanCode);
		} else {
			if (!"".equals(SessionUtil.getSessionValue(request, SessionUtil.TENDER_PLAN_CODE))) {
				mv.addObject("account", SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_ACCOUNT));
				mv.addObject("tenderPlanCode", SessionUtil.getSessionValue(request, SessionUtil.TENDER_PLAN_CODE));
			}
		}
		return mv;
	}
}
