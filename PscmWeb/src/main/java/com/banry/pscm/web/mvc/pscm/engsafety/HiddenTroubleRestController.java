package com.banry.pscm.web.mvc.pscm.engsafety;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.account.SysUsersKey;
import com.banry.pscm.service.account.SysUsersService;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleBill;
import com.banry.pscm.service.engsafety.HiddenTroubleBillService;
import com.banry.pscm.service.engsafety.HiddenTroubleService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.Engineering;
import com.banry.pscm.service.schedule.EngineeringService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.EnumVarService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.service.workflow.ApprovalTrack;
import com.banry.pscm.service.workflow.WorkFlowService;
import com.banry.pscm.web.mvc.model.TroubleModel;
import com.banry.pscm.web.utils.DivLevelUtil;
import com.banry.pscm.web.utils.SystemConstants;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;

@RestController
public class HiddenTroubleRestController {
	
    @Autowired
	private HiddenTroubleBillService hiddenTroubleBillService;
    @Autowired
	private EngDivisionService engDivisionService;
    @Autowired
    private RuntimeService runtimeService;
	@Autowired
    private TaskService taskService;
	@Autowired
	private HiddenTroubleService hiddenTroubleService;
	@Autowired
	private ContractAttService contractAttService;
	@Autowired
	private SysUsersService userService;
	@Autowired
	private EnumVarService enumVarService;
	@Autowired
	private EngineeringService engineeringService;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private SystemConstants constants;
	
	private static Logger logger = LoggerFactory.getLogger(HiddenTroubleRestController.class);
	
