package com.qkl.tfcc.provider.dao.impl;


import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;



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
	
	private String namespace = "Accdetail";

	@Override
	public void addAccDetail(AccDetail accDetail) {
		getSqlSession().insert(namespace+"."+"add", accDetail);	
		
	}
	
	
	
	
	
	
	
	

}
