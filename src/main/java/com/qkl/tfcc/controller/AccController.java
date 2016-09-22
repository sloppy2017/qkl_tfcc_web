package com.qkl.tfcc.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qkl.tfcc.api.common.CodeConstant;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.po.acc.Acc;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
/**
 * 账户的控制类
 * <p>Description： 账户的控制类 </p>
 * @project_Name qkl_tfcc_web
 * @class_Name AccController.java
 * @author zhangchunming
 * @date 2016年9月9日
 * @version v
 */
@Controller
@RequestMapping("/service/acc")
public class AccController extends BaseAction{
    
    @Autowired
    private UserService userService;
    @Autowired
    private AccService accService;
    @Autowired
    private SmsService smsService;
    
    /**
     * @describe:投资公司发放奖励
     * @author: zhangchunming
     * @date: 2016年9月9日上午11:06:59
     * @param request
     * @return: AjaxResponse
     */
    @RequestMapping(value="/rewardTfcc", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse modifyuser(HttpServletRequest request){
        logBefore(logger, "投资机构发放tfcc给LP会员");
        Map<String,String> resMap = new HashMap<String, String>();
        try {
             pd = this.getPageData();
             if(pd.get("params") == null){
                 ar.setSuccess(false);
                 ar.setErrorCode(CodeConstant.PARAM_ERROR);
                 ar.setMessage("请求参数有误");
                 return ar;
             }
             JSONArray jsonArray = JSONArray.parseArray(pd.get("params").toString());
             User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
             Acc acc = new Acc();
             acc.setUserCode(user.getUserCode());
             acc.setSyscode(Constant.CUR_SYS_CODE);
             Acc tAcc = accService.findAcc(acc, Constant.VERSION_NO);
             if(tAcc.getAvbAmnt()==null||tAcc.getAvbAmnt().compareTo(new BigDecimal("0"))==0){
                 resMap.put("failStr", "您的账户余额不足发放失败！");
             }
             resMap = accService.rewardTfcc(jsonArray,user.getUserCode(),tAcc.getAvbAmnt(),Constant.VERSION_NO);
             StringBuffer successStr = new StringBuffer("发放成功的手机号：");
             if(resMap.get("successStr")!=null){
                 JSONArray array = JSONArray.parseArray(resMap.get("successStr"));
                 for(int i=0;i<array.size();i++){
                     JSONObject obj = (JSONObject)array.get(i);
                     successStr.append(obj.getString("phone")+"，额度："+obj.getString("tfccNum")+"；");
                   SmsSend.sendSms(obj.getString("phone"), "尊敬的【"+obj.getString("phone")+"】会员您好，恭喜您获得"+obj.getString("tfccNum")+"TFCC奖励。");
                 }
                 resMap.remove("successStr");
                 resMap.put("successStr", successStr.toString());
             }
             /*for(int i=0;i<successList.size();i++){
                 String phone = ((Map)successList.get(i)).get("phone").toString();
                 String tfccNum = ((Map)successList.get(i)).get("tfccNum").toString();
                 SmsSend.sendSms(phone, "尊敬的【"+phone+"】会员您好，恭喜您获得"+tfccNum+"TFCC奖励。");
             }*/
             ar.setSuccess(true);
             ar.setData(resMap);
             ar.setMessage("发放成功");
             return ar;
        } catch (Exception e) {
            e.printStackTrace();
            ar.setSuccess(false);
            ar.setMessage("系统异常");
        }finally{
            logAfter(logger);
        }
        return ar;
    }
}
