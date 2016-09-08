package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.MemberLimit;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.provider.dao.MemberLimitDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;

@Repository
public class MemberLimitDaoImpl extends DaoSupport<MemberLimit> implements MemberLimitDao {
	
protected static final Log logger = LogFactory.getLog(MemberLimitDaoImpl.class);
	
	private String namespace = "MemberLimit";

	@Override
	public void addMemberLimit(MemberLimit memberLimit) {
		getSqlSession().insert(namespace+"."+"add", memberLimit);			
	}

	@Override
	public void modifyMemberLimit(MemberLimit memberLimit) {
		getSqlSession().update(namespace+"."+"update", memberLimit);	
		
	}

	@Override
	public MemberLimit findMemberLimit(String userCode) {
		MemberLimit tMemberLimit =getSqlSession().selectOne(namespace+"."+"findByuserCode", userCode);
		return tMemberLimit;
	}

	

}
