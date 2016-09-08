package com.qkl.tfcc.provider.bankacc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.acc.BankAccInfo;
import com.qkl.tfcc.api.service.acc.api.BankAccService;
import com.qkl.tfcc.provider.dao.BankAccDao;


@Service
public class BankAccServiceImpl implements BankAccService {

	
	private Logger loger = LoggerFactory.getLogger(BankAccServiceImpl.class);
	
	@Autowired
	private BankAccDao bankAccDao;
	
	
	@Override
	public BankAccInfo findBankAccInfo(String orgName, String versionNo) {
		try{
			return bankAccDao.findBankAccInfo(orgName);
		}catch(Exception e){
			loger.error("findBankAccInfo fail ,reason is "+e.getMessage());
			return null;
		}
		
		
	}

}
