package com.banry.pscm.service.schedule;

import java.util.List;

public interface SubDivWorkChangeBillService {

    List<SubDivWorkBillChange> findWorkBillsByCodes(String contractCode, String changeCode);

    void saveSubDivWorkChangeBill(SubDivWorkChangeBill subDivWorkChangeBill);

    void deleteSubDivWorkChangeBill(String divisionSnCode, String changeCode);

    SubDivWorkChangeBill findBillChangeByKey(SubDivWorkChangeBillKey subDivWorkChangeBillKey);

    void updateSubDivWorkChangeBill(SubDivWorkChangeBill originalChangeBill);
}
