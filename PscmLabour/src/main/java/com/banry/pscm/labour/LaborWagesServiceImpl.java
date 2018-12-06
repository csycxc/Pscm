package com.banry.pscm.labour;

import com.banry.pscm.persist.dao.LaborWagesMapper;
import com.banry.pscm.persist.dao.LabourMapper;
import com.banry.pscm.service.labour.LaborWages;
import com.banry.pscm.service.labour.LaborWagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LaborWagesServiceImpl implements LaborWagesService {

    private static Logger log = LoggerFactory.getLogger(LaborWagesServiceImpl.class);

    @Autowired
    private LabourMapper labourMapper;
    @Autowired
    private LaborWagesMapper laborWagesMapper;

    @Override
    public List<HashMap> selectSalaryByDownContractCodeAndDate(String downContractCode, String startDate, String endDate) {
        return labourMapper.selectSalaryByDownContractCodeAndDate(downContractCode,startDate,endDate);
    }

    @Override
    public List<String> selectInIdsFromLaborWagesByDay(String monthDate) {
        String start = monthDate + "-00";
        String end = monthDate + "-32";
        return labourMapper.selectInIdsFromLaborWagesByDay(start,end);
    }

    @Override
    public int insertLaborWages(LaborWages laborWages) {
        return laborWagesMapper.insertSelective(laborWages);
    }
}
