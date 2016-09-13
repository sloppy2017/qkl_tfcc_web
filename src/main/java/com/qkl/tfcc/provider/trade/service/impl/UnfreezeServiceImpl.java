package com.qkl.tfcc.provider.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.service.trade.api.UnfreezeService;
import com.qkl.tfcc.provider.dao.UnfreezeDetailDao;
import com.qkl.util.help.pager.PageData;


@Service
public class UnfreezeServiceImpl implements UnfreezeService {

	private Logger loger = LoggerFactory.getLogger(UnfreezeServiceImpl.class);
	
	@Autowired
	private UnfreezeDetailDao unfreezeDetailDao;
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addUnfreezeDetail(PageData pd, String versionNo) {
		try{			
			unfreezeDetailDao.addUnfreezeDetail(pd);
			return true;
		}catch(Exception e){
			loger.debug("addUnfreezeDetail fail,reason is "+e.getMessage());
			return false;
		}
	}

}
