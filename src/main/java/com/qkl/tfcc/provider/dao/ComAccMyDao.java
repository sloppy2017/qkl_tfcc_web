package com.qkl.tfcc.provider.dao;

import java.util.List;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.ComAccMy;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface ComAccMyDao extends DAO<ComAccMy> {

	
	 /**
	  * 查询TFCC的余额
	  * @param userCode
	  * @return
	  */
	public long findTB(String userCode);
	

	/**
	 * 查询JFFC的余额
	 * @param userCode
	 * @return
	 */
	public long findJB(String userCode);
	
	
	/**
	 * 查询推荐会员奖励的TFCC
	 * @param userCode
	 * @return
	 */
	public long findReward(String userCode);
	

	/**
	 * 查询推荐网点奖励的TFCC
	 * @param userCode
	 * @return
	 */
	public long findWReward(String userCode);
	

	/**
	 * 查询累奖励的TFCC
	 * @param userCode
	 * @return
	 */
	public long findTTReward(String userCode);

	/**
	 * 查询推荐各级别会员所得奖励列表
	 * @return
	 */
	public List<PageData> findAllPage(Page page);
}
