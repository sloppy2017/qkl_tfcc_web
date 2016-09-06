package com.qkl.tfcc.provider.trade.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.trade.TradeDetail;
import com.qkl.tfcc.api.service.trade.api.TradeService;

@Service
public class TradeServiceImpl implements TradeService {

	private Logger loger = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	@Override
	public boolean addTradeDetail(TradeDetail tradeDetail, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyTradeDetail(TradeDetail tradeDetail, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

}
