package com.qkl.tfcc.provider.dao;

import java.util.List;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface LPmemberDao extends DAO<UserDetail> {
	
	/**
	 * 查询LP会员数量
	 * @param userDetail
	 * @return
	 */
	public long findLPmemberNum(String userCode);
	
	
	/**
	 * 查询LP会员信息
	 * @param userDetail
	 * @return
	 */
	public List<PageData> findLPmemberInfo(Page page);

}
