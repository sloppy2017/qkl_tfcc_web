package com.qkl.tfcc.provider.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.ComAccMy;
import com.qkl.tfcc.provider.dao.ComAccMyDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;



@Repository
public class ComAccMyDaoImpl extends DaoSupport<ComAccMy> implements ComAccMyDao {

	protected static final Log logger = LogFactory.getLog(ComAccMyDaoImpl.class);
	private String namespace="ComAccMy";
	
	@Override
	public ComAccMy findTB(String userCode) {
		ComAccMy cad = getSqlSession().selectOne(namespace+"."+"findTB", userCode);
		return cad;
	}

	@Override
	public ComAccMy findJB(String userCode) {
		ComAccMy cad = getSqlSession().selectOne(namespace+"."+"fingJB", userCode);
		return cad;
	}

	@Override
	public List<PageData> findAllPage(Page page) {
		List<PageData> list = getSqlSession().selectList(namespace+"."+"findAllPage",page);
		return list;
	}

	@Override
	public ComAccMy findReward(String userCode) {
		ComAccMy cad = getSqlSession().selectOne(namespace+"."+"findReward", userCode);
		return cad;
	}

	@Override
	public ComAccMy findWReward(String userCode) {
		ComAccMy cad = getSqlSession().selectOne(namespace+"."+"findWReward", userCode);
		return cad;
	}

	@Override
	public ComAccMy findTTReward(String userCode) {
		ComAccMy cad = getSqlSession().selectOne(namespace+"."+"findTTReward", userCode);
		return cad;
	}

}
