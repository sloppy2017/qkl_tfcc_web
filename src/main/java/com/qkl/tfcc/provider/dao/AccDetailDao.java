package com.qkl.tfcc.provider.dao;

import java.util.List;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface AccDetailDao extends DAO<AccDetail> {

	
	
	/** 添加用户账户明细信息
	 * @return addAccDetail 添加用户账户明细信息
	 * @create author kezhiyi
	 * @create date 2016年8月29日
	 */ 
	public void addAccDetail(AccDetail accDetail);
	/** 添加用户明细信息
	 * @return addUserDetail 用户注册信息
	 * @create author kezhiyi
	 * @create date 2016年8月17日
	 */ 
	public void addAccDetaillv(PageData pd);
	/**
	 * @describe:修改用户账户明细信息
	 * @author: zhangchunming
	 * @date: 2016年9月9日上午10:01:04
	 * @params: accDetail
	 * @return: void
	 */
	public void updateAccDetail(AccDetail accDetail);
	/**
	 * @describe:分页查询账户明细列表
	 * @author: zhangchunming
	 * @date: 2016年9月9日上午10:24:58
	 * @param page
	 * @return
	 * @return: List<PageData>
	 */
	public List<PageData> findAccDetailPage(Page page);
	/**
	 * @describe:查询符合条件的所有账户明细信息
	 * @author: zhangchunming
	 * @date: 2016年9月9日上午10:33:19
	 * @param pd
	 * @return
	 * @return: List<PageData>
	 */
	public List<PageData> findAccDetailList(PageData pd);
	/**
	 * @describe:查询账户明细
	 * @author: zhangchunming
	 * @date: 2016年9月9日下午3:06:20
	 * @param accDetail
	 * @return
	 * @return: AccDetail
	 */
	public AccDetail findAccDetail(AccDetail accDetail);
}
