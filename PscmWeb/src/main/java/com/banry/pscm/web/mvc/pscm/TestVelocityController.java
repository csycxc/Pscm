package com.banry.pscm.web.mvc.pscm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.model.TestGridModel;

@Controller
@RequestMapping("/ve")
public class TestVelocityController {

	/**
	 * 列表数据
	 * 
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public Object test() {
		List<TestGridModel> listGrid = new ArrayList<TestGridModel>();
		DataTableModel data = new DataTableModel();
		
		for(int i=1;i<70;i++){
			TestGridModel m1 = new TestGridModel();
			m1.setName1("name1"+i);
			m1.setName2("name2"+i);
			m1.setName3("name3"+i);
			listGrid.add(m1);
		}
	
		data.setData(listGrid);
		data.setDraw(1L);
		data.setRecordsTotal(1L);
		data.setRecordsFiltered(1L);
		return data;
	}

	@RequestMapping("/gantt")
	public Object gantt() {
		System.out.println("欢迎您gantt");
		ModelAndView mv = new ModelAndView("gantt");
		mv.addObject("test", "欢迎您Ve");
		return mv;
	}
}
