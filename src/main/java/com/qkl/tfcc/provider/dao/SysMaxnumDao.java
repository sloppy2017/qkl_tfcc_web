package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.sys.SysMaxnum;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface SysMaxnumDao extends DAO<SysMaxnum> {

	
	/** 根据notype查询最大号信息
	 * @return findMaxnum  根据notype查询最大号信息
	 * @create author kezhiyi
	 * @create date 2016年8月20日
	 */ 
	public SysMaxnum findMaxnum(String notype);
	
	
	/** 根据notype查询最大号信息
	 * @return findMaxnum  根据notype查询最大号信息
	 * @create author kezhiyi
	 * @create date 2016年8月20日
	 */ 
	public void modifyMaxnum(SysMaxnum sysMaxnum);
	
	
}
