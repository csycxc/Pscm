package com.banry.pscm.labour;

import com.banry.pscm.persist.dao.LaborInOutMapper;
import com.banry.pscm.persist.dao.LabourMapper;
import com.banry.pscm.persist.dao.WorkAttendanceMapper;
import com.banry.pscm.service.labour.LaborInOut;
import com.banry.pscm.service.labour.LaborInOutService;
import com.banry.pscm.service.labour.LaborInOutWithBLOBs;
import com.banry.pscm.service.labour.WorkAttendance;
import com.banry.pscm.service.util.EnumVar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class LaborInOutServiceImpl implements LaborInOutService {

    @Autowired
    private LaborInOutMapper laborInOutMapper;
    @Autowired
    private LabourMapper labourMapper;
    @Autowired
    private WorkAttendanceMapper workAttendanceMapper;

    @Override
    public String selectIdNumberByInId(String inId) {
        LaborInOut lio = laborInOutMapper.selectByPrimaryKey(inId);
        return lio.getIdNumber();
    }

    @Override
    public LaborInOutWithBLOBs selectLaborInOutById(String inId) {
        return laborInOutMapper.selectByPrimaryKey(inId);
    }

    @Override
    public int insertLaborInOut(LaborInOutWithBLOBs laborInOut) {
        return laborInOutMapper.insertSelective(laborInOut);
    }

    @Override
    public int updateLaborInOutSelective(LaborInOutWithBLOBs laborInOut) {
        return laborInOutMapper.updateByPrimaryKeySelective(laborInOut);
    }

    @Override
    public int deleteLaborInOutByInId(String inId) {
        List<WorkAttendance> list = labourMapper.selectWorkAttendanceByInId(inId);
        if(list.size()>0){
            return -1;
        }else{
            return laborInOutMapper.deleteByPrimaryKey(inId);
        }
    }

    @Override
    public List<HashMap> selectLaborInOutsByTrainCode(String trainCode) {
        return labourMapper.selectLaborInOutsByTrainCode(trainCode);
    }

    @Override
    public int saveLaborInOut(LaborInOutWithBLOBs laborInOut) {
        String inId = laborInOut.getInId();
        if(inId != null && !"".equals(inId)){
            LaborInOutWithBLOBs l = laborInOutMapper.selectByPrimaryKey(inId);
            if(l != null){//更新
                return laborInOutMapper.updateByPrimaryKeySelective(laborInOut);
            }else{//插入
                return laborInOutMapper.insertSelective(laborInOut);
            }
        }else{
            laborInOut.setInId(String.valueOf(System.currentTimeMillis()));
            return laborInOutMapper.insertSelective(laborInOut);
        }

    }

    @Override
    public List<LaborInOutWithBLOBs> findLaborInOutWithBLOBsByTrainCode(String trainCode) {
        return labourMapper.findLaborInOutWithBLOBsByTrainCode(trainCode);
    }

    @Override
    public List<HashMap> getLaborInOutByDownContractCode(String downContractCode) {
        return labourMapper.getLaborInOutByDownContractCode(downContractCode);
    }

    @Override
    public List<HashMap> getLaborInOutByTrainCode(String trainCode) {
        return labourMapper.getLaborInOutByTrainCode(trainCode);
    }

    @Override
    public int letInLaborInOut(List<LaborInOutWithBLOBs> list) {
        int j = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(new Date());//当前时间
            Date date = sdf.parse(dateNowStr);
            for (int i=0;i<list.size();i++){
                list.get(i).setInDate(date);
                j += laborInOutMapper.updateByPrimaryKeySelective(list.get(i));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return j;
    }

    @Override
    public List<HashMap> getLaborInOutByDownContractCodeAndInOrOut(String downContractCode, String inOrOut) {
        if("in".equals(inOrOut)){//入场，未离场   身份证号，平安卡号，姓名，工种，身份证照片，入场手持身份证照片
            return labourMapper.getLaborInOutByDownContractCodeAndIn(downContractCode);
        }else{//离场
            return labourMapper.getLaborInOutByDownContractCodeAndOut(downContractCode);
        }
    }

    @Override
    public List<HashMap> getLaborInOutByTrainCodeAndInOrOut(String trainCode, String inOrOut) {
        if("in".equals(inOrOut)){//入场，未离场   身份证号，平安卡号，姓名，工种，身份证照片，入场手持身份证照片
            return labourMapper.getLaborInOutByTrainCodeAndIn(trainCode);
        }else{//离场
            return labourMapper.getLaborInOutByTrainCodeAndOut(trainCode);
        }
    }

    @Override
    public List<EnumVar> selectEnumVarForLaborWages() {
        return labourMapper.selectEnumVarForLaborWages();
    }

    @Override
    public List<String> getInIdsByDownContractCode(String downContractCode) {
        return labourMapper.getInIdsByDownContractCode(downContractCode);
    }

    @Override
    public int letOutLaborInOut(List<LaborInOutWithBLOBs> list) {
        int j = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateNowStr = sdf.format(new Date());//当前时间
            Date date = sdf.parse(dateNowStr);
            for (int i=0;i<list.size();i++){
                list.get(i).setOutDate(date);
                j += laborInOutMapper.updateByPrimaryKeySelective(list.get(i));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return j;
    }


}
