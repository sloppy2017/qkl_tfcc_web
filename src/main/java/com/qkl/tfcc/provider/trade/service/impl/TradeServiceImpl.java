package com.qkl.tfcc.provider.trade.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.entity.Page;
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

	@Override
	public List<PageData> findTradeInfo(Page page,String versionNo) {
		List<PageData> tradeInfo=null;
		try {
		 tradeInfo = tradeDetailDao.findTradeInfo(page);
		 
		} catch (Exception e) {
			loger.debug("findTradeInfo fail,reason is "+e.getMessage());
		}
		return tradeInfo;
	}

	@Override
	public int findTradeCount(PageData pd, String versionNo) {//获取一个人的交易次数
		int tradeCount=0;
		try {
			tradeCount = tradeDetailDao.findTradeCount(pd);
		} catch (Exception e) {
			loger.debug("findTradeCount fail,reason is "+e.getMessage());
		}
		return tradeCount;
	}



	
}
