package com.qkl.tfcc.provider.dao.impl;


import java.util.Map;
import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.ComTeamVip;
import com.qkl.tfcc.provider.dao.ComTeamVipDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;


@Repository("vipDao")
public class ComTeamVipDaoImpl extends DaoSupport<ComTeamVip> implements ComTeamVipDao {

	protected static final Log logger = LogFactory.getLog(ComTeamVipDaoImpl.class);
	private String namespace = "ComTeamVip";
	@Override
	public ComTeamVip findcount(String userCode) {
		ComTeamVip count = getSqlSession().selectOne(namespace+"."+"findcount", userCode);
		return  count;	
	}
	@Override
	public Object findVipList(Page page) {
		Object list = getSqlSession().selectList(namespace+"."+"findViplistPage",page);
		return list;
	}
	@Override
	public int findfyCount(Map<String, Object> mp) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(namespace+"."+"findfyCount",mp);
	}

	
}
