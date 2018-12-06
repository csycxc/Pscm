package com.banry.pscm.web.mvc.pscm.conf;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banry.pscm.service.conf.PscmConfException;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.Hazards;
import com.banry.pscm.service.schedule.HazardsService;
import com.banry.pscm.service.schedule.ScheduleException;


/**
 * @author chenshiyu
 *
 */
@RestController
@RequestMapping("/hazards")
public class HazardsController {

//	@Autowired
//	private PSCMConfService pscmConfService;
	@Autowired
	private HazardsService hazardsService;
	@Autowired
	private EngDivisionService engDivisionService;
	
	private static Logger log = LoggerFactory.getLogger(HazardsController.class);

	@RequestMapping("/findbycode")
	public Hazards findHazardsByHazardsCode(HttpSession session, String hazardscode) {
		log.info("findHazardsByHazardsCode call with hazardscode="+hazardscode);
		try {
			InitTenant.setCurrentSessionDS(session);
			return hazardsService.findHazardsByKey(hazardscode);
		} catch (Exception e) {
			log.error("findHazardsByHazardsCode call fail:"+e.getMessage());
			e.printStackTrace();	
			return null;
		}
	}

	@RequestMapping("/findbydivitemcode")
	public List<Hazards> findHazardsByDivItemCode(HttpSession session, String divitemcode) {
		log.info("findHazardsByDivItemCode call with divitemcode="+divitemcode);
		try {
			InitTenant.setCurrentSessionDS(session);
			return hazardsService.findHazardssByDivItemCode(divitemcode);
		} catch (Exception e) {
			log.error("findHazardsByDivItemCode call fail:"+e.getMessage());
			e.printStackTrace();	
			return null;
		}
	}

	@RequestMapping("/findbydivsncode")
	public List<Hazards> findHazardsByDivSnCode(HttpSession session, String divisionsncode) {
		log.info("findHazardsByDivSnCode call with divisionsncode="+divisionsncode);
		try {
			InitTenant.setCurrentSessionDS(session);
			return hazardsService.findHazardssByDivSnCode(divisionsncode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/delete")
	public Integer deleteHazardsByCode(HttpSession session, String hazardscode) {
		log.info("deleteHazardsByCode call with hazardscode="+hazardscode);
		try {
			InitTenant.setCurrentSessionDS(session);
			return hazardsService.deleteHazardsByKey(hazardscode);
		} catch (Exception e) {
			log.error("deleteHazardsByCode call fail:"+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	@RequestMapping("/save")
	public void saveHazards(HttpSession session, Hazards hazards) {
		log.info("saveHazards call with hazardscode="+hazards.getHazardsCode());
		try {
			InitTenant.setCurrentSessionDS(session);
			hazardsService.saveHazards(hazards);
		} catch (Exception e) {
			log.error("saveHazards call fail:"+e.getMessage());
			e.printStackTrace();
		}
	}

	@RequestMapping("/saveselective")
	public void saveHazardsSelective(HttpSession session, Hazards hazards) {
		log.info("saveHazardsSelective call with hazardscode="+hazards.getHazardsCode());
		try {
			InitTenant.setCurrentSessionDS(session);
			hazardsService.saveHazardsSelective(hazards);
		} catch (Exception e) {
			log.error("saveHazardsSelective call fail:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 处理jqGrid 行编辑请求，支持删除和更新两个操作，操作标识oper=edit 或者 del
	 * 
	 * @param session
	 * @param hazards 
	 * @param oper add/edit/delete
	 * @param id
	 * @return 
	 * @paeam htCodes 删除多行时前台传送过来的数组
	 */
	@RequestMapping("/handlerowopera")
	public ResponseEntity<?> handleHazardsRowOperation(HttpSession session, Hazards hazards, String divsncode, String oper,
			String id, String haCodes) {

		log.info("handleHazardsRowOperation called with ha.getHazardsCode()=" + hazards.getHazardsCode() + " and oper=" + oper
				+ " and id=" + id + " divsncode=" + divsncode + " htCodes="+ haCodes);

		try {
			InitTenant.setCurrentSessionDS(session);
		} catch (PscmConfException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if ("add".equals(oper)) {
			
			// 根据divsncode 反查divItemCode 和 divlevel
			EngDivision engDivision;
			try {
				engDivision = engDivisionService.findEngDivisionByKey(divsncode);
			} catch (ScheduleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("saveHiddenTroubleBill failed:" + e.getMessage());
				return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
			try {				
				hazards.setDivItemCode(engDivision.getDivItemCode());
				hazards.setDivLevel(engDivision.getDivLevel().toString());				
				hazardsService.saveHazards(hazards);
			} catch (Exception e) {
				log.error("saveHazards failed:" + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
		}

		// edit row mode
		if ("edit".equals(oper)) {
			try {
				hazardsService.saveHazardsSelective(hazards);
			} catch (Exception e) {
				log.error("saveHazardsSelective failed:" + e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
			}
		}

		// del row mode
		if ("del".equals(oper)) {
			if (haCodes != null) {
				try {
					String[] ids = haCodes.split(",");
					for (int i = 0; i < ids.length; i++) {
						log.info("trying to deleteHazardsByKey " + ids[i]);
						hazardsService.deleteHazardsByKey(ids[i]);
					}
				} catch (Exception e) {
					log.error("deleteHazardsByKey failed:" + e.getMessage());
					e.printStackTrace();
					return new ResponseEntity<>(e.getMessage()	, HttpStatus.INTERNAL_SERVER_ERROR);// 保存失败，返回500
				}
			}
		}
		
		return new ResponseEntity<>(null, HttpStatus.OK);// 保存成功，返回200
	}

}
