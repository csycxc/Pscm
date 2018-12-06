package com.banry.pscm.labour;

import com.banry.pscm.persist.dao.LabourMapper;
import com.banry.pscm.service.labour.WorkAttendance;
import com.banry.pscm.service.labour.WorkAttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkAttendanceServiceImpl implements WorkAttendanceService {

    @Autowired
    private LabourMapper labourMapper;

    private static Logger log = LoggerFactory.getLogger(WorkAttendanceServiceImpl.class);

    @Override
    public List<HashMap> getWorkAttendancesByDownContractCode(String downContractCode) {
        List<String> list = labourMapper.getInIdsByDownContractCode(downContractCode);
        String inIds = "";
        if(list.size()>0){
            inIds = getInIds(list);
        }else{
            return new ArrayList<HashMap>();
        }
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        log.info("inIds========"+inIds+"today======"+today);
        return labourMapper.getTodayWorkAttendancesByInIds(inIds,today);
    }

    @Override
    public List<HashMap> getWorkAttendancesByTrainCode(String trainCode) {
        List<String> list = labourMapper.getInIdsByTrainCode(trainCode);
        String inIds = "";
        if(list.size()>0){
            inIds = getInIds(list);
        }else{
            return new ArrayList<HashMap>();
        }
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        log.info("inIds========"+inIds+"today======"+today);
        return labourMapper.getTodayWorkAttendancesByInIds(inIds,today);
    }

    @Override
    public List<HashMap> selectWorkAttendanceByInId(String inId) {
        return labourMapper.selectWorkAttendanceForTableByInId(inId);
    }

    @Override
    public List<WorkAttendance> selectWorkAttendancesByInIdAndDate(String inId, String monthDate) {
        String start = monthDate + "-00";
        String end = monthDate + "-32";
        return labourMapper.selectWorkAttendancesByInIdAndDate(inId,start,end);
    }

    public String getInIds(List<String> list){
        String inIds = "";
        if(list.size()>0){
            for (int i = 0;i<list.size();i++){
                if(i == list.size()-1)
                    inIds += list.get(i);
                else
                    inIds += list.get(i) + ",";
            }
        }
        return inIds;
    }

}
