package com.qkl.tfcc.provider.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.ComAccMy;
import com.qkl.tfcc.provider.dao.ComAccMyDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;



@Repository
public class ComAccMyDaoImpl extends DaoSupport<ComAccMy> implements ComAccMyDao {

	
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
	public List<PageData> findAll(Page page) {
		List<PageData> list = getSqlSession().selectList(namespace+"."+"findAll",page);
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
