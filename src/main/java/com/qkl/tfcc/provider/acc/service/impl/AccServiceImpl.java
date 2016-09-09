package com.qkl.tfcc.provider.acc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.util.help.pager.PageData;




@Service
public class AccServiceImpl implements AccService {

	private Logger loger = LoggerFactory.getLogger(AccServiceImpl.class);

	@Autowired
	private AccDetailDao accDetailDao;
	@Autowired
	private AccDao accDao;
	
	@Override
	public boolean addAccDetail(AccDetail accDetail, String versionNo) {
		try{			
			accDetailDao.addAccDetail(accDetail);
			return true;
		}catch(Exception e){
			loger.error("addAccDetail fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	public boolean addAcc(PageData pd, String versionNo) {
		try{			
			accDao.addAcc(pd);
			return true;
		}catch(Exception e){
			loger.error("addAcc fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	public boolean modifyAcc(PageData pd, String versionNo) {
		try{			
			accDao.addAcc(pd);
			return true;
		}catch(Exception e){
			loger.error("modifyAcc fail,reason is "+e.getMessage());
			return false;
		}
	}

	@Override
	public Acc findAcc(Acc acc, String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PageData> findAccDetailList(Page page, String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
