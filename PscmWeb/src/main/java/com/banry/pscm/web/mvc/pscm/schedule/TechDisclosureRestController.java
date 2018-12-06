package com.banry.pscm.web.mvc.pscm.schedule;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.account.SysUsersKey;
import com.banry.pscm.service.account.SysUsersService;
import com.banry.pscm.service.schedule.*;
/*import com.banry.pscm.service.schedule.EngDivFinishRate;
import com.banry.pscm.service.schedule.EngDivFinishRateService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionModel;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.Engineering;
import com.banry.pscm.service.schedule.EngineeringService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.schedule.TechDisclosure;
import com.banry.pscm.service.schedule.TechDisclosureService;*/
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.TechDisclosureModel;
import com.banry.pscm.web.mvc.model.TreeNode;
import com.banry.pscm.web.utils.DateUtils;
import com.banry.pscm.web.utils.DivLevelUtil;
import com.banry.pscm.web.utils.SystemConstants;
import com.banry.pscm.web.utils.TreeUtil;

@RestController
public class TechDisclosureRestController {
	
    @Autowired
	private ScheduleLaborService laborService;
	@Autowired
	private TechDisclosureService techDisclosureService;
	@Autowired
	private ContractAttService contractAttService;
	@Autowired
	private EngDivisionService engDivisionService;
	@Autowired
	private EngDivFinishRateService engDivFinishRateService;
	@Autowired
	private SysUsersService sysUsersService;
	@Autowired
	private EngineeringService engineeringService;
	@Autowired
	private SystemConstants constants;
	
