package com.qkl.tfcc.provider.acc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.qkl.tfcc.api.service.acc.api.AccLimitService;
import com.qkl.tfcc.provider.dao.AccLimitDao;
import com.qkl.util.help.pager.PageData;
/**
 * 账户限额接口
 * @author zhangchunming
 * @date: 2016年9月21日上午9:27:07
 */
public class AccLimitServiceImpl implements AccLimitService{
    
    @Autowired
    private AccLimitDao accLimitDao;
    @Override
    public PageData getAccLimit(PageData pd, String versionNo) {
        return accLimitDao.getAccLimit(pd);
    }
    
	
}
