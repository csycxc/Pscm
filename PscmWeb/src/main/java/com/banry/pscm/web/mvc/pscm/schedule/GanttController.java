package com.banry.pscm.web.mvc.pscm.schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.service.schedule.EngDivFinishRate;
import com.banry.pscm.service.schedule.EngDivFinishRateService;
import com.banry.pscm.service.schedule.EngDivisionPlan;
import com.banry.pscm.service.schedule.EngDivisionPlanLinks;
import com.banry.pscm.service.schedule.EngDivisionPlanLinksService;
import com.banry.pscm.service.schedule.EngDivisionPlanService;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.web.mvc.model.GanttTask;
import com.banry.pscm.web.utils.DateUtils;

@RestController
@RequestMapping("/gantt")
public class GanttController {
	
	private final static String ERROR = "error";
    private final static String INVALID = "invalid";
    private final static String INSERTED = "inserted";
    private final static String UPDATED = "updated";
    private final static String DELETED = "deleted";
    private final static String QUERIED = "queried";
    //时间格式化格式
    public final static String TIMEPATTERN = "yyyy-MM-dd";
    
    @Autowired
	private SubDivWorkBillService subDivWorkBillService;
	@Autowired
	private EngDivisionPlanService engDivisionPlanService;
	@Autowired
	private EngDivFinishRateService engDivFinishRateService;
	@Autowired
	private EngDivisionPlanLinksService engDivisionPlanLinksService;

