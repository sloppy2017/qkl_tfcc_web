package com.qkl.tfcc.provider.acc.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.service.acc.api.AccOutdetailService;
import com.qkl.tfcc.provider.trade.service.impl.UnfreezeServiceImpl;
import com.qkl.util.help.pager.PageData;


@Service
public class AccOutdetailServiceImpl implements AccOutdetailService {

	private Logger loger = LoggerFactory.getLogger(AccOutdetailServiceImpl.class);
	
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addAccOutdetail(PageData pd, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyAccOutdetailStatus(PageData pd, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

}
