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
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.api.service.acc.api.ComAccMyService;
import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.pager.PageData;
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
    @Autowired
    private ComAccMyService cams;
    
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
        logBefore(logger, "投资机构发放tfcc给普通会员会员");
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
             UserDetail userDetail = userService.findUserDetailByUserCode(user.getUserCode(), Constant.VERSION_NO);
             Acc acc = new Acc();
             acc.setUserCode(user.getUserCode());
             acc.setSyscode(Constant.CUR_SYS_CODE);
             Acc tAcc = accService.findAcc(acc, Constant.VERSION_NO);
             if(tAcc == null){
                 resMap.put("failStr", "您的账户不存在，请联系客服！");
                 ar.setSuccess(false);
                 ar.setData(resMap);
                 ar.setMessage("您的账户不存在，请联系客服！");
                 return ar;
             }
             if(tAcc.getAvbAmnt()==null||tAcc.getAvbAmnt().compareTo(new BigDecimal("0"))==0){
                 resMap.put("failStr", "您的账户余额不足发放失败！");
                 ar.setSuccess(false);
                 ar.setData(resMap);
                 ar.setMessage("您的账户余额不足发放失败！");
                 return ar;
             }
             resMap = accService.rewardTfcc(jsonArray,userDetail,tAcc.getAvbAmnt(),Constant.VERSION_NO);
             StringBuffer successStr = new StringBuffer("发放成功的手机号：");
             StringBuffer phoneStr = new StringBuffer("");
             BigDecimal totalSan = new BigDecimal("0");//总计转出SAN数量
             if(resMap.get("successStr")!=null){
                 JSONArray array = JSONArray.parseArray(resMap.get("successStr"));
                 for(int i=0;i<array.size();i++){
                     JSONObject obj = (JSONObject)array.get(i);
                     successStr.append(obj.getString("phone")+"，额度："+obj.getString("tfccNum")+"；");
                     phoneStr.append(obj.getString("phone")+"，");
                     totalSan = totalSan.add(new BigDecimal(obj.getString("tfccNum")));
                   SmsSend.sendSms(obj.getString("phone"), "尊敬的【"+obj.getString("phone")+"】会员您好，【"+userDetail.getPhone()+"】会员给您发转入【"+obj.getString("tfccNum")+"】SAN数字货币，请登录网站查收！");
                   
                 }
                 resMap.remove("successStr");
                 resMap.put("successStr", successStr.toString());
             }
             PageData  accPd  = new PageData();
             accPd.put("user_code", userDetail.getUserCode());
             accPd = cams.getAmnt(accPd);
             if(accPd!=null){
                 resMap.put("avb_amnt", accPd.get("avb_amnt")==null?null:String.format("%.4f",new BigDecimal(accPd.get("avb_amnt").toString())));
                 resMap.put("froze_amnt", accPd.get("froze_amnt")==null?null:String.format("%.4f",new BigDecimal(accPd.get("froze_amnt").toString())));
                 resMap.put("total_amnt", accPd.get("total_amnt")==null?null:String.format("%.4f",new BigDecimal(accPd.get("total_amnt").toString())));
             }
             //给当前登陆用户发送短信
             if(totalSan.compareTo(new BigDecimal("0"))>0){//判断发放总额是否大于0
                 phoneStr = new StringBuffer(phoneStr.substring(0, phoneStr.length()-1));
                 if(phoneStr.toString().contains(",")){
                     SmsSend.sendSms(userDetail.getPhone(), "尊敬的【"+userDetail.getPhone()+"】会员您好，您向【"+phoneStr+"】会员转出【"+totalSan+"】SAN数字货币成功，账户余额【"+resMap.get("avb_amnt").toString()+"】，祝您生活愉快！如有疑问请及时联系网站客服。");
                 }else{
                     SmsSend.sendSms(userDetail.getPhone(), "尊敬的【"+userDetail.getPhone()+"】会员您好，您向【"+phoneStr+"】会员共计转出【"+totalSan+"】SAN数字货币成功，账户余额【"+resMap.get("avb_amnt").toString()+"】，祝您生活愉快！如有疑问请及时联系网站客服。");
                 }
                 
             }
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
