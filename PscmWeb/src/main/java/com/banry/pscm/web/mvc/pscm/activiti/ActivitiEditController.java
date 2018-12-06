package com.banry.pscm.web.mvc.pscm.activiti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActivitiEditController {
	
    @GetMapping("editor")
    public String editor(){
        return "modeler";
    }
}
