package com.banry.pscm.web.mvc.pscm.labour;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.labour.LaborInOut;
import com.banry.pscm.service.labour.LaborInOutService;
import com.banry.pscm.service.labour.LaborInOutWithBLOBs;
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.SystemConstants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/laborInOut")
public class LaborInOutController {

    @Autowired
    private LaborInOutService laborInOutService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private SystemConstants constants;
    @Autowired
    private ContractAttService contractAttService;

    private static Logger log = LoggerFactory.getLogger(LaborInOutController.class);


    @RequestMapping(value = "/deleteLaborInOut", method = RequestMethod.POST)
    @ResponseBody
    public int deleteLaborInOutByInId(HttpServletRequest request, String inId,String inIdPhoto) {
        //附件不为空时先删除附件
        if(inIdPhoto != null && !"".equals(inIdPhoto)){
            deleteContractAtt(inIdPhoto);
        }
        return laborInOutService.deleteLaborInOutByInId(inId);
    }

    @RequestMapping(value = "/selectLaborInOutsByTrainCode", method = RequestMethod.GET)
    @ResponseBody
    public Object selectLaborsByTrainCode(String trainCode) {
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = laborInOutService.selectLaborInOutsByTrainCode(trainCode);
        dt.setData(list);
        return dt;
    }

