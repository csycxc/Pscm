package com.banry.pscm.web.mvc.pscm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.SystemConstants;

@Controller
@RequestMapping(value="contractAtt")
public class ContractAttController {

	@Autowired
	ContractAttService contractAttService;
	@Autowired
	SystemConstants constants;
	
	@RequestMapping(value = "getContractAttList")
	@ResponseBody
	public Object getContractAttList(String fileInNames) {
		DataTableModel data = new DataTableModel();
		try {
			if (fileInNames != null && !"".equals(fileInNames)) {
				List listGrid = contractAttService.findByFileInNames(fileInNames);
				data.setData(listGrid);
			} else {
				data.setData(new ArrayList());
			}
			return data;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "deleteContractAtt")
	@ResponseBody
	public String deleteContractAtt(String indexId) {
		try {
			String ids[] = indexId.split(",");
			for (String id : ids) {
				contractAttService.deleteContractAtt(id);
			}
			return "{success : true}";
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{success : true}";
	}

	@RequestMapping(value = "saveContractAtt")
	@ResponseBody
	public String saveContractAtt(HttpServletRequest request,HttpServletResponse response) {
		try {
			MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest)request;
            Map<String, MultipartFile> files = Murequest.getFileMap();//得到文件map对象
			String folder = "attach/";
			String upaloadUrl = constants.getUploadDirReal() + folder;
			
			File dir = new File(upaloadUrl);
            if(!dir.exists()) {//目录不存在则创建
                dir.mkdirs();
            }
            String fileInNames = "";
			for(MultipartFile mFile :files.values()){
				ContractAtt doc = new ContractAtt();
				String originalFilename = mFile.getOriginalFilename();
				String type = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
				String fileName = String.valueOf(new Date().getTime());
//				byte[] bytes = mFile.getBytes();
				// 文件保存路径  
	            String filePath = upaloadUrl + fileName + type;  
	            // 转存文件  
	            mFile.transferTo(new File(filePath));
				doc.setType(type);
				doc.setActualFileName(originalFilename);
				doc.setLocation(folder + fileName + type);
				doc.setFileInName(fileName);
				contractAttService.saveContractAtt(doc);
				if ("".equals(fileInNames)) {
					fileInNames = fileName;
				} else {
					fileInNames += "," + fileName;
				}
			}
			return "{success : true, fileInNames:'" + fileInNames + "'}";
		} catch (IOException e) {
			e.printStackTrace();
			return "{success : false}";
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success : false}";
		}
	}
	
	@RequestMapping(value = "fileDownload" )
	@ResponseBody
	public ModelAndView fileDownload(HttpServletRequest request, HttpServletResponse response,
			String fileName, String fileRealPath) throws IOException{
		fileName = java.net.URLDecoder.decode(fileName, "utf-8");
        String contentType = "application/octet-stream";  
        request.setCharacterEncoding("UTF-8");  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
  
        long fileLength = new File(constants.getUploadDirReal() + fileRealPath).length();  
  
        response.setContentType(contentType);  
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(fileName.getBytes("GB2312"), "ISO_8859_1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(constants.getUploadDirReal() + fileRealPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();  
        bos.close();  
		return null ;
	}
}
