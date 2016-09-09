package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;


@Repository
public class AccDaoImpl extends DaoSupport<Acc> implements AccDao {

protected static final Log logger = LogFactory.getLog(AccDaoImpl.class);
	
	private String namespace = "Acc";
	

	@Override
	public void addAcc(PageData pd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyAcc(PageData pd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findAcc(String userCode) {
		// TODO Auto-generated method stub
		
	}

	

}
