package com.qkl.tfcc.provider.dao.impl;


import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.AccOutdetail;
import com.qkl.tfcc.provider.dao.AccOutdetailDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;


@Repository
public class AccOutdetailDaoImpl extends DaoSupport<AccOutdetail> implements
		AccOutdetailDao {

	protected static final Log logger = LogFactory.getLog(AccOutdetailDaoImpl.class);
	
	private String namespace = "AccOutdetail";
	
	
	@Override
	public void addAccOutdetail(PageData pd) {
		getSqlSession().insert(namespace+"."+"add", pd);	
	}

	@Override
	public void modifyAccOutdetailStatus(PageData pd) {
		getSqlSession().update(namespace+"."+"updatestatus", pd);

	}

    @Override
    public boolean updateStatusByOrderId(PageData pd) {
        return getSqlSession().update(namespace+"."+"updateStatusByOrderId", pd)>0;
    }

    @Override
    public PageData getAccOutDetailByOrderId(String orderId) {
        return getSqlSession().selectOne(namespace+"."+"getAccOutDetailByOrderId", orderId);
    }

}
