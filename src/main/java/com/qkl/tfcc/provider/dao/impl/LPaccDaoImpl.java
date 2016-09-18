package com.qkl.tfcc.provider.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.provider.dao.LPaccDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;
import com.qkl.util.help.pager.PageData;

@Repository
public class LPaccDaoImpl extends DaoSupport<AccDetail> implements LPaccDao {

	protected static final Log logger = LogFactory.getLog(LPaccDaoImpl.class);
	
	
	private String namespace = "LPTeam";
	
	
	@Override
	public long findLPBalance(String userCode) {
		long tfcc=0;
		try {
			tfcc = getSqlSession().selectOne(namespace+"."+"findLPBalance",userCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据为空");
		}
		return tfcc;
	}
	@Override
	public long findtotalReward(String userCode) {
		long ttfcc=0;
		try {
			ttfcc = getSqlSession().selectOne(namespace+"."+"findtotalReward",userCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据为空");
		}
		return ttfcc;
	}
	@Override
	public long findrefReward(String userCode) {
		long rtfcc=0;
		try {
			rtfcc = getSqlSession().selectOne(namespace+"."+"findrefReward",userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据为空");
		}
		return rtfcc;
	}
	@Override
	public long findmyReward(String userCode) {
		long mytfcc=0;
		try {
			mytfcc = getSqlSession().selectOne(namespace+"."+"findmyReward",userCode);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据为空");
		}
		return mytfcc;
	}
	@Override
	public List<PageData> findRewardInfo(Page page) {
		List<PageData> rewardInfo=null;
		try {
			rewardInfo = getSqlSession().selectList(namespace+"."+"findRewardInfoPage",page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("数据为空");
		}
		return rewardInfo;
	}

	

}
