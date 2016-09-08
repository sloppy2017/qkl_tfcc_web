package com.qkl.tfcc.provider.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.LPTeam;
import com.qkl.tfcc.provider.dao.LPTeamDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;

@Repository
public class LPTeamDaoImpl extends DaoSupport<LPTeam > implements LPTeamDao {

	protected static final Log logger = LogFactory.getLog(LPTeamDaoImpl.class);
	private String namespace = "LPTeam";

	@Override
	public long selectLPcount(String userCode) {
		long selectOne = getSqlSession().selectOne(namespace+"."+"findLPcount", userCode);
		return selectOne;
	}

	@Override
	public List<LPTeam> select(Map<String, Object> map) {
		List<LPTeam> list = getSqlSession().selectList(namespace+"."+"find",map); 
		return list;
	}

}
