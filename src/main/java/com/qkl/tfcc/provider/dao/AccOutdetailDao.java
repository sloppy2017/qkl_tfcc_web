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
	/**
	 * @describe:转账回调时更新状态
	 * @author: zhangchunming
	 * @date: 2016年10月10日上午10:52:13
	 * @param pd
	 * @return: void
	 */
	public boolean updateStatusByOrderId(PageData pd);
	/**
	 * @describe:根据订单id查询转账记录
	 * @author: zhangchunming
	 * @date: 2016年10月10日下午3:35:38
	 * @param orderId
	 * @return: PageData
	 */
	public PageData getAccOutDetailByOrderId(String orderId);
	
	
}
