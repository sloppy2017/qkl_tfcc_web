package com.qkl.tfcc.provider.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;



/**AccDetailDao的实现
 * <p>AccDetailDao的实现  </p>
 * @project_Name qkl_tfcc_web
 * @class_Name AccDetailDaoImpl.java
 * @author kezhiyi
 * @date 2016年8月29日
 * @version v1.0
 */
@Repository
public class AccDetailDaoImpl extends DaoSupport<AccDetail> implements AccDetailDao {

	
	protected static final Log logger = LogFactory.getLog(SmsSendDaoImpl.class);
	
	private String namespace = "AccDetail";

	@Override
	public void addAccDetail(AccDetail accDetail) {
		getSqlSession().insert(namespace+"."+"addAccDetail", accDetail);	
		
	}
	
	@Override
	public void addAccDetaillv(PageData pd) {
		getSqlSession().insert(namespace+"."+"addlv", pd);	
		
	}

    @Override
    public void updateAccDetail(AccDetail accDetail) {
        getSqlSession().update(namespace+"."+"updateAccDetail", accDetail);    
    }

    @Override
    public List<PageData> findAccDetailPage(Page page) {
        return getSqlSession().selectList(namespace+"."+"findAccDetailPage", page);
    }

    @Override
    public List<PageData> findAccDetailList(PageData pd) {
        return getSqlSession().selectList(namespace+"."+"findAccDetailList", pd);
    }

    @Override
    public AccDetail findAccDetail(AccDetail accDetail) {
        return getSqlSession().selectOne(namespace+"."+"findAccDetail", accDetail);
    }

}
