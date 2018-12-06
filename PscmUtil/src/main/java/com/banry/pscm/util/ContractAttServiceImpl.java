package com.banry.pscm.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.ContractAttMapper;
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.util.ContractAttService;
import com.banry.pscm.service.util.UtilException;

/**
 * 附件
 * @author Xu Dingkui
 * @date 2017年8月13日
 */
@Service
public class ContractAttServiceImpl implements ContractAttService {
	@Autowired
	ContractAttMapper contractAttMapper;

	@Override
	public ContractAtt findByPrimaryKey(String fileInName) throws UtilException {
		// TODO Auto-generated method stub
		return contractAttMapper.selectByPrimaryKey(fileInName);
	}

	@Override
	public void saveContractAtt(ContractAtt contractAtt) throws UtilException {
		// TODO Auto-generated method stub
		contractAttMapper.insert(contractAtt);
	}

	@Override
	public int deleteContractAtt(String fileInName) throws UtilException {
		// TODO Auto-generated method stub
		return contractAttMapper.deleteByPrimaryKey(fileInName);
	}

	@Override
	public List<ContractAtt> findByFileInNames(String fileInNames) throws UtilException {
		// TODO Auto-generated method stub
		return contractAttMapper.selectByFileInNames(fileInNames);
	}
	

}
