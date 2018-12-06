package com.banry.pscm.labour;

import java.util.HashMap;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.banry.pscm.persist.dao.TrainMapper;
import com.banry.pscm.service.labour.LaborInOut;
import com.banry.pscm.service.labour.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.LabourMapper;
import com.banry.pscm.service.labour.TrainService;

@Service
public class TrainServiceImpl implements TrainService{
	
	@Autowired
	private LabourMapper labourMapper;
	@Autowired
	private TrainMapper trainMapper;
	
	@Override
	public List<HashMap> getTrainsByDownContractCode(String downContractCode) {
		// TODO Auto-generated method stub
		List<HashMap> list = labourMapper.getTrainsByDownContractCode(downContractCode);
		return list;
	}

	@Override
	public int deleteTrainByTrainCode(String trainCode) {
		List<LaborInOut> list = labourMapper.selectLaborInOutByTrainCode(trainCode);
		if(list.size()==0){
			return labourMapper.deleteTrainByTrainCode(trainCode);
		}else{
			return 0;
		}
	}

	/**
	 * 根据传入的对象和文件名，增加部分后，返回新的字符串作为附件属性值
	 */
	public String getNewAttaStringAdd(Train train,String fileInName){
		String trainAttach = "";
		if(train != null){
			trainAttach += train.getTrainAttach();
			if(StringUtils.isEmpty(trainAttach))
				trainAttach = fileInName;
			else
				trainAttach += "," + fileInName;
		}
		return trainAttach;
	}
	@Override
	public void saveAtt(String trainCode,String fileInName) {
		Train train = trainMapper.selectByPrimaryKey(trainCode);
		String trainAttach = getNewAttaStringAdd(train,fileInName);
		train.setTrainAttach(trainAttach);
		trainMapper.updateByPrimaryKey(train);
	}

	/**
	 * 根据传入的对象和文件名，删除部分后，返回新的字符串作为附件属性值
	 */
	public String getNewAttaStringDel(Train train,String fileInName){
		if(train != null){
			String trainAttach = train.getTrainAttach();
			if(trainAttach != null && !"".equals(trainAttach)){
				if(trainAttach.contains(fileInName)){
					if(trainAttach.equals(fileInName)){
						return "";
					}else if(trainAttach.endsWith(fileInName) && !trainAttach.startsWith(fileInName)){
						return trainAttach.replaceAll(","+fileInName,"");
					}else {
						return trainAttach.replaceAll(fileInName+",","");
					}
				}else{
					return trainAttach;
				}
			}
		}
		return "";
	}
	@Override
	public int deleteTrainAttachById(String id,String trainCode) {
		Train train = trainMapper.selectByPrimaryKey(trainCode);
		String trainAttach = getNewAttaStringDel(train,id);
		train.setTrainAttach(trainAttach);
		return trainMapper.updateByPrimaryKey(train);
	}

	@Override
	public int saveTrainAsForm(Train train) {
		Train t = trainMapper.selectByPrimaryKey(train.getTrainCode());
		if(t != null){
			return trainMapper.updateByPrimaryKeySelective(train);
		}else{
			return trainMapper.insert(train);
		}
	}

	@Override
	public int updateTrain(Train train) {
		return trainMapper.updateByPrimaryKeySelective(train);
	}

	@Override
	public int addTrain(Train train) {
		return trainMapper.insert(train);
	}

	@Override
	public Train selectTrainByTrainCode(String trainCode) {
		return trainMapper.selectByPrimaryKey(trainCode);
	}

	@Override
	public List<Train> getTrainsByContract(String downContractCode) {
		return labourMapper.getTrainsByContract(downContractCode);
	}


}
