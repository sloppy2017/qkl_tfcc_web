package com.qkl.tfcc.provider.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.CorpTeam;
import com.qkl.tfcc.provider.dao.CorpTeamDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;

@Repository
public class CorpTeamDaoImpl extends DaoSupport<CorpTeam> implements CorpTeamDao {

	
	protected static final Log logger = LogFactory.getLog(CorpTeamDaoImpl.class);
	private String namespace = "CorpTeam";
	
	@Override
	public long findLPNum(String userCode) {
		long LPNum = getSqlSession().selectOne(namespace+"."+"findLPNum", userCode);
		return LPNum;
	}

	@Override
	public List<PageData> findlpInfo(Page page) {
		List<PageData> lpInfo = getSqlSession().selectList(namespace+"."+"findlpInfoPage", page);
		return lpInfo;
	}

}
