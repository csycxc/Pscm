package com.banry.pscm.web.mvc.pscm.tender;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.service.tender.ReleaseTarget;
import com.banry.pscm.service.tender.ReleaseTargetService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.web.mvc.VO.ResultVO;
import com.banry.pscm.web.mvc.enums.ResultEnum;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.ResultVOUtil;

/**
 * 发布目标Controller
 * @author chenJuan
 * @date 2018-6-1
 */
@Controller
@RequestMapping("/releaseTarget")
public class ReleaseTargetController {
	
	@Autowired
	ReleaseTargetService targetService;
	@Autowired
	TenderPlanService tenderService;
	
	private static Logger log = LoggerFactory.getLogger(ReleaseTargetController.class);
	
	/**
	 * 查询全部发布目标
	 * @return Object
	 */
	@RequestMapping("/getAllTarget")
	@ResponseBody
	public Object getAllTarget(){
		DataTableModel data = new DataTableModel();
		try {
			List<ReleaseTarget> target = targetService.findAllReleaseTarget();
			data.setData(target);
			return data;
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	/**
	 * 根据招标计划编码查询发布目标
	 * @return Object
	 */
	@RequestMapping("/findReleaseByTenderPlanCode")
	@ResponseBody
	public Object findReleaseByTenderPlanCode(String tenderPlanCode){
		List<ReleaseTarget> target = new ArrayList<>();
		try {
			target = targetService.findReleaseByTenderPlanCode(tenderPlanCode);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return target;
	}
	
	/**
	 * 根据发布目标编码查找发布目标
	 * @param targetCode
	 * @return Object
	 */
	@RequestMapping("/findTargetByCode")
	@ResponseBody
	public Object findTargetByCode(String targetCode){
		ReleaseTarget target = new ReleaseTarget();
		try {
			target = targetService.selectByPrimaryKey(targetCode);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return target;
	}
	
	/**
	 * 新增或修改发布目标
	 * @param target
	 * @param flag
	 * @return Map
	 */
	@RequestMapping("/addOrUpdateTarget")
	@ResponseBody
	public ResultVO addOrUpdateTarget(String operate,String tenderPlanCode,String JSONText){
		try {
			List<ReleaseTarget> releaseList = JSON.parseArray(JSONText, ReleaseTarget.class);
			targetService.saveOrSubmitTarget(releaseList, operate, tenderPlanCode);
			if ("save".equals(operate)) {
				return ResultVOUtil.success(ResultEnum.SAVE_SUCCESS.getMessage());
			} else if ("submit".equals(operate)) {
				return ResultVOUtil.success(ResultEnum.RELEASE_SUCCESS.getMessage());
			} else {
				log.error("数据错误");
				return ResultVOUtil.error(ResultEnum.DATA_ERROR.getMessage());
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}
	
	/**
	 * 删除发布目标
	 * @param targetCodes
	 * @return Map
	 */
	@RequestMapping("/deleteTarget")
	@ResponseBody
	public ResultVO deleteTarget(String targetCodes){
		try {
			if (targetCodes != null && !"".equals(targetCodes)) {
				String[] targetCode = targetCodes.split(",");
				for (String tc : targetCode) {
					targetService.deleteByPrimaryKey(tc);
				}
			}
			return ResultVOUtil.success(ResultEnum.DELETE_SUCCESS.getMessage());
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}

}
