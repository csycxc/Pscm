package com.banry.pscm.service.labour;

import java.util.HashMap;
import java.util.List;

/**
 * 劳务管理：劳务人员接口
 */
public interface LaborService {

    /**
     * 根据身份证号，查询劳务人员信息
     * @param idNumber
     * @return
     */
    List<HashMap> getLaborByIdNumber(String idNumber);

    /**
     * 保存劳务人员（包括更新和插入）
     * @param labor
     * @return
     */
    int saveLabor(LaborWithBLOBs labor);

    /**
     * 根据传入的idNumber值，查询LaborWithBLOBs
     * @param idNumber
     * @return
     */
    LaborWithBLOBs selectLaborWithBLOBsByIdNumber(String idNumber);

    /**
     * 更新labor
     * @param labor
     * @return
     */
    int updateLabor(LaborWithBLOBs labor);

    /**
     * 插入labor
     * @param labor
     * @return
     */
    int insertLabor(LaborWithBLOBs labor);
}
