package com.banry.pscm.web.mvc.pscm.engsafety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.conf.PscmConfException;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleBill;
import com.banry.pscm.service.engsafety.HiddenTroubleBillService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.EnumVarService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.model.TreeNode;
import com.banry.pscm.web.mvc.pscm.conf.InitTenant;

@Controller
@RequestMapping("/hiddenTroubleBill")
public class HiddenTroubleBillController {
	
    @Autowired
	private HiddenTroubleBillService hiddenTroubleBillService;
	@Autowired
	private EnumVarService enumVarService;
	@Autowired
	private EngDivisionService engDivisionService;
	
	private static Logger log = LoggerFactory.getLogger(HiddenTroubleBillController.class);
	
	//获取隐患清单
	@RequestMapping("/getHiddenTroubleBillList")
	@ResponseBody
	public Object getHiddenTroubleBillList(HttpServletRequest request, String divItemCode, String fromCode, String trobleFrom) {
		DataTableModel data = new DataTableModel();
		try {
			if (divItemCode != null && !"".equals(divItemCode)) {
				String sqlWhere = "FIND_IN_SET(a.div_item_code,getParentList('" + divItemCode + "'))";
				if (fromCode != null && !"".equals(fromCode)) {
					sqlWhere += " and a.from_code='" + fromCode + "'";
				}
				if (trobleFrom != null && !"".equals(trobleFrom)) {
					sqlWhere += " and a.trouble_from='" + trobleFrom + "'";
				}
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				List<HiddenTroubleBill> listGrid = hiddenTroubleBillService.findHiddenTroubleBillBySqlWhere(sqlWhere, parentTenantAccount);
				data.setData(listGrid);
			} else {
				data.setData(new ArrayList<HiddenTrouble>());
			}
			return data;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	//获取隐患清单的隐患类别和排查项目
	@RequestMapping("/findTroubleCateInvestItem")
	@ResponseBody
	public Object findTroubleCateInvestItem(HttpServletRequest request, String divItemCode, String fromCode, String troubleFrom) {
		DataTableModel data = new DataTableModel();
		try {
			if (divItemCode != null && !"".equals(divItemCode)) {
				String sqlWhere = "FIND_IN_SET(a.div_item_code,getParentList('" + divItemCode + "'))";
				if (fromCode != null && !"".equals(fromCode)) {
					sqlWhere += " and a.from_code='" + fromCode + "'";
				}
				if (troubleFrom != null && !"".equals(troubleFrom)) {
					sqlWhere += " and a.trouble_from='" + troubleFrom + "'";
				}
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				//查询隐患类别编码TroubleCate，排查项目InvestItem，隐患类别trobulecatename
				List<HiddenTroubleBill> itemList = hiddenTroubleBillService.findTroubleCateInvestItem(sqlWhere, parentTenantAccount);
				//隐患类别
				Map<Integer, String> hcMap = new HashMap<Integer, String>();
				for (HiddenTroubleBill ht : itemList) {
					if(ht.getTroubleCate()!=null) {
						hcMap.put(ht.getTroubleCate().getEnumValue(), ht.getTroubleCate().getEnumValueName());			
					}
				}
//				List<HiddenTroubleBill> listGrid = hiddenTroubleBillService.findHiddenTroubleBillBySqlWhere(sqlWhere);
				List<HiddenTroubleBill> dataGrid = new ArrayList<HiddenTroubleBill>();
				//隐患类别
				for (Map.Entry<Integer, String> hc : hcMap.entrySet()) {
					HiddenTroubleBill ht = new HiddenTroubleBill();
					ht.setInvestContent(hc.getValue());
					ht.setClassname("treegrid-h"+hc.getKey());
					ht.setClassid("h"+hc.getKey());
					dataGrid.add(ht);
					//排查项目
					for (int i = 0; i< itemList.size(); i++) {
						HiddenTroubleBill item = itemList.get(i);
						if (item.getTroubleCate() != null && hc.getKey().intValue() == item.getTroubleCate().getEnumValue().intValue()) {
							ht = new HiddenTroubleBill();
							ht.setInvestContent(item.getInvestItem());
							ht.setInvestItem(item.getInvestItem());
							ht.setTroubleCate(item.getTroubleCate());
							ht.setClassname("treegrid-i"+i+" treegrid-parent-h"+hc.getKey());
							ht.setClassid("i"+i);
//							ht.setTrobulelevel(String.valueOf(i));//
							dataGrid.add(ht);
						}
					}
				}
				data.setData(dataGrid);
			} else {
				data.setData(new ArrayList<HiddenTrouble>());
			}
			return data;
		} catch (EngsafetyException e) {
			e.printStackTrace();
			return data;
		}
	}
	
	//依据隐患类别和排查项目获取隐患清单
	@RequestMapping("/findHiddTrouListByTroCateInvItem")
	@ResponseBody
	public Object findHiddTrouListByTroCateInvItem(HttpServletRequest request, String divItemCode, String fromCode, String troubleFrom, String troubleCate, String investItem, String i) {
		DataTableModel data = new DataTableModel();
		try {
			if (divItemCode != null && !"".equals(divItemCode)) {
				String sqlWhere = "FIND_IN_SET(a.div_item_code,getParentList('" + divItemCode + "'))";
				if (fromCode != null && !"".equals(fromCode)) {
					sqlWhere += " and a.from_code='" + fromCode + "'";
				}
				if (troubleFrom != null && !"".equals(troubleFrom)) {
					sqlWhere += " and a.trouble_from='" + troubleFrom + "'";
				}
				sqlWhere += " and a.trouble_cate='" + troubleCate + "' and a.invest_item='" + investItem + "'";
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				List<HiddenTroubleBill> listGrid = hiddenTroubleBillService.findHiddenTroubleBillBySqlWhere(sqlWhere, parentTenantAccount);
				//隐患清单
				for (int j = 0; j< listGrid.size(); j++) {
					HiddenTroubleBill entity = listGrid.get(j);
					entity.setClassname("treegrid-leaf treegrid-j"+j+" treegrid-parent-"+i);
					entity.setClassid("j"+j);
				}
				data.setData(listGrid);
			} else {
				data.setData(new ArrayList<HiddenTrouble>());
			}
			return data;
		} catch (EngsafetyException e) {
			e.printStackTrace();
			return data;
		}
	}
	
	//新增隐患
	@RequestMapping("/hiddenTroubleBillSite")
	public Object hiddenTroubleBillSite(HttpServletRequest request, String troubleCode, String divItemCode) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTroubleBillSite");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//隐患类别
			List<EnumVar> tcLst = enumVarService.findByEnumName("TroubleCate");
			mv.addObject("tcLst", tcLst);
			List<EnumVar> tcLst1 = enumVarService.findByEnumName("TroubleCate1");
			mv.addObject("tcLst1", tcLst1);
			//隐患级别
			List<EnumVar> tlLst = enumVarService.findByEnumName("TroubleLevel");
			mv.addObject("tlLst", tlLst);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	//质量隐患--xudingkui
	@RequestMapping("/qualityHiddenTroubleBill")
	public Object qualityHiddenTroubleBill(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTroubleBill");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//隐患类别
			List<EnumVar> tcLst = enumVarService.findByEnumName("TroubleCate");
			mv.addObject("tcLst", tcLst);
			List<EnumVar> tcLst1 = enumVarService.findByEnumName("TroubleCate1");
			mv.addObject("tcLst1", tcLst1);
			//隐患级别
			List<EnumVar> tlLst = enumVarService.findByEnumName("TroubleLevel");
			mv.addObject("tlLst", tlLst);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	//安全隐患@author xudingkui
	@RequestMapping("/safeHiddenTroubleBill")
	public Object safeHiddenTroubleBill(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("engsafety/safeHiddenTroubleBill");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//隐患类别
			List<EnumVar> tcLst = enumVarService.findByEnumName("TroubleCate");
			mv.addObject("tcLst", tcLst);
			List<EnumVar> tcLst1 = enumVarService.findByEnumName("TroubleCate1");
			mv.addObject("tcLst1", tcLst1);
			//隐患级别
			List<EnumVar> tlLst = enumVarService.findByEnumName("TroubleLevel");
			mv.addObject("tlLst", tlLst);
		} catch (UtilException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/edit")
	public Object edit(HttpServletRequest request, String troubleCode, String divItemCode) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTroubleBill-edit");
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			//编辑时获取隐患信息
			if (troubleCode != null && !"".equals(troubleCode)) {
				HiddenTroubleBill r = hiddenTroubleBillService.findHiddenTroubleBillByKey(troubleCode, parentTenantAccount);
				mv.addObject("r", r);
				mv.addObject("flag", "U");
			} else {
				mv.addObject("flag", "I");
			}
			//工程划分
			List<EngDivision> divLst = engDivisionService.selectBySqlWhere("a.div_item_code, a.div_name", "a.div_item_code='" + divItemCode + "'");
			mv.addObject("divLst", divLst);
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			//隐患类别
			List<EnumVar> tcLst = enumVarService.findByEnumName("TroubleCate");
			mv.addObject("tcLst", tcLst);
			//隐患级别
			List<EnumVar> tlLst = enumVarService.findByEnumName("TroubleLevel");
			mv.addObject("tlLst", tlLst);
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveHiddenTroubleBill")
	@ResponseBody
	public Map saveHiddenTroubleBill(HiddenTroubleBill ht, String flag) {
		Map map = new HashMap();
		try {
			//新增
			if ("I".equals(flag)) {
				ht.setFromCode("现场增加");
			}
			hiddenTroubleBillService.saveHiddenTroubleBill(ht);
			map.put("result", true);
			map.put("retMsg", "");
			map.put("troublecode", ht.getTroubleCode());
			return map;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping("/saveQualityHiddenTroubleBill")
	@ResponseBody
	public boolean saveQualityHiddenTroubleBill(HttpServletRequest request, HiddenTroubleBill ht) {
		try {
			if(ht.getTroubleCode() !=null && !"".equals(ht.getTroubleCode())) {
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				HiddenTroubleBill hiddenTrouble=hiddenTroubleBillService.findHiddenTroubleBillByKey(ht.getTroubleCode(), parentTenantAccount);
				hiddenTrouble.setTroubleCate(ht.getTroubleCate());
				hiddenTrouble.setTroubleLevel(ht.getTroubleLevel());
				hiddenTroubleBillService.saveHiddenTroubleBill(hiddenTrouble);
				return true;
			}else {
				return false;
			}
		} catch (EngsafetyException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RequestMapping("/saveSafeHiddenTroubleBill")
	@ResponseBody
	public boolean saveSafeHiddenTroubleBill(HttpServletRequest request, HiddenTroubleBill ht) {
		try {
			if(ht.getTroubleCode() !=null && !"".equals(ht.getTroubleCode())) {
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				HiddenTroubleBill hiddenTrouble=hiddenTroubleBillService.findHiddenTroubleBillByKey(ht.getTroubleCode(), parentTenantAccount);
				hiddenTrouble.setTroubleCate(ht.getTroubleCate());
				hiddenTrouble.setTroubleLevel(ht.getTroubleLevel());
				hiddenTroubleBillService.saveHiddenTroubleBill(hiddenTrouble);
				return true;				
			}else {
				return false;
			}
		} catch (EngsafetyException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@RequestMapping("/deleteHiddenTroubleBill")
	@ResponseBody
	public String deleteHiddenTroubleBill(String troubleCodes) {
		try {
			hiddenTroubleBillService.deleteHiddenTroubleBillByKey(troubleCodes);
			return "{success:true}";
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
	@RequestMapping("/findbycode")
	public HiddenTroubleBill findHiddenTroubleByTroubleCode(HttpSession session, String troublecode) {
		log.info("findHiddenTroubleByTroubleCode call with troublecode="+troublecode);
		try {
			String parentTenantCode = (String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");
			return hiddenTroubleBillService.findHiddenTroubleBillByKey(troublecode,parentTenantCode);
		} catch (Exception e) {
			log.error("findHiddenTroubleByTroubleCode call fail:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// 暂不区分 质量隐患还是安全隐患
	@RequestMapping("/findbydivitemcode")
	public List<HiddenTroubleBill> findHiddenTroubleByDivItemCode(HttpSession session, String divitemcode) {
		log.info("findHiddenTroubleByDivItemCode call with divitemcode="+divitemcode);
		try {
			return hiddenTroubleBillService.findHiddenTroubleBillsByDivItemCode(divitemcode,(String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
		} catch (Exception e) {
			log.error("findHiddenTroubleByDivItemCode call fail:"+e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping("/findbydivsncode")
	@ResponseBody
	public List<HiddenTroubleBill> findHiddenTroubleBillByDivSnCode(HttpSession session, String divSnCode) {
		log.info("findHiddenTroubleBillByDivSnCode call with divisionsncode="+divSnCode);
		try {
			List<HiddenTroubleBill> list = hiddenTroubleBillService.findHiddenTroubleBillsByDivSnCode(divSnCode,(String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	@RequestMapping("/findEnumVarOfHiddenTroubleCate")
	@ResponseBody
	public List<EnumVar> findEnumVarOfHiddenTroubleCate(HttpSession session) {
		log.info("findEnumVarOfHiddenTroubleCate call with...");
		try {
			List<EnumVar> troubleCateList = hiddenTroubleBillService.findEnumVarAllTroubleCate((String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
			return troubleCateList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("/findEnumVarOfHiddenTroubleLevel")
	@ResponseBody
	public List<EnumVar> findEnumVarOfHiddenTroubleLevel(HttpSession session) {
		log.info("findEnumVarOfHiddenTroubleLevel call with...");
		try {
			List<EnumVar> troubleLevelList = hiddenTroubleBillService.findEnumVarAllTroubleLevel((String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
			return troubleLevelList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/delete")
	public Integer deleteHiddenTroubleByCode(HttpSession session, String troublecode) {
		log.info("deleteHiddenTroubleByCode call with troublecode="+troublecode);
		try {
			return hiddenTroubleBillService.deleteHiddenTroubleBillByKey(troublecode);
		} catch (Exception e) {
			log.error("deleteHiddenTroubleByCode call fail:"+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@RequestMapping("/save")
	public void saveHiddenTrouble(HttpSession session, HiddenTroubleBill hiddentrouble) {
		log.info("saveHiddenTrouble call with troublecode="+hiddentrouble.getTroubleCode());
		try {
			hiddenTroubleBillService.saveHiddenTroubleBill(hiddentrouble);
		} catch (Exception e) {
			log.error("saveHiddenTrouble call fail:"+e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping("/saveselective")
	public void saveHiddenTroubleSelective(HttpSession session, HiddenTroubleBill hiddentrouble) {
		log.info("saveHiddenTroubleSelective call with troublecode="+hiddentrouble.getTroubleCode());
		try {
			hiddenTroubleBillService.saveHiddenTroubleBillSelective(hiddentrouble);
		} catch (Exception e) {
			log.error("saveHiddenTroubleSelective call fail:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理jqGrid 行编辑请求，支持删除和更新两个操作，操作标识oper=edit 或者 del
	 * 
	 * @param session
	 * @param HiddenTroubleBill ht
	 * @param oper add/edit/delete
	 * @param id
	 * @return 
	 * @paeam htCodes 删除多行时前台传送过来的数组
	 */
	@RequestMapping("/handlerowopera")
	public ResponseEntity<?> handleHiddenTroubleBillRowOperation(HttpSession session, HiddenTroubleBill ht, String divsncode, String oper,
			String id, String htCodes) {

		log.info("handleHiddenTroubleBillRowOperation called with ht.getResCode()=" + ht.getTroubleCode() + " and oper=" + oper
				+ " and id=" + id + " divsncode=" + divsncode + " htCodes="+ htCodes);

		if ("add".equals(oper)) {
			// 根据divsncode 反查divItemCode 和 divlevel
			EngDivision engDivision;
			try {
				engDivision = engDivisionService.findEngDivisionByKey(divsncode);
			} catch (ScheduleException e) {
				e.printStackTrace();
				log.error("saveHiddenTroubleBill failed:" + e.getMessage());
				return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
			
			try {		
				EnumVar troubleCateEnumVar = hiddenTroubleBillService.findEnumVarByEnumValueName(ht.getTroubleCate().getEnumValueName(),(String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
				ht.setTroubleCate(troubleCateEnumVar);
				EnumVar troubleLevelEnumVar = hiddenTroubleBillService.findEnumVarByEnumValueName(ht.getTroubleLevel().getEnumValueName(),(String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
				ht.setTroubleLevel(troubleLevelEnumVar);
				
				ht.setDivItemCode(engDivision.getDivItemCode());
				ht.setDivLevel(engDivision.getDivLevel());
				hiddenTroubleBillService.saveHiddenTroubleBill(ht);
				
			} catch (Exception e) {
				log.error("saveHiddenTroubleBill failed:" + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
		}

		// edit row mode
		if ("edit".equals(oper)) {
			try {
				EnumVar troubleCateEnumVar = hiddenTroubleBillService.findEnumVarByEnumValueName(ht.getTroubleCate().getEnumValueName(),(String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
				ht.setTroubleCate(troubleCateEnumVar);
				EnumVar troubleLevelEnumVar = hiddenTroubleBillService.findEnumVarByEnumValueName(ht.getTroubleLevel().getEnumValueName(),(String) session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT"));
				ht.setTroubleLevel(troubleLevelEnumVar);
				
				hiddenTroubleBillService.saveHiddenTroubleBillSelective(ht);

			} catch (Exception e) {
				log.error("saveHiddenTroubleBillSelective failed:" + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
		}

		// del row mode
		if ("del".equals(oper)) {
			if (htCodes != null) {
				try {
					String[] ids = htCodes.split(",");
					for (int i = 0; i < ids.length; i++) {
						log.info("trying to deleteHiddenTroubleBillByKey " + ids[i]);
						hiddenTroubleBillService.deleteHiddenTroubleBillByKey(ids[i]);
					}
				} catch (Exception e) {
					log.error("deleteHiddenTroubleBillByKey failed:" + e.getMessage());
					e.printStackTrace();
					return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
				}
			}
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);// 保存成功，返回200
		
	}
}
