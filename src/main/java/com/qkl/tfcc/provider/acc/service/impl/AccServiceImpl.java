package com.qkl.tfcc.provider.acc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.provider.dao.AccDetailDao;




@Service
public class AccServiceImpl implements AccService {

	private Logger loger = LoggerFactory.getLogger(AccServiceImpl.class);

	@Autowired
	private AccDetailDao accDetailDao;
	
	@Override
	public boolean addAccDetail(AccDetail accDetail, String versionNo) {
		try{			
			accDetailDao.addAccDetail(accDetail);
			return true;
		}catch(Exception e){
			loger.error("addAccDetail fail,reason is "+e.getMessage());
			return false;
		}
	}
	
	
	
}
