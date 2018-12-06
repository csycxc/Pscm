/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.EngDivisionMapper;
import com.banry.pscm.service.conf.TreeNode;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionModel;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class EngDivisionServiceImpl implements EngDivisionService {
	@Autowired
	EngDivisionMapper engDivisionMapper;
	
	private static Logger log = LoggerFactory.getLogger(EngDivisionServiceImpl.class);

	
	@Override
	public EngDivision findEngDivisionByKey(String divisionSnCode) throws ScheduleException {
		log.info("EngDivisionServiceImpl : findEngDivisionByKey call with divisionSnCode="+divisionSnCode);
		return engDivisionMapper.selectByPrimaryKey(divisionSnCode);
	}
	@Override
	public List<EngDivision> findEngDivisionsByDivItemCode(String divItemCode) throws ScheduleException {
		log.info("EngDivisionServiceImpl : findEngDivisionsByDivItemCode call with divItemCode="+divItemCode);
		return engDivisionMapper.findEngDivisionsByDivItemCode(divItemCode);
	}
	@Override
	public List<EngDivision> findEngDivisionsByDivName(String divName) throws ScheduleException {
		log.info("EngDivisionServiceImpl : findEngDivisionsByDivName call with divName="+divName);
		return engDivisionMapper.findEngDivisionsByDivName(divName);
	}
	@Override
	public List<EngDivision> findAllEngDivision() throws ScheduleException {
		log.info("EngDivisionServiceImpl : findAllEngDivision start...");
		return engDivisionMapper.findAllEngDivision();
	}
	@Override
	public List<HashMap> findAllEngDivisionForZTree() throws ScheduleException {
		log.info("EngDivisionServiceImpl : findAllEngDivisionForZTree start...");
		return engDivisionMapper.findAllEngDivisionForZTree();
	}
	@Override
	public TreeNode findTreeByDivisionSnCode(String divisionSnCode) throws ScheduleException {
		log.info("EngDivisionServiceImpl : findTreeByDivisionSnCode call with divisionSnCode = "+divisionSnCode);
		//List<EngDivision> edList = engDivisionMapper.findEngDivisionTreeByCode(code);//查询出符合条件的划分,有问题，不能用。
		List<EngDivision> edList = engDivisionMapper.findAllEngDivision();//查出所有
		List<Integer> levelLst = engDivisionMapper.selectDivLevel();//查询出所有的划分级别，降序排列
		Integer leafLevel = 0;//叶子节点的划分级别
		if (levelLst.size() > 0) {
			for (int i = 0; i < levelLst.size(); i++) {
				if (i == 0) 
					leafLevel = levelLst.get(i);
				else 
					break;
			}
		}
		TreeNode tree = new TreeNode();
		if(edList.size()>0) {
			for (EngDivision ed : edList) {
				if(ed.getDivisionSnCode().equals(divisionSnCode)) {
					tree.setId(ed.getDivisionSnCode());
					tree.setGroupid(ed.getDivItemCode());
					tree.setName(ed.getDivName());
					tree.setSkill(ed.getSkill());
					tree.setDivLevel(""+ed.getDivLevel());
					tree.setpId(ed.getParentDivSnCode());
					if (leafLevel.equals(ed.getDivLevel())) {
						tree.setOpen(true);
						tree.setIsLeaf("Y");
						break;
					} else {
						tree.setOpen(false);
						tree.setIsLeaf("N");
						tree.setChildren(getTreeNodeList(edList,leafLevel,ed.getDivisionSnCode()));
					}
				}
			}
		}
		return tree;
	}
			//递归——————为findTreeByDivisionSnCode实现写的方法.
	public List<TreeNode> getTreeNodeList(List<EngDivision> edList,Integer leafLevel,String divitemcode){
		List<TreeNode> list = new ArrayList<TreeNode>();
		if(edList.size()>0) {
			for(EngDivision ed: edList){
	            //遍历出父id等于参数的id，add进子节点集合  
	            if(divitemcode.equals(ed.getParentDivSnCode())){  
	                //递归遍历下一级  
	            	TreeNode tree = new TreeNode();
	            	tree.setId(ed.getDivisionSnCode());
					tree.setGroupid(ed.getDivItemCode());
					tree.setName(ed.getDivName());
					tree.setSkill(ed.getSkill());
					tree.setDivLevel(""+ed.getDivLevel());
					tree.setpId(ed.getParentDivSnCode());
					if (leafLevel.equals(ed.getDivLevel())) {
						tree.setOpen(true);
						tree.setIsLeaf("Y");
						list.add(tree);
					} else {
						tree.setOpen(false);
						tree.setIsLeaf("N");
						tree.setChildren(getTreeNodeList(edList,leafLevel,ed.getDivisionSnCode()));
						list.add(tree);
					}
	            }
	        }
		}
		return list;
	}
	@Override
	public List<EngDivision> findSonEngDivisions(String parentDivisionSnCode) throws ScheduleException {
		log.info("EngDivisionServiceImpl : findSonEngDivisions call with parentDivisionSnCode = "+parentDivisionSnCode);
		return engDivisionMapper.findSonEngDivisions(parentDivisionSnCode);
	}
	@Override
	public List<Integer> findAllDivLevelFromEngDivision() {
		return  engDivisionMapper.selectDivLevel();
	}
	
	@Override
	@Transactional
	public String saveEngDivision(EngDivision engDivision) throws ScheduleException {
		if(!(engDivision.getDivisionSnCode() == null || "".equals(engDivision.getDivisionSnCode())))
		{
			EngDivision existingEngDivision = engDivisionMapper.selectByPrimaryKey(engDivision.getDivisionSnCode());
			if(existingEngDivision!=null)
			{
				engDivisionMapper.updateByPrimaryKey(engDivision);
				return "update";
			} 	 
			else
			{
				engDivisionMapper.insert(engDivision);
				return "insert";
			}
		}
		else
			throw new ScheduleException("divisionSnCode can not be null");
		
	}
	@Override
	@Transactional
	public String saveEngDivisionSelective(EngDivision engDivision) throws ScheduleException {
		if(engDivision.getDivisionSnCode() == null || "".equals(engDivision.getDivisionSnCode())) {
			//插入	设置divisionSnCode和DivItemCode相等，都为uuid
			engDivisionMapper.insertSelective(engDivision);
			return "insertSelective";
		}else { //更新
			engDivisionMapper.updateByPrimaryKeySelective(engDivision);
			return "updateSelective";
		}
	}
	@Override
	@Transactional
	public int deleteEngDivisionByKey(String divisionSnCode) throws ScheduleException {
		return engDivisionMapper.deleteByPrimaryKey(divisionSnCode);
	}
	@Override
	@Transactional
	public void updateEngDivision(EngDivision engDivision) throws ScheduleException {
		if(engDivision.getDivisionSnCode() == null || "".equals(engDivision.getDivisionSnCode())) {
			throw new ScheduleException("主键DivisionSnCode为null，更新失败！");			
		}
		engDivisionMapper.updateByPrimaryKey(engDivision);
	}
	@Override
	@Transactional
	public void updateEngDivisionSelective(EngDivision engDivision) throws ScheduleException {
		if(engDivision.getDivisionSnCode() == null || "".equals(engDivision.getDivisionSnCode())) {
			throw new ScheduleException("主键DivisionSnCode为null，更新失败！");			
		}
		engDivisionMapper.updateByPrimaryKeySelective(engDivision);
	}
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionService#selectBySqlWhere(java.lang.String)
	 */
	@Override
	public List<EngDivision> selectBySqlWhere(String select, String sqlWhere) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionMapper.selectBySqlWhere(select, sqlWhere);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionService#selectTechnologyLeaderFillData(java.lang.String)
	 */
	@Override
	public List<EngDivisionModel> selectTechnologyLeaderFillData(String userid, String divcode, String pDivSnCode) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionMapper.selectTechnologyLeaderFillData(userid, divcode, pDivSnCode);
	}

	@Override
	public List<Integer> selectDivLevel() throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionMapper.selectDivLevel();
	}
	
	/**
	 * 根据主键查询divitemcode,再查询划分表中该divitemcode的数量
	 */
	@Override
	public int findDivItemCodeCountFromEngDivisionByDivisionSnCode(String divisionsncode) {
		return engDivisionMapper.findDivItemCodeCountFromEngDivisionByDivisionSnCode(divisionsncode);
	}
}
