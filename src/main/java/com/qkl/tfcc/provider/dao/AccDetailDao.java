package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface AccDetailDao extends DAO<AccDetail> {

	
	
	/** 添加用户账户明细信息
	 * @return addAccDetail 添加用户账户明细信息
	 * @create author kezhiyi
	 * @create date 2016年8月29日
	 */ 
	public void addAccDetail(AccDetail accDetail);
	
	
	
	
	
	
}
