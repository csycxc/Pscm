package com.banry.pscm.web.mvc.pscm.activiti;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by liuruijie on 2017/4/20. 模型管理
 */
@RestController
@RequestMapping("models")
public class ModelerController {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	ObjectMapper objectMapper;

	protected Logger logger = LoggerFactory.getLogger(getClass()); 
	
	/**
	 * 模型列表
	 */
	@RequestMapping(value = "model-list")
	public ModelAndView modelList() {
		ModelAndView mav = new ModelAndView("activiti/model-list");
		return mav;
	}
	
	/**
	 * 新建一个空模型
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("newModel")
	public void newModel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		try {
			// 初始化一个空模型
			Model model = repositoryService.newModel();

			// 设置一些默认信息
			String name = "new-process";
			String description = "";
			int revision = 1;
			String key = "process";

			ObjectNode modelNode = objectMapper.createObjectNode();
			modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

			model.setName(name);
			model.setKey(key);
			model.setMetaInfo(modelNode.toString());

			repositoryService.saveModel(model);
			String id = model.getId();

			// 完善ModelEditorSource
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));
			response.sendRedirect(request.getContextPath() + "/editor?modelId=" + id);
		} catch (Exception e) {
			logger.error("创建模型失败：", e);
		}
	}
	
	/**
     * 发布模型为流程定义
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("{id}/deployment")
    public Map<String, Object> deploy(@PathVariable("id")String id) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try {
	        //获取模型
	        Model modelData = repositoryService.getModel(id);
	        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
	
	        if (bytes == null) {
	            map.put("result", false);
				map.put("retMsg", "模型数据为空，请先设计流程并成功保存，再进行发布。");
				return map;
	        }
	
	        JsonNode modelNode = new ObjectMapper().readTree(bytes);
	
	        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
	        if(model.getProcesses().size()==0){
	            map.put("result", false);
				map.put("retMsg", "数据模型不符要求，请至少设计一条主线流程。");
				return map;
	        }
	        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
	
	        //发布流程
	        String processName = modelData.getName() + ".bpmn20.xml";
	        Deployment deployment = repositoryService.createDeployment()
	                .name(modelData.getName())
	                .addString(processName, new String(bpmnBytes, "UTF-8"))
	                .deploy();
	        modelData.setDeploymentId(deployment.getId());
	        repositoryService.saveModel(modelData);
	        map.put("result", true);
			return map;
    	} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
    }
    
    @DeleteMapping("{id}")
	public Map<String, Object> deleteOne(@PathVariable("id") String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
        try {
        	repositoryService.deleteModel(id);
        	map.put("result", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
    }
    
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public Map<String, Object> deployUploadedFile(
            @RequestParam("uploadfile") MultipartFile uploadfile) {
    	Map<String, Object> map = new HashMap<String, Object>();
        InputStreamReader in = null;
        try {
            try {
                String fileName = uploadfile.getOriginalFilename();
                if (fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn")) {
                    XMLInputFactory xif = XMLInputFactory.newInstance();
                    in = new InputStreamReader(new ByteArrayInputStream(uploadfile.getBytes()), "UTF-8");
                    XMLStreamReader xtr = xif.createXMLStreamReader(in);
                    BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

                    if (bpmnModel.getMainProcess() == null || bpmnModel.getMainProcess().getId() == null) {
                    	map.put("result", false);
            			map.put("retMsg", "上传失败");
            			return map;
                    } else {

                        if (bpmnModel.getLocationMap().isEmpty()) {
                			map.put("result", false);
                			map.put("retMsg", "上传失败");
                			return map;
                        } else {

                            String processName = null;
                            if (StringUtils.isNotEmpty(bpmnModel.getMainProcess().getName())) {
                                processName = bpmnModel.getMainProcess().getName();
                            } else {
                                processName = bpmnModel.getMainProcess().getId();
                            }
                            Model modelData;
                            modelData = repositoryService.newModel();
                            ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processName);
                            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
                            modelData.setMetaInfo(modelObjectNode.toString());
                            modelData.setName(processName);

                            repositoryService.saveModel(modelData);

                            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                            ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

                            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
                        }
                    }
                } else {
        			map.put("result", false);
        			map.put("retMsg", "只能上传.bpmn20.xml或.bpmn结尾的文件");
        			return map;
                }
                map.put("result", true);
    			return map;
            } catch (Exception e) {
            	e.printStackTrace();
    			map.put("result", false);
    			map.put("retMsg", e.getMessage());
    			return map;
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                	e.printStackTrace();
        			map.put("result", false);
        			map.put("retMsg", e.getMessage());
        			return map;
                }
            }
        }
    }
    
    /**
     * 导出model的xml文件
     */
    @RequestMapping(value = "export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
      try {
        Model modelData = repositoryService.getModel(modelId);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
        ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
        IOUtils.copy(in, response.getOutputStream());
        String filename = bpmnModel.getMainProcess().getId() + ".bpmn20.xml";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.flushBuffer();
      } catch (Exception e) {
        logger.error("导出model的xml文件失败：modelId={}", modelId, e);
      }
    }
}
