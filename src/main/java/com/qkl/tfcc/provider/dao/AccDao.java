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
	public void addAcc(Acc acc);
	/** 修改用户账户信息
	 * @return modifyAcc  修改用户账户明细信息
	 * @create author kezhiyi
	 * @create date 2016年9月8日
	 */ 
	public void modifyAcc(Acc acc);
	
	
	/** 查询用户账户信息
	 * @return findAcc  查询用户账户信息
	 * @create author kezhiyi
	 * @create date 2016年9月8日
	 */ 
	public Acc findAcc(Acc acc);
	/**
	 * @describe:查询用户账户可用tfcc余额
	 * @author: zhangchunming
	 * @date: 2016年9月9日上午10:07:59
	 * @param acc
	 * @return: Integer
	 */
	public Integer getAvailableBalance(Acc acc);
	/**
	 * @describe:收入
	 * @author: zhangchunming
	 * @date: 2016年9月9日上午10:08:48
	 * @param acc
	 * @return: void
	 */
	public void updateIn(Acc acc);
	/**
	 * @describe:支出
	 * @author: zhangchunming
	 * @date: 2016年9月9日上午10:09:52
	 * @param acc
	 * @return: void
	 */
	public void updateOut(Acc acc);
}
