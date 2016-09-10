package com.qkl.tfcc.provider.acc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.ComAccMy;
import com.qkl.tfcc.api.service.acc.api.ComAccMyService;
import com.qkl.tfcc.provider.dao.ComAccMyDao;
import com.qkl.util.help.pager.PageData;

@Service
public class ComAccMyServiceImpl implements ComAccMyService {

	private Logger loger = LoggerFactory.getLogger(ComAccMyServiceImpl.class);
	
	@Autowired
	private ComAccMyDao comAccMyDao;

	@Override
	public ComAccMy findTB(String userCode) {
		// TODO Auto-generated method stub
		
		return comAccMyDao.findTB(userCode);
	}

	@Override
	public ComAccMy findJB(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findJB(userCode);
	}

	

	@Override
	public ComAccMy findReward(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findReward(userCode);
	}

	@Override
	public ComAccMy findWReward(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findWReward(userCode);
	}

	@Override
	public ComAccMy findTTReward(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findTTReward(userCode);
	}

	@Override
	public List<PageData> findAll(Page page) {
		// TODO Auto-generated method stub
		return comAccMyDao.findAllPage(page);
	}

}
