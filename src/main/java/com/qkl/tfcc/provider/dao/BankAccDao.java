package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.acc.BankAccInfo;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface BankAccDao extends DAO<BankAccInfo> {

	
	/** 根据查收款账户信息
	 * @return findBankAccInfo
	 * @create author kezhiyi
	 * @create date 2016年9月5日
	 */ 
	public BankAccInfo findBankAccInfo(String orgName);
	
}
