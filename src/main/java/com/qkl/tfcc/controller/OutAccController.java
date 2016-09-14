package com.qkl.tfcc.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.qkl.tfcc.api.common.CodeConstant;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.po.sys.SysGencode;
import com.qkl.tfcc.api.po.user.Sendsms;
import com.qkl.tfcc.api.po.user.SendsmsDetail;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.api.service.sys.api.SysGenCodeService;
import com.qkl.tfcc.api.service.sys.api.SysMaxnumService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.DateUtil;
import com.qkl.util.help.FileUtil;
import com.qkl.util.help.IdcardUtils;
import com.qkl.util.help.ImgUtil;
import com.qkl.util.help.MD5Util;
import com.qkl.util.help.StringUtil;
import com.qkl.util.help.pager.PageData;
/**
 * <p>Description： 账户的控制类 </p>
 * @project_Name qkl_tfcc_web
 * @class_Name AccOutController.java
 * @author kezhiyi
 * @date 2016年9月13日
 * @version v1.0.0
 */
@Controller
@RequestMapping("/service/outacc")
public class OutAccController extends BaseAction{
	
	private final Logger logger = LoggerFactory.getLogger(OutAccController.class);
	
	private  List<SysGencode> tSysGencodeAll = new  ArrayList<SysGencode>();
 
	@Autowired
	private UserService userService;

   
	
	
	
	/**
	 * 更新转出状态
	 * <p> 更新转出状态  </p>
	 * @Title: updatestatus 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年9月13日
	 */
	@RequestMapping(value="/updatestatus", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse updatestatus(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			String userName  =request.getParameter("phone");
			String passWord  =request.getParameter("pass");
			
			Map<String, Object> map = userService.login(userName, passWord, Constant.CUR_SYS_CODE,Constant.VERSION_NO);
			if ((Integer) map.get("status") == Constant.SUCCESS) {
				User user = (User) map.get(Constant.LOGIN_USER);
				request.getSession().setAttribute(Constant.LOGIN_USER, user);
				logger.info(userName + "登录成功");
				ar.setSuccess(true);
			} else {
				ar.setSuccess(false);
				ar.setMessage((String)map.get("msg"));
			}			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}	
		ar.setData(data);
		return ar;
	}
	
	
}
