package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.trade.UnfreezeDetail;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;



public interface UnfreezeDetailDao extends DAO<UnfreezeDetail> {

	/** 添加解冻信息明细
	 * @return addUnfreezeDetail 添加解冻信息明细
	 * @create author kezhiyi
	 * @create date 2016年9月13日
	 */ 
	public boolean addUnfreezeDetail(PageData pd);
	/** 修改解冻状态
	 * @return addUnfreezeDetail 修改解冻状态
	 * @create author kezhiyi
	 * @create date 2016年9月13日
	 */ 
	public boolean modifyUnfreezeDetailStatus(PageData pd);
	
	
	
}
