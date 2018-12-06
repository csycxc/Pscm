package com.banry.pscm.service.labour;

import java.util.HashMap;
import java.util.List;

/**
 * 劳务管理：工资接口
 */
public interface LaborWagesService {
    /**
     * 根据 合同编号、开始日期、结束日期查询工资
     * @param downContractCode
     * @param startDate
     * @param endDate
     * @return
     */
    List<HashMap> selectSalaryByDownContractCodeAndDate(String downContractCode, String startDate, String endDate);

    List<String> selectInIdsFromLaborWagesByDay(String monthDate);

    int insertLaborWages(LaborWages laborWages);
}
