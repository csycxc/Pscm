package com.banry.pscm.service.labour;

import com.banry.pscm.service.util.EnumVar;

import java.util.HashMap;
import java.util.List;

/**
 * 劳务管理：劳务人员入出记录
 */
public interface LaborInOutService {


    /**
     * 根据id查询劳务人员身份证号
     * @param inId
     * @return
     */
    String selectIdNumberByInId(String inId);

    /**
     * 根据id查询劳务人员入离场
     * @param inId
     * @return
     */
    LaborInOutWithBLOBs selectLaborInOutById(String inId);

    /**
     * 添加一个劳务人员入离场
     * @param laborInOut
     */
    int insertLaborInOut(LaborInOutWithBLOBs laborInOut);

    /**
     * 更新一个劳务人员入离场
     * @param laborInOut
     */
    int updateLaborInOutSelective(LaborInOutWithBLOBs laborInOut);

    /**
     * 根据平安卡号
     * @param inId
     * @return
     */
    int deleteLaborInOutByInId(String inId);

    /**
     * 根据trainCode查询该培训记录下的劳务人员
     * @param trainCode
     * @return
     */
    List<HashMap> selectLaborInOutsByTrainCode(String trainCode);

    /**
     * 保存劳务人员入出场记录（包括更新和插入）
     * @param laborInOut
     * @return
     */
    int saveLaborInOut(LaborInOutWithBLOBs laborInOut);

    /**
     * 根据trainCode查询有几个inId
     * @param trainCode
     * @return
     */
    List<LaborInOutWithBLOBs> findLaborInOutWithBLOBsByTrainCode(String trainCode);

    /**
     * 根据合同编码查询该合同下的受培训人员入场信息
     * @param downContractCode
     * @return
     */
    List<HashMap> getLaborInOutByDownContractCode(String downContractCode);

    /**
     * 根据培训编号查询培训人员
     * @param trainCode
     * @return
     */
    List<HashMap> getLaborInOutByTrainCode(String trainCode);

    /**
     * 全部入场（添加时间）
     * @param list
     * @return
     */
    int letInLaborInOut(List<LaborInOutWithBLOBs> list);

    /**
     * 根据合同号和是否入离场查询
     * @param downContractCode
     * @param inOrOut
     * @return
     */
    List<HashMap> getLaborInOutByDownContractCodeAndInOrOut(String downContractCode, String inOrOut);

    /**
     * 根据培训号和是否入离场查询
     * @param trainCode
     * @param inOrOut
     * @return
     */
    List<HashMap> getLaborInOutByTrainCodeAndInOrOut(String trainCode, String inOrOut);

    /**
     * 查找所有enum_var表中的记录
     * @return
     */
    List<EnumVar> selectEnumVarForLaborWages();

    List<String> getInIdsByDownContractCode(String downContractCode);


    /**
     * 全部离场（添加时间）
     * @param list
     * @return
     */
    int letOutLaborInOut(List<LaborInOutWithBLOBs> list);
}
