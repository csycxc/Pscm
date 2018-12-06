package com.banry.pscm.labour;

import com.banry.pscm.persist.dao.LaborMapper;
import com.banry.pscm.persist.dao.LabourMapper;
import com.banry.pscm.service.labour.LaborService;
import com.banry.pscm.service.labour.LaborWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LaborServiceImpl implements LaborService {

    @Autowired
    private LabourMapper labourMapper;
    @Autowired
    private LaborMapper laborMapper;

    @Override
    public List<HashMap> getLaborByIdNumber(String idNumber) {
        return labourMapper.getLaborByIdNumber(idNumber);
    }

    @Override
    public int saveLabor(LaborWithBLOBs labor) {
        LaborWithBLOBs l = laborMapper.selectByPrimaryKey(labor.getIdNumber());
        if(l != null){//更新
            return laborMapper.updateByPrimaryKeySelective(labor);
        }else{//插入
            return laborMapper.insertSelective(labor);
        }
    }

    @Override
    public LaborWithBLOBs selectLaborWithBLOBsByIdNumber(String idNumber) {
        return laborMapper.selectByPrimaryKey(idNumber);
    }

    @Override
    public int updateLabor(LaborWithBLOBs labor) {
        return laborMapper.updateByPrimaryKeySelective(labor);
    }

    @Override
    public int insertLabor(LaborWithBLOBs labor) {
        return laborMapper.insertSelective(labor);
    }


}