	@RequestMapping(value="/ganttData",produces = "text/html;charset=UTF-8")
	public String ganttData(String gantt_mode, HttpServletRequest request) {
		System.out.println(gantt_mode);
		Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            Object param = params.nextElement();
            System.out.println("参数对: " + param.toString() + " = " + request.getParameter(param.toString()));
        }
        return this.handleRequest(request);
	}
	
    public String handleRequest(HttpServletRequest request) {
        String[] ids = this.getIds(request);
        List<Map> params = this.getPostValues(ids, request);
//        List<ActionResult> results = new ArrayList<ActionResult>();
        boolean roolback = false;
        for (Map<String, String> param : params) {
            //获取对象实体类型
            Class<?> clazz = this.mode2EntityType(param.get("mode"));
            //判断对象操作类型 inserted updated deleted
            String outerOp = param.get("!nativeeditor_status");
            //返回的提示信息
            String output = null;
            String id = param.get("id");
            String version = param.get("version");
            //分项工程
            String itemcode = param.get("itemcode");
            //工序
            String workcode = param.get("workcode");
            String nid = id;
            //仅对数据抛出的异常操作.
            try {
                //操作对象
                if (QUERIED.equals(outerOp)) {
                		//查询此分项工程下面的所有工序
                		String sqlWhere = "getParentDivSnCode(a.division_sn_code)='" + itemcode + "' and a.div_version='" + version + "'";
                		String linkSqlWhere = "exists (select 1 from eng_division b where a.source = concat(b.division_sn_code,'|','" + version + "') and getParentDivSnCode(b.division_sn_code)='" + itemcode + "')";
                        List<EngDivisionPlanLinks> links = engDivisionPlanLinksService.selectBySqlWhere(linkSqlWhere);
                        List<EngDivisionPlan> planLst = engDivisionPlanService.selectBySqlWhere(sqlWhere);
                        List<GanttTask> tasks = new ArrayList<GanttTask>();
                        for (EngDivisionPlan plan : planLst) {
                        	GanttTask task = new GanttTask();
                        	//划分编号+“|”+版本
                        	task.setId(plan.getDivisionSnCode() + "|" + plan.getDivVersion());
                        	task.setVersion(plan.getDivVersion());
                        	//获取分项工程清单
                        	SubDivWorkBill subDiv = subDivWorkBillService.findSubDivWorkBillByKey(plan.getDivisionSnCode());
                        	//获取进度
                        	EngDivFinishRate rate = engDivFinishRateService.sumFinishNumberByDivSnCode(plan.getDivisionSnCode());
                        	if (subDiv != null) {
	                        	task.setText(subDiv.getDivisionSnCode().getDivName());
	                        	task.setStart_date(DateUtils.format(plan.getStartDate(), TIMEPATTERN));
	                        	task.setDuration((plan.getEndDate().getTime() - plan.getStartDate().getTime())/(1000*24*60*60) + 1);
                        	
                        		if (rate != null && subDiv.getRawConMapQuan() != null && subDiv.getRawConMapQuan() != 0 && rate.getFinishNumber() != null) {
                        			task.setProgress((float)(rate.getFinishNumber()/(subDiv.getRawConMapQuan() 
                        					+ (subDiv.getConsMapSumVaryQuan() != null ? subDiv.getConsMapSumVaryQuan() : 0))));
                        		} else {
                        			task.setProgress(0);
                        		}
                        	}
                        	tasks.add(task);
                        }
                        Map<String, Object> root = new HashMap();
                        root.put("data", tasks);
                        Map<String, List> linkst = new HashMap<String, List>();
                        linkst.put("links", links);
                        root.put("collections", linkst);
                        String jsonString = JSON.toJSONString(root);
                        return jsonString;
                } else if (UPDATED.equals(outerOp)) {
                    //更新实体
                        if (clazz.equals(EngDivisionPlanLinks.class)) {
                            EngDivisionPlanLinks obj = engDivisionPlanLinksService.findEngDivisionPlanLinksByPrimaryKey(Long.valueOf(id));
                            obj.setSource(param.get("source"));
                            obj.setTarget(param.get("target"));
                            obj.setType(param.get("type"));
                            engDivisionPlanLinksService.saveEngDivisionPlanLinks(obj);
                            nid = String.valueOf(obj.getId());
                        } else if (clazz.equals(GanttTask.class)) {
                        	if (id.split("\\|").length == 2) {
	                        	String code = id.split("\\|")[0];
	                        	version = id.split("\\|")[1];
	                        	EngDivisionPlan obj = new EngDivisionPlan();
	                        	obj.setDivisionSnCode(code);
	                        	obj.setDivVersion(version);
	                        	Date date = DateUtils.parse(param.get("start_date"), TIMEPATTERN);
	                        	Calendar c = Calendar.getInstance();
	                        	c.setTime(date);
	                        	c.add(Calendar.DATE, Integer.valueOf(param.get("duration"))  - 1);
	                        	obj.setStartDate(date);
	                        	obj.setEndDate(c.getTime());
	                        	engDivisionPlanService.saveEngDivisionPlan(obj);
	                        	nid = id;
                        	}
                        }
                } else if (INSERTED.equals(outerOp)) {
                        if (clazz.equals(EngDivisionPlanLinks.class)) {
                        	EngDivisionPlanLinks obj = (EngDivisionPlanLinks) this.getNewEntityInstance(clazz, param);
                        	engDivisionPlanLinksService.saveEngDivisionPlanLinks(obj);
                            nid = String.valueOf(obj.getId());
                        } else if (clazz.equals(GanttTask.class)) {
                        	EngDivisionPlan obj = new EngDivisionPlan();
                        	obj.setDivisionSnCode(id);
                        	obj.setDivVersion(version);
                        	Date date = DateUtils.parse(param.get("start_date"), TIMEPATTERN);
                        	Calendar c = Calendar.getInstance();
                        	c.setTime(date);
                        	c.add(Calendar.DATE, Integer.valueOf(param.get("duration")) - 1);
                        	obj.setStartDate(date);
                        	obj.setEndDate(c.getTime());
                        	engDivisionPlanService.saveEngDivisionPlan(obj);
                        	nid = id + "|" + version;
//                            nid = String.valueOf(obj.getId());
                        }
                } else if (DELETED.equals(outerOp)) {
                        if (clazz.equals(EngDivisionPlanLinks.class)) {
                        	engDivisionPlanLinksService.deleteEngDivisionPlanLinks(Long.valueOf(id));
                        } else if (clazz.equals(GanttTask.class)) {
                        	if (id.split("\\|").length == 2) {
                        		String code = id.split("\\|")[0];
	                        	version = id.split("\\|")[1];
	                        	EngDivisionPlan entity = new EngDivisionPlan();
	                        	entity.setDivisionSnCode(code);
	                        	entity.setDivVersion(version);
	                        	engDivisionPlanService.deleteEngDivisionPlan(entity);
                        	}
                        }
                } else {
                        outerOp = INVALID;
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
                roolback = true;
                break;
            } finally {
//                results.add(new ActionResult(outerOp, id, nid, output, null));
            }
        }//end for
        //处理是否回滚事件
//        if (roolback) {
//            for (ActionResult ar : results) {
//                ar.setStatus(ERROR);
//            }
//        }
        return "";
    }
    
    private String[] getIds(HttpServletRequest request) {
        String value = request.getParameter("ids");
        return value.split(",");
    }
    
    protected List<Map> getPostValues(String[] ids, HttpServletRequest request) {
        List<Map> rets = new ArrayList<Map>();
        for (String id : ids) {
            Map<String, String> ret = new HashMap<String, String>();
            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = String.valueOf(params.nextElement());
                //判断是不是合法的参数
                if (!name.startsWith(id + "_") || name.split("_").length < 2) {
                    if (!name.equals("gantt_mode") && !name.equals("0_version") && !name.equals("0_itemcode") && !name.equals("0_workcode")) {
                        continue;
                    }
                }
                ret.put(name.substring(name.indexOf("_") + 1), request.getParameter(name));
            }
            rets.add(ret);
        }//end for
        return rets;
    }
    
    private Class mode2EntityType(String name) {
        if (name != null) {
            if ("tasks".equals(name)) {
            	 return GanttTask.class;
            } else if ("links".equals(name)) {
            	return EngDivisionPlanLinks.class;
            } else {
            	throw new RuntimeException("不匹配的请求.");
            }
        }
        return null;
    }
    
    private Object getNewEntityInstance(Class clazz, Map<String, String> param) {
        if (clazz.equals(EngDivisionPlanLinks.class)) {
        	EngDivisionPlanLinks gl = new EngDivisionPlanLinks();
        	gl.setId(Long.valueOf(param.get("id")));
            gl.setSource(param.get("source"));
            gl.setTarget(param.get("target"));
            gl.setType(param.get("type"));
            return gl;
        } else if (clazz.equals(GanttTask.class)) {
            GanttTask gt = new GanttTask();
            gt.setDuration(Long.valueOf(param.get("duration")));
            gt.setParent(param.get("parent"));
            gt.setProgress(Float.valueOf(param.get("progress")));
            if (null != param.get("sortorder")) {
                gt.setSortorder(Long.valueOf(param.get("sortorder")));
            }
            gt.setStart_date(param.get("start_date"));
            gt.setText(param.get("text"));
            return gt;
        }
        return null;
    }
    
}
