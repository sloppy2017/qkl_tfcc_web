package com.qkl.tfcc.provider.sms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.user.Sendsms;
import com.qkl.tfcc.api.po.user.SendsmsDetail;
import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.provider.dao.LogSmsDao;
import com.qkl.tfcc.provider.dao.SmsSendDao;
import com.qkl.tfcc.provider.dao.SmsSendDetailDao;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.util.help.pager.PageData;



@Service
public class SmsServiceImpl implements SmsService {

	private Logger loger = LoggerFactory.getLogger(SmsServiceImpl.class);

	@Autowired
	private SmsSendDao smsSendDao;//发送验证码
	@Autowired
	private LogSmsDao logSmsDao;//发送短信
	@Autowired
	private SmsSendDetailDao smsSendDetailDao;
	
	
	
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addSendsmsDetail(SendsmsDetail smsDetail) {
		smsSendDetailDao.addSmsSendDetail(smsDetail);
		return true;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addSendsmsInfo(Sendsms SendsmsInfo) {
		smsSendDao.addSmsSend(SendsmsInfo);			
		return true;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifySendsmsInfo(Sendsms SendsmsInfo) {
		smsSendDao.modifySmsSend(SendsmsInfo);			
		return true;
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public Sendsms findSendsmsInfo(String userCode) {
		return smsSendDao.findByUserCode(userCode);
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public String findSendsmsDetail(String phone, String sysCode) {
		/*if(smsSendDetailDao.findPhoneIsExist(phone)==0){//校验用户手机号是否存在应从用户表里查询
			return "";
		}*/
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

    @Override
    @Transactional(propagation =Propagation.SUPPORTS)    
    public List<PageData> getSmsNoSend(PageData pd) {
        return logSmsDao.getSmsNoSend(pd);
    }

    @Override
    @Transactional(propagation =Propagation.REQUIRED)  
    public boolean updateSms(PageData pd) {
        logSmsDao.updateSms(pd);
        return true;
    }
    /**
     * @describe:发送库里未发送的短息
     * @author: zhangchunming
     * @date: 2016年9月19日下午7:49:23
     * @return: void
     *//*
    @Transactional(propagation =Propagation.REQUIRED)
    public void sendNoSendSms(PageData pd){
        List<PageData> smsList = logSmsDao.getSmsNoSend(pd);
        for(PageData tpd:smsList){
            boolean result = SmsSend.sendSms(tpd.getString("ls_phone"), tpd.getString("ls_content"));
            if(result){
                pd.put("log_sms_id", tpd.getString("log_sms_id"));
                pd.put("ls_is_success", 1);
                pd.put("ls_is_send", 1);
                logSmsDao.updateSms(pd);
            }
        }
    }*/

}
