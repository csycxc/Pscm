package com.banry.pscm.web.mvc.pscm.labour;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.labour.*;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.web.mvc.model.DataTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/laborWages")
public class LaborWagesController {

    @Autowired
    private LaborWagesService laborWagesService;
    @Autowired
    private LaborInOutService laborInOutService;
    @Autowired
    private WorkAttendanceService workAttendanceService;


    private static Logger log = LoggerFactory.getLogger(LaborWagesController.class);

    @RequestMapping(value = "/selectSalaryByDownContractCodeAndDate", method = RequestMethod.GET)
    @ResponseBody
    public Object getIn(String downContractCode,String startDate,String endDate) {
        log.info("downContractCode==============="+downContractCode+"; startDate="+startDate+"; endDate="+endDate);
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = new ArrayList<HashMap>();
        //非空判断
        if(
                (downContractCode == null || "".equals(downContractCode) || "undefined".equals(downContractCode)) ||
                (startDate == null || "".equals(startDate) || "undefined".equals(startDate)) ||
                (endDate == null || "".equals(endDate) || "undefined".equals(endDate))
        ){
            dt.setData(list);
            return  dt;
        }
        //日期格式判断+日期格式设置
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf1.parse(startDate);
            Date end = sdf1.parse(endDate);
            startDate = sdf2.format(start);
            endDate = sdf2.format(end);
        } catch (ParseException e) {
            e.printStackTrace();
            dt.setData(list);
            return  dt;
        }
        log.info("downContractCode==============="+downContractCode+"; startDate="+startDate+"; endDate="+endDate);
        list = laborWagesService.selectSalaryByDownContractCodeAndDate(downContractCode,startDate,endDate);
        dt.setData(list);
        return dt;
    }

    @RequestMapping(value = "/createSalary", method = RequestMethod.POST)
    @ResponseBody
    public Object createSalary(HttpServletRequest request, String downContractCode, String monthDate) {
        try {
            //根据合同编号，查找该合同下的人员出入记录，根据出入记录和日期  查找 该人员下的考勤，根据考勤生成工资。
            List<String> laborInOutInIdList = laborInOutService.getInIdsByDownContractCode(downContractCode);
            //查找工资表中 已经 有的inId  （即已经生成过的）
            List<String> laborWagesInIdList = laborWagesService.selectInIdsFromLaborWagesByDay(monthDate);
            //查询需要的EnumVar
            String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
            DynamicDataSourceContextHolder.set(parentTenantAccount);
            List<EnumVar> enumVarlist = laborInOutService.selectEnumVarForLaborWages();
            String currentTenantAccount = request.getSession().getAttribute("CURRENT_TENANT_ACCOUNT").toString();
            DynamicDataSourceContextHolder.set(currentTenantAccount);
            //log.info("parentTenantAccount="+parentTenantAccount);
            //log.info("currentTenantAccount="+currentTenantAccount);

            int insertNum = 0;//插入数据条数
            String laborWagesIds = "";//有哪些id

            if(laborWagesInIdList != null && laborWagesInIdList.size()>0)
                if(laborInOutInIdList != null && laborInOutInIdList.size()>0)
                    for(int i=0;i<laborWagesInIdList.size();i++){
                        if(laborInOutInIdList.contains(laborWagesInIdList.get(i)))
                            laborInOutInIdList.remove(laborWagesInIdList.get(i));
                    }
            //此时laborInOutInIdList 中剩下的都是未生成工资的 inId
            log.info("此时laborInOutInIdList.size================="+laborInOutInIdList.size());


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Double workedLoads = 0.0;
            if(laborInOutInIdList != null && laborInOutInIdList.size()>0){
                for(int i=0;i<laborInOutInIdList.size();i++){
                    List<WorkAttendance> workAttendanceList  =  workAttendanceService.selectWorkAttendancesByInIdAndDate(laborInOutInIdList.get(i),monthDate);
                    if(workAttendanceList != null && workAttendanceList.size()>0){//如果有考勤
                        //log.info("workAttendanceList.get(i) 的  id 是================="+workAttendanceList.get(i).getAttendanceId());
                        workedLoads = 0.0;
                        LaborWages laborWages = new LaborWages();
                        laborWages.setWageId(String.valueOf(System.currentTimeMillis()));
                        laborWages.setInId(laborInOutInIdList.get(i));
                        laborWages.setYearMonth(sdf.parse(monthDate+"-01"));
                        laborWages.setWorkDays(workAttendanceList.size());
                        for(int j = 0;j<workAttendanceList.size();j++){
                            workedLoads += workAttendanceList.get(j).getWorkedLoad();
                        }
                        laborWages.setWorkedLoads(workedLoads);
                        Double grossPay = 0.0;
                        if(enumVarlist!=null && enumVarlist.size()>0){
                            LaborInOutWithBLOBs labor = laborInOutService.selectLaborInOutById(laborInOutInIdList.get(i));
                            //log.info("labor.getWageUnitPrice()================="+labor.getWageUnitPrice());
                            if(labor != null && labor.getWageUnitPrice() != null){
                                for(int k=0;k<enumVarlist.size();k++){
                                    if("wage_unit_price".equals(enumVarlist.get(k).getEnumName())
                                            && isEqual(Double.valueOf(labor.getWageUnitPrice()),Double.valueOf(enumVarlist.get(k).getEnumValue()))){
                                        grossPay = Double.valueOf(enumVarlist.get(k).getEnumValueName()) * workedLoads;
                                    }
                                }
                            }
                        }
                        laborWages.setGrossPay(grossPay);//应付金额  lio.wage_unit_price*lw.worked_loads

                        log.info(laborWages.toString());

                        int m = laborWagesService.insertLaborWages(laborWages);
                        insertNum += m;
                        laborWagesIds += laborWages.getWageId()+",";
                    }
                }
            }
            log.info("插入了"+insertNum+"条数据，主键分别为："+laborWagesIds);
            return JSON.parse("{msg : 'success'}");
        }catch (Exception e){
            e.printStackTrace();
            return JSON.parse("{msg : 'error'}");
        }
    }

    /**
     * 判断两个double是否相等
     */
    public boolean isEqual(double a, double b) {
        if ((a- b> -0.000001) && (a- b) < 0.000001)
            return true;
        else
            return false;
    }

}
