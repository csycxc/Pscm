package com.banry.pscm.web.mvc.pscm.labour;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.labour.*;
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.SystemConstants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.service.contract.DownContract;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 劳务管理：劳务人员
 * @author csy
 */
@Controller
@RequestMapping("/labor")
public class LaborController {

	@Autowired
	private LaborService laborService;
	@Autowired
	private LaborInOutService laborInOutService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private SystemConstants constants;
	@Autowired
	private ContractAttService contractAttService;

	private static Logger log = LoggerFactory.getLogger(LaborController.class);

	
	@RequestMapping("getcontract")
	@ResponseBody
	public Map<String,List<DownContract>> getContract() {
		log.info("");
		List<DownContract> list = contractService.findDownContracts(null,null,null);
		Map<String,List<DownContract>> map = new HashMap<String,List<DownContract>>();
		((HashMap) map).put("contract",list);
		return map;
	}

	@RequestMapping("getEnumVar")
	@ResponseBody
	public Map<String,List<EnumVar>> getEnumVar(HttpServletRequest request) {

		String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
		DynamicDataSourceContextHolder.set(parentTenantAccount);

		Map<String,List<EnumVar>> map = new HashMap<String,List<EnumVar>>();
		List<EnumVar>list = laborInOutService.selectEnumVarForLaborWages();

		List<EnumVar> wage_modelList = new ArrayList<EnumVar>();
		List<EnumVar> wage_umintList = new ArrayList<EnumVar>();
		List<EnumVar> wage_unit_priceList = new ArrayList<EnumVar>();
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				log.info("================"+list.get(i).getEnumName());
				if("wage_model".equals(list.get(i).getEnumName()))
					wage_modelList.add(list.get(i));
				if("wage_umint".equals(list.get(i).getEnumName()))
					wage_umintList.add(list.get(i));
				if("wage_unit_price".equals(list.get(i).getEnumName()))
					wage_unit_priceList.add(list.get(i));
			}
		}
		map.put("wageModel",wage_modelList);
		map.put("wageUmint",wage_umintList);
		map.put("wageUnitPrice",wage_unit_priceList);
		return map;
	}

	@RequestMapping("getlaborbyidnumber")
	@ResponseBody
	public Object getLaborByIdNumber(String idNumber,String inId) {
		if (idNumber != null && idNumber.length() > 0) {
			List<HashMap> list = laborService.getLaborByIdNumber(idNumber);
			return list;
		} else if (inId != null && inId.length() > 0) {
			String selectIdNumber = laborInOutService.selectIdNumberByInId(inId);
			if (selectIdNumber != null && selectIdNumber.length() > 0) {
				return laborService.getLaborByIdNumber(selectIdNumber);
			} else {
				return null;
			}
		}else {
			return null;
		}
	}

	@RequestMapping(value = "/savelaborform", method = RequestMethod.POST)
	@ResponseBody
	public Object saveLaborAsForm(String downContractCode,String trainCode, String idNumber, String inId, String name,
								  String sex, String height, String weigh, String birthday, String educationDegree,
								  String workerType, String inDate, String address, String examScore,
								  String wageModel, String wageUnitPrice, String wageUmint,
								  MultipartFile idPhoto, MultipartFile inIdPhoto) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(idNumber != null && !"".equals(idNumber)){
				String idPhotoFileInName = "";
				if(idPhoto != null && !"".equals(idPhoto)){
					//添加新的idPhoto图片附件
					idPhotoFileInName = saveFileAndGetFileName(idPhoto);
				}
				LaborWithBLOBs labor = new LaborWithBLOBs();
				labor.setIdNumber(idNumber);
				if(name != null && !"".equals(name))
					labor.setName(name);
				if(sex != null && !"".equals(sex))
					labor.setSex(sex);
				if(birthday != null && !"".equals(birthday))
					labor.setBirthday(sdf.parse(birthday));
				if(educationDegree != null && !"".equals(educationDegree))
					labor.setEducationDegree(Integer.parseInt(educationDegree));
				if(height != null && !"".equals(height))
					labor.setHeight(Integer.parseInt(height));
				if(weigh != null && !"".equals(weigh))
					labor.setWeigh(Integer.parseInt(weigh));
				if(workerType != null && !"".equals(workerType))
					labor.setWorkerType(workerType);
				if(address != null && !"".equals(address))
					labor.setAddress(address);

				if(idPhotoFileInName != ""){
					labor.setIdPhoto(idPhotoFileInName);
					//如果要添加新的idPhoto图片附件，需要先把老的删除（删除的是附件表contract_att中的记录和硬盘上的文件）
					LaborWithBLOBs oldLabor = laborService.selectLaborWithBLOBsByIdNumber(idNumber);
					if(oldLabor != null){//更新（且删除图片）
						String oldIdPhoto = oldLabor.getIdPhoto();
						if(oldIdPhoto != null && !"".equals(oldIdPhoto)){
							deleteContractAtt(oldIdPhoto);
						}
					}
				}
				laborService.saveLabor(labor);
				if(downContractCode != null && !"".equals(downContractCode)){
					String inIdPhotoFileInName = "";
					if(inIdPhoto != null && !"".equals(inIdPhoto)){
						inIdPhotoFileInName = saveFileAndGetFileName(inIdPhoto);
					}
					LaborInOutWithBLOBs laborInOut = new LaborInOutWithBLOBs();
					if(inId != null && !"".equals(inId))
						laborInOut.setInId(inId);
					laborInOut.setIdNumber(idNumber);
					laborInOut.setDownContractCode(downContractCode);
					if(trainCode != null && !"".equals(trainCode))
						laborInOut.setTrainCode(trainCode);
					if(examScore != null && !"".equals(examScore))
						laborInOut.setExamScore(Double.parseDouble(examScore));
					if(inDate != null && !"".equals(inDate))
						laborInOut.setInDate(sdf.parse(inDate));
					if(wageModel != null && !"".equals(wageModel))
						laborInOut.setWageModel(Integer.parseInt(wageModel));
					if(wageUnitPrice != null && !"".equals(wageUnitPrice))
						laborInOut.setWageUnitPrice(Double.parseDouble(wageUnitPrice));
					if(wageUmint != null && !"".equals(wageUmint))
						laborInOut.setWageUmint(wageUmint);
					if(inIdPhotoFileInName != ""){
						laborInOut.setInIdPhoto(inIdPhotoFileInName);
						//判断更新还是插入，更新的话删除已有的附件（图片）
						LaborInOutWithBLOBs lio = laborInOutService.selectLaborInOutById(inId);
						if(lio != null){//更新
							String deletePhotoName = lio.getInIdPhoto();
							if(deletePhotoName != null && !"".equals(deletePhotoName)){
								deleteContractAtt(deletePhotoName);
							}
						}
					}
					laborInOutService.saveLaborInOut(laborInOut);
				}else{
					return JSON.parse("{msg : '合同编码不能为空'}");
				}
			}else{
				return JSON.parse("{msg : '身份证号不能为空'}");
			}
		} catch (ParseException e) {
			log.error("解析异常:", e);
			return JSON.parse("{msg : 'failed'}");
		}
		return JSON.parse("{msg : 'success'}");
	}

	@RequestMapping("uploadPhoto")
	@ResponseBody
	public String uploadPhoto(@RequestParam("inId") String inId, @RequestParam("string") String string, @RequestParam("base64Data") String base64Data, HttpServletRequest request, HttpServletResponse response) {
		log.info("inId====="+inId+",,,string====="+string);
		String fileInName = String.valueOf(System.currentTimeMillis());
		String suffix = "";
		String actualFileName = "";
		try{
			String dataPrix = "";
			String data = "";
			if(base64Data == null || "".equals(base64Data)){
				throw new Exception("上传失败，上传图片数据为空");
			}else{
				String [] d = base64Data.split("base64,");
				if(d != null && d.length == 2){
					dataPrix = d[0];
					data = d[1];
				}else{
					throw new Exception("上传失败，数据不合法");
				}
			}
			if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
				suffix = ".jpg";
			} else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
				suffix = ".ico";
			} else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
				suffix = ".gif";
			} else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
				suffix = ".png";
			}else{
				throw new Exception("上传图片格式不合法");
			}
			actualFileName = fileInName + suffix;
			log.info("上传照的图片：：：：：：：=========================="+actualFileName);

			//因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
			byte[] bs = Base64Utils.decodeFromString(data);
			try{
				//使用apache提供的工具类操作流//System.out.println(request.getServletContext().getRealPath("/upload"));
				FileUtils.writeByteArrayToFile(new File(constants.getUploadDirReal() + constants.getAttach(), actualFileName), bs);

				//附件表插入数据
				ContractAtt contractAtt = new ContractAtt();
				contractAtt.setFileInName(fileInName);
				contractAtt.setActualFileName(actualFileName);
				contractAtt.setLocation(constants.getAttach() + fileInName + suffix);
				contractAtt.setType(suffix);
				contractAttService.saveContractAtt(contractAtt);
			}catch(Exception ee){
				throw new Exception("上传失败，写入文件失败，"+ee.getMessage());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		LaborInOutWithBLOBs lio = laborInOutService.selectLaborInOutById(inId);
		if(lio != null && lio.getIdNumber() != null){
			//判断string  是  idPhoto、inIdPhoto、outPhoto三种中的哪个，分别处理
			if("idPhoto".equals(string)){
				LaborWithBLOBs labor = laborService.selectLaborWithBLOBsByIdNumber(lio.getIdNumber());
				if(labor != null){
					if(labor.getIdPhoto() != null && !"".equals(labor.getIdPhoto())){
						deleteContractAtt(labor.getIdPhoto());
					}
					labor.setIdPhoto(fileInName);
					laborService.updateLabor(labor);
					return fileInName;
				}
			}else if("inIdPhoto".equals(string)){
				if(lio.getInIdPhoto() != null && !"".equals(lio.getInIdPhoto())){
					deleteContractAtt(lio.getInIdPhoto());
				}
				lio.setInIdPhoto(fileInName);
				laborInOutService.updateLaborInOutSelective(lio);
				return fileInName;
			}else if("outPhoto".equals(string)){
				if(lio.getOutPhoto() != null && !"".equals(lio.getOutPhoto())){
					deleteContractAtt(lio.getOutPhoto());
				}
				lio.setOutPhoto(fileInName);
				laborInOutService.updateLaborInOutSelective(lio);
				return fileInName;
			}
		}
		return "";
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

	public String saveFileAndGetFileName(MultipartFile file){
		String actualFileName = file.getOriginalFilename();
		String type = actualFileName.substring(actualFileName.lastIndexOf('.'));
		String fileInName = String.valueOf(System.currentTimeMillis());
		String path = constants.getUploadDirReal() + constants.getAttach() + fileInName + type;
		try {
			File dest = new File(path);
			file.transferTo(dest);
			ContractAtt contractAtt = new ContractAtt();
			contractAtt.setFileInName(fileInName);
			contractAtt.setActualFileName(actualFileName);
			contractAtt.setLocation(constants.getAttach() + fileInName + type);
			contractAtt.setType(type);
			contractAttService.saveContractAtt(contractAtt);
			return fileInName;
		} catch (IOException e) {
			log.error("上传身份证照片文件异常:" + path, e);
			return "";
		} catch (UtilException e) {
			log.error("保存身份证照片信息异常", e);
			return "";
		}
	}

}
