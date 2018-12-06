package com.banry.pscm.web.mvc.pscm.labour;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.labour.*;
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.SystemConstants;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 劳务管理：培训
 */
@Controller
@RequestMapping("/train")
public class TrainController {
    @Autowired
    private TrainService trainService;
    @Autowired
    private SystemConstants constants;
    @Autowired
    private ContractAttService contractAttService;
    @Autowired
    private LaborInOutService laborInOutService;
    @Autowired
    private ContractService contractService;


    private static Logger log = LoggerFactory.getLogger(TrainController.class);

    @RequestMapping("gettrains")
    @ResponseBody
    public Object getTrain(String downContractCode) {
        DataTableModel dt = new DataTableModel();
        List<HashMap> list = trainService.getTrainsByDownContractCode(downContractCode);
        dt.setData(list);
        return dt;
    }

    //getTrainsByContract
    @RequestMapping("getTrainsByContract")
    @ResponseBody
    public List<Train> getTrainsByContract(String downContractCode) {
        List<Train> list = trainService.getTrainsByContract(downContractCode);
        return list;
    }

    @RequestMapping("deletetrain")
    @ResponseBody
    public int deleteTrainByTrainCode(HttpServletRequest request,String trainCode) {
        //首先查找该培训记录下的劳务人员入出场记录，如果有劳务人员入出场记录，不能删除；如果没有，先删除附件，再删除培训记录
        List<String> photoList = new ArrayList<String>();
        List<LaborInOutWithBLOBs> list = laborInOutService.findLaborInOutWithBLOBsByTrainCode(trainCode);
        if(list.size()>0){
            return -1;
        }else{
            //删除附件
            Train train = trainService.selectTrainByTrainCode(trainCode);
            if(train != null){
                if(train.getTrainAttach() != null && !"".equals(train.getTrainAttach())){
                    deleteContractAtt(train.getTrainAttach());
                }
            }
            //删除培训记录
            return trainService.deleteTrainByTrainCode(trainCode);
        }
    }

    @RequestMapping(value = "/savetrainform", method = RequestMethod.POST)
    @ResponseBody
    public Object saveTrainAsForm(String trainCode,String downContractCode,String constructionTeam,String trainer,
                                  String trianDate,String trainAddress,String trainConntent) {
        try {
            Train train = new Train();  //培训记录
            Boolean b;//用于判断是添加还是更新
            if(trainCode != null && !"".equals(trainCode)){
                train.setTrainCode(trainCode);//update
                b = true;//更新
            }else{
                train.setTrainCode(String.valueOf(System.currentTimeMillis()));//insert
                b = false;//新增
            }
            if(downContractCode != null && !"".equals(downContractCode))
                train.setDownContractCode(downContractCode);
            if(trainer != null && !"".equals(trainer))
                train.setTrainer(trainer);
            if(trianDate != null && !"".equals(trianDate)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = sdf.parse(trianDate);
                train.setTrianDate(d);
            }
            if(trainAddress != null && !"".equals(trainAddress))
                train.setTrainAddress(trainAddress);
            if(trainConntent != null && !"".equals(trainConntent))
                train.setTrainConntent(trainConntent);
            if(b){
                trainService.updateTrain(train);//更新
            }else{
                trainService.addTrain(train);//新增
            }
            contractService.updateConstructionTeam(downContractCode,constructionTeam);
        } catch (ParseException e) {
            e.printStackTrace();
            return JSON.parse("{msg : 'error'}");
        }
        return JSON.parse("{msg : 'success'}");
    }

    @RequestMapping(value = "/uploadtrain", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(MultipartFile file,String trainCode) {
        String result = "success";

        String actualFileName = file.getOriginalFilename();
        String type = actualFileName.substring(actualFileName.lastIndexOf('.'));
        String fileInName = String.valueOf(System.currentTimeMillis());
        String path = constants.getUploadDirReal() + constants.getAttach() + fileInName + type;
        // log.info("path is :::::::::::::"+path);
        log.info("trainCode is :::::::::::::"+trainCode);
        try {
            File dest = new File(path);
            file.transferTo(dest);
            ContractAtt contractAtt = new ContractAtt();
            contractAtt.setFileInName(fileInName);
            contractAtt.setActualFileName(actualFileName);
            contractAtt.setLocation(constants.getAttach() + fileInName + type);
            contractAtt.setType(type);
            contractAttService.saveContractAtt(contractAtt);

            trainService.saveAtt(trainCode,fileInName);

        } catch (IOException e) {
            log.error("上传文件异常:" + path, e);
            result = "failed";
        } catch (UtilException e) {
            log.error("保存附件信息异常", e);
            result = "failed";
        }
        return result;
    }

    @RequestMapping(value = "deleteContractAtt")
    @ResponseBody
    public Object deleteContractAtt(String indexId,String trainCode) {
        try {
            String ids[] = indexId.split(",");
            for (String id : ids) {
                if(id != null && !"".equals(id)){
                    //删除硬盘上的文件
                    List<ContractAtt> list = contractAttService.findByFileInNames(id);
                    if(list.size()>0){
                        String fileName = list.get(0).getFileInName()+list.get(0).getType();//待删除的文件名
                        String filePath = constants.getUploadDirReal() + constants.getAttach() + fileName;
                        File file = new File(filePath);
                        if(file.exists()){
                            file.delete();
                        }else{
                            log.info("硬盘上不存在该文件，删除失败！");
                        }
                    }
                    contractAttService.deleteContractAtt(id);
                    trainService.deleteTrainAttachById(id,trainCode);
                }
            }
            return JSON.parse("{msg:'success'}");
        } catch (UtilException e) {
            e.printStackTrace();
            return JSON.parse("{msg:'failed'},{exception:"+e+"}");
        }
    }

    /**
     * 删除附件表（contract_att）中的记录，和磁盘上的附件
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

}
