package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;


@Repository
public class AccDaoImpl extends DaoSupport<Acc> implements AccDao {

protected static final Log logger = LogFactory.getLog(AccDaoImpl.class);
	
	private String namespace = "Acc";

    @Override
    public void addAcc(Acc acc) {
        getSqlSession().insert(namespace+"."+"addAcc", acc);    
    }

    @Override
    public void modifyAcc(Acc acc) {
        getSqlSession().update(namespace+"."+"modifyAcc", acc);    
    }

    @Override
    public Acc findAcc(Acc acc) {
        return (Acc)getSqlSession().selectOne(namespace+"."+"findAcc", acc);    
    }

    @Override
    public Integer getAvailableBalance(Acc acc) {
        return (Integer)getSqlSession().selectOne(namespace+"."+"getAvailableBalance", acc); 
    }

    @Override
    public boolean updateIn(Acc acc) {
         return getSqlSession().update(namespace+"."+"updateIn", acc)>0; 
    }

    @Override
    public boolean updateOut(Acc acc) {
        return getSqlSession().update(namespace+"."+"updateOut", acc)>0; 
    }

	@Override
	public void updatefroze(PageData pd) {		
		 getSqlSession().update(namespace+"."+"updatefroze", pd); 
	}

    @Override
    public boolean thaw(String ratio) {
       return getSqlSession().update(namespace+"."+"thaw", ratio)>0; 
    }

    @Override
    public boolean transfering(PageData pd) {
        return getSqlSession().update(namespace+"."+"transfering", pd)>0; 
    }

    @Override
    public boolean transferSuccess(PageData pd) {
        return getSqlSession().update(namespace+"."+"transferSuccess", pd)>0; 
    }

    @Override
    public boolean transferfail(PageData pd) {
        return getSqlSession().update(namespace+"."+"transferfail", pd)>0; 
    }
	
}
