package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface AccDao extends DAO<Acc> {

	
	
	/** 添加用户账户信息
	 * @return addAcc 添加用户账户明细信息
	 * @create author kezhiyi
	 * @create date 2016年9月8日
	 */ 
	public void addAcc(PageData pd);
	/** 修改用户账户信息
	 * @return modifyAcc  修改用户账户明细信息
	 * @create author kezhiyi
	 * @create date 2016年9月8日
	 */ 
	public void modifyAcc(PageData pd);
	
	
	/** 查询用户账户信息
	 * @return findAcc  查询用户账户明细信息
	 * @create author kezhiyi
	 * @create date 2016年9月8日
	 */ 
	public void findAcc(String userCode);
	
	
}
