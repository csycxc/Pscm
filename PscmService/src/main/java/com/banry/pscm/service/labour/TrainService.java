package com.banry.pscm.service.labour;

import java.util.HashMap;
import java.util.List;


/**
 * 劳务管理  培训  接口
 * @author csy
 *
 */
public interface TrainService {

    /**
     * 根据合同编码获取培训记录
     * @param downContractCode
     * @return
     */
	List<HashMap> getTrainsByDownContractCode(String downContractCode);

    /**
     * 根据培训记录id删除培训记录
     * @param trainCode
     * @return
     */
    int deleteTrainByTrainCode(String trainCode);

    /**
     * 保存一个附件
     * @param trainCode
     */
    void saveAtt(String trainCode,String fileInName);

    /**
     * 根据培训id删除它的部分（包括全部）附件
     * @param id
     * @param trainCode
     * @return
     */
    int deleteTrainAttachById(String id,String trainCode);

    /**
     * 保存培训记录详细信息（不包括附件）
     * @param train
     * @return
     */
    int saveTrainAsForm(Train train);

    /**
     * 更新培训记录
     * @param train
     * @return
     */
    int updateTrain(Train train);

    /**
     * 新增培训记录
     * @param train
     * @return
     */
    int addTrain(Train train);

    /**
     * 根据trainCode查询培训记录
     * @param trainCode
     * @return
     */
    Train selectTrainByTrainCode(String trainCode);

    /**
     * 根据合同编号查询该合同下的培训列表
     * @param downContractCode
     * @return
     */
    List<Train> getTrainsByContract(String downContractCode);
}
