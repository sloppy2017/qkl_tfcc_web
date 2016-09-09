package com.qkl.tfcc.controller;

import java.util.List;
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
			 Map<String,List> mapPhone = accService.rewardTfcc(jsonArray,user.getUserCode(),Constant.VERSION_NO);
			 List successList = mapPhone.get("successPhone");
			 for(int i=0;i<successList.size();i++){
			     SmsSend.sendSms(((Map)successList.get(i)).get("phone").toString(), "尊敬的用户，恭喜您获得"+((Map)successList.get(i)).get("tfccNum")+"TFCC奖励。");
			 }
			 ar.setSuccess(true);
			 ar.setData(mapPhone);
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
