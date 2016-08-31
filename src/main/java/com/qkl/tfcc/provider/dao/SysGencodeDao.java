package com.qkl.tfcc.provider.dao;

import java.util.List;

import com.qkl.tfcc.api.po.sys.SysGencode;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface SysGencodeDao extends DAO<SysGencode> {

	
	/** 查询所有代码
	 * @return findAll  查询所有代码
	 * @create author kezhiyi
	 * @create date 2016年8月24日
	 */ 
	public List<SysGencode> findAll();
	/** 根据groupid查询所有代码
	 * @return findAll  根据groupid查询所有代码
	 * @create author kezhiyi
	 * @create date 2016年8月24日
	 */	
	public List<SysGencode> findByGroupCode(String groupid);
	
}
