package com.banry.pscm.schedule;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.EngDivisionMapper;
import com.banry.pscm.persist.dao.RoleDivisionMapper;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.RoleDivision;
import com.banry.pscm.service.schedule.RoleDivisionService;
/**
 * 角色划分service实现类
 * @author chenshiyu
 * @date 2017年12月15日
 */
@Service
public class RoleDivisionServiceImpl implements RoleDivisionService {
	
	@Autowired
	RoleDivisionMapper roleDivisionMapper;
	@Autowired
	EngDivisionMapper engDivisionMapper;

	/**
	 * 保存角色对应的划分
	 * @date 2017年12月15日
	 * @param roleCode 角色id
	 * @param ids 勾选的划分id集合
	 */
	@Override
	public void saveRoleDivisions(String roleCode, String ids, String tenantCode) {
		//删除未勾选的
		roleDivisionMapper.deleteByRoleCode(roleCode, tenantCode);
		//保存勾选的
		if (ids != null && !"".equals(ids)) {
			String eDCodes[] = ids.split(",");//eDCodes是划分的勾选主键
			if(eDCodes.length>0) {
				for (String divisionSnCode : eDCodes) {
					Integer divlevel = engDivisionMapper.selectByPrimaryKey(divisionSnCode).getDivLevel();
					//if(Integer.valueOf(divlevel).intValue() == 6) {
						RoleDivision ed = new RoleDivision();
						ed.setRoleDivCode(UUID.randomUUID().toString().replaceAll("\\-", ""));//主键
						ed.setRoleCode(roleCode);
						ed.setTenantCode(tenantCode);
						ed.setDivCode(divisionSnCode);//eng_division的主键
//						ed.setDivLevel(String.valueOf(divlevel));
						//ed.setOperType(operType);//保留，，，设置操作类型：1，修改，0，删除
//						ed.setStatus(1);//设置状态   0，有权限      1，无权限
						roleDivisionMapper.insert(ed);
					//}
				}
			}
		}
	}

	/**
	 * 查询角色对应的划分
	 * @date 2017年12月15日
	 * @param roleCode 角色id
	 * @return 划分的集合
	 */
	@Override
	public List<EngDivision> findRoleDivisions(String roleCode) {
		//没有3，5级别，在这里添加上，以免前端页面没有勾选
		//List<EngDivision> upList = engDivisionMapper.selectUpLevel();//-----------3,5级别划分
		List<EngDivision> engs = engDivisionMapper.selectEngDivisionByRoleCode(roleCode);//6级别划分(保存了3，5，直接查)
		return engs;
	}

	/**
	 * 全不选时，删除该角色的划分
	 * @date 2017年12月15日
	 * @param roleCode 角色id
	 * @return 删除的划分个数
	 */
	@Override
	public int deleteRoleDivisions(String roleCode, String tenantCode) {
		return roleDivisionMapper.deleteByRoleCode(roleCode, tenantCode);
	}

}
