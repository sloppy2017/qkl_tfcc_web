package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.acc.AccOutdetail;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface AccOutdetailDao extends DAO<AccOutdetail> {

	/** 添加转出明细信息
	 * @return addAccOutdetail添加转出明细信息
	 * @create author kezhiyi
	 * @create date 2016年9月13日
	 */ 
	public void addAccOutdetail(PageData pd);
	
	/** 修改转出明细信息状态 
	 * @return modifyAccOutdetailStatus修改转出明细信息状态 
	 * @create author kezhiyi
	 * @create date 2016年9月13日
	 */ 
	public void modifyAccOutdetailStatus(PageData pd);
	
	
}
