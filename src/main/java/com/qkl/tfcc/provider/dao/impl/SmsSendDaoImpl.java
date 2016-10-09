package com.qkl.tfcc.provider.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.user.Sendsms;
import com.qkl.tfcc.provider.dao.SmsSendDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;

/**SmsSendDaoImpl的实现
 * <p>Description：SmsSendDaoImpl的实现  </p>
 * @project_Name qkl_tfcc_web
 * @class_Name SmsSendDaoImpl.java
 * @author kezhiyi
 * @date 2016年8月17日
 * @version v1.0
 */
@Repository
public class SmsSendDaoImpl extends DaoSupport<Sendsms> implements SmsSendDao {

	protected static final Log logger = LogFactory.getLog(SmsSendDaoImpl.class);
	
	private String namespace = "Sendsms";
	
	@Override
	public Sendsms findByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSmsSend(Sendsms sendsms) {
		getSqlSession().insert(namespace+"."+"add", sendsms);		
	}

	@Override
	public void modifySmsSend(Sendsms sendsms) {
		getSqlSession().update(namespace+"."+"update", sendsms);	
		
	}

	@Override	
	public int findByPhone(String phone,long second) {
		int sendCnt =0;	
		 sendCnt=  getSqlSession().selectOne(namespace+"."+"findByPhone", phone);
		return sendCnt;
	}

	@Override
	public int findPhoneIsExist(String phone) {	
		int eCnt=  getSqlSession().selectOne(namespace+"."+"findIsExist", phone);
		return eCnt;
	}

	@Override
	public int findBlackPhoneIsExist(String phone) {
		 
		
		return getSqlSession().selectOne(namespace+"."+"findBlackPhoneIsExist", phone);
	}

	
	 
	

}
