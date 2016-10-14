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
	public boolean addAccOutdetail(PageData pd) {
		return getSqlSession().insert(namespace+"."+"add", pd)>0;	
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

    @Override
    public PageData getTurnOutInfo(String orderId) {
        return getSqlSession().selectOne(namespace+"."+"getTurnOutInfo", orderId);
    }

    @Override
    public boolean updateStatusByFlowId(PageData pd) {
        return getSqlSession().update(namespace+"."+"updateStatusByFlowId", pd)>0;
    }

}