	/**
	 * 获取隐患中当前正在施工的划分列表
	 * @author Xu Dingkui
	 * @date 2017年12月20日
	 * @param userid
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/troubleProcedureList")
	public Map troubleProcedureList(String userid, String pDivSnCode, String tenantCode) {
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			Map umap = new HashMap();
			SysUsersKey key = new SysUsersKey(userid, tenantCode);
			SysUsers u = userService.getByCode(key);
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
			umap.put("state", "当前正在施工的" + DivLevelUtil.getLevelValue(leafLevel));
			//当前正在施工的划分 已经交底或者计划日期已经到达 
			String sqlWhere = "div_level=" + leafLevel 
					//未完工
					+ " and exists (select 1 from sub_div_work_bill d where a.division_sn_code = d.division_sn_code and d.raw_con_map_quan + d.cons_map_sum_vary_quan > getSumFinishByDivSnCode(a.division_sn_code)) "
					//已经交底
					+ " and (exists (select 1 from tech_disclosure b where a.division_sn_code = b.division_sn_code and b.dis_date is not null)"
					//计划日期已经到达
					+ " or exists (select 1 from eng_division_plan c where a.division_sn_code = c.division_sn_code and c.start_date <= now()))"
					//权限
					+ " and checkEngDivAuthority('" + userid + "', a.division_sn_code) = 'Y'";
			if (pDivSnCode != null && !"".equals(pDivSnCode)) {
				sqlWhere += " and getParentDivSnCode(a.division_sn_code)='" + pDivSnCode + "'";
			}
			List<EngDivision> divList = engDivisionService.selectBySqlWhere("a.division_sn_code, a.div_name, a.div_item_code", sqlWhere);
			umap.put("procedure", divList);
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
	 * 获取此工序下的隐患清单 按照最近一个月上报次数排序
	 * @author Xu Dingkui
	 * @date 2018年1月11日
	 * @param userid
	 * @param Code
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/troubleList")
	public Map troubleList(String userid, String Code) {
		try {
			logger.info("获取此工序下的隐患清单");
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			EngDivision div = engDivisionService.findEngDivisionByKey(Code);
			if (div != null) {
				map.put("divitemcode", div.getDivItemCode());
				List<HiddenTroubleBill> htLst = hiddenTroubleBillService.findHiddenTroubleBillOrderByRate(div.getDivItemCode(),"");
				List<TroubleModel> tb = new ArrayList<TroubleModel>();
				for(HiddenTroubleBill ht : htLst) {
				   TroubleModel tm = new TroubleModel();
				   tm.setTroubleCode(ht.getTroubleCode());
				   tm.setTroubleCate(ht.getTroubleCate().getEnumValueName());
				   tm.setInvestContent(ht.getInvestContent());
				   tm.setInvestItem(ht.getInvestItem());
				   tm.setFrequency(ht.getFrequency());
				   tb.add(tm);
				}
				map.put("data", tb);
			} else {
				map.put("status", 1);
				map.put("message", "依据划分序号编码未找到工程划分");
			}
			return map;	
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
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
	 * 获取此工序下的隐患清单 按照隐患类别分组
	 * @author Xu Dingkui
	 * @date 2017年8月4日
	 * @param userid 用户ID
	 * @param Code 工序ID
	 * @param troubleFrom 隐患来源
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/troubleBill")
	public Map troubleBill(String userid, String Code, String troubleFrom) {
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			EngDivision div = engDivisionService.findEngDivisionByKey(Code);
			if (div != null) {
				map.put("divitemcode", div.getDivItemCode());
				Map<Integer,String> groups = new HashMap<Integer,String>();
				if (troubleFrom != null && !"".equals(troubleFrom)) {
					//质量隐患
					if ("1".equals(troubleFrom)) {
						troubleFrom = "质量隐患";
					} else {
						troubleFrom = "安全隐患";
					}
				} else {
					troubleFrom = "安全隐患";
				}
				List<HiddenTroubleBill> htLst = hiddenTroubleBillService.findHiddenTroubleBillByDivItemCode(div.getDivItemCode(), troubleFrom, "");
				for (HiddenTroubleBill ht : htLst) {
					if (ht.getTroubleCate() != null) {
						groups.put(ht.getTroubleCate().getEnumValue(),ht.getTroubleCate().getEnumValueName());
//						//查询枚举的valueName
//						EnumVar dict = new EnumVar();
//		            	dict.setEnumValue(Integer.parseInt(ht.getTrobulecate()));
//		            	if ("质量隐患".equals(ht.getTroblefrom())) {
//		            		dict.setEnumName("TrobuleCate");
//		            	} else {
//		            		dict.setEnumName("TrobuleCate1");
//		            	}
//		            	EnumVar dd = enumVarService.findEnumVarByPrimaryKey(dict);
//		            	if (dd != null) {
//		            		groups.put(ht.getTrobulecate(), dd.getEnumValueName());
//		            	} else {
//		            		groups.put(ht.getTrobulecate(),ht.getTrobulecate());
//		            	}
//					} else {
//						groups.put("","");
					}
				}
				List<Map> tbData = new ArrayList<Map>();
				//隐患类别为空的隐患
				List<TroubleModel> nulltb = new ArrayList<TroubleModel>();
				for(HiddenTroubleBill ht : htLst) {
				   if(ht.getTroubleCate() == null){
					   TroubleModel tm = new TroubleModel();
					   tm.setTroubleCode(ht.getTroubleCode());
					   tm.setInvestContent(ht.getInvestContent());
					   tm.setInvestItem(ht.getInvestItem());
					   tm.setFrequency(ht.getFrequency());
					   nulltb.add(tm);
				   }
				}
				for(Integer key : groups.keySet()){
					Map cate = new HashMap();
					cate.put("troublecate", groups.get(key));
					List<TroubleModel> tb = new ArrayList<TroubleModel>();
					for(HiddenTroubleBill ht : htLst) {
					   if(ht.getTroubleCate() != null && key.intValue() == ht.getTroubleCate().getEnumValue().intValue()
							   /*|| ("".equals(key) && (ht.getTrobulecate() == null || "".equals(ht.getTrobulecate())))*/){
						   TroubleModel tm = new TroubleModel();
						   tm.setTroubleCode(ht.getTroubleCode());
						   tm.setInvestContent(ht.getInvestContent());
						   tm.setInvestItem(ht.getInvestItem());
						   tm.setFrequency(ht.getFrequency());
						   tb.add(tm);
					   }
					}
					cate.put("troubleBill", tb);
					tbData.add(cate);
				}
				map.put("data", tbData);
				map.put("troubleBill", nulltb);
			} else {
				map.put("status", 1);
				map.put("message", "依据划分序号编码未找到工程划分");
			}
			return map;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
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
	 * 获取隐患详情
	 * @author Xu Dingkui
	 * @date 2017年12月13日
	 * @param userid
	 * @param troubleCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/troubleBillDetail")
	public Map troubleBillDetail(String userid, String troubleCode, String parentTenantAccount) {
		try {
			Map map = new HashMap();
			map.put("status", 0);
			map.put("message", "成功");
			HiddenTroubleBill ht = hiddenTroubleBillService.findHiddenTroubleBillByKey(troubleCode, parentTenantAccount);
			if (ht != null) {
				TroubleModel m = new TroubleModel();
				m.setTroubleCode(ht.getTroubleCode());
				//隐患类别
	        	if (ht.getTroubleCate() != null) {
	        		m.setTroubleCate(ht.getTroubleCate().getEnumValueName());	//隐患类别
	        	} else {
	        		m.setTroubleCate("");
	        	}
	        	//隐患级别
	        	if (ht.getTroubleLevel() != null) {
	            	m.setTroubleLevel(ht.getTroubleLevel().getEnumValueName());	//隐患类别
	        	} else {
	        		m.setTroubleLevel("");
	        	}
				//排查项目
				m.setInvestItem(ht.getInvestItem());
				//排查内容
				m.setInvestContent(ht.getInvestContent());
				//描述
				m.setDescription(ht.getDescription());
				map.put("data", m);
				//隐患描述列表
//				List<EnumVar> listGrid = enumVarService.findByEnumName("TroubleDesc");
//				map.put("troubleDesc", listGrid);
			} else {
				map.put("status", 1);
				map.put("message", "隐患不存在");
			}
			return map;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 上报隐患
	 * @author Xu Dingkui
	 * @date 2017年10月13日
	 * @param userid
	 * @param Code
	 * @param troubleCode
	 * @param takePlace
	 * @param describe
	 * @param deductDate
	 * @param realDeduct
	 * @param amercement
	 * @param rectifyTimeLimit
	 * @param files
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/reportTrouble")
	public Map reportTrouble(String userid,	//用户ID
			String Code,	//交底工序ID
			String troubleCode,	//隐患编码
			String takePlace,	//隐患发生地点
			String describe,	//描述
			String startDate,	//开始时间
			String deductDate,	//扣分时间
			String realDeduct,	//实际扣分
			String amercement,	//罚款金额
			String rectifyTimeLimit,	//整改期限(日期)
			String rectifypostpone,		//整改延期(数值)
			String attachUrl,	//附件URL
			String parentTenantAccount,
			String tenantCode
	) {
		try {
			//安全负责人
			EngDivision div = engDivisionService.findEngDivisionByKey(Code);//.getSafetychargeuser();
			if (div != null) {
				Map map = new HashMap();
				HiddenTroubleBill ht = hiddenTroubleBillService.findHiddenTroubleBillByKey(troubleCode, parentTenantAccount);
				if (ht == null) {
					map.put("status", 1);
					map.put("message", "隐患不存在");
					return map;
				}
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//				Map<String, Object> variables = new HashMap<String, Object>();
				map.put("status", 0);
				map.put("message", "成功");
				HiddenTrouble htt = new HiddenTrouble();
				htt.setTroubleCode(String.valueOf((new Date()).getTime()));
				htt.setTroubleBillItemCode(ht);;
				if (deductDate != null && !"".equals(deductDate)) {
					htt.setStartDate(startDate);
				} else {
					htt.setStartDate(sf.format(new Date()));
				}
				htt.setDivisionSnCode(Code);
				if (takePlace != null && !"".equals(takePlace)) {
					htt.setTakePlace(takePlace);
				}
				htt.setDescription(describe);
				if (deductDate != null && !"".equals(deductDate)) {
					htt.setDeductDate(sf.parse(deductDate));
				} else {
					htt.setDeductDate(new Date());
				}
				if (realDeduct != null && !"".equals(realDeduct)) {
					htt.setRealDeduct(Double.parseDouble(realDeduct));
				}
				if (amercement != null && !"".equals(amercement)) {
					htt.setAmercement(Double.parseDouble(amercement));
				}
				htt.setAmercement(0.0);
				if (rectifyTimeLimit != null && !"".equals(rectifyTimeLimit)) {
//					Calendar c = Calendar.getInstance();  
//					c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(rectifyTimeLimit));// 今天+1天
					htt.setRectifyTimeLimit(sf.parse(rectifyTimeLimit));
				}
				if (rectifypostpone != null && !"".equals(rectifypostpone)) {
					htt.setRectifyPostpone(Integer.parseInt(rectifypostpone));
				} else {
					htt.setRectifyPostpone(0);
				}
				
//				String safetyOfficer = div.getSafetychargeuser();
//				if (safetyOfficer != null) {
//					variables.put("personFlag", true);
//					variables.put("safetyOfficer", safetyOfficer);
//					htt.setSafetychargeuser(safetyOfficer);
//				} else {
//					variables.put("personFlag", false);
//				}
//				if (htt.getRectifypostpone() > 0) {
//					variables.put("delayFlag", true);
//				} else {
//					variables.put("delayFlag", false);
//				}
//				ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProcessDefinitionKey.HIDDENTROUBLE,
//						htt.getTroubletreacode(), variables);
//				htt.setProcessid(processInstance.getId());
//				HistoricActivityInstance hai = historyService.createHistoricActivityInstanceQuery()//
//			    .processInstanceId(processInstance.getId())//
//			    .unfinished()//未完成的活动(任务)
//			    .singleResult();
//				if (hai != null) {
//					//处理状态
//					htt.setTreatstate(hai.getActivityId());
//					//获取下一环节可执行人
//					List<IdentityLink> list = taskService.getIdentityLinksForTask(hai.getTaskId());// 获取列表
//					//推送别名
//					Set<String> aliasSet=new HashSet<String>();
//					if (list != null && list.size() > 0) {
//						Map<String, String> extras = new HashMap<String, String>();
//						extras.put("divisionsncode", htt.getDivsncode());
//						extras.put("troubleId", htt.getTroubletreacode());
//						DataDictionary dict = new DataDictionary();
//						//隐患类别
//		            	if (ht.getTrobulecate() != null && !"".equals(ht.getTrobulecate())) {
//			            	dict.setDatacode(ht.getTrobulecate());
//			            	if ("质量隐患".equals(ht.getTroblefrom())) {
//			            		dict.setDatatype("TrobuleCate");
//			            	} else {
//			            		dict.setDatatype("TrobuleCate1");
//			            	}
//			            	DataDictionary dd = dataDictionaryService.findDataDictionaryByPrimaryKey(dict);
//			            	if (dd != null) {
//			            		extras.put("troubleCate", dd.getDatavalue());
//			            	}
//		            	} else {
//		            		extras.put("troubleCate", "");
//		            	}
//		            	//隐患级别
//		            	if (ht.getTrobulelevel() != null && !"".equals(ht.getTrobulelevel())) {
//			            	dict.setDatacode(ht.getTrobulelevel());
//			            	dict.setDatatype("Trobulelevel");
//			            	DataDictionary dd = dataDictionaryService.findDataDictionaryByPrimaryKey(dict);
//			            	if (dd != null) {
//			            		extras.put("troubleLevel", dd.getDatavalue());
//			            	}
//		            	} else {
//		            		extras.put("troubleLevel", "");
//		            	}
//		            	//排查项目
//		            	extras.put("investItem", ht.getInvestitem());
//		            	//排查内容
//		            	extras.put("investContent", ht.getInvestcontent());
//		            	//隐患说明
//		            	extras.put("description", htt.getDescription());
//		            	//LEC评价值
//		            	extras.put("lecValue", ht.getLecvalue());
//		            	//整改期限
//		            	extras.put("rectifyTimeLimit", sf.format(htt.getRectifytimelimit()));
//		            	//整改延期
//		            	extras.put("rectifypostpone", String.valueOf(htt.getRectifypostpone()));
//		            	//安全责任人
//		            	if (htt.getSafetychargeuser() != null) {
//		            		extras.put("personLiable", htt.getSafetychargeuser());
//		            	} else {
//		            		extras.put("personLiable", "");
//		            	}
//		            	//状态
//		            	extras.put("treatstate", htt.getTreatstate());
//		            	extras.put("taskId", hai.getTaskId());
//		            	if (htt.getRectifysteps() != null) {
//		            		extras.put("rectifysteps", htt.getRectifysteps());	
//		            	} else {
//		            		extras.put("rectifysteps", "");
//		            	}
//			            for (IdentityLink il : list) {
//			            	if (IdentityLinkType.CANDIDATE.equals(il.getType())) {
//			            		List<SysUsers> userLst = roleService.findUsersByRole(il.getGroupId());
//			            		for (SysUsers u : userLst) {
//			            			aliasSet.add(u.getUsercode());
//			            		}
//			            	} else if (IdentityLinkType.ASSIGNEE.equals(il.getType())) {
//			            		aliasSet.add(il.getUserId());
//			            	}
//			            }
//			    		if (!aliasSet.isEmpty()) {
//			    			logger.info("隐患处理推送:"+htt.getTroubletreacode()+"-"+ht.getInvestitem());
//				            MessagePush push= new MessagePush("有您需要审批的内容"/*ht.getInvestitem()+"-"+hai.getActivityName()*/,"隐患处理", extras);
//				            long msgId = push.sendPushAlias(aliasSet);
//			    		}
//			        }
//				}
				if (attachUrl != null && !"".equals(attachUrl)) {
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
				SysUsersKey key = new SysUsersKey(userid, tenantCode);
				SysUsers u = userService.getByCode(key);
				hiddenTroubleService.startProcessInstance(htt, ProcessDefinitionKey.HIDDENTROUBLE, u.getUserName(), tenantCode, parentTenantAccount);
				return map;
			} else {
				Map map = new HashMap();
				map.put("status", 1);
				map.put("message", "划分不存在");
				return map;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 隐患上传附件
	 * @author Xu Dingkui
	 * @date 2017年10月15日
	 * @param attachUrl
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/troubleUploadAttach")
	public Map troubleUploadAttach(@RequestParam("attachUrl") MultipartFile[] attachUrl) {

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
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/customTroubleBill")
	public Map customTroubleBill(String userid,	//用户ID
			String divisionSncode,	//交底工序ID
//			String troubleCode,	//隐患编码
			String investItem,	//排查项目
			String trobuleCate,	//隐患类别
			String trobuleLevel,//隐患级别
			String influScope,	//隐患影响范围
			String influfactorcate, //隐患因素类别
			String lecValue,	//LEC评价值
			String troubleContent,	//排查内容
			String trobleFrom,	//隐患来源
			String description		//隐患说明
	) {
		try {
			//查询划分
			EngDivision div = engDivisionService.findEngDivisionByKey(divisionSncode);
			if (div != null) {
				if (div.getDivItemCode() != null && !"".equals(div.getDivItemCode())) {
					Map map = new HashMap();
					map.put("status", 0);
					map.put("message", "成功");
					HiddenTroubleBill ht = new HiddenTroubleBill();
					ht.setDivItemCode(div.getDivItemCode());
					ht.setTroubleCode(String.valueOf(new Date().getTime()));
					ht.setInvestItem(investItem);
					EnumVar cate = new EnumVar();
					cate.setEnumValue(Integer.parseInt(trobuleCate));
					ht.setTroubleCate(cate);
					EnumVar level = new EnumVar();
					level.setEnumValue(Integer.parseInt(trobuleLevel));
					ht.setTroubleLevel(level);
					ht.setInvestContent(troubleContent);
					ht.setTroubleFrom(trobleFrom);
					ht.setDescription(description);
					ht.setFromCode("现场增加");
					hiddenTroubleBillService.saveHiddenTroubleBill(ht);
					map.put("troublecode", ht.getTroubleCode());
					return map;
				} else {
					Map map = new HashMap();
					map.put("status", 1);
					map.put("message", "此划分的划分项编码为空");
					return map;
				}
			} else {
				Map map = new HashMap();
				map.put("status", 1);
				map.put("message", "划分不存在");
				return map;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}

	/**
	 * 获取此工序下隐患列表数据
	 * @author Xu Dingkui
	 * @date 2017年10月15日
	 * @param userid
	 * @param Code
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/handleTrouble")
	public Map handleTrouble(String userid, String Code, String status, String parentTenantAccount) {
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			if (!"1".equals(status) && !"2".equals(status)) {
				map.put("status", 1);
				map.put("message", "状态条件不正确，1：未整改，2：已整改");
				return map;
			}
			List<TroubleModel> results = new ArrayList();
			List<Task> tasks = new ArrayList<Task>();
	        // 根据当前人的ID查询
	        List<Task> todoList = taskService.createTaskQuery().processDefinitionKey(ProcessDefinitionKey.HIDDENTROUBLE).taskAssignee(userid).list();
	        // 根据当前人未签收的任务
	        List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(ProcessDefinitionKey.HIDDENTROUBLE).taskCandidateUser(userid).list();
	        // 合并
	        tasks.addAll(todoList);
	        tasks.addAll(unsignedTasks);
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	        // 根据流程的业务ID查询实体并关联
	        for (Task task : tasks) {
	            String processInstanceId = task.getProcessInstanceId();
	            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	            String businessKey = processInstance.getBusinessKey();
	            if (businessKey != null) {
		            HiddenTrouble htt = hiddenTroubleService.findHiddenTroubleByPrimaryKey(businessKey, parentTenantAccount);
		            if (htt == null) {
		            	continue;
		            }
		            HiddenTroubleBill ht = htt.getTroubleBillItemCode();
		            if (Code.equals(htt.getDivisionSnCode())) {
		            	//未整改
		            	if ("1".equals(status)
		            			//延期整改
		            			&& 3 != htt.getStatus().getEnumValue().intValue()
		            			//分配责任人
		            			&& 2 != htt.getStatus().getEnumValue().intValue()
		            			//整改中
		            			&& 4 != htt.getStatus().getEnumValue().intValue()) {
		            		continue;
		            	}
		            	//已整改
		            	if ("2".equals(status)
		            			//延期整改
		            			&& (3 == htt.getStatus().getEnumValue().intValue()
		            			//分配责任人
		            			|| 2 == htt.getStatus().getEnumValue().intValue()
		            			//整改中
		            			|| 4 == htt.getStatus().getEnumValue().intValue())) {
		            		continue;
		            	}
		            	TroubleModel model = new TroubleModel();
		            	model.setTroubleId(htt.getTroubleCode());
		            	EnumVar dict = new EnumVar();
		            	if (ht.getTroubleCate() != null) {
		            		model.setTroubleCate(ht.getTroubleCate().getEnumValueName());
		            	} else {
		            		model.setTroubleCate("");
		            	}
		            	if (ht.getTroubleLevel() != null) {
		            		model.setTroubleLevel(ht.getTroubleLevel().getEnumValueName());
		            	} else {
		            		model.setTroubleLevel("");
		            	}
		            	model.setInvestItem(ht.getInvestItem());
		            	model.setInvestContent(ht.getInvestContent());
		            	model.setDescription(htt.getDescription());
		            	//计算整改天数
		            	if (htt.getStartDate() != null && !"".equals(htt.getStartDate()) && htt.getRectifyTimeLimit() != null) {
		            		long day = 0;
		            		Date beginDate = sf.parse(htt.getStartDate());
		            		day = (htt.getRectifyTimeLimit().getTime()-beginDate.getTime())/(24*60*60*1000) + 1;
		            		model.setRectifydays(String.valueOf(day+htt.getRectifyPostpone()));
		            	}
		            	model.setRectifyTimeLimit(sf.format(htt.getRectifyTimeLimit()));
		            	model.setRectifypostpone(String.valueOf(htt.getRectifyPostpone()));
		            	if (htt.getSafetyChargeUser() != null) {
		            		model.setPersonLiable(htt.getSafetyChargeUser());
		            	} else {
		            		model.setPersonLiable("");
		            	}
		            	model.setTreatstate(String.valueOf(htt.getStatus().getEnumValue()));
		            	model.setTaskId(task.getId());
		            	if (htt.getRectifySteps() != null) {
		            		model.setRectifysteps(htt.getRectifySteps());	
		            	} else {
		            		model.setRectifysteps("");
		            	}
			            results.add(model);
		            }
	            }
	        }
			map.put("data", results);
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
	 * 选择此工序下隐患责任人
	 * @author Xu Dingkui
	 * @date 2017年10月15日
	 * @param Code
	 * @param dangerId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/dangerPerson")
	public Map dangerPerson(HttpServletRequest request, String Code/*, String dangerId, String userId*/) {
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
//			List<SysUsers> listGrid = userService.selectByDivSnCode(Code);
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<SysUsers> listGrid = userService.findAllUser(tenantCode);
			map.put("data", listGrid);
			return map;
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 对隐患进行审批
	 * @author Xu Dingkui
	 * @date 2017年10月17日
	 * @param Code
	 * @param dangerId
	 * @param userId
	 * @param approvalContent
	 * @param measures
	 * @param personLiable
	 * @param rectifypostpone 延期天数
	 * @param approvalLevel
	 * @param status
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/approvalDanger")
	public Map approvalDanger(String taskId, String dangerId, String userId, String approvalContent,
			String measures, String personLiable, String rectifypostpone, String approvalLevel, String status,// String troubleTreatCode,
			String attachUrl, String parentTenantAccount, String tenantCode) {
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			HiddenTrouble htt = hiddenTroubleService.findHiddenTroubleByPrimaryKey(dangerId, parentTenantAccount);
			if (htt == null ) {
				map.put("status", 1);
				map.put("message", "隐患处理不存在");
				return map;
			}
			SysUsersKey key = new SysUsersKey(userId, tenantCode);
			SysUsers u = userService.getByCode(key);
			if (u == null) {
				map.put("status", 1);
				map.put("message", "用户不存在");
				return map;
			}
	        Map<String, Object> variables = new HashMap<String, Object>();
	        //分配责任人
	        if (htt.getStatus().getEnumValue().intValue() == 2) {
	        	if (personLiable != null && !"".equals(personLiable)) {
		        	htt.setSafetyChargeUser(personLiable);
		        	variables.put("safetyOfficer", personLiable);
	        	} else {
	        		map.put("status", 1);
					map.put("message", "责任人不能为空");
					return map;
	        	}
	        //整改延期
	        } else if (htt.getStatus().getEnumValue().intValue() == 3) {
	        	if (rectifypostpone != null && !"".equals(rectifypostpone) && Integer.parseInt(rectifypostpone) > 0) {
	        		htt.setRectifyPostpone(Integer.parseInt(rectifypostpone));
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            	Calendar cl = Calendar.getInstance();
	                cl.setTime(htt.getRectifyTimeLimit());
	                //延期后的期限
	                cl.add(Calendar.DATE, Integer.parseInt(rectifypostpone) + htt.getRectifyPostpone());
	            	variables.put("duTime", sdf.format(cl.getTime()) + "T23:59:59");
	            	variables.put("rectifypostpone", rectifypostpone);
	        	} else {
	        		map.put("status", 1);
					map.put("message", "延期天数不能为空");
					return map;
	        	}
	        //整改
	        } else if (htt.getStatus().getEnumValue().intValue() == 4) {
	        	if (measures == null || "".equals(measures)) {
	        		map.put("status", 1);
					map.put("message", "整改措施不能为空");
					return map;
	        	}
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    			String now = sdf.format(new Date());
    			Calendar c = Calendar.getInstance();
    			c.setTime(htt.getRectifyTimeLimit());
    			//整改期限+延期天数
    			c.add(Calendar.DAY_OF_YEAR, htt.getRectifyPostpone());
    			if (c.getTime().before(sdf.parse(now))) {
    				variables.put("timeoutFlag", true);
    			} else {
    				variables.put("timeoutFlag", false);
    			}
    			variables.put("safetyOfficer", userId);
    			variables.put("rectifysteps", measures);
//    			htt.setRectifysteps(measures);
//    			htt.setRectifytime(sdf.parse(now));
    		//安全部长选择隐患级别
	        } else if (htt.getStatus().getEnumValue().intValue() == 5) {
	        	//通过时需要选择级别
	        	if ("0".equals(status)) {
	        		if (approvalLevel == null || "".equals(approvalLevel)) {
	        			map.put("status", 1);
						map.put("message", "级别不能为空");
						return map;
	        		}
	        	}
	        	variables.put("trobuleLevel", approvalLevel);
	        	variables.put("ministerApprove", "0".equals(status));
	        //安全副经理
	        } else if (htt.getStatus().getEnumValue().intValue() == 6) {
	        	//通过时需要选择级别
	        	if ("0".equals(status)) {
	        		if (approvalLevel == null || "".equals(approvalLevel)) {
	        			map.put("status", 1);
						map.put("message", "级别不能为空");
						return map;
	        		}
	        	}
	        	variables.put("trobuleLevel", approvalLevel);
	        	variables.put("securityManagerApprove", "0".equals(status));
	        //项目经理
	        } else if (htt.getStatus().getEnumValue().intValue() == 7) {
	        	variables.put("projectManagerApprove", "0".equals(status));
	        }
            hiddenTroubleService.complete(taskId, variables, userId, dangerId, attachUrl);
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
	 * 
	 * @author Xu Dingkui
	 * @date 2017年12月21日
	 * @param dataType
	 * @return
	 */
	@RequestMapping(value="/getDataDict")
	@SuppressWarnings("unchecked")
	public Object getDataDict(String dataType) {
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			List<EnumVar> listGrid = enumVarService.findByEnumName(dataType);
			map.put("data", listGrid);
			return map;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 获取审批记录
	 * 
	 * @author Xu Dingkui
	 * @date 2017年8月26日
	 * @param troubleTreatCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getTrackFileList")
	public Object getTrackFileList(String troubleTreatCode, String tenantCode, String parentTenantAccount) {
		Map map = new HashMap();
		try {
			map.put("status", 0);
			map.put("message", "成功");
			List<ApprovalTrack> listGrid = workFlowService.getHisUserTaskActivityInstanceList(ProcessDefinitionKey.HIDDENTROUBLE + "-" + troubleTreatCode, tenantCode, parentTenantAccount);
			HiddenTrouble ht = hiddenTroubleService.selectByKeyWithoutBill(troubleTreatCode);
			List fileGrid = contractAttService.findByFileInNames(ht.getTroubleAttach());
			map.put("trackData", listGrid);
			map.put("fileData", fileGrid);
			return map;
		} catch (Exception e) {
			map.put("status", 1);
			map.put("message", e.getMessage());
			return map;
		}
	}
}
