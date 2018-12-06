package com.banry.pscm.web.mvc.pscm.labour;

import com.banry.pscm.service.labour.WorkAttendanceService;
import com.banry.pscm.web.mvc.model.DataTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/workAttendance")
public class WorkAttendanceController {

    @Autowired
    private WorkAttendanceService workAttendanceService;

    private static Logger log = LoggerFactory.getLogger(WorkAttendanceController.class);

    @RequestMapping(value = "/getTodayWorkAttendance", method = RequestMethod.GET)
    @ResponseBody
    public Object getWorkAttendanceIn(String downContractCode,String trainCode) {
        log.info("downContractCode==============="+downContractCode+"and trainCode="+trainCode);
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = new ArrayList<HashMap>();
        if(downContractCode == null || "".equals(downContractCode) || "undefined".equals(downContractCode)){
            dt.setData(list);
            return  dt;
        }
        if(trainCode == null || "".equals(trainCode) || "undefined".equals(trainCode))
            list = workAttendanceService.getWorkAttendancesByDownContractCode(downContractCode);
        else
            list = workAttendanceService.getWorkAttendancesByTrainCode(trainCode);
        dt.setData(list);
        return dt;
    }

    @RequestMapping(value = "/selectWorkAttendanceByInId", method = RequestMethod.GET)
    @ResponseBody
    public DataTableModel selectWorkAttendanceByInId(String inId) {
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = workAttendanceService.selectWorkAttendanceByInId(inId);
        /*if(list.size()>0){
            for(int i=0;i<list.size();i++){
                log.info("============================="+String.valueOf(list.get(i)));
            }
        }*/
        dt.setData(list);
        return dt;
    }

}
