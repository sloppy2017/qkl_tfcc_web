package com.qkl.tfcc.provider.acc.service.impl;

import java.math.BigDecimal;
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
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.tfcc.provider.dao.AccLimitdefDao;
import com.qkl.tfcc.provider.dao.UserDao;
import com.qkl.tfcc.provider.dao.UserFriendshipDao;
import com.qkl.util.help.DateUtil;
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
    @Autowired
    private AccLimitdefDao accLimitdefDao;
    @Autowired
    private UserFriendshipDao userFriendshipDao;
    
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
    public Map<String,String> rewardTfcc(JSONArray jsonArray,UserDetail userDetail,BigDecimal avbAmnt,String versionNo) {
        Map<String,String> map = new HashMap<String,String>();
        StringBuffer successStr = new StringBuffer("[");
        StringBuffer failStr = new StringBuffer("发放失败的手机号：");
        for(int i = 0;i<jsonArray.size();i++){
            JSONObject obj = (JSONObject)jsonArray.get(i);
            String phone = obj.getString("phone").trim();
            String tfccNum = obj.getString("tfccNum").trim();
            if(Validator.isMobile(phone)&&Validator.isNumberMax7(tfccNum)){
                User tUser = userDao.findUserByPhone(phone);
                if(tUser==null){
                    failStr.append(phone+"-会员不存在；");
                    map.put("failStr", failStr.toString());
                    continue;
                }
                PageData pdFriend = new PageData();
                pdFriend.put("user_code", userDetail.getUserCode());
                pdFriend.put("recomuser_code", tUser.getUserCode());
                pdFriend.put("syscode", Constant.CUR_SYS_CODE);
                /*boolean isFriend = userFriendshipDao.isFriend(pdFriend);
                if(!isFriend){
                    failStr.append(phone+"-会员未绑定该机构；");
                    map.put("failStr", failStr.toString());
                    continue;
                }*/
                BigDecimal limit = null;//账户限额
                BigDecimal totalAmnt = null;
                BigDecimal tfccNumTemp = new BigDecimal(tfccNum);
                //如果可用余额小于发放额度
                if(avbAmnt.compareTo(tfccNumTemp)<0){
                    failStr.append(phone+"-账户余额不足；");
                    map.put("failStr", failStr.toString());
                    return map;
                }
                if(tUser!=null&&"1".equals(tUser.getStatus())&&"1".equals(tUser.getUserType())){
                    PageData pd = new PageData();
                    pd.put("cuy_type", "1");
                    pd.put("acc_no", "01");
                    PageData accLimit = accLimitdefDao.getAccLimit(pd);
                    limit = new BigDecimal(accLimit.get("credit_limit").toString());
                    Acc acc = new Acc();
                    acc.setUserCode(tUser.getUserCode());
                    acc.setSyscode(Constant.CUR_SYS_CODE);
                    acc =  accDao.findAcc(acc);
                    totalAmnt =acc.getTotalAmnt();
                }
                if(limit == null){
                    failStr = new StringBuffer("发放失败，系统未设置限额，请联系客服！");
                    map.put("failStr", failStr.toString());
                    return map;
                }
                //判断是否超过限额
                if((limit!=null&&totalAmnt!=null&&totalAmnt.add(tfccNumTemp).compareTo(limit)<=0)||(limit != null&&totalAmnt == null&&tfccNumTemp.compareTo(limit)<=0)){
                    //投资机构账户支出
                    Acc accOut = new Acc();
                    accOut.setUserCode(userDetail.getUserCode());
                    accOut.setAvbAmnt(tfccNumTemp);
                    accOut.setTotalAmnt(tfccNumTemp);
                    accOut.setSyscode(Constant.CUR_SYS_CODE);
                    boolean result = accDao.updateOut(accOut);
                    if(result){
                      //普通用户账户明细收入
                        AccDetail accDetail = new AccDetail();
                        accDetail.setUserCode(userDetail.getUserCode());//投资机构用户编码
                        accDetail.setRelaUsercode(tUser.getUserCode());//关联用户编码 
                        accDetail.setBounsSource1("40");//投资机构发放SAN
                        accDetail.setBounsSource2("4001");//投资机构发放SAN给普通会员
                        accDetail.setCaldate(DateUtil.getCurrentDate());
                        accDetail.setCntflag("1");
                        accDetail.setSyscode(Constant.CUR_SYS_CODE);
                        accDetail.setCreateTime(DateUtil.getCurrentDate());
                        accDetail.setModifyTime(DateUtil.getCurrentDate());
                        accDetail.setOperator(userDetail.getPhone());
                        accDetail.setRelaUserlevel("");
                        accDetail.setSubAccno("010301");
                        accDetail.setAmnt(tfccNumTemp);
                        accDetail.setStatus("1");
                        accDetailDao.addAccDetail(accDetail);
                        
                        //普通用户账户汇总收入
                        Acc accIn = new Acc();
                        accIn.setUserCode(tUser.getUserCode());
                        accIn.setFrozeAmnt(tfccNumTemp);//投资机构发放的SAN币全部冻结
                        accIn.setTotalAmnt(tfccNumTemp);
                        accIn.setSyscode(Constant.CUR_SYS_CODE);
                        accDao.updateIn(accIn);
                        
                        /*AccDetail taccDetail = accDetailDao.findAccDetail(accDetail);
                        if(taccDetail == null){
                            accDetail.setAmnt(tfccNumTemp);
                            accDetail.setStatus("1");
                            accDetailDao.addAccDetail(accDetail);
                        }else{
                            accDetail.setAmnt(tfccNumTemp);
                            accDetailDao.updateAccDetail(accDetail);
                        }*/
                        successStr.append("{phone:'"+phone+"',tfccNum:'"+tfccNum+"'},");
                    }
                }else{
                    if((limit!=null&&totalAmnt!=null&&totalAmnt.add(tfccNumTemp).compareTo(limit)>0)||limit != null&&totalAmnt == null&&tfccNumTemp.compareTo(limit)>0){
                        failStr.append(phone+"-超出限额；");
                    }
                }
            }else{
                if(Validator.isMobile(phone)){
                    failStr.append(phone+"-手机号格式有误；");
                }
                if(Validator.isNumberMax7(tfccNum)){
                    failStr.append(phone+"-发放额度格式有误；");
                }
                
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
        }
        map.put("failStr", failStr.toString());
        return map;
    }

    @Override
    public AccDetail findAccDetail(AccDetail accDetail, String versionNo) {
        return null;
    }
    
    public static void main(String[] args) {
        BigDecimal limit = new BigDecimal("1200.00");//账户限额
        BigDecimal totalAmnt = new BigDecimal("1100.00");
        BigDecimal tfccNumTemp = new BigDecimal("99.01");
        if(totalAmnt.add(tfccNumTemp).compareTo(limit)==-1){
            System.out.println("测试相等");
        }
    }
}