	/**
	 * 获取工人待接受或已经接受交底的数据
	 * @author Xu Dingkui
	 * @date 2017年12月17日
	 * @param idcard
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/wokerDiscloseList")
	public Map wokerDiscloseList(String idcard) {
		//查询工人信息
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			Map labormap = new HashMap();
			ScheduleLabor labor = laborService.findLaborByUserCode(idcard);
			if (labor == null) {
				map.put("status", 1);
				map.put("message", "没有找到该用户的劳务公司");
				return map;
			}
			Engineering eng = engineeringService.getEngineeringByTenantCode("1000");
			labormap.put("projectDept", eng.getEngFullName());
			labormap.put("laborCompany", labor.getLaborcompany());
			labormap.put("laborWorkType", labor.getLaborpost());
			labormap.put("laborerName", labor.getLabor());
			//待接受交底
			List<TechDisclosure> disclosureLst = techDisclosureService.findTechDisclosureByDisclo(idcard);
			List<TechDisclosureModel> discloseData = new ArrayList<TechDisclosureModel>();
			for (TechDisclosure td : disclosureLst) {
				TechDisclosureModel m = new TechDisclosureModel();
				//交底编码
				m.setDisclosurecode(td.getDisclosureCode());
				m.setDisdivsncode(td.getDivisionSnCode().getDivisionSnCode());
				//查找工程项目划分
				EngDivision ed = td.getDivisionSnCode();
				if (ed != null) {
					m.setDivname(ed.getDivName());
				}
				m.setDisclosurestatus("1");
				m.setDivlevel(DivLevelUtil.getLevelValue(ed.getDivLevel()));
				discloseData.add(m);
			}
			//已经接受交底
			disclosureLst = techDisclosureService.findHisTechDisclosureByDisclo(idcard);
			for (TechDisclosure td : disclosureLst) {
				TechDisclosureModel m = new TechDisclosureModel();
				//交底编码
				m.setDisclosurecode(td.getDisclosureCode());
				m.setDisdivsncode(td.getDivisionSnCode().getDivisionSnCode());
				//查找工程项目划分
				EngDivision ed = td.getDivisionSnCode();
				if (ed != null) {
					m.setDivname(ed.getDivName());
				}
				m.setDisclosurestatus("2");
				m.setDivlevel(DivLevelUtil.getLevelValue(ed.getDivLevel()));
				discloseData.add(m);
			}
			labormap.put("discloseData", discloseData);
			map.put("data", labormap);
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 获取工人接受交底的数据
	 * @author Xu Dingkui
	 * @date 2017年7月16日
	 * @param idcard
	 * @param DivisionSnCode 划分序号编码
	 * @param Disclosurecode 技术交底编码
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/discloseContent")
	public Map discloseContent(String idcard, String DivisionSnCode, String Disclosurecode, String tenantCode) {
		//查询工人信息
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			Map labormap = new HashMap();
			ScheduleLabor labor = new ScheduleLabor();
			labor.setIdcard(idcard);
			labor.setDivisionsncode(DivisionSnCode);
			labor = laborService.findLaborByPrimaryKey(labor);
			if (labor == null) {
				map.put("status", 1);
				map.put("message", "没有找到该用户的劳务公司");
				return map;
			}
			Engineering eng = engineeringService.getEngineeringByTenantCode("1000");
			labormap.put("projectDept", eng.getEngFullName());
			labormap.put("laborCompany", labor.getLaborcompany());
			labormap.put("laborWorkType", labor.getLaborpost());
			labormap.put("laborerName", labor.getLabor());
//			TechDisclosure td = techDisclosureService.findTechDisclosureByDisDivSnCode(DivisionSnCode);
			TechDisclosure td = techDisclosureService.findTechDisclosureByPrimaryKey(Disclosurecode);
			TechDisclosureModel m = new TechDisclosureModel();
			if (td != null) {
				m.setDisclosurecode(td.getDisclosureCode());
				m.setDisdivsncode(td.getDivisionSnCode().getDivisionSnCode());
				m.setDivname(td.getDivisionSnCode().getDivName());
				m.setDisrole(td.getDisRole());
				SysUsersKey key = new SysUsersKey(td.getDisclosurer(), tenantCode);
				m.setDisclosurer(sysUsersService.getByCode(key).getUserName());
				if (td.getDisDate() != null) {
					m.setDisdate(DateUtils.format(td.getDisDate(), "yyyy-MM-dd"));
				}
				m.setDisclocontent(td.getDisContent());
				m.setDiscloinclude(td.getDisInclude());
				if (labor.getAcceptTime() != null) {
					m.setAccepttime(DateUtils.format(labor.getAcceptTime(), "yyyy-MM-dd"));
				} else {
					m.setAccepttime("");
				}
				List<ContractAtt> attachLst = contractAttService.findByFileInNames(td.getDisAttach());
				ArrayList arr = new ArrayList();
				for (ContractAtt att :attachLst) {
					arr.add(att.getLocation());
				}
				m.setAttachment(arr);
			} else {
				map.put("status", 1);
				map.put("message", "没有找到交底数据");
				return map;
			}
			labormap.put("discloseData", m);
			map.put("data", labormap);
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 工人对交底进行确认
	 * @author Xu Dingkui
	 * @date 2017年7月17日
	 * @param idcard
	 * @param Disclosurecode 技术交底编码
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/confirmDisclose")
	public Map confirmDisclose(String DivisionSnCode, String idcard, String Disclosurecode) {
		Map map = new HashMap();
		try {
//			TechDisclosure td = techDisclosureService.findTechDisclosureByDisDivSnCodeDisclo(DivisionSnCode, idcard);
			TechDisclosure td = techDisclosureService.findTechDisclosureByPrimaryKey(Disclosurecode);
			if (td != null) {
				if (td.getDisDate() == null) {
					map.put("status", 1);
					map.put("message", "改交底还未提交");
					return map;
				}
				if (td.getDisRecipient() != null && !"".equals(td.getDisRecipient())) {
					if (td.getDisRecipient().indexOf(idcard) > -1) {
						map.put("status", 1);
						map.put("message", "已经交底确认");
						return map;
					}
					td.setDisRecipient(td.getDisRecipient() + "," + idcard);
				} else {
					td.setDisRecipient(idcard);
				}
				techDisclosureService.saveTechDisclosure(td);
				ScheduleLabor labor = new ScheduleLabor();
				labor.setIdcard(idcard);
				labor.setDivisionsncode(DivisionSnCode);
				labor = laborService.findLaborByPrimaryKey(labor);
				labor.setAcceptTime(new Date());
				laborService.updateByPrimaryKey(labor);
				map.put("status", 0);
				map.put("message", "成功");
				return map;
			} else {
				map.put("status", 1);
				map.put("message", "没有找到待交底数据");
				return map;
			}
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 获取划分Tree
	 * @author Xu Dingkui
	 * @date 2017年12月27日
	 * @param userid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/divTree")
	public Map divTree(String userid) {
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			//叶子节点的划分级别
			Integer leafLevel = 0;
			//叶子节点的上一节划分级别
			Integer leafUpLevel = 0;
			List<Integer> levelLst = engDivisionService.selectDivLevel();
			if (levelLst.size() > 0) {
				for (int i = 0; i < levelLst.size(); i++) {
					if (i == 0) {
						leafLevel = levelLst.get(i);
					} else if (i == 1) {
						leafUpLevel = levelLst.get(i);
					} else {
						break;
					}
				}
			}
			//查询有权限的叶子节点的父节点
			StringBuffer sqlWhere = new StringBuffer("div_level=" + leafUpLevel);
			//权限
			sqlWhere.append(" 	and exists ( select 1 from role_division c, role_user b ");
			sqlWhere.append(" 	where c.Status=1 and c.RoleCode = b.RoleCode and b.UserCode='" + userid + "'");
			sqlWhere.append(" 	and a.division_sn_code = c.DivCode )");
			//
			sqlWhere.append(" 	and ( exists ( ");
			sqlWhere.append(" 	select a1.division_sn_code ");
			sqlWhere.append(" 	from eng_division a1, tech_disclosure b1 ");
			sqlWhere.append(" 	where a1.div_level = " + leafLevel + " and a1.ConsSchemeNumber > getSumFinishByDivSnCode(a1.division_sn_code) ");
			sqlWhere.append(" 	and  a1.division_sn_code = b1.division_sn_code and b1.dis_date is not null");
			sqlWhere.append("  	and a.division_sn_code = getParentDivSnCode(a1.division_sn_code) ) ");
			sqlWhere.append(" 	or exists ( ");
			sqlWhere.append(" 	select a1.division_sn_code ");
			sqlWhere.append(" 	from eng_division a1, eng_division_plan b ");
			sqlWhere.append(" 	where a1.div_level =  " + leafLevel + " and a1.ConsSchemeNumber > getSumFinishByDivSnCode(a1.DivisionSnCode) ");
			sqlWhere.append(" 	and not exists (select 1 from tech_disclosure b1 where a1.division_sn_code = b1.division_sn_code and b1.dis_date is not null) ");
			sqlWhere.append(" 	and a1.division_sn_code = b.division_sn_code and b.start_date < now() ");
			sqlWhere.append(" 	and a.division_sn_code = getParentDivSnCode(a1.division_sn_code) ");
			sqlWhere.append(" 	) or exists ( ");
			sqlWhere.append(" 	select a1.division_sn_code ");
			sqlWhere.append(" 	from eng_division a1, eng_division_plan b ");
			sqlWhere.append(" 	where a1.div_level =  " + leafLevel );
			sqlWhere.append(" 	and not exists (select 1 from tech_disclosure b1 where a1.division_sn_code = b1.division_sn_code and b1.dis_date is not null) ");
			sqlWhere.append(" 	and a1.division_sn_code = b.division_sn_code ");
			sqlWhere.append(" 	and b.start_date > DATE_FORMAT(now(),'%Y-%m-%d') and b.start_date <= date_add(DATE_FORMAT(now(),'%Y-%m-%d'),interval 30 day) ");
			sqlWhere.append(" 	and a.division_sn_code = getParentDivSnCode(a1.division_sn_code) ) ) ");
			List<EngDivision> engDivList = engDivisionService.selectBySqlWhere("getParentList(a.division_sn_code) division_sn_code", sqlWhere.toString());
			StringBuffer div = new StringBuffer("");
			for (EngDivision eng : engDivList) {
				if ("".equals(div.toString())) {
					div.append(eng.getDivisionSnCode());
				} else {
					div.append("," + eng.getDivisionSnCode());
				}
			}
			sqlWhere = new StringBuffer(" FIND_IN_SET(division_sn_code, '" + div + "')");
			engDivList = engDivisionService.selectBySqlWhere("a.division_sn_code, a.div_name, a.div_item_code, a.div_level", sqlWhere.toString());
//			List<EngDivision> engDivList = engDivisionService.selectBySql(sql.toString());
			List<TreeNode> treeLst = new ArrayList<TreeNode>();
//			Set set = new HashSet();
//			for (EngDivision eng : engDivList) {
//				TreeNode tree = new TreeNode();
//				tree.setId(eng.getDivisionsncode());
//				if (eng.getDivisionsncode().indexOf(".") > -1) {
//					tree.setpId(eng.getDivisionsncode().substring(0, eng.getDivisionsncode().lastIndexOf(".")));
//					//父项set
//					set.add(tree.getpId());
//				} else {
//					tree.setpId("");
//				}
//				tree.setName(eng.getDivname());
//				tree.setStatus(eng.getDivitemcode());
//				treeLst.add(tree);
//			}
//			StringBuffer div = new StringBuffer("");
//			//查询父项的所有划分
//			for(Iterator it = set.iterator();  it.hasNext();) {             
////				System.out.println("value="+it.next().toString()); 
//				List<EngDivision> pdiv = engDivisionService.selectBySql("select getParentList('" + it.next().toString() + "') DivisionSnCode");
//				if (pdiv.size() > 0) {
//					if ("".equals(div.toString())) {
//						div.append(pdiv.get(0).getDivisionsncode());
//					} else {
//						div.append("," + pdiv.get(0).getDivisionsncode());
//					}
//				}
//	        }
//			StringBuffer sql1 = new StringBuffer("SELECT a.DivisionSnCode, a.DivName FROM eng_division a WHERE FIND_IN_SET(DivisionSnCode, '" + div + "') order by DivisionSnCode");
//			engDivList = engDivisionService.selectBySql(sql1.toString());
			for (EngDivision eng : engDivList) {
				TreeNode tree = new TreeNode();
				tree.setId(eng.getDivisionSnCode());
				if (eng.getDivisionSnCode().indexOf(".") > -1) {
					tree.setpId(eng.getDivisionSnCode().substring(0, eng.getDivisionSnCode().lastIndexOf(".")));
				} else {
					tree.setpId("");
				}
				tree.setName(eng.getDivName());
				treeLst.add(tree);
			}
			map.put("data", TreeUtil.buildListToTree(treeLst));
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}

	/**
	 * 获取技术主管需要交底的数据
	 * @author Xu Dingkui
	 * @date 2017年7月18日
	 * @param userid	技术主管用户ID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/technologyLeaderDisclose")
	public Map technologyLeaderDisclose(String userid, String pDivSnCode, String tenantCode) {
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			Map umap = new HashMap();
			SysUsersKey key = new SysUsersKey(userid, tenantCode);
			SysUsers u = sysUsersService.getByCode(key);
			if (u == null) {
				map.put("status", 1);
				map.put("message", "用户不存在");
				return map;
			}
			Engineering eng = engineeringService.getEngineeringByTenantCode(u.getTenantCode());
			umap.put("projectDept", eng.getEngFullName());
			umap.put("username", u.getUserName());
			//获取叶子节点的划分
			Integer leafLevel = 0;
			List<Integer> levelLst = engDivisionService.selectDivLevel();
			if (levelLst.size() > 0) {
				leafLevel = levelLst.get(0);
			}
			//当前已经交底正在施工的工序
			Map cpmap = new HashMap();
			cpmap.put("state", "当前已经交底正在施工的" + DivLevelUtil.getLevelValue(leafLevel));
			//and (not exists (select 1 from labor c where instr(ifnull(b.Disclo,' '), c.idcard) = 0 and c.DivisionCode = b.DisDivCode))
//			String sqlWhere = "DivLevel='" + leafLevel + "' and a.ConsSchemeNumber > getSumFinishByDivSnCode(a.DivisionSnCode) and exists (select 1 from tech_disclosure b where a.DivisionSnCode = b.DisDivSnCode and b.disclosurer='" + userid + "')";
			String sqlWhere = "div_level=" + leafLevel + " and exists (select 1 from sub_div_work_bill d where a.division_sn_code = d.division_sn_code and d.raw_con_map_quan + d.cons_map_sum_vary_quan > getSumFinishByDivSnCode(a.division_sn_code)) "
					+ "and exists (select 1 from tech_disclosure b where a.division_sn_code = b.division_sn_code and b.dis_date is not null)";
			if (pDivSnCode != null && !"".equals(pDivSnCode)) {
				sqlWhere += " and getParentDivSnCode(a.division_sn_code)='" + pDivSnCode + "'";
			}
			List<EngDivision> constructingProcedure = engDivisionService.selectBySqlWhere("a.division_sn_code, a.div_name, a.div_item_code, a.div_level", sqlWhere);
			cpmap.put("data", constructingProcedure);
			//计划已经施工但还未交底的工序
			Map dpmap = new HashMap();
			dpmap.put("state", "计划已经施工但还未交底的" + DivLevelUtil.getLevelValue(leafLevel));
			sqlWhere = "div_level=" + leafLevel + " and exists (select 1 from sub_div_work_bill d where a.division_sn_code = d.division_sn_code and d.raw_con_map_quan + d.cons_map_sum_vary_quan > getSumFinishByDivSnCode(a.division_sn_code)) "
					+ " and not exists (select 1 from tech_disclosure b where a.division_sn_code = b.division_sn_code and b.dis_date is not null)"// and a.RealStartDate < now()";
					+ " and exists (select 1 from eng_division_plan b where a.division_sn_code = b.division_sn_code and b.start_date < now())"
					//权限
					+ " and checkEngDivAuthority('" + userid + "', a.division_sn_code) = 'Y'";
			if (pDivSnCode != null && !"".equals(pDivSnCode)) {
				sqlWhere += " and getParentDivSnCode(a.division_sn_code)='" + pDivSnCode + "'";
			}
			List<EngDivision> discloseProcedure = engDivisionService.selectBySqlWhere("a.division_sn_code, a.div_name, a.div_item_code, a.div_level",sqlWhere);
			dpmap.put("data", discloseProcedure);
			//计划30天内施工的工序
			Map ppmap = new HashMap();
			ppmap.put("state", "计划30天内施工的" + DivLevelUtil.getLevelValue(leafLevel));
			sqlWhere = "div_level=" + leafLevel
					+ " and not exists (select 1 from tech_disclosure b where a.division_sn_code = b.division_sn_code and b.dis_date is not null)"
					+ " and exists (select 1 from eng_division_plan b where a.division_sn_code = b.division_sn_code "
					+ " and b.start_date > DATE_FORMAT(now(),'%Y-%m-%d') and b.start_date <= date_add(DATE_FORMAT(now(),'%Y-%m-%d'),interval 30 day))"
					//权限
					+ " and checkEngDivAuthority('" + userid + "', a.division_sn_code) = 'Y'";
			if (pDivSnCode != null && !"".equals(pDivSnCode)) {
				sqlWhere += " and getParentDivSnCode(a.division_sn_code)='" + pDivSnCode + "'";
			}
			List<EngDivision> planProcedure = engDivisionService.selectBySqlWhere("a.division_sn_code, a.div_name, a.div_item_code, a.div_level",sqlWhere);
			ppmap.put("data", planProcedure);
			umap.put("constructingProcedure", cpmap);
			umap.put("discloseProcedure", dpmap);
			umap.put("planProcedure", ppmap);
			map.put("data", umap);
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 技术主管技术交底上传附件
	 * @author Xu Dingkui
	 * @date 2017年7月20日
	 * @param userid 技术主管用户ID
	 * @param Code	交底工序ID
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/technologyLeaderUploadAttach")
	public Map technologyLeaderUploadAttach(/*String userid, String Code,*/ @RequestParam("files") MultipartFile[] attachUrl) {
		Map map = new HashMap();
		try {
			Map attMap = new HashMap();
			//定义文件路径
			String folder = "attach/";
			String path = constants.getUploadDirReal() + folder;
			
			File saveDirFile = new File(path);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			List attUrl = new ArrayList();
			//判断file数组不能为空并且长度大于0  
			if(attachUrl !=null && attachUrl.length>0) {  
			    //循环获取file数组中得文件  
				for(int i = 0; i < attachUrl.length; i++){
					MultipartFile file = attachUrl[i];
					//保存文件 
					if (!file.isEmpty()) {  
						try {
							String originalFilename = file.getOriginalFilename();
							String type = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
							String fileName = new Date().getTime() + type;
							// 文件保存路径  
				            String filePath = path + fileName;  
				            // 转存文件  
				            file.transferTo(new File(filePath));
				            attUrl.add(folder + fileName);
						} catch (Exception e) {  
				            e.printStackTrace();  
				        }  
				   }
			    }
			}
			attMap.put("attachUrl", attUrl);
			map.put("status", 0);
			map.put("message", "成功");
			map.put("data", attMap);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 技术主管技术交底上传附件(文件名不变)
	 * @author Xu Dingkui
	 * @date 2017年7月20日
	 * @param userid 技术主管用户ID
	 * @param Code	交底工序ID
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/technologyLeaderUploadAttach1")
	public Map technologyLeaderUploadAttach1(/*String userid, String Code,*/ @RequestParam("files") MultipartFile[] attachUrl) {
		Map map = new HashMap();
		try {
			Map attMap = new HashMap();
			//定义文件路径
			String folder = "attach/";
			String path = constants.getUploadDirReal() + folder;
			
			File saveDirFile = new File(path);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}
			List attUrl = new ArrayList();
			//判断file数组不能为空并且长度大于0  
			if(attachUrl !=null && attachUrl.length>0) {  
			    //循环获取file数组中得文件  
				for(int i = 0; i < attachUrl.length; i++){
					MultipartFile file = attachUrl[i];
					//保存文件 
					if (!file.isEmpty()) {  
						try {  
							// 文件保存路径  
				            String filePath = path + file.getOriginalFilename();  
				            // 转存文件  
				            file.transferTo(new File(filePath));
				            attUrl.add(folder + file.getOriginalFilename());
						} catch (Exception e) {  
				            e.printStackTrace();  
				        }  
				   }
			    }
			}
			attMap.put("attachUrl", attUrl);
			map.put("status", 0);
			map.put("message", "成功");
			map.put("data", attMap);
			return map;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 技术主管提交交底数据
	 * @author Xu Dingkui
	 * @date 2017年7月20日
	 * @param userid 技术主管用户ID
	 * @param Code	交底工序ID
	 * @param discloseContent 交底内容
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/technologyLeaderCommitDisclose")
	public Map technologyLeaderCommitDisclose(String userid, String Code, String discloseContent, String attachUrl) {
		Map map = new HashMap();
		try {
			//已经保存交底的数据
			List<TechDisclosure> tdList = techDisclosureService.findTechDisclosureByDisDivSnCode(Code);
			TechDisclosure td = null;
			for (TechDisclosure t : tdList) {
				//判断已经保存的交底数据是否已经提交
				if (t.getDisDate() != null) {
					map.put("status", 1);
					map.put("message", "存在未提交的交底数据");
					return map;
				}
			}
				//判断用户是否有此划分权限
				String flag = sysUsersService.checkEngDivAuthority(userid, Code);
				if ("N".equals(flag)) {
					map.put("status", 1);
					map.put("message", "无权操作此划分");
					return map;
				}
				//保存交底数据
				td = new TechDisclosure();
				td.setDisContent(discloseContent);
				td.setDisclosurer(userid);
				//查询工程项目划分
				EngDivision engDiv = engDivisionService.findEngDivisionByKey(Code);
				if (engDiv == null) {
					map.put("status", 1);
					map.put("message", "划分不存在");
					return map;
				}
				td.setDivisionSnCode(engDiv);
				//依据交底工序ID在劳务表查询劳务公司
				String laborCompany = laborService.selectLaborCompanyByDivSnCode(Code);
				if (laborCompany == null || "".equals(laborCompany)) {
					throw new ScheduleException("未找到劳务公司");
				}
				td.setRecipientRole(laborCompany);
				td.setDisclosureCode(String.valueOf((new Date()).getTime()));
				td.setDisDate(new Date());
				techDisclosureService.saveTechDisclosure(td);
				
				//判断file数组不能为空并且长度大于0  
				if(attachUrl !=null && !"".equals(attachUrl)) {
					String url[] = attachUrl.split(",");
					for (String u : url) {
						ContractAtt doc = new ContractAtt();
						String type = u.substring(u.lastIndexOf("."), u.length());
						doc.setType(type);
						doc.setActualFileName(u.substring(u.lastIndexOf("/"), u.length()));
						doc.setLocation(u);
						doc.setFileInName(String.valueOf((new Date()).getTime()));
						try {
							contractAttService.saveContractAtt(doc);
						} catch (UtilException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				map.put("status", 0);
				map.put("message", "成功");
				return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}

	/**
	 * 获取接技术交底详细的数据
	 * @author Xu Dingkui
	 * @date 2017年7月16日
	 * @param userid
	 * @param DivisionSnCode 划分序号编码
	 * @param Disclosurecode 技术交底编码
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/discloseDetail")
	public Map discloseDetail(String userid, String DivisionSnCode, String Disclosurecode, String tenantCode) {
		//查询工人信息
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
//			TechDisclosure td = techDisclosureService.findTechDisclosureByDisDivSnCode(DivisionSnCode);
			TechDisclosure td = techDisclosureService.findTechDisclosureByPrimaryKey(Disclosurecode);
			TechDisclosureModel m = new TechDisclosureModel();
			if (td != null) {
				//判断用户是否有此划分权限
				String flag = sysUsersService.checkEngDivAuthority(userid, DivisionSnCode);
				if ("N".equals(flag)) {
					map.put("status", 1);
					map.put("message", "无权操作此划分");
					return map;
				}
				m.setDisclosurecode(td.getDisclosureCode());
				m.setDisdivsncode(td.getDivisionSnCode().getDivisionSnCode());
				//查找工程项目划分
				EngDivision ed = td.getDivisionSnCode();
				if (ed != null) {
					m.setDivname(ed.getDivName());
				}
				//交底对象
//				if (td.getDisrole() != null && !"".equals(td.getDisrole())) {
//				}
//				m.setDisrole(td.getDisrole());
				SysUsersKey key = new SysUsersKey(td.getDisclosurer(), tenantCode);
				m.setDisclosurer(sysUsersService.getByCode(key).getUserName());
				if (td.getDisDate() != null) {
					m.setDisdate(DateUtils.format(td.getDisDate(), "yyyy-MM-dd"));
				}
				m.setDisclocontent(td.getDisContent());
				m.setDiscloinclude(td.getDisInclude());
				List<ContractAtt> attachLst = contractAttService.findByFileInNames(td.getDisAttach());
				ArrayList arr = new ArrayList();
				for (ContractAtt att :attachLst) {
					arr.add(att.getLocation());
				}
				m.setAttachment(arr);
			} else {
				map.put("status", 1);
				map.put("message", "没有找到交底数据");
				return map;
			}
			map.put("data", m);
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}

	/**
	 * 获取技术主管填报数据
	 * @author Xu Dingkui
	 * @date 2017年7月24日
	 * @param userid
	 * @param pDivSnCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/divFinishRateFillData")
	public Map divFinishRateFillData(String userid, String pDivSnCode, String tenantCode) {
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			Map umap = new HashMap();
			SysUsersKey key = new SysUsersKey(userid, tenantCode);
			SysUsers u = sysUsersService.getByCode(key);
			if (u == null) {
				map.put("status", 1);
				map.put("message", "用户不存在");
				return map;
			}
			//技术主管填报数据
			List<EngDivisionModel> filldata = engDivisionService.selectTechnologyLeaderFillData(userid, "", pDivSnCode);
			umap.put("filldata", filldata);
			map.put("data", umap);
			return map;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 技术主管填报今日完成数量
	 * @author Xu Dingkui
	 * @date 2017年7月24日
	 * @param userid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/submitTodayQuantity")
	public Map submitTodayQuantity(String userid, String DivisionSncode, Double quantity) {
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			//判断用户是否有此划分权限
			String flag = sysUsersService.checkEngDivAuthority(userid, DivisionSncode);
			if ("N".equals(flag)) {
				map.put("status", 1);
				map.put("message", "无权操作此划分");
				return map;
			}
			//依据工程划分编码查询当天的填报信息
			EngDivFinishRate query = new EngDivFinishRate();
			query.setDivisionSnCode(DivisionSncode);
			query.setWorkDate(new Date());
			EngDivFinishRate rate = engDivFinishRateService.findEngDivFinishRateByPrimaryKey(query);
			if (rate != null) {
				map.put("status", 1);
				map.put("message", "已经填报");
				return map;
			} else {
				rate = new EngDivFinishRate();
				rate.setDivisionSnCode(DivisionSncode);
				rate.setWorkDate(new Date());
				rate.setFinishNumber(quantity);
				engDivFinishRateService.saveEngDivFinishRate(rate);
				List<EngDivisionModel> filldata = engDivisionService.selectTechnologyLeaderFillData(userid, DivisionSncode, "");
				if (filldata.size() > 0) {
					EngDivisionModel engDiv = filldata.get(0);
					map.put("data", engDiv);
				}
			}
			return map;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
}
