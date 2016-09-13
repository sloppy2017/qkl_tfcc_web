package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.trade.TradeDetail;
import com.qkl.tfcc.provider.dao.TradeDetailDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;


@Repository
public class TradeDetailDaoImpl extends DaoSupport<TradeDetail> implements TradeDetailDao {

	protected static final Log logger = LogFactory.getLog(TradeDetailDaoImpl.class);
	
	private String namespace = "TradeDetail";
	    
	@Override
	public void addTradeDetail(PageData pd) {
		getSqlSession().insert(namespace+"."+"add", pd);	
		
	}

	@Override
	public void modifyTradeDetail(PageData pd) {
		getSqlSession().update(namespace+"."+"update", pd);	
		
	}

	@Override
	public void modifyTradeStatus(PageData pd) {
		getSqlSession().update(namespace+"."+"updatestatus", pd);
		
	}

	

}
