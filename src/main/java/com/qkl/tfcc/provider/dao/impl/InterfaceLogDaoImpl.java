package com.qkl.tfcc.provider.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.api.po.log.InterfaceLog;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dao.InterfaceLogDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;


@Repository
public class InterfaceLogDaoImpl extends DaoSupport<InterfaceLog>  implements InterfaceLogDao {

    protected static final Log logger = LogFactory.getLog(InterfaceLogDaoImpl.class);
    private String namespace = "InterfaceLog";
    
    @Override
    public boolean insert(PageData pd) {
        return getSqlSession().insert(namespace+"."+"insert", pd)>0;    
    }

    @Override
    public boolean insertSelective(PageData pd) {
        return getSqlSession().insert(namespace+"."+"insert", pd)>0;  
    }

    @Override
    public PageData selectById(Long id) {
        return (PageData)getSqlSession().selectOne(namespace+"."+"findAcc", id);    
    }

    @Override
    public List<PageData> listPageLog(PageData pd) {
        return getSqlSession().selectList(namespace+"."+"listPageLog", pd);    
    }

    @Override
    public List<PageData> listAllLog(PageData pd) {
        return getSqlSession().selectList(namespace+"."+"listPageLog", pd);    
    }


    



}
