package com.banry.pscm.web.mvc.pscm.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.banry.pscm.service.schedule.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.web.mvc.model.DataTableModel;

@Controller
@RequestMapping("/techDisclosure")
public class TechDisclosureController {

	@Autowired
	private ScheduleLaborService laborService;
	@Autowired
	private TechDisclosureService techDisclosureService;
	@Autowired
	private EngDivisionService engDivisionService;
	@Autowired
	private EngDivFinishRateService engDivFinishRateService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor cust = new CustomDateEditor(sdf, true);
		bin.registerCustomEditor(Date.class, cust);
	}

	@RequestMapping("/getTechDisclosureList")
	@ResponseBody
	public Object getTechDisclosureList(String disclosurer) {
		DataTableModel data = new DataTableModel();
		try {
			List<TechDisclosure> listGrid = techDisclosureService.findTechDisclosureBySqlWhere("");
			data.setData(listGrid);
			return data;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}

	// @RequestMapping("/edit")
	// public Object edit(String disclosureCode) {
	// ModelAndView mv = new ModelAndView("schedule/techDisclosure-edit");
	// try {
	// //编辑时获取隐患信息
	// if (disclosureCode != null && !"".equals(disclosureCode)) {
	// TechDisclosure r =
	// techDisclosureService.findTechDisclosureByPrimaryKey(disclosureCode);
	// if (r != null) {
	// mv.addObject("r", r);
	// mv.addObject("flag", "U");
	// } else {
	// mv.addObject("msg", "技术交底不存在");
	// }
	// } else {
	// mv.addObject("flag", "I");
	// mv.addObject("msg", "");
	// }
	// //工程划分
	// List<EngDivision> divLst =
	// engDivisionService.selectBySqlWhere("a.DivisionCode, a.DivName",
	// "a.DivLevel=10 and not exists (select 1 from tech_disclosure b where
	// a.DivisionCode = b.DisDivCode)");
	// mv.addObject("divLst", divLst);
	// } catch (ScheduleException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return mv;
	// }

	@RequestMapping("/edit")
	public Object edit(String divSnCode) {
		ModelAndView mv = new ModelAndView("schedule/techDisclosure-edit");
		try {
			// 编辑时获取隐患信息
			if (divSnCode != null && !"".equals(divSnCode)) {
				String sql="division_sn_code='"+divSnCode+"'and dis_date is null";
				List<TechDisclosure> r = techDisclosureService.findTechDisclosureBySqlWhere(sql);
				
				if (r.size()>0) {
					mv.addObject("r", r.get(0));
					mv.addObject("flag", "U");
				} else {
					mv.addObject("flag", "I");
					mv.addObject("disdivsncode", divSnCode);
				}
			} else {
				mv.addObject("flag", "I");
				mv.addObject("msg", "");
				mv.addObject("disdivsncode", "");
			}
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping("/findTechDisclosureByDisDivSnCodeAndDisDate")
	@ResponseBody
	public Object findTechDisclosureByDisDivSnCodeAndDisDate(String divSnCode) {
		try {
			String sql="division_sn_code='"+divSnCode+"'and dis_date is null";
			List<TechDisclosure> r = techDisclosureService.findTechDisclosureBySqlWhere(sql);
			if(r.size() > 0){
				return r.get(0);	
			}else{
				return null;
			}
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
//	@RequestMapping("/findTechDisclosureByDisDivSnCode")
//	@ResponseBody
//	public Object findTechDisclosureByDisDivSnCode(String divSnCode) {
//		try {
//			TechDisclosure r = techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode);
//			return r;
//		} catch (ScheduleException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}

	// @RequestMapping("/view")
	// public Object view(String disclosureCode) {
	// ModelAndView mv = new ModelAndView("schedule/techDisclosure-view");
	// try {
	// //获取技术交底
	// if (disclosureCode != null && !"".equals(disclosureCode)) {
	// TechDisclosure r =
	// techDisclosureService.findTechDisclosureByPrimaryKey(disclosureCode);
	// mv.addObject("r", r);
	// }
	// } catch (ScheduleException e) {
	// e.printStackTrace();
	// }
	// return mv;
	// }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/acceptTechDisclosureByDisDivSnCode")
	@ResponseBody
	public Map acceptTechDisclosureByDisDivSnCode(String divSnCode) {
		Map map = new HashMap();
		try {
			// 获取技术交底
			if (divSnCode != null && !"".equals(divSnCode)) {
				String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
				List<TechDisclosure> rList = techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode);
				if(rList.size()>0){
					for(TechDisclosure r : rList){
						if(r.getDisDate()!=null){
							ScheduleLabor lab = new ScheduleLabor();
							lab.setDivisionsncode(divSnCode);
							lab.setIdcard(userCode);
							// 查询当前用户的劳务数据
							lab = laborService.findLaborByPrimaryKey(lab);
							if (lab != null) {
								if (lab.getLaborcompany() != null && r.getRecipientRole() != null
										&& lab.getLaborcompany().equals(r.getRecipientRole())) {
									map.put("r", r);
									if (r.getDisRecipient() != null && r.getDisRecipient().indexOf(userCode) > -1) {
										map.put("retMsg", "已接受交底");
									} else {
										map.put("retMsg", "");
									}
								} else {
									map.put("retMsg", "当前工程划分的劳务公司与当前用户的劳务公司不一致");
								}

							} else {
								map.put("retMsg", "劳务表不存在当前用户，请先维护劳务数据");
							}

//						} else {
//							map.put("retMsg", "该工程划分还未交底");
						}
					}
				}else{
					map.put("retMsg", "该工程划分还未交底");
				}
			}
		} catch (ScheduleException e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("/view")
	public Object view(String divSnCode) {
		ModelAndView mv = new ModelAndView("schedule/techDisclosure-view");
		try {
			//mv.addObject("retMsg", "");
			// 获取技术交底
			if (divSnCode != null && !"".equals(divSnCode)) {
				String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal()).getUserCode();
				List<TechDisclosure> rList = techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode);
				if(rList.size()>0){
					for(TechDisclosure r : rList){
						if (r.getDisDate() != null) {
							ScheduleLabor lab = new ScheduleLabor();
							lab.setDivisionsncode(divSnCode);
							lab.setIdcard(userCode);
							// 查询当前用户的劳务数据
							lab = laborService.findLaborByPrimaryKey(lab);
							if (lab != null) {
								if (lab.getLaborcompany() != null && r.getRecipientRole() != null
										&& lab.getLaborcompany().equals(r.getRecipientRole())) {
									mv.addObject("r", r);
									if (r.getDisRecipient() != null && r.getDisRecipient().indexOf(userCode) > -1) {
										mv.addObject("retMsg", "已接受交底");
									} else {
										mv.addObject("retMsg", "");
									}
								} else {
									mv.addObject("retMsg", "当前工程划分的劳务公司与当前用户的劳务公司不一致");
								}
								
							} else {
								mv.addObject("retMsg", "劳务表不存在当前用户，请先维护劳务数据");
							}
							
//						} else {
//							mv.addObject("retMsg", "该工程划分还未交底");
						}
						
					}
				}else {
					mv.addObject("retMsg", "该工程划分还未交底");
				}
			} else {
				mv.addObject("retMsg", "");
			}
		} catch (ScheduleException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * @author chenJuan
	 * @data 2018-3-1
	 * 根据划分编码获得tech-disclosure表数据
	 * @param divSnCode划分编码
	 * @return  data
	 */
	@RequestMapping("/getSaveList")
	@ResponseBody
	public Object getSaveList(String divSnCode){
		DataTableModel data = new DataTableModel();
		try {
			if (divSnCode != null && !"".equals(divSnCode)) {
				List<TechDisclosure> techList = techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode);
				data.setData(techList);
			}else{
				data.setData(new ArrayList<TechDisclosure>());
			}
			return data;
		} catch (ScheduleException e) {
			e.printStackTrace();
			return null;
		} 
	}

	/**
	 * 保存技术交底
	 * @author chenjuani
	 * @date 2018年2月28日
	 * @param td
	 * @return Boolean
	 */
	@RequestMapping("/saveTechDisclosure")
	@ResponseBody
	public Map saveTechDisclosure(TechDisclosure td) {
		Map map=new HashMap();
		try {
//			String s  = "";
//			s = td.getDisdivsncode();
			//td.setDisdivsncode("1.1.1.1");;
			String sql="division_sn_code='"+td.getDivisionSnCode().getDivisionSnCode()+"'and dis_date is null";
			List<TechDisclosure> rList = techDisclosureService.findTechDisclosureBySqlWhere(sql);
			TechDisclosure r = new TechDisclosure();
			String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			String laborComp = laborService.selectLaborCompanyByDivSnCode(td.getDivisionSnCode().getDivisionSnCode());
			if(rList.size()>0){
				r=rList.get(0);
				if(r!=null && r.getDisDate()==null){  //如果有对象存在，修改
					r.setDisclosurer(userCode);
					r.setRecipientRole(laborComp);
					r.setDisContent(td.getDisContent().trim());
					r.setDisInclude(td.getDisInclude());
					if (r.getDisAttach() != null && !"".equals(r.getDisAttach())) {
						r.setDisAttach(r.getDisAttach() + "," + td.getDisAttach());
					} else {
						r.setDisAttach(td.getDisAttach());
					}
					techDisclosureService.saveTechDisclosure(r);
					map.put("result", true);
					map.put("retMsg", "");
					map.put("disCode", td.getDisclosureCode());
				}
			}else{  //如果没有对象存在，新增
				td.setDisDate(null);
				td.setDisclosureCode(String.valueOf((new Date()).getTime()));
				td.setDisclosurer(userCode);
				if (laborComp != null && !"".equals(laborComp)) {
					td.setRecipientRole(laborComp);
					techDisclosureService.saveTechDisclosure(td);
					map.put("result", true);
					map.put("retMsg", "");
					map.put("disCode", td.getDisclosureCode());
				} else {
					map.put("result", false);
					map.put("retMsg", "没有找到该划分的劳务公司");
				}
			}
			return map;
		} catch (ScheduleException e) {
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * 提交技术交底
	 * 
	 * @author Xu Dingkui
	 * @date 2017年11月21日
	 * @param td
	 * @param flag
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/submitTechDisclosure")
	@ResponseBody
	public Map submitTechDisclosure(TechDisclosure td, String flag) {
		Map map = new HashMap();
		try {
			// 新增
			//if ("I".equals(flag)) {
				List<TechDisclosure> listGrid = techDisclosureService.findTechDisclosureBySqlWhere("division_sn_code='" + td.getDivisionSnCode().getDivisionSnCode() + "'");
				List<ScheduleLabor> laborList = laborService.findLaborByDivSnCode(td.getDivisionSnCode().getDivisionSnCode());
				for(TechDisclosure t : listGrid){
					if(t.getDisDate()!=null){
						for(ScheduleLabor la : laborList){
							if(t.getDisRecipient()!=null && t.getDisRecipient().contains(la.getLabor())){
								continue;
							}else{
								map.put("result", false);
								map.put("retMsg", "接受交底后才可以继续提交");
								return map;
							}
						}
					}
				}
				String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
				td.setDisclosurer(userCode);
				td.setDisContent(td.getDisContent().trim());
					
			//} else {
//				List<TechDisclosure> listGrid = techDisclosureService.findTechDisclosureBySqlWhere("division_sn_code='" + td.getDisdivsncode() + "'");
//				if (listGrid.size() > 0) {
//					map.put("result", false);
//					map.put("retMsg", "该工程划分已经交底");
//					return map;
//				}
//				td.setDisclo(null);
//			}
			// 依据当前划分查询劳务公司
			String laborComp = laborService.selectLaborCompanyByDivSnCode(td.getDivisionSnCode().getDivisionSnCode());
			if (laborComp != null && !"".equals(laborComp)) {
				td.setRecipientRole(laborComp);
				td.setDisDate(new Date());
				techDisclosureService.saveTechDisclosure(td);
				map.put("result", true);
				map.put("retMsg", "");
				map.put("disCode", td.getDisclosureCode());
			} else {
				map.put("result", false);
				map.put("retMsg", "没有找到该划分的劳务公司");
			}
			return map;
		} catch (ScheduleException e) {
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}

	/**
	 * 获取工人接受交底的数据
	 * 
	 * @author Xu Dingkui
	 * @date 2017年8月28日
	 * @param disclo
	 * @return
	 */
	@RequestMapping("/getAcceptTechDisclosureList")
	@ResponseBody
	public Object getAcceptTechDisclosureList() {
		DataTableModel data = new DataTableModel();
		try {
			String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal()).getUserCode();
			List<TechDisclosure> listGrid = techDisclosureService.findTechDisclosureByDisclo(userCode);
			data.setData(listGrid);
			return data;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}

	@RequestMapping("/getDivNameByDivisionsncode")
	@ResponseBody
	public Object getDivNameByDivCode(String divisionsncode) {
		String divname = "";
		try {
			EngDivision eng = engDivisionService.findEngDivisionByKey(divisionsncode);
			if (eng != null) {
				divname = eng.getDivName();
				return divname;
			} else {
				return divname;
			}
		} catch (ScheduleException e) {
			e.printStackTrace();
			return divname;
		}
	}

	/**
	 * 工人对交底进行确认
	 * 
	 * @author Xu Dingkui
	 * @date 2017年7月17日
	 * @param idcard
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/acceptTechDisclosure")
	@ResponseBody
	public Map acceptTechDisclosure(String disclosurecode) {
		Map map = new HashMap();
		try {
			String idcard = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.getUserCode();
			TechDisclosure td = techDisclosureService.findTechDisclosureByPrimaryKey(disclosurecode);
			if (td != null) {
				if (td.getDisRecipient() != null && !"".equals(td.getDisRecipient())) {
					if (td.getDisRecipient().indexOf(idcard) > -1) {
						map.put("result", false);
						map.put("retMsg", "已经交底确认");
						return map;
					}
					td.setDisRecipient(td.getDisRecipient() + "," + idcard);
				} else {
					td.setDisRecipient(idcard);
				}
				techDisclosureService.saveTechDisclosure(td);
				map.put("result", true);
				map.put("retMsg", "");
				return map;
			} else {
				map.put("result", false);
				map.put("retMsg", "该划分还未交底");
				return map;
			}
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}

	@RequestMapping("/getEngDivFinishRateList")
	@ResponseBody
	public Object getEngDivFinishRateList() {
		DataTableModel data = new DataTableModel();
		try {
			List<EngDivFinishRate> listGrid = engDivFinishRateService.findEngDivFinishRateBySqlWhere("");
			data.setData(listGrid);
			return data;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}

	// @RequestMapping("/editEngDivFinishRate")
	// public Object editEngDivFinishRate(EngDivFinishRate edfr) {
	// ModelAndView mv = new ModelAndView("schedule/engDivFinishRate-edit");
	// try {
	// //编辑时获取进度信息
	// if (edfr.getDivsncode() != null && !"".equals(edfr.getDivsncode())) {
	// EngDivFinishRate r =
	// engDivFinishRateService.findEngDivFinishRateByPrimaryKey(edfr);
	// if (r != null) {
	// mv.addObject("r", r);
	// mv.addObject("flag", "U");
	// } else {
	// mv.addObject("msg", "工程进度不存在");
	// }
	// } else {
	// mv.addObject("flag", "I");
	// mv.addObject("msg", "");
	// }
	// //工程划分
	// List<EngDivision> divLst =
	// engDivisionService.selectBySqlWhere("a.DivisionCode, a.DivName",
	// "a.DivLevel=10 and exists(select 1 from tech_disclosure b where
	// a.DivisionCode = b.DisDivCode)");
	// mv.addObject("divLst", divLst);
	// } catch (ScheduleException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return mv;
	// }

	/*	*//**
			 * 获取进度填报信息
			 * 
			 * @author Xu Dingkui
			 * @date 2017年11月21日
			 * @param divSnCode
			 * @return
			 *//*
			 * @SuppressWarnings({ "rawtypes", "unchecked" })
			 * 
			 * @RequestMapping("/getEngDivFinishRate")
			 * 
			 * @ResponseBody public Map getEngDivFinishRate(String divSnCode) {
			 * Map map = new HashMap(); try { //获取进度信息 if (divSnCode != null &&
			 * !"".equals(divSnCode)) { String userCode =
			 * ((CustomUserDetails)SecurityContextHolder.getContext().
			 * getAuthentication().getPrincipal()).getUserCode();
			 * List<EngDivisionModel> filldata =
			 * engDivisionService.selectTechnologyLeaderFillData(userCode,
			 * divSnCode, ""); if (filldata.size() > 0) { EngDivisionModel
			 * engDiv = filldata.get(0); // //查看是否已经交底 // TechDisclosure td =
			 * techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode)
			 * ; // //如果已经交底或者计划日期早于当前日期则可以交底 // SimpleDateFormat sf = new
			 * SimpleDateFormat("yyyy-MM-dd"); // if
			 * (sf.parse(engDiv.getStartDate()).before(new Date()) || td !=
			 * null) { map.put("r", engDiv); map.put("retMsg", ""); // } else {
			 * // map.put("retMsg", "未交底且还未到计划日期"); // } } else {
			 * map.put("retMsg", "未交底且还未到计划日期"); } } } catch (ScheduleException
			 * e) { // TODO Auto-generated catch block e.printStackTrace(); // }
			 * catch (ParseException e) { // // TODO Auto-generated catch block
			 * // e.printStackTrace(); // map.put("retMsg", "日期转换出错"); } return
			 * map; }
			 */

	/**
	 * 获取进度填报信息
	 * 
	 * @author chenjuan
	 * @date 2018年2月8日
	 * @param divSnCode
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getEngDivFinishRate")
	@ResponseBody
	public Map getEngDivFinishRate(String divSnCode) {
		Map map = new HashMap();
		try {
			// 获取进度信息
			if (divSnCode != null && !"".equals(divSnCode)) {
				String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal()).getUserCode();
				List<EngDivisionModel> filldata = engDivisionService.selectTechnologyLeaderFillData(userCode, divSnCode,
						"");
				if (filldata.size() > 0) {
					EngDivisionModel engDiv = filldata.get(0);
					map.put("r", engDiv);
					map.put("retMsg", "");
				} else {
					map.put("retMsg", "未交底且还未到计划日期");
				}
			}
			String takeplace = "";// 隐患发生地点
			String[] arr = divSnCode.split("\\.");
			if (arr.length > 2) {
				String s1 = arr[0];
				String s2 = s1 + "." + arr[1];
				String s3 = s2 + "." + arr[2];
				String divname1 = "";
				String divname2 = "";
				String divname3 = "";
				List<EngDivision> all = engDivisionService.findAllEngDivision();
				for (EngDivision eng : all) {
					if (eng.getDivisionSnCode().equals(s1)) {
						divname1 = eng.getDivName();
					}
					if (eng.getDivisionSnCode().equals(s2)) {
						divname2 = eng.getDivName();
					}
					if (eng.getDivisionSnCode().equals(s3)) {
						divname3 = eng.getDivName();
					}
				}
				takeplace = divname1 + ">" + divname2 + ">" + divname3;
				map.put("takeplace", takeplace);
			}
		} catch (ScheduleException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 进度填报
	 * 
	 * @author chenjuani
	 * @date 2018年2月8日
	 * @param divSnCode
	 * @return
	 */
	@RequestMapping("/editEngDivFinishRate")
	public Object editEngDivFinishRate(String divSnCode) {
		ModelAndView mv = new ModelAndView("schedule/engDivFinishRate-edit");
		try {
			mv.addObject("retMsg", "");
			// 获取进度信息
			if (divSnCode != null && !"".equals(divSnCode)) {
				String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal()).getUserCode();
				List<EngDivisionModel> filldata = engDivisionService.selectTechnologyLeaderFillData(userCode, divSnCode,
						"");
				if (filldata.size() > 0) {
					EngDivisionModel engDiv = filldata.get(0);
					// 查看是否已经交底
					List<TechDisclosure> tdList = techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode);
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					for(TechDisclosure td : tdList){
						// 如果已经交底或者计划日期早于当前日期则可以交底
						if (sf.parse(engDiv.getStartDate()).before(new Date()) || td.getDisDate() != null) {
							mv.addObject("r", engDiv);
							mv.addObject("retMsg", "");
						} else {
							mv.addObject("retMsg", "未交底且还未到计划日期");
						}
					}
				} else {
					mv.addObject("retMsg", "工程划分计划不存在");
				}
			} else {
				mv.addObject("retMsg", "");
			}
			String takeplace = "";// 隐患发生地点
			String[] arr = divSnCode.split("\\.");
			if (arr.length > 2) {
				String s1 = arr[0];
				String s2 = s1 + "." + arr[1];
				String s3 = s2 + "." + arr[2];
				String divname1 = "";
				String divname2 = "";
				String divname3 = "";
				List<EngDivision> all = engDivisionService.findAllEngDivision();
				for (EngDivision eng : all) {
					if (eng.getDivisionSnCode().equals(s1)) {
						divname1 = eng.getDivName();
					}
					if (eng.getDivisionSnCode().equals(s2)) {
						divname2 = eng.getDivName();
					}
					if (eng.getDivisionSnCode().equals(s3)) {
						divname3 = eng.getDivName();
					}
				}
				takeplace = divname1 + ">" + divname2 + ">" + divname3;
				mv.addObject("takeplace", takeplace);
			}
		} catch (ScheduleException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			mv.addObject("retMsg", "日期转换出错");
		}
		return mv;
	}

	/*	*//**
			 * 进度填报
			 * 
			 * @author Xu Dingkui
			 * @date 2017年11月21日
			 * @param divSnCode
			 * @return
			 *//*
			 * @RequestMapping("/editEngDivFinishRate") public Object
			 * editEngDivFinishRate(String divSnCode) { ModelAndView mv = new
			 * ModelAndView("schedule/engDivFinishRate-edit"); try { //获取进度信息
			 * if (divSnCode != null && !"".equals(divSnCode)) { String userCode
			 * = ((CustomUserDetails)SecurityContextHolder.getContext().
			 * getAuthentication().getPrincipal()).getUserCode();
			 * List<EngDivisionModel> filldata =
			 * engDivisionService.selectTechnologyLeaderFillData(userCode,
			 * divSnCode, ""); if (filldata.size() > 0) { EngDivisionModel
			 * engDiv = filldata.get(0); //查看是否已经交底 TechDisclosure td =
			 * techDisclosureService.findTechDisclosureByDisDivSnCode(divSnCode)
			 * ; SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			 * //如果已经交底或者计划日期早于当前日期则可以交底 if
			 * (sf.parse(engDiv.getStartDate()).before(new Date()) || td !=
			 * null) { mv.addObject("r", engDiv); mv.addObject("retMsg", ""); }
			 * else { mv.addObject("retMsg", "未交底且还未到计划日期"); } } else {
			 * mv.addObject("retMsg", "工程划分计划不存在"); } } else {
			 * mv.addObject("retMsg", ""); } } catch (ScheduleException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); } catch
			 * (ParseException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); mv.addObject("retMsg", "日期转换出错"); } return
			 * mv; }
			 */

	/**
	 * 保存进度填报
	 * 
	 * @author Xu Dingkui
	 * @date 2017年11月21日
	 * @param edm
	 * @param ht
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveEngDivFinishRate")
	@ResponseBody
	public Map saveEngDivFinishRate(EngDivisionModel edm, EngDivFinishRate ht) {
		Map map = new HashMap();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			ht.setDivisionSnCode(edm.getDivisionsncode());
			try {
				ht.setWorkDate(sf.parse(sf.format(new Date())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			EngDivFinishRate entity = engDivFinishRateService.findEngDivFinishRateByPrimaryKey(ht);
			if (entity != null) {
				map.put("result", false);
				map.put("retMsg", "已经填报");
				return map;
			} else {
				// 工程划分数据是否更新
				boolean upFlag = false;
				// 查看工程划分数据
				EngDivision ed = engDivisionService.findEngDivisionByKey(edm.getDivisionsncode());
				// 第一次填报，实际开始日期为空
				if (ed.getRealStartDate() == null) {
					// 查看是否已经交底
					List<TechDisclosure> tdLst = techDisclosureService.findTechDisclosureByDisDivSnCode(edm.getDivisionsncode());
					// 如果已经交底，则实际开工日期则为交底日期
					if (tdLst.size() > 0) {
						TechDisclosure td = tdLst.get(0);
						ed.setRealStartDate(td.getDisDate());
					} else {
						try {
							ed.setRealStartDate(sf.parse(edm.getStartDate()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					upFlag = true;
				}
				String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal()).getUserCode();
				// 查看累计完成数量
				List<EngDivisionModel> filldata = engDivisionService.selectTechnologyLeaderFillData(userCode,
						edm.getDivisionsncode(), "");
				if (filldata.size() > 0) {
					edm = filldata.get(0);
					// 如果累计完成数量+今日完成数量>施工方案工程总量
					if (edm.getCompletedQuantity() + ht.getFinishNumber() > edm.getConsSchemeQuantity()) {
						map.put("result", false);
						map.put("retMsg", "累计完成数量不能大于施工方案工程总量");
						return map;
					}
					// 如果数量完成，设置实际完工日期
					if (edm.getCompletedQuantity() + ht.getFinishNumber() == edm.getConsSchemeQuantity()) {
						ed.setRealEndDate(ht.getWorkDate());
						upFlag = true;
					}
				}
				engDivFinishRateService.saveEngDivFinishRate(ht);
				// 更新实际开工日期，或者实际完工日期
				if (upFlag == true) {
					engDivisionService.saveEngDivision(ed);
				}
				map.put("result", true);
				map.put("retMsg", "");
				return map;
			}
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping(value = "deleteContractAtt")
	@ResponseBody
	public String deleteContractAtt(String disclosureCode, String indexId) {
		try {
			techDisclosureService.deleteContractAtt(disclosureCode, indexId);
			return "{success : true}";
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{success : true}";
	}

}
