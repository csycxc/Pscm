package com.banry.pscm.web.utils;

import com.banry.pscm.web.mvc.VO.ResultVO;

/**
 * xudk
 * 2018-10-24
 */
public class ResultVOUtil {

    public static ResultVO success(Object object, String retMsg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setResult(true);;
        resultVO.setRetMsg(retMsg);
        return resultVO;
    }
    
    public static ResultVO success(String retMsg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(true);;
        resultVO.setRetMsg(retMsg);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(String retMsg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(false);;
        resultVO.setRetMsg(retMsg);
        return resultVO;
    }
}
