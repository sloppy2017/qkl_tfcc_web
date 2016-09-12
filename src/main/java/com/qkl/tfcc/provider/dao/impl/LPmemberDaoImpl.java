package com.qkl.tfcc.provider.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.provider.dao.LPmemberDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;

@Repository
public class LPmemberDaoImpl extends DaoSupport<UserDetail> implements LPmemberDao {

	
	private String namespace = "UserDetail";
	
	@Override
	public long findLPmemberNum(String userCode) {
		long num = getSqlSession().selectOne(namespace+"."+"findLPmemberNum", userCode);
		return num;
	}

	@Override
	public List<PageData> findLPmemberInfo(Page page) {
		List<PageData> list = getSqlSession().selectList(namespace+"."+"findLPmemberInfolistPage", page);
		return list;
	}

}
