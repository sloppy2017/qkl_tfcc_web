package com.qkl.tfcc.provider.acc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
	public double findTB(String userCode) {
		// TODO Auto-generated method stub
		
		return comAccMyDao.findTB(userCode);
	}

	@Override
	public double findJB(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findJB(userCode);
	}

	

	@Override
	public double findReward(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findReward(userCode);
	}

	@Override
	public double findWReward(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findWReward(userCode);
	}

	@Override
	public double findTTReward(String userCode) {
		// TODO Auto-generated method stub
		return comAccMyDao.findTTReward(userCode);
	}

	@Override
	public List<PageData> findAll(Page page) {
		// TODO Auto-generated method stub
		return comAccMyDao.findAllPage(page);
	}

	@Override
	public Map<String, Object> findNum(String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		
		double findTTReward = comAccMyDao.findTTReward(userCode);
		double findTB = comAccMyDao.findTB(userCode);
		double findReward = comAccMyDao.findReward(userCode);
		
		map.put("findTTReward", findTTReward);
		map.put("findTB", findTB);
		map.put("findReward", findReward);
		return map;
	}

}
