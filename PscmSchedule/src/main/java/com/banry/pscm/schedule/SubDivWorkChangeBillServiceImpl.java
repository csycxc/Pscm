package com.banry.pscm.schedule;

import com.banry.pscm.persist.dao.SubDivWorkChangeBillMapper;
import com.banry.pscm.service.schedule.SubDivWorkBillChange;
import com.banry.pscm.service.schedule.SubDivWorkChangeBill;
import com.banry.pscm.service.schedule.SubDivWorkChangeBillKey;
import com.banry.pscm.service.schedule.SubDivWorkChangeBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubDivWorkChangeBillServiceImpl implements SubDivWorkChangeBillService {

    @Autowired
    private SubDivWorkChangeBillMapper subDivWorkChangeBillMapper;

    @Override
    public List<SubDivWorkBillChange> findWorkBillsByCodes(String contractCode, String changeCode) {
        return subDivWorkChangeBillMapper.findWorkBillsByCodes(contractCode, changeCode);
    }

    @Override
    public void saveSubDivWorkChangeBill(SubDivWorkChangeBill subDivWorkChangeBill) {
        subDivWorkChangeBillMapper.insertSelective(subDivWorkChangeBill);
    }

    @Override
    public void deleteSubDivWorkChangeBill(String divisionSnCode, String changeCode) {
        SubDivWorkChangeBillKey key = new SubDivWorkChangeBillKey();
        key.setDivisionSnCode(divisionSnCode);
        key.setChangeCode(changeCode);
        subDivWorkChangeBillMapper.deleteByPrimaryKey(key);
    }

    @Override
    public SubDivWorkChangeBill findBillChangeByKey(SubDivWorkChangeBillKey subDivWorkChangeBillKey) {
        return subDivWorkChangeBillMapper.selectByPrimaryKey(subDivWorkChangeBillKey);
    }

    @Override
    public void updateSubDivWorkChangeBill(SubDivWorkChangeBill originalChangeBill) {
        subDivWorkChangeBillMapper.updateByPrimaryKeySelective(originalChangeBill);
    }


}
