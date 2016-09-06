package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.BankAccInfo;
import com.qkl.tfcc.provider.dao.BankAccDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;


@Repository
public class BankAccDaoImpl extends DaoSupport<BankAccInfo> implements BankAccDao {

	
	protected static final Log logger = LogFactory.getLog(BankAccDaoImpl.class);
	
	private String namespace = "Bankacc";
	
	@Override
	public BankAccInfo findBankAccInfo(String orgName) {
		BankAccInfo tBankAccInfo =getSqlSession().selectOne(namespace+"."+"find", orgName);
		return tBankAccInfo;
	}

	

}
