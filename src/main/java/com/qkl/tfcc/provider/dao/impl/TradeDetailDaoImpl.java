package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.trade.TradeDetail;
import com.qkl.tfcc.provider.dao.TradeDetailDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;


@Repository
public class TradeDetailDaoImpl extends DaoSupport<TradeDetail> implements TradeDetailDao {

	protected static final Log logger = LogFactory.getLog(TradeDetailDaoImpl.class);
	
	private String namespace = "TradeDetail";
	    
	@Override
	public void addTradeDetail(TradeDetail tradeDetail) {
		getSqlSession().insert(namespace+"."+"add", tradeDetail);	
		
	}

	@Override
	public void modifyTradeDetail(TradeDetail tradeDetail) {
		getSqlSession().insert(namespace+"."+"update", tradeDetail);	
		
	}

	@Override
	public void modifyTradeStatus(TradeDetail tradeDetail) {
		getSqlSession().update(namespace+"."+"updatestatus", tradeDetail);
		
	}

	

}
