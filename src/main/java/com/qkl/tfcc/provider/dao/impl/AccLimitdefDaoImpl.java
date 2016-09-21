package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.AccLimitdef;
import com.qkl.tfcc.provider.dao.AccLimitdefDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;

@Repository
public class AccLimitdefDaoImpl extends DaoSupport<AccLimitdef> implements AccLimitdefDao {

protected static final Log logger = LogFactory.getLog(AccDaoImpl.class);
    
    private String namespace = "AccLimitdef";

    @Override
    public PageData getAccLimit(PageData pd) {
        return getSqlSession().selectOne(namespace+"."+"findAccLimit", pd);   
    }
}
