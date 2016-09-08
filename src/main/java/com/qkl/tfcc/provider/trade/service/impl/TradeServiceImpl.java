package com.qkl.tfcc.provider.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.trade.TradeDetail;
import com.qkl.tfcc.api.service.trade.api.TradeService;
import com.qkl.tfcc.provider.dao.TradeDetailDao;
import com.qkl.util.help.pager.PageData;

@Service
public class TradeServiceImpl implements TradeService {

	private Logger loger = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	@Autowired
	private TradeDetailDao tradeDetailDao;
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addTradeDetail(PageData pd, String versionNo) {
		try{			
			tradeDetailDao.addTradeDetail(pd);
			return true;
		}catch(Exception e){
			loger.debug("addTradeDetail fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyTradeDetail(PageData pd, String versionNo) {
		try{			
			tradeDetailDao.modifyTradeDetail(pd);
			return true;
		}catch(Exception e){
			loger.debug("modifyTradeDetail fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyTradeStatus(PageData pd, String versionNo) {
		try{			
			tradeDetailDao.modifyTradeStatus(pd);
			return true;
		}catch(Exception e){
			loger.debug("modifyTradeStatus fail,reason is "+e.getMessage());
			return false;
		}
	}

}
