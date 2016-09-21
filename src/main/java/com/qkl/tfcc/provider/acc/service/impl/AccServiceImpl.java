package com.qkl.tfcc.provider.acc.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.tfcc.provider.dao.UserDao;
import com.qkl.util.help.Validator;
import com.qkl.util.help.pager.PageData;




@Service
public class AccServiceImpl implements AccService {

    private Logger loger = LoggerFactory.getLogger(AccServiceImpl.class);

    @Autowired
    private AccDetailDao accDetailDao;
    @Autowired
    private AccDao accDao;
    @Autowired
    private UserDao userDao;
    
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
    public boolean addAcc(Acc acc, String versionNo) {
        try{            
            accDao.addAcc(acc);
            return true;
        }catch(Exception e){
            loger.error("addAcc fail,reason is "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modifyAcc(Acc acc, String versionNo) {
        try{            
            accDao.modifyAcc(acc);
            return true;
        }catch(Exception e){
            loger.error("modifyAcc fail,reason is "+e.getMessage());
            return false;
        }
    }

    @Override
    public Acc findAcc(Acc acc, String versionNo) {
        return accDao.findAcc(acc);
    }

    @Override
    public boolean modifyAccDetail(AccDetail accDetail, String versionNo) {
        try{            
            accDetailDao.updateAccDetail(accDetail);
            return true;
        }catch(Exception e){
            loger.error("modifyAcc fail,reason is "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<PageData> findAccDetailPage(Page page, String versionNo) {
        return accDetailDao.findAccDetailPage(page);
    }

    @Override
    public Integer getAvailableBalance(Acc acc, String versionNo) {
        return accDao.getAvailableBalance(acc);
    }

    @Override
    public boolean updateIn(Acc acc, String versionNo) {
        try{            
            accDao.updateIn(acc);
            return true;
        }catch(Exception e){
            loger.error("modifyAcc fail,reason is "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateOut(Acc acc, String versionNo) {
        try{            
            accDao.updateOut(acc);
            return true;
        }catch(Exception e){
            loger.error("modifyAcc fail,reason is "+e.getMessage());
            return false;
        }
    }

    @Override
    public List<PageData> findAccDetailList(PageData pd, String versionNo) {
        return accDetailDao.findAccDetailList(pd);
    }

    @Override
    @Transactional(propagation =Propagation.REQUIRED)
    public Map<String,String> rewardTfcc(JSONArray jsonArray,String userCode,String versionNo) {
        Map<String,String> map = new HashMap<String,String>();
        StringBuffer successStr = new StringBuffer("[");
        StringBuffer failStr = new StringBuffer("发放失败的手机号：");
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject obj = (JSONObject)jsonArray.get(i);
            String phone = obj.getString("phone").trim();
            String tfccNum = obj.getString("tfccNum").trim();
            if(Validator.isMobile(phone)&&Validator.isNumberMax7(tfccNum)){
                User user  = userDao.findUserByPhone(phone);
                if(user != null&&"1".equals(user.getStatus())){
                    //账户支出
                    Acc accOut = new Acc();
                    accOut.setUserCode(userCode);
                    accOut.setAvbAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                    accOut.setTotalAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                    accDao.updateOut(accOut);
                    
                    //账户汇总收入
                    Acc accIn = new Acc();
                    accIn.setUserCode(user.getUserCode());
                    Acc tacc = accDao.findAcc(accIn);
                    if(tacc == null){
                        accIn.setAvbAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                        accIn.setTotalAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                        accDao.addAcc(accIn);
                    }else{
                        accIn.setAvbAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                        accDao.updateIn(tacc);
                    }
                    //账户明细收入
                    AccDetail accDetail = new AccDetail();
                    accDetail.setUserCode(userCode);//投资机构用户编码
                    accDetail.setRelaUsercode(user.getUserCode());//关联用户编码 
                    accDetail.setBounsSource1("29");//LP会员累计
                    accDetail.setBounsSource2("2900");
                    AccDetail taccDetail = accDetailDao.findAccDetail(accDetail);
                    if(taccDetail == null){
                        accDetail.setAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                        accDetail.setStatus("1");
                        accDetailDao.addAccDetail(accDetail);
                    }else{
                        accDetail.setAmnt(BigDecimal.valueOf(Long.valueOf(tfccNum)));
                        accDetailDao.updateAccDetail(accDetail);
                    }
                    successStr.append("{phone:'"+phone+"',tfccNum:'"+tfccNum+"'},");
                }else{
                    failStr.append(phone+",");
                }
            }else{
                failStr.append(phone+",");
            }
        }
        if(successStr.toString().equals("[")){
            successStr = null;
        }else{
            successStr = new StringBuffer(successStr.substring(0, successStr.length()-1)+"]");
            map.put("successStr", successStr.toString());
        }
        if(failStr.toString().equals("发放失败的手机号：")){
            failStr.append("无");  
        }else{
            failStr = new StringBuffer(failStr.substring(0, failStr.length()-1));
        }
        map.put("failStr", failStr.toString());
        return map;
    }

    @Override
    public AccDetail findAccDetail(AccDetail accDetail, String versionNo) {
        return null;
    }
}
