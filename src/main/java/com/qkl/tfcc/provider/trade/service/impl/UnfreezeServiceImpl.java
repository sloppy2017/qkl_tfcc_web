package com.qkl.tfcc.provider.trade.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.service.trade.api.UnfreezeService;
import com.qkl.tfcc.provider.dao.SysGencodeDao;
import com.qkl.tfcc.provider.dao.UnfreezeDetailDao;
import com.qkl.util.help.DateUtil;
import com.qkl.util.help.pager.PageData;


@Service
public class UnfreezeServiceImpl implements UnfreezeService {

	private Logger loger = LoggerFactory.getLogger(UnfreezeServiceImpl.class);
	
	@Autowired
	private UnfreezeDetailDao unfreezeDetailDao;
	@Autowired
	private SysGencodeDao sysGencodeDao;
	
	
	
	
	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addUnfreezeDetail(PageData pd, String versionNo) {
		try{	
			 List<Map<String,Object>> tSysGencodeList =sysGencodeDao.findByGroupCode("UNFREE_DATE");
			 String ptufdate ="";
			 String lpufdate="";
			 String jgufdate="";
			 
	            for(Map<String,Object> mapObj:tSysGencodeList){
	                if("1".equals(mapObj.get("codeName"))){
	                	ptufdate = mapObj.get("codeValue").toString();
	                }
	                if("3".equals(mapObj.get("codeName"))){
	                	lpufdate = mapObj.get("codeValue").toString();
	                }
	                if("4".equals(mapObj.get("codeName"))){
	                	jgufdate = mapObj.get("codeValue").toString();
	                }
	            }
			
	        //普通会员解冻
			if(DateUtil.getNowDay().equals(ptufdate)){
				
				
				
			}
			 //LP会员解冻
			if(DateUtil.getNowDay().equals(lpufdate)){
				
			}
			 //机构会员解冻
			if(DateUtil.getNowDay().equals(jgufdate)){
				
			}
			
			
	            
	            
			
			unfreezeDetailDao.addUnfreezeDetail(pd);
			return true;
		}catch(Exception e){
			loger.debug("addUnfreezeDetail fail,reason is "+e.getMessage());
			return false;
		}
	}

}
