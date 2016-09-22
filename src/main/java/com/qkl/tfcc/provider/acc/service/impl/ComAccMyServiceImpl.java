package com.qkl.tfcc.provider.acc.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
	public List<PageData> findAll(Page page) {
		// TODO Auto-generated method stub
		return comAccMyDao.findAllPage(page);
	}

	@Override
	public Map<String, Object> findNum(String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		
		BigDecimal findTTReward = comAccMyDao.findTTReward(userCode);
		BigDecimal findTB = comAccMyDao.findTB(userCode);
		BigDecimal findReward = comAccMyDao.findReward(userCode);
		
		String format1="";
		String format2="";
		String format3="";
		
		if (findTTReward!=null) {
			format1 = String .format("%.4f",findTTReward);
		}
		if (findTB!=null) {
			 format2 = String .format("%.4f",findTB);
		}
		if (findReward!=null) {
	         format3 = String .format("%.4f",findReward);
		}
		
		
		map.put("findTTReward", format1);
		map.put("findTB", format2);
		map.put("findReward", format3);
		return map;
	}

	
}