    /**
     * 删除照片   表（contract_att）中的记录，和磁盘上的附件
     * @param deleteFileName
     */
    public void deleteContractAtt(String deleteFileName){
        String ids[] = deleteFileName.split(",");
        for (String id : ids) {
            List<ContractAtt> list = null;
            try {
                list = contractAttService.findByFileInNames(id);
                if(list.size()>0){
                    String fileName = list.get(0).getFileInName()+list.get(0).getType();//待删除的文件名
                    String filePath = constants.getUploadDirReal() + constants.getAttach() + fileName;
                    File file = new File(filePath);
                    if(file.exists()){ file.delete(); }else{
                        log.info("硬盘上该文件不存在，删除失败！");
                    }
                }
                contractAttService.deleteContractAtt(id);
            } catch (UtilException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/getLaborInOutByDownContractCodeOrTrainCode", method = RequestMethod.GET)
    @ResponseBody
    public Object getLaborInOutByDownContractCodeOrTrainCode(String downContractCode,String trainCode) {
        log.info("downContractCode==============="+downContractCode);
        log.info("trainCode==============="+trainCode);
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = new ArrayList<HashMap>();
        if(downContractCode == null || "".equals(downContractCode) || "undefined".equals(downContractCode)){
            dt.setData(list);
            return  dt;
        }
        if(trainCode == null || "".equals(trainCode) || "undefined".equals(trainCode))
            list = laborInOutService.getLaborInOutByDownContractCode(downContractCode);
        else
            list = laborInOutService.getLaborInOutByTrainCode(trainCode);
        dt.setData(list);
        return dt;
    }
    @RequestMapping(value = "/getLaborInOutForWorkAttendance", method = RequestMethod.GET)
    @ResponseBody
    public Object getLaborInOutForWorkAttendance(String downContractCode,String trainCode,String inOrOut) {
        log.info("downContractCode==============="+downContractCode+"trainCode="+trainCode+"inOrOut="+inOrOut);
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = null;
        if(downContractCode == null || "".equals(downContractCode) || "undefined".equals(downContractCode)){
            dt.setData(list);
            return  dt;
        }
        if(trainCode == null || "".equals(trainCode) || "undefined".equals(trainCode))
            list = laborInOutService.getLaborInOutByDownContractCodeAndInOrOut(downContractCode,inOrOut);
        else
            list = laborInOutService.getLaborInOutByTrainCodeAndInOrOut(trainCode,inOrOut);
        dt.setData(list);
        return dt;
    }

    @RequestMapping(value = "/saveLaborInOutOfReamrk", method = RequestMethod.POST)
    @ResponseBody
    public int saveLaborInOutOfReamrk(String inId,String reamrk) {
        LaborInOutWithBLOBs labor = new LaborInOutWithBLOBs();
        labor.setInId(inId);
        labor.setReamrk(reamrk);
        return laborInOutService.updateLaborInOutSelective(labor);
    }

    @RequestMapping(value = "/saveLaborInOutLetIn", method = RequestMethod.POST)
    @ResponseBody
    public int saveLaborInOutLetIn(@RequestBody String jsonData) {
        log.info("jsonData======"+jsonData);
        //{'laborInOutList':[{"inId":"1542676429422","examScore":90,"inDate":""},{"inId":"1542763444642","examScore":55,"inDate":""}]}
        JSONObject jsonObj = JSONObject.fromObject(jsonData);
        JSONArray jsonArray = (JSONArray) jsonObj.get("laborInOutList");
        List<LaborInOutWithBLOBs> list = new ArrayList<LaborInOutWithBLOBs>();
        for (int i=0; i < jsonArray.size(); i++)    {
            LaborInOutWithBLOBs laborInOutWithBLOBs = new LaborInOutWithBLOBs();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String inDate = jsonObject.getString("inDate");
            laborInOutWithBLOBs.setInId(jsonObject.getString("inId"));
            Double examScore = null;
            if(jsonObject.getString("examScore") != null && !"".equals(jsonObject.getString("examScore"))){
                examScore = Double.parseDouble(jsonObject.getString("examScore"));
                laborInOutWithBLOBs.setExamScore(examScore);
            }
            if((examScore != null && examScore>60)&&(inDate == null || "".equals(inDate))){
                list.add(laborInOutWithBLOBs);
            }
        }
        if(list.size()>0){
            return laborInOutService.letInLaborInOut(list);
        }else{
            return 0;
        }
    }

    @RequestMapping(value = "/singleLaborInOutForOut", method = RequestMethod.POST)
    @ResponseBody
    public Object singleLaborInOutForOut(String inId) {
        log.info("inId======"+inId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LaborInOutWithBLOBs oldLaborInOut = laborInOutService.selectLaborInOutById(inId);
        if(oldLaborInOut != null && (oldLaborInOut.getOutDate() == null || "".equals(oldLaborInOut.getOutDate()))){
            Date date = new Date();
            LaborInOutWithBLOBs laborInOut = new LaborInOutWithBLOBs();
            laborInOut.setInId(inId);
            laborInOut.setOutDate(date);
            laborInOutService.updateLaborInOutSelective(laborInOut);
            return JSON.parse("{outDate: '"+sdf.format(date)+"'}");
        }else{
            return JSON.parse("{outDate:''}");
        }
    }

    @RequestMapping(value = "/singleLaborInOutForIn", method = RequestMethod.POST)
    @ResponseBody
    public Object singleLaborInOutForIn(String inId) {
        log.info("inId======"+inId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LaborInOutWithBLOBs oldLaborInOut = laborInOutService.selectLaborInOutById(inId);
        if(
           oldLaborInOut != null && (oldLaborInOut.getInDate() == null || "".equals(oldLaborInOut.getInDate())) &&
           (oldLaborInOut.getExamScore() != null && !"".equals(oldLaborInOut.getExamScore())&&oldLaborInOut.getExamScore() > 60)
        ){
            Date date = new Date();
            LaborInOutWithBLOBs laborInOut = new LaborInOutWithBLOBs();
            laborInOut.setInId(inId);
            laborInOut.setInDate(date);
            laborInOutService.updateLaborInOutSelective(laborInOut);
            return JSON.parse("{inDate: '"+sdf.format(date)+"'}");
        }else{
            return JSON.parse("{inDate:''}");
        }
    }

    @RequestMapping(value = "/saveLaborInOutLetOut", method = RequestMethod.POST)
    @ResponseBody
    public int saveLaborInOutLetOut(@RequestBody String jsonData) {
        log.info("jsonData======"+jsonData);
        JSONObject jsonObj = JSONObject.fromObject(jsonData);
        JSONArray jsonArray = (JSONArray) jsonObj.get("laborInOutList");
        List<LaborInOutWithBLOBs> list = new ArrayList<LaborInOutWithBLOBs>();
        for (int i=0; i < jsonArray.size(); i++)    {
            LaborInOutWithBLOBs laborInOutWithBLOBs = new LaborInOutWithBLOBs();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String outDate = jsonObject.getString("outDate");
            laborInOutWithBLOBs.setInId(jsonObject.getString("inId"));
            if(outDate == null || "".equals(outDate)){
                list.add(laborInOutWithBLOBs);
            }
        }
        if(list.size()>0){
            return laborInOutService.letOutLaborInOut(list);
        }else{
            return 0;
        }
    }


}
