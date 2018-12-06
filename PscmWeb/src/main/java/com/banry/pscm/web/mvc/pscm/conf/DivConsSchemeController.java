package com.banry.pscm.web.mvc.pscm.conf;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banry.pscm.service.schedule.DivConsScheme;
import com.banry.pscm.service.schedule.DivConsSchemeService;

/**
 * 施工方案
 * 
 * @author chenshiyu
 *
 */
@RestController
@RequestMapping("/divconsscheme")
public class DivConsSchemeController {
	/*@Autowired
	private PSCMConfService pscmConfService;*/
	@Autowired
	private DivConsSchemeService divConsSchemeService;

	private static Logger log = LoggerFactory.getLogger(DivConsSchemeController.class);
	
	@RequestMapping("/findbycode")
	public DivConsScheme findEngDivisionByCode(HttpSession session, String schemecode) {
		log.info("findEngDivisionByCode call with schemecode="+schemecode);
		try {
			InitTenant.setCurrentSessionDS(session);
			return divConsSchemeService.findDivConsSchemeByPrimaryKey(schemecode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping("/findbydivisionsncode")
	public List<DivConsScheme> findDivConsSchemeByCode(HttpSession session, String divisionsncode) {
		log.info("findDivConsSchemeByCode call with divisionsncode="+divisionsncode);
		try {
			InitTenant.setCurrentSessionDS(session);
			return divConsSchemeService.findDivConsSchemesByDivisionSnCode(divisionsncode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
