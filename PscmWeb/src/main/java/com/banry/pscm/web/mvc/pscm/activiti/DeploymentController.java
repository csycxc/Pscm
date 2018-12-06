package com.banry.pscm.web.mvc.pscm.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuruijie on 2017/4/20.
 */
@RestController
@RequestMapping("deployments")
public class DeploymentController {
    @Autowired
    RepositoryService repositoryService;


    @DeleteMapping("{id}")
	public Map<String, Object> deleteOne(@PathVariable("id") String id) {
    	Map<String, Object> map = new HashMap<String, Object>();
        try {
        	repositoryService.deleteDeployment(id);
        	map.put("result", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
    }
}
