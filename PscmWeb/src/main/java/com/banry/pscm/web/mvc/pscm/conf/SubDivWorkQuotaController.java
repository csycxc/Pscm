package com.banry.pscm.web.mvc.pscm.conf;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.schedule.SubDivWorkQuota;
import com.banry.pscm.service.schedule.SubDivWorkQuotaService;

/**
 * @author chenshiyu
 * @date 2018年06月21日
 */
@RestController
@RequestMapping("/subdivworkquota")
public class SubDivWorkQuotaController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment ev;
	@Autowired
	private SubDivWorkQuotaService subDivWorkQuotaService;
	@Autowired
	private SubDivWorkBillService subDivWorkBillService;
	
	private static Logger log = LoggerFactory.getLogger(SubDivWorkQuotaController.class);

	/**
	 * 根据主键查询定额数据表，这个方法暂时用不上。
	 * 
	 * @param session
	 * @param rescode
	 * @return
	 */
	@RequestMapping("/findbycode")
	public SubDivWorkQuota findSubDivWorkQuotaByCode(String rescode) {
		log.info("findSubDivWorkQuotaByCode call with rescode=" + rescode);		
		try {
			return subDivWorkQuotaService.findSubDivWorkQuotaByKey(rescode);
		} catch (Exception e) {
			log.error("findSubDivWorkQuotaByCode call fail:" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 根据清单信息主键，查询多个定额数据表
	 * 
	 * @param session
	 * @param divisionsncode
	 * @return
	 */
	@RequestMapping("/findbydivsncode")
	public List<SubDivWorkQuota> findSubDivWorkQuotasByDivisionSnCode(String divisionsncode) {
		log.info("findSubDivWorkQuotasByDivisionSnCode called with divisionsncode=" + divisionsncode);
		try {
			return subDivWorkQuotaService.findSubDivWorkQuotaByDivisionSnCode(divisionsncode);
		} catch (Exception e) {
			log.error("findSubDivWorkQuotasByDivisionSnCode called failed:" + e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping("/delete")
	public @ResponseBody ResponseEntity<?> deleteSubDivWorkQuotaByKey(String rescode) {
		log.info("deleteSubDivWorkQuotaByKey called by rescode =" + rescode);
		try {
			if(rescode != null) {
				int status = subDivWorkQuotaService.findSubDivWorkBillStatus(rescode);
				if(status == 2) {
					log.info("清单已提交，不可更改定额数据表信息。");
					return new ResponseEntity<>("清单已提交，不可更改定额数据表信息。", HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
				}				
			}
			subDivWorkQuotaService.deleteSubDivWorkQuotaByKey(rescode);
			return new ResponseEntity<>("删除成功。", HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			log.error("deleteSubDivWorkQuotaByKey called fail:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
		}
	}

	/**
	 * 处理jqGrid 行编辑请求，支持删除和更新两个操作，操作标识oper=edit 或者 del
	 * 
	 * @param session
	 * @param sdwq
	 * @param oper
	 * @param id
	 */
	@RequestMapping("/handlerowopera")
	public @ResponseBody ResponseEntity<?> handleQuotaRowOperation(SubDivWorkQuota sdwq, String divsncode, String oper,
			String id, String resCodes) {

		log.info("handleQuotaRowOperation called with sdwq.getResCode()=" + sdwq.getResCode() + " and oper=" + oper
				+ " and id=" + id + " divsncode=" + divsncode + " resCodes=" + resCodes);
		
		if(divsncode != null) {
			int status = subDivWorkBillService.findSubDivWorkBillStatusByDivisionSnCode(divsncode);
			if(status == 2) {
				log.info("清单已提交，不可更改定额数据表信息。");
				return new ResponseEntity<>("清单已提交，不可更改定额数据表信息。", HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
			}
		}
		if(resCodes != null) {
			resCodes = resCodes.replaceAll("\"", "");
			int status2 = subDivWorkQuotaService.findSubDivWorkBillStatus(resCodes);
			if(status2 == 2) {
				log.info("清单已提交，不可更改定额数据表信息。");
				return new ResponseEntity<>("清单已提交，不可更改定额数据表信息。", HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
			}
		}
		
		if ("add".equals(oper)) {
			try {
				// 强制设置 divsncode ，这个是从url中获得的，因为quota表单不需要单独输入这个数据项
				sdwq.setDivisionSnCode(divsncode);
				subDivWorkQuotaService.saveSubDivWorkQuota(sdwq);
				return new ResponseEntity<>("新增。", HttpStatus.OK);//保存成功，返回200
			} catch (Exception e) {
				log.error("saveSubDivWorkQuota failed:" + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// edit row mode
		if ("edit".equals(oper)) {
			try {
				subDivWorkQuotaService.saveSubDivWorkQuotaSelective(sdwq);
				return new ResponseEntity<>("更新。", HttpStatus.OK);//保存成功，返回200
			} catch (Exception e) {
				log.error("saveSubDivWorkQuota failed:" + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// del row mode
		if ("del".equals(oper)) {
			if (resCodes != null) {
				try {
					String[] ids = resCodes.split(",");
					for (int i = 0; i < ids.length; i++) {
						log.info("trying to deleteSubDivWorkQuotaByKey " + ids[i]);
						subDivWorkQuotaService.deleteSubDivWorkQuotaByKey(ids[i]);
					}
					return new ResponseEntity<>("删除。", HttpStatus.OK);//保存成功，返回200
				} catch (Exception e) {
					log.error("deleteSubDivWorkQuotaByKey failed:" + e.getMessage());
					e.printStackTrace();
					return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		}
		return new ResponseEntity<>("参数出现错误。", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@RequestMapping("/save")
	public @ResponseBody ResponseEntity<?> saveSubDivWorkQuota(SubDivWorkQuota sdwq) {
		log.info("saveSubDivWorkQuota called with sdwq.getDivisionSnCode()=" + sdwq.getDivisionSnCode());
		try {
			if(sdwq.getDivisionSnCode() != null) {
				int status = subDivWorkBillService.findSubDivWorkBillStatusByDivisionSnCode(sdwq.getDivisionSnCode());
				if(status == 2) {
					log.info("清单已提交，不可更改定额数据表信息。");
					return new ResponseEntity<>("清单已提交，不可更改定额数据表信息。", HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
				}				
			}
			subDivWorkQuotaService.saveSubDivWorkQuota(sdwq);
			return new ResponseEntity<>("保存成功。", HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			log.error("saveSubDivWorkQuota failed:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
		}
	}

	@RequestMapping("/saveselective")
	public @ResponseBody ResponseEntity<?> saveSubDivWorkQuotaSelective(SubDivWorkQuota sdwq) {
		log.info("saveSubDivWorkQuotaSelective called with sdwq.getDivisionSnCode()=" + sdwq.getDivisionSnCode());
		try {
			if(sdwq.getDivisionSnCode() != null) {
				int status = subDivWorkBillService.findSubDivWorkBillStatusByDivisionSnCode(sdwq.getDivisionSnCode());
				if(status == 2) {
					log.info("清单已提交，不可更改定额数据表信息。");
					return new ResponseEntity<>("清单已提交，不可更改定额数据表信息。", HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
				}				
			}
			
			subDivWorkQuotaService.saveSubDivWorkQuotaSelective(sdwq);
			return new ResponseEntity<>("保存成功。", HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			log.error("saveSubDivWorkQuotaSelective called fail:" + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//保存成功，返回200
		}
	}

}
