package com.qkl.tfcc.provider.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.entity.Page;
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

	@Override
	public List<PageData> findTradeInfo(Page page) {
		List<PageData> tradeinfo = getSqlSession().selectList(namespace+"."+"findTradelistPage", page);
		return tradeinfo;
	}

	@Override
	public int findTradeCount(PageData pd) {
		int tradecount = getSqlSession().selectOne(namespace+"."+"findTradeCount", pd);
		return tradecount;
	}

	@Override
	public BigDecimal findTradeAmnt(String userCode) {
		 BigDecimal findTradeAmnt = getSqlSession().selectOne(namespace+"."+"findTradeAmnt", userCode);
		return findTradeAmnt;
	}

	

	

}
