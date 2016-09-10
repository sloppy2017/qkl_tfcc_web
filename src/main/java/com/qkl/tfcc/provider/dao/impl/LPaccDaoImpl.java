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
	private String namespace = "AccDetail";
	
	
	@Override
	public long findLPBalance(String userCode) {
		long tfcc = getSqlSession().selectOne(namespace+"."+"findLPBalance",userCode);
		return tfcc;
	}
	@Override
	public long findtotalReward(String userCode) {
		long ttfcc = getSqlSession().selectOne(namespace+"."+"findtotalReward",userCode);
		return ttfcc;
	}
	@Override
	public long findrefReward(String userCode) {
		long rtfcc = getSqlSession().selectOne(namespace+"."+"findrefReward",userCode);
		return rtfcc;
	}
	@Override
	public long findmyReward(String userCode) {
		long mytfcc = getSqlSession().selectOne(namespace+"."+"findmyReward",userCode);
		return mytfcc;
	}
	@Override
	public List<PageData> findRewardInfo(Page page) {
		List<PageData>   rewardInfo  = getSqlSession().selectList(namespace+"."+"findRewardInfoPage",page);
		return rewardInfo;
	}

	

}
