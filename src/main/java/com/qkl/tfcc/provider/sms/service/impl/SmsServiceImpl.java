package com.qkl.tfcc.provider.sms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.user.Sendsms;
import com.qkl.tfcc.api.po.user.SendsmsDetail;
import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.provider.dao.SmsSendDao;
import com.qkl.tfcc.provider.dao.SmsSendDetailDao;



@Service
public class SmsServiceImpl implements SmsService {

	private Logger loger = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Autowired
	private SmsSendDao smsSendDao;
	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	
	
	
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addSendsmsDetail(SendsmsDetail smsDetail) {
		try{			
			smsSendDetailDao.addSmsSendDetail(smsDetail);
			return true;
		}catch(Exception e){
			loger.debug("addSendsmsDetail fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addSendsmsInfo(Sendsms SendsmsInfo) {
		try{			
			smsSendDao.addSmsSend(SendsmsInfo);			
			return true;
		}catch(Exception e){
			loger.debug("addSendsmsInfo fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifySendsmsInfo(Sendsms SendsmsInfo) {
		try{			
			smsSendDao.modifySmsSend(SendsmsInfo);			
			return true;
		}catch(Exception e){
			loger.debug("modifySendsmsInfo fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public Sendsms findSendsmsInfo(String userCode) {
		return smsSendDao.findByUserCode(userCode);
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public String findSendsmsDetail(String phone, String sysCode) {
		if(smsSendDetailDao.findPhoneIsExist(phone)==0){
			return "";
		}
		return smsSendDetailDao.findByPhone(phone,sysCode); 
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public int findSendCntByPhone(String phone,long second) {
		if(smsSendDao.findPhoneIsExist(phone)==0){
			return 0;
		}
		return smsSendDao.findByPhone(phone,second);
	}

}
