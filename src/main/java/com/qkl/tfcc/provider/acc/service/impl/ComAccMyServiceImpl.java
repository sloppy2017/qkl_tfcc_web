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
            format1 = String.format("%.4f",findTTReward);
        }
        if (findTB!=null) {
             format2 = String.format("%.4f",findTB);
        }
        if (findReward!=null) {
             format3 = String.format("%.4f",findReward);
        }
        
        
        map.put("findTTReward", format1);
        map.put("findTB", format2);
        map.put("findReward", format3);
        return map;
    }
	@Override
	public Map<String, Object> findMyAcc(String userCode) {
		Map<String, Object> map=new HashMap<String, Object>();
		//查询购买总奖励
		BigDecimal totalGMJL = comAccMyDao.findTTReward(userCode);
		if(totalGMJL ==null){
		    totalGMJL = new BigDecimal("0");
		}
		//查询账户余额，总额，冻结SAN
		PageData pd = new PageData();
		pd.put("user_code", userCode);
		pd = comAccMyDao.getAmnt(pd);
		if(pd!=null){
		    map.put("avb_amnt", pd.get("avb_amnt")==null?"0.0000":String.format("%.4f",new BigDecimal(pd.get("avb_amnt").toString())));
		    map.put("froze_amnt", pd.get("froze_amnt")==null?"0.0000":String.format("%.4f",new BigDecimal(pd.get("froze_amnt").toString())));
		    map.put("total_amnt", pd.get("total_amnt")==null?"0.0000":String.format("%.4f",new BigDecimal(pd.get("total_amnt").toString())));
		}
		//查询投资机构发放给我的SAN奖励
		BigDecimal findFFReward = comAccMyDao.findFFReward(userCode);
		if(findFFReward == null){
		    findFFReward = new BigDecimal("0");
		}
		String totalGMJL1 = String.format("%.4f",totalGMJL);
		String totalReward1 = String.format("%.4f",totalGMJL.add(findFFReward));
		map.put("totalGMJL", totalGMJL1);
		map.put("totalReward", totalReward1);
		return map;
	}

    @Override
    public PageData getAmnt(PageData pd) {
        return comAccMyDao.getAmnt(pd);
    }
    public static void main(String[] args) {
		System.out.println(String.format("%.4f",new BigDecimal("0.12")));
	}
	
}
