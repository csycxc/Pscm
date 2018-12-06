package com.banry.pscm.service.labour;

import java.util.HashMap;
import java.util.List;

/**
 * 劳务管理  考勤接口
 */
public interface WorkAttendanceService {

    /**
     * 根据合同编号查询该合同下的人员考勤
     * @param downContractCode
     * @return
     */
    List<HashMap> getWorkAttendancesByDownContractCode(String downContractCode);

    /**
     * 根据培训编号查询该培训下的人员考勤
     * @param trainCode
     * @return
     */
    List<HashMap> getWorkAttendancesByTrainCode(String trainCode);

    /**
     * 根据平安卡号查询考勤
     * @param inId
     * @return
     */
    List<HashMap> selectWorkAttendanceByInId(String inId);

    List<WorkAttendance> selectWorkAttendancesByInIdAndDate(String inId, String monthDate);
}
