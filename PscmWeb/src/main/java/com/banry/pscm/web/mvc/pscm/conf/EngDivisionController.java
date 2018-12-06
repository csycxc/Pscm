package com.banry.pscm.web.mvc.pscm.conf;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.banry.pscm.CommonDBCache;
import com.banry.pscm.service.conf.UUIDModel;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTroubleBill;
import com.banry.pscm.service.engsafety.HiddenTroubleBillService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.Hazards;
import com.banry.pscm.service.schedule.HazardsService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.schedule.SubDivWorkQuota;
import com.banry.pscm.service.schedule.SubDivWorkQuotaService;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.model.TreeNode;

/**
 * @author chenshiyu
 *
 */
@RestController
@RequestMapping("/engDivision")
public class EngDivisionController {
	@Autowired
	private EngDivisionService engDivisionService;
	@Autowired
	private SubDivWorkBillService subDivWorkBillService;
	@Autowired
	private SubDivWorkQuotaService subDivWorkQuotaService;
	@Autowired
	private HiddenTroubleBillService hiddenTroubleBillService;
	@Autowired
	private HazardsService hazardsService;

	private static Logger log = LoggerFactory.getLogger(EngDivisionController.class);

	@RequestMapping("/findbycode")
	public EngDivision findEngDivisionByCode(String divisionsncode) {
		log.info("findEngDivisionByCode call with divisionsncode="+divisionsncode);
		try {
			return engDivisionService.findEngDivisionByKey(divisionsncode);
		} catch (Exception e) {
			log.error("findEngDivisionByCode error:"+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/findbydivitemcode")
	public List<EngDivision> findEngDivisionsByDivItemCode(String divitemcode) {
		log.info("findEngDivisionsByDivItemCode call with divitemcode="+divitemcode);
		try {
			return engDivisionService.findEngDivisionsByDivItemCode(divitemcode);
		} catch (Exception e) {
			log.error("findEngDivisionsByDivItemCode error:"+e.getMessage());
			e.printStackTrace();	
			return null;
		}

	}

	@RequestMapping("/findbydivname")
	public List<EngDivision> findEngDivisionsByDivName(HttpSession session, String divname) {
		log.info("findEngDivisionsByDivName call with divname="+divname);
		try {
			return engDivisionService.findEngDivisionsByDivName(divname);
		} catch (Exception e) {
			log.error("findEngDivisionsByDivName error:"+e.getMessage());
			e.printStackTrace();	
			return null;
		}
	}

	@RequestMapping("/findall")
	public List<EngDivision> findAllEngDivisions(HttpSession session) {
		log.info("findAllEngDivisions start...");
		try {
			return engDivisionService.findAllEngDivision();
		} catch (Exception e) {
			log.error("findAllEngDivisions error:"+e.getMessage());
			e.printStackTrace();	
			return null;
		}

	}

	@RequestMapping("/getdivtree")
	public ResponseEntity<?> getDivTree(HttpSession session) {
		log.info("getDivTree start...");
		try {
			return new ResponseEntity<>(engDivisionService.findAllEngDivisionForZTree(), HttpStatus.OK);// 未登录或者回话超时，返回500
		} catch (Exception e) {
			log.error("getDivTree error:" + e);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);// 未登录或者回话超时，返回500
		}
	}

	@RequestMapping("/delete")
	public ResponseEntity<Object> deleteEngDivisionByCode(HttpSession session, String divisionsncode) {

		log.info("deleteEngDivisionByCode called with divisionsncode=" + divisionsncode);
		// 检查是否分项工程已经提交，如果已经提交，返回前台消息，提示不能删除
		SubDivWorkBill subDivWorkBill = null;
		try {
			subDivWorkBill = subDivWorkBillService.findSubDivWorkBillByKey(divisionsncode);
			Integer status = -1;
			if(subDivWorkBill != null) {
				status = subDivWorkBill.getStatus();
				log.info("subDivWorkBill.getStatus():" + status);
				// 分项工程状态为2的是已经提交，不能删除
				if (status == 2) {
					return new ResponseEntity<>("该分项工程" + divisionsncode + "已经提交，不能删除", HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		}
		if(subDivWorkBill != null) {
			try {
				// 删除划分时，首先要删除与之对应的清单、定额、（有可能删除隐患、危险源）最后才删除划分
				subDivWorkQuotaService.deleteSubDivWorkQuotaByDivisionSnCode(divisionsncode);// 删除定额
				subDivWorkBillService.deleteSubDivWorkBillByKey(divisionsncode);// 删除清单

				// EngDivision ed = pscmConfService.findEngDivisionByKey(divisionsncode);
				// String divItemCode = ed.getDivItemCode();
				int count = engDivisionService.findDivItemCodeCountFromEngDivisionByDivisionSnCode(divisionsncode);
				if (count == 1) {
					// 该划分对应的divItemCode在数据库中仅此一条，删除与之对应的 隐患和危险源
					hiddenTroubleBillService.deleteHiddenTroubleBillByDivisionSnCode(divisionsncode);
					hazardsService.deleteHazardsByDivisionSnCode(divisionsncode);
				}

				//hiddenTroubleBillService.deleteHiddenTroubleBillByKey(divisionsncode);
				engDivisionService.deleteEngDivisionByKey(divisionsncode);

				return new ResponseEntity<>(null, HttpStatus.OK);// 保存成功返回200

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
		}else {
			try {
				engDivisionService.deleteEngDivisionByKey(divisionsncode);
			} catch (ScheduleException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>("该分项工程未保存，可直接删除。", HttpStatus.OK);// 保存失败，返回500
		}
	}

	@RequestMapping("/save")
	public @ResponseBody ResponseEntity<?> saveEngDivision(HttpSession session,@RequestBody EngDivision engdivision) {
		log.info("saveEngDivision call with divisionsncode="+engdivision.getDivisionSnCode());
		try {
			String string = engDivisionService.saveEngDivision(engdivision);
			if(string.equals("insert")) {
				log.debug("insert engdivision ,divisionsncode = "+engdivision.getDivisionSnCode()+"...");
			}else if(string.equals("update")) {
				log.debug("update engdivision,divisionsncode = "+engdivision.getDivisionSnCode()+"...");
			}
			log.debug("finish save engdivision...");
			return new ResponseEntity<>(null, HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//删除失败，返回500
		}
	}

	@RequestMapping("/saveselective")
	public @ResponseBody ResponseEntity<?> saveEngDivisionSelective(HttpSession session,@RequestBody EngDivision engdivision) {
		log.info("saveEngDivisionSelective call with divisionsncode="+engdivision.getDivisionSnCode());
		try {
			engDivisionService.saveEngDivisionSelective(engdivision);
			return new ResponseEntity<>(null, HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//删除失败，返回500
		}
	}

	/**
	 * 保存划分树的配置
	 * 
	 * @param divs
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/savedivtree", method = { RequestMethod.POST }, 
			produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.APPLICATION_JSON_VALUE )
	public @ResponseBody ResponseEntity<?> saveDivTree(@RequestBody String divs, HttpSession session) {

		log.debug("saveDivTree called...");
		log.debug("input divs:" + divs);

		long currentTM = System.currentTimeMillis();

		// System.out.println("============================="+divs);
		JSONArray divTree = JSON.parseArray(divs);
		
		String confSource = (String) session.getAttribute("MAJOR_SOURCE");
		//获取到公共库中    清单、定额数据表、隐患、危险源的信息
		JSONArray allsubdivwork = (JSONArray)((Hashtable)CommonDBCache.commonDBCache.get(confSource)).get("subdivworkList");
		JSONArray allsubdivworkquota = (JSONArray)((Hashtable)CommonDBCache.commonDBCache.get(confSource)).get("subdivworkquotaList");
		JSONArray allhiddentrouble = (JSONArray)((Hashtable)CommonDBCache.commonDBCache.get(confSource)).get("hiddentroubleList");
		JSONArray allhazards = (JSONArray)((Hashtable)CommonDBCache.commonDBCache.get(confSource)).get("hazardsList");
		//JSONArray allengdivision = (JSONArray)((Hashtable)CommonDBCache.commonDBCache.get("municipal_bridge_housing_dept")).get("engdivisionList");//划分
		//JSONArray allitemconsscheme = (JSONArray)((Hashtable)CommonDBCache.commonDBCache.get("municipal_bridge_housing_dept")).get("itemconsschemeList");
		
		Set<String> set = new HashSet<String>();//隐患和危险源与划分的对应关系是 一对多的，所以拿出来单独保存
		String engDivisionString = "";
		try {
			// 根据divLevel保存，先保存divLevel级别高的，即从1开始保存，到10结束
			for (int i = 1; i < 11; i++) {
				for (int j = 0; j < divTree.size(); j++) {
					JSONObject obj = (JSONObject) divTree.get(j);
					if (("" + i).equals(obj.getString("divLevel"))) {
						EngDivision engDivision = new EngDivision();
						engDivision.setDivisionSnCode(obj.getString("id"));
						engDivision.setDivItemCode(obj.getString("divItemCode"));
						engDivision.setDivName(obj.getString("name"));
						engDivision.setSkill(obj.getString("skill"));
						engDivision.setDivLevel(i);
						engDivision.setParentDivSnCode(obj.getString("pId"));
						engDivision.setPath(obj.getString("path"));

						log.info("saving engDivision:" + engDivision.toString());

						engDivisionString = engDivisionService.saveEngDivision(engDivision);// 保存该条划分
						
						// 如果划分是新插入的
						if (i > 6 && engDivisionString.equals("insert")) {
							// 保存与该条划分对应的 其他信息（清单、定额数据表、隐患、危险源）需要知道有哪些叶子节点的 divItemCode
							set.add(engDivision.getDivItemCode());

							Boolean b = true;//用于判断新加的划分是公共库中已有的还是用户自定义的
							
							for (int k = 0; k < allsubdivwork.size(); k++) {
								if (engDivision.getDivItemCode()
										.equals(allsubdivwork.getJSONObject(k).getString("subDivCode"))) {
									SubDivWorkBill sdwb = new SubDivWorkBill();
									sdwb.setDivisionSnCode(engDivision);// 划分主键 编码
									sdwb.setName(allsubdivwork.getJSONObject(k).getString("name"));// 项目名称
									sdwb.setCharactDes(allsubdivwork.getJSONObject(k).getString("charactDes"));// 项目特征描述
									sdwb.setUnit(allsubdivwork.getJSONObject(k).getString("unit"));// 计量单位
									sdwb.setTemporaryMeasurePrice(Double.parseDouble(
											allsubdivwork.getJSONObject(k).getString("temporaryMeasurePrice")));// 暂估价
									sdwb.setCompUnitPrice(Double
											.parseDouble(allsubdivwork.getJSONObject(k).getString("compUnitPrice")));// 综合单价
									sdwb.setQuotaManualFee(Double
											.parseDouble(allsubdivwork.getJSONObject(k).getString("quotaManualFee")));//// 定额人工费
									sdwb.setRawConMapQuan(
											Double.parseDouble(allsubdivwork.getJSONObject(k).getString("number")));// 工程量
									sdwb.setStatus(0);// 状态
									log.info("saving SubDivWorkBill DivisionSnCode=:" + sdwb.getDivisionSnCode());
									subDivWorkBillService.saveSubDivWorkBill(sdwb);// 保存清单
									b = false;
								}
							}
							if(b) {
								SubDivWorkBill sdwbModel = new SubDivWorkBill();
								sdwbModel.setDivisionSnCode(engDivision);
								sdwbModel.setName(engDivision.getDivName());
								sdwbModel.setStatus(0);// 状态
								subDivWorkBillService.saveSubDivWorkBill(sdwbModel);
							}
							
							for (int l = 0; l < allsubdivworkquota.size(); l++) {
								if (engDivision.getDivItemCode()
										.equals(allsubdivworkquota.getJSONObject(l).getString("subDivCode"))) {
									SubDivWorkQuota sdwq = new SubDivWorkQuota();
									sdwq.setResCode(UUIDModel.getUUID());// 资源码
									sdwq.setDivisionSnCode(engDivision.getDivisionSnCode());// 划分序号编码
									sdwq.setResourcesType(
											allsubdivworkquota.getJSONObject(l).getString("resourcesType"));// 资源类型为：人工、材料、机械
									sdwq.setResName(allsubdivworkquota.getJSONObject(l).getString("itemName"));// 资源名称
									sdwq.setResTypeLevel(allsubdivworkquota.getJSONObject(l).getString("resTypeLevel"));// 资源型号级别
									sdwq.setUnit(allsubdivworkquota.getJSONObject(l).getString("unit"));// 资源单位
									// sdwq.setBidMapQuan(Double.parseDouble(allsubdivworkquota.getJSONObject(l).getString("")));//招标图量
									// sdwq.setRawConsMapQuan(Double.parseDouble(allsubdivworkquota.getJSONObject(l).getString("")));//原始施工图量
									// sdwq.setConsMapSumVaryQuan(Double.parseDouble(allsubdivworkquota.getJSONObject(l).getString("")));//施工图累计变更量
									sdwq.setLossRate(Double
											.parseDouble(allsubdivworkquota.getJSONObject(l).getString("lossRate")));// 损耗率
									// sdwq.setCompUnitPrice(Double.parseDouble(allsubdivworkquota.getJSONObject(l).getString("")));//综合单价
									sdwq.setSaveExcessRate(Double.parseDouble(
											allsubdivworkquota.getJSONObject(l).getString("saveExcessRate")));// 节超预警率
									log.info("saving SubDivWorkQuota ResCode=:" + sdwq.getResCode());
									subDivWorkQuotaService.saveSubDivWorkQuota(sdwq);
								}
							}
						}
					}
				}
			}

			// set里面存储的divitemcode值
			for (int i = 0; i < allhiddentrouble.size(); i++) {
				if (set.contains(allhiddentrouble.getJSONObject(i).getString("divItemCode"))) {
					HiddenTroubleBill ht = new HiddenTroubleBill();
					ht.setTroubleCode(allhiddentrouble.getJSONObject(i).getString("troubleCode"));
					ht.setDivItemCode(allhiddentrouble.getJSONObject(i).getString("divItemCode"));
					ht.setDivLevel(Integer.parseInt(allhiddentrouble.getJSONObject(i).getString("divLevel")));
					EnumVar cate = new EnumVar();
					cate.setEnumValue(Integer.parseInt(allhiddentrouble.getJSONObject(i).getString("trobuleCate")));
					ht.setTroubleCate(cate);
					EnumVar level = new EnumVar();
					level.setEnumValue(Integer.parseInt(allhiddentrouble.getJSONObject(i).getString("trobuleLevel")));
					ht.setTroubleLevel(level);
					ht.setInvestItem(allhiddentrouble.getJSONObject(i).getString("investItem"));
					ht.setInvestContent(allhiddentrouble.getJSONObject(i).getString("investContent"));
					ht.setDescription(allhiddentrouble.getJSONObject(i).getString("description"));
					log.info("saving HiddenTroubleBill DivItemCod=:" + ht.getDivItemCode());
					hiddenTroubleBillService.saveHiddenTroubleBill(ht);
				}
			}
			for (int i = 0; i < allhazards.size(); i++) {
				if (set.contains(allhazards.getJSONObject(i).getString("divItemCode"))) {
					Hazards h = new Hazards();
					h.setHazardsCode(allhazards.getJSONObject(i).getString("hazardsCode"));// 危险源编码
					h.setDivItemCode(allhazards.getJSONObject(i).getString("divItemCode"));// 工程划分编码
					h.setDivLevel(allhazards.getJSONObject(i).getString("divLevel"));
					h.setHazardsFactors(allhazards.getJSONObject(i).getString("hazardsFactors"));// 危险源及危害因素
					h.setHazardsLevel(allhazards.getJSONObject(i).getString("hazardsLevel"));// 风险级别
					h.setAccidents(allhazards.getJSONObject(i).getString("accidents"));// 可能造成的事故事件
					h.setControlsMeasures(allhazards.getJSONObject(i).getString("controlsMeasures"));// 控制措施
					h.setDescription(allhazards.getJSONObject(i).getString("description"));// 备注说明
					log.info("saving Hazards DivItemCode=:" + h.getDivItemCode());
					hazardsService.saveHazards(h);
				}
			}

			log.info("saveDivTree call time consumed:" + (System.currentTimeMillis() - currentTM));

		} catch (ScheduleException e) {
			log.error("saveDivTree error:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		} catch (EngsafetyException e) {
			log.error("saveDivTree error:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
		}
		return new ResponseEntity<>(null, HttpStatus.OK);// 保存成功，返回200
	}

	/**
	 * 根据专业来源来取公共库中定义的划分树
	 * 
	 * @param session
	 * @return 未登录返回http500
	 */
	@RequestMapping("/getcommondivtree")
	public ResponseEntity<?> getCommonDivTree(HttpSession session) {
		log.info("getCommonDivTree start...");
		// 当前租户 专业来源
		String majorSource = (String) session.getAttribute("MAJOR_SOURCE");
		log.info("majorSource =========== ============="+majorSource);
		// 如果未登录或者登陆超时，获取不到session中的信息，返回给前端通知用户重新登陆
		if (majorSource == null)
			return new ResponseEntity<>("can not get 'MAJOR_SOURCE' on session.", HttpStatus.INTERNAL_SERVER_ERROR);// 未登录或者回话超时，返回500
		else
			return new ResponseEntity<>(
					(JSONArray)((Hashtable)CommonDBCache.commonDBCache.get(majorSource)).get("engdivisionList"),
					HttpStatus.OK);

	}

	/**
	 * 点击角色划分页面的时候加载所有划分（放到ModelAndView中）
	 * @return
	 */
	@RequestMapping("/roledivision")
	@ResponseBody
	public Object roleDivision() {
		ModelAndView mav = new ModelAndView("account/roledivision");
		DataTableModel data = new DataTableModel();
		try {
			
			List<TreeNode> menuList = new ArrayList<TreeNode>();
			List<EngDivision> engList = engDivisionService.findAllEngDivision();
			for (EngDivision engDivision : engList) {
				if(engDivision.getDivLevel() < 7) { 
					TreeNode tree = new TreeNode();
					tree.setId(engDivision.getDivisionSnCode());
					if (engDivision.getDivisionSnCode().indexOf(".") > -1) {
						tree.setpId(engDivision.getDivisionSnCode().substring(0, engDivision.getDivisionSnCode().lastIndexOf(".")));
					}
					tree.setName(engDivision.getDivName());
					tree.setOpen(true);
					tree.setDraggable(false);
					//tree.setDivlevel(engDivision.getDivlevel());
					menuList.add(tree);
				}
			}
			String js = JSON.toJSONString(menuList);
			mav.addObject("menuN",js);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	
	
	@RequestMapping(value="/findEngDivisionByPrimaryKey")
	public EngDivision findEngDivisionByPrimaryKey(String divisionsncode) {
		try {
			EngDivision plan = engDivisionService.findEngDivisionByKey(divisionsncode);
			return plan;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
