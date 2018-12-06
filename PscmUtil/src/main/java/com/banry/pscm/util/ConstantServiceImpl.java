package com.banry.pscm.util;

import com.banry.pscm.persist.dao.ConstantMapper;
import com.banry.pscm.service.util.Constant;
import com.banry.pscm.service.util.ConstantService;
import com.banry.pscm.service.util.UtilException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConstantServiceImpl implements ConstantService {

    @Autowired
    private ConstantMapper constantMapper;

    @Override
    public Constant findConstantByPrimaryKey(String constantName) throws UtilException {
        return constantMapper.selectByPrimaryKey(constantName);
    }

    @Override
    public List<Constant> findAllConstant() throws UtilException {
        return constantMapper.selectAll();
    }

    @Override
    public void saveConstant(Constant constant) throws UtilException {
        constantMapper.insert(constant);
    }

    @Override
    public int deleteConstant(String constantName) throws UtilException {
        return constantMapper.deleteByPrimaryKey(constantName);
    }

    @Override
    public int updateConstant(Constant constant) {
        return constantMapper.updateByPrimaryKey(constant);
    }
}
