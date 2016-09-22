package com.qkl.tfcc.provider.trade.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.service.trade.api.TradeService;
import com.qkl.tfcc.provider.dao.TradeDetailDao;
import com.qkl.util.help.pager.PageData;

@Service
public class TradeServiceImpl implements TradeService {

	private Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);
	
	@Autowired
	private TradeDetailDao tradeDetailDao;
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addTradeDetail(PageData pd, String versionNo) {
		tradeDetailDao.addTradeDetail(pd);
		return true;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyTradeDetail(PageData pd, String versionNo) {
		tradeDetailDao.modifyTradeDetail(pd);
		return true;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyTradeStatus(PageData pd, String versionNo) {
		tradeDetailDao.modifyTradeStatus(pd);
		return true;
	}

	@Override
	public List<PageData> findTradeInfo(Page page,String versionNo) {
		return tradeDetailDao.findTradeInfo(page);
	}

	@Override
	public int findTradeCount(PageData pd, String versionNo) {//获取一个人的交易次数
		return tradeDetailDao.findTradeCount(pd);
	}
}
