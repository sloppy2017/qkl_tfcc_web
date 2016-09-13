package com.qkl.tfcc.provider.dao.impl;


import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.trade.UnfreezeDetail;
import com.qkl.tfcc.provider.dao.UnfreezeDetailDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;

@Repository
public class UnfreezeDetailDaoImpl extends DaoSupport<UnfreezeDetail> implements
		UnfreezeDetailDao {

	protected static final Log logger = LogFactory.getLog(UnfreezeDetailDaoImpl.class);
	
	private String namespace = "UnfreezeDetail";
	
	@Override
	public void addUnfreezeDetail(PageData pd) {
		getSqlSession().insert(namespace+"."+"add", pd);	
	}

}
