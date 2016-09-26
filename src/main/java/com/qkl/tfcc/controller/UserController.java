package com.qkl.tfcc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.qkl.tfcc.api.common.CodeConstant;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.entity.Page;
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
import com.qkl.tfcc.api.service.testUser.api.TestUserService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.DateUtil;
import com.qkl.util.help.FileUtil;
import com.qkl.util.help.IdcardUtils;
import com.qkl.util.help.ImgUtil;
import com.qkl.util.help.MD5Util;
import com.qkl.util.help.StringUtil;
import com.qkl.util.help.Validator;
import com.qkl.util.help.pager.PageData;
/**
 * 用户的控制类
 * <p>Description： 用户的控制类 </p>
 * @project_Name qkl_tfcc_web
 * @class_Name UserController.java
 * @author kezhiyi
 * @date 2016年8月18日
 * @version v
 */
@Controller
@RequestMapping("/service/user")
public class UserController extends BaseAction{
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final long smsInterval = 3600;
	
	private final int smsLimit = 10;
	
	private final String notype ="USERCODE";
	
	private  List<SysGencode> tSysGencodeAll = new  ArrayList<SysGencode>();
 
	@Autowired
	private UserService userService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private SysMaxnumService sysMaxnumService;
	@Autowired
	private SysGenCodeService sysGenCodeService;
	@Autowired
	private AccService accService;
	@Autowired
	private TestUserService testUserService;
	
	
	
	/**
	 * 用户登陆
	 * <p> 用户登陆  </p>
	 * @Title: login 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月18日
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse login(HttpServletRequest request,HttpServletResponse response ,Page page){
		AjaxResponse ar = new AjaxResponse();
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			String userName  =request.getParameter("phone");
			String passWord  =URLDecoder.decode(request.getParameter("password"), "utf-8");
			
			Map<String, Object> map = userService.login(userName, passWord, Constant.CUR_SYS_CODE,Constant.VERSION_NO);
			if ((Integer) map.get("status") == Constant.SUCCESS) {
				User user = (User) map.get(Constant.LOGIN_USER);
				request.getSession().setAttribute(Constant.LOGIN_USER, user);
				logger.info(userName + "登录成功");
				ar.setSuccess(true);
				data.put("userType", user.getUserType());
				data.put("userCode", user.getUserCode());
				ar.setData(data);
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
		
		/*AjaxResponse ar = new AjaxResponse();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData> userList = testUserService.queryTestUserList(page);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userList", userList);	
		map.put("pd", pd);	
		map.put("page", page);
		ar.setSuccess(true);
		ar.setMessage("查询成功！");
		ar.setData(map);
		return ar;*/
	}
	
	/**
	 * 用户修改
	 * <p> 用户修改  </p>
	 * @Title: modifyuser 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月18日
	 */
	@RequestMapping(value="/modifyuser", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse modifyuser(UserDetail mUserDetail,HttpServletRequest request){
		logBefore(logger, "修改用户信息");		
	    try {
	    	UserDetail tUserDetail = new UserDetail();
			String wxnum = request.getParameter("wxnum");
			String bankaccno = request.getParameter("bankaccno");
			String mailAddrss =request.getParameter("mailAddrss"); 
			String zipCode =request.getParameter("zipCode");		
			String imgAddrss =request.getParameter("imgAddrss");
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			String userCode="";
			if(user==null){
				userCode =request.getParameter("userCode");
			}else{
				userCode =user.getUserCode();
			}
			UserDetail userDetail = userService.findUserDetailByUserCode(userCode, Constant.VERSION_NO);
			tUserDetail.setUserCode(userCode);
			tUserDetail.setWxnum(wxnum);
			tUserDetail.setBankaccno(bankaccno);
			tUserDetail.setMailAddrss(mailAddrss);
			tUserDetail.setZipCode(zipCode);
			tUserDetail.setImgAddrss(imgAddrss);
			tUserDetail.setModifyTime(DateUtil.getCurrentDate());
			if(!StringUtil.isEmpty(userDetail.getRealName().trim())){
			    tUserDetail.setOperator(userDetail.getRealName());
			}else{
			    tUserDetail.setOperator(userDetail.getPhone());
			}
			
			if(userService.modifyUserDetail(tUserDetail, Constant.VERSION_NO)){			
			    ar.setSuccess(true);
                ar.setMessage("个人信息修改成功！");
                return ar;
			}	
			 ar.setSuccess(false);
             ar.setMessage("个人信息修改失败！");
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
	
	/**
	 * 用户注册
	 * <p> 用户注册  </p>
	 * @Title: register 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月18日
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse register(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			String userName  =request.getParameter("phone");
			String passWord  =request.getParameter("password");
			String cfPassWord  =request.getParameter("resPassword");
			String vcode  =request.getParameter("yzm");
			String refPhone  =request.getParameter("phone1");
			String userType =request.getParameter("userType");
//			String branchName =request.getParameter("branchName")==null?"":URLDecoder.decode(request.getParameter("branchName"), "UTF-8"); 
			String realName = request.getParameter("realName")==null?"":URLDecoder.decode(request.getParameter("realName"), "UTF-8");  
			String idno =request.getParameter("idno");
			String cropName  =request.getParameter("cropName")==null?"":URLDecoder.decode(request.getParameter("cropName"), "UTF-8");
			String cropPerson  =request.getParameter("cropPerson")==null?"":URLDecoder.decode(request.getParameter("cropPerson"), "UTF-8");
			
			userName=userName==null?"":userName.trim();
			passWord=passWord==null?"":passWord.trim();
			cfPassWord=cfPassWord==null?"":cfPassWord.trim();
			refPhone=refPhone==null?"":refPhone.trim();
			vcode=vcode==null?"":vcode.trim();
//			branchName=branchName==null?"":branchName.trim();
			realName=realName==null?"":realName.trim();
			idno=idno==null?"":idno.trim();
			cropName=cropName==null?"":cropName.trim();
			cropPerson=cropPerson==null?"":cropPerson.trim();
			
			if(!Validator.isMobile(userName)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}
			
			//判断用户是否已存在
	        if(userService.findIsExist(userName, Constant.VERSION_NO)){
	        	ar.setSuccess(false);
				ar.setMessage("用户已存在！");
				return ar;
	        }
	        
	      //判断推荐人是否存在
	        if(!userService.findIsExist(refPhone, Constant.VERSION_NO)){
	        	ar.setSuccess(false);
				ar.setMessage("推荐人不存在！");
				return ar;
	        }
			
			if(StringUtil.isEmpty(passWord)||StringUtil.isEmpty(cfPassWord)){
				ar.setSuccess(false);
				ar.setMessage("密码不能为空!");
				return ar;
			}					
			
			if(!passWord.equals(cfPassWord)){
				ar.setSuccess(false);
				ar.setMessage("两次密码输入不一致！");
				return ar;
			}
		
			if(idno!=null&&!idno.trim().equals("")){
				if(!IdcardUtils.validateCard(idno)){
					ar.setSuccess(false);
					ar.setMessage("身份证格式不正确！");
					return ar;
				};
			}
			try{
				tSysGencodeAll =sysGenCodeService.findAll();
			}catch(Exception e){
				logger.info("sysGenCodeService.findAll fail ! reason is "+e.getMessage());
			}
			
			//1小时之内的短信验证码有效
			String tVcode =smsService.findSendsmsDetail(userName,Constant.CUR_SYS_CODE); 
			if(tVcode==null||!vcode.equals(tVcode.trim())){
				ar.setSuccess(false);
				ar.setMessage("验证码输入不正确！");
				return ar;
			}
			
			Long tMaxno =sysMaxnumService.findMaxNo(notype, Constant.VERSION_NO);
			if(tMaxno==null){
				logger.info("tSysMaxnum findMaxNo  is null!");
				ar.setSuccess(false);
				ar.setMessage("系统繁忙,请稍后重试！");
				return ar;
			}
			//生成用户编号
			String UserCode = userType +StringUtil.LCh(tMaxno.toString(), "0", 10);
			
			User tUser = new User();
			tUser.setUserCode(UserCode);
			tUser.setPhone(userName);
			tUser.setPwdhash(MD5Util.getMd5Code(passWord));
			tUser.setUserType(userType);
			tUser.setRegTime(DateUtil.getCurrentDate());
			tUser.setStatus("1");
			/*if(userType.equals("2")){//网点会员
			tUser.setStatus("0");
			}else {
			tUser.setStatus("1");
			}	*/	
			tUser.setCreateTime(DateUtil.getCurrentDate());
			tUser.setModifyTime(DateUtil.getCurrentDate());
			
			User refUser= userService.findbyPhone(refPhone, Constant.VERSION_NO);
			if(refUser == null){
			    ar.setSuccess(false);
			    ar.setMessage("推介人不存在！");
			    return ar;
			}
			UserFriendship fMaxFriendship =  userService.findMaxFriendship(refUser.getUserCode(), Constant.VERSION_NO);//推荐人上级关系最大级数    
			
			if("1".equals(refUser.getUserType())&&fMaxFriendship == null){//如果用户为普通用户且没有上级用户
			    ar.setSuccess(false);
                ar.setMessage("推介人数据异常，请联系客服！");
                return ar;
			}
		
			UserDetail tUserDetail = new UserDetail();
			tUserDetail.setUserCode(UserCode);
			tUserDetail.setPhone(userName);
			tUserDetail.setRefPhone(refPhone);
//			tUserDetail.setBranchName(branchName);
			tUserDetail.setRealName(realName);
			tUserDetail.setIdno(idno);
			tUserDetail.setCropName(cropName);
			tUserDetail.setCropPerson(cropPerson);
			tUserDetail.setQrCode("");
			tUserDetail.setUserType(userType);
			if(refUser.getUserType()==null||"4".equals(refUser.getUserType())||"0".equals(refUser.getUserType())||"".equals(refUser.getUserType())){//推介人是投资机构的话，用户的冻结标志要冻结
				tUserDetail.setFreezeFlag("0");
			}else{
				tUserDetail.setFreezeFlag("1");
			}
			
			if(realName!=null&&!realName.equals("")&&idno!=null&&!idno.equals("")){
				tUserDetail.setRealStat("1");	
			}else{
			    tUserDetail.setRealStat("0");    
			}			
			tUserDetail.setCreateTime(DateUtil.getCurrentDate());
			tUserDetail.setModifyTime(DateUtil.getCurrentDate());
			tUserDetail.setOperator(userName);
			
			
			if(!userService.addUser(tUser,tUserDetail,Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("注册失败！");
				return ar;
			}
			
			//添加用户关系
//			createUserFriendship(tUserDetail);
			
			//计算注册奖励
//			calRegitAccDetail(tUserDetail);
			
			 
			request.getSession().setAttribute(Constant.LOGIN_USER, tUser);
			ar.setSuccess(true);
			ar.setMessage("注册成功！");
			return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}
		return ar;
	}

	
	/**
	 * 计算奖励关系
	 * 1-普通会员
	   2-网点会员
	   3-LP会员
	   4-投资公司
	   5-众筹会员
	 * */
	/*private void   calRegitAccDetail(UserDetail mUserDetail){
		String refPhone =mUserDetail.getRefPhone(); 
		String UserCode = mUserDetail.getUserCode();
		String userType = mUserDetail.getUserType();
		//普通奖金定义参数
		SysGencode tSysGencodeBNTJDM1 = new   SysGencode();
		SysGencode tSysGencodeBNTJDM2 = new   SysGencode();
		SysGencode tSysGencodeBNTJDM3 = new   SysGencode();
		SysGencode tSysGencodeBNTJDMWD4 = new   SysGencode();
		SysGencode tSysGencodeBNDMZC5 = new   SysGencode();
		SysGencode tSysGencodeBNDMWDKD6 = new   SysGencode();
		
		//LP奖励参数
		SysGencode tSysGencodeLPRDSL1 = new   SysGencode();
		SysGencode tSysGencodeLPRDSL2 = new   SysGencode();
		SysGencode tSysGencodeLPRDSL3 = new   SysGencode();
		SysGencode tSysGencodeLPRDML1 = new   SysGencode();
		SysGencode tSysGencodeLPRDML2 = new   SysGencode();
		SysGencode tSysGencodeLPRDML3 = new   SysGencode();
		SysGencode tSysGencodeLPRDML4 = new   SysGencode();
		
		try{
			if(tSysGencodeAll!=null&&tSysGencodeAll.size()>0){
				for(SysGencode tSysGencode:tSysGencodeAll){
					if(tSysGencode.getGroupCode().equals("BOUND_DEF")){//获取奖金定义
						if(tSysGencode.getCodeValue().equals("TJDM1")){
							tSysGencodeBNTJDM1 = tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("TJDM2")){
							tSysGencodeBNTJDM2= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("TJDM3")){
							tSysGencodeBNTJDM3= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("TJDMWD")){
							tSysGencodeBNTJDMWD4= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("DMZC")){
							tSysGencodeBNDMZC5= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("DMWDKD")){
							tSysGencodeBNDMWDKD6= tSysGencode;
						}
						
					}else if(tSysGencode.getGroupCode().equals("LPREWARD_DEF")){
						if(tSysGencode.getCodeValue().equals("SL1")){
							tSysGencodeLPRDSL1 = tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("SL2")){
							tSysGencodeLPRDSL2= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("SL2")){
							tSysGencodeLPRDSL2= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("SL3")){
							tSysGencodeLPRDSL3= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("ML1")){
							tSysGencodeLPRDML1= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("ML2")){
							tSysGencodeLPRDML2= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("ML3")){
							tSysGencodeLPRDML3= tSysGencode;
						}else if(tSysGencode.getCodeValue().equals("ML4")){
							tSysGencodeLPRDML4= tSysGencode;
						}
					}
				}
			}
		}catch(Exception e){
			logger.info("sysGenCodeService.findAll fail ! reason is "+e.getMessage());
		}
				
		if(userType.equals("1")){//普通用户
			//普通会员注册奖励计算
			if(mUserDetail.getRealStat().equals("1")){
				if(!calPtMemberRegitBouns(mUserDetail)){
					logger.error("calPtMemberRegitBouns fail ! ");
				};				
			}
		   //普通会员推荐奖励计算
			calPtMemberRecmBouns(mUserDetail);
			
		}else if(userType.equals("2")){//网点用户，审核通过后再计算奖励
			
			
			
		}else if(userType.equals("3")){
			
		}else if(userType.equals("4")){
			
		}
		
	}*/
	
	
	/**
	 * 计算网点用户注册奖励关系
	 * 
	 * */
	/*private boolean   calWDMemberRegitBouns(UserDetail mUserDetail){				
		SysGencode tSysGencodeBNDMZC5 = new SysGencode();
		if(tSysGencodeAll!=null&&tSysGencodeAll.size()>0){
			for(SysGencode tSysGencode:tSysGencodeAll){
				if(tSysGencode.getGroupCode().equals("BOUND_DEF")){//获取奖金定义
					if(tSysGencode.getCodeValue().equals("DMZC")){
						tSysGencodeBNDMZC5= tSysGencode;
						break;
					}					
				}
			}
		}		
		//根据tb_acc_def的定义，设置子账号
	    AccDetail tAccDetail = new AccDetail();
	    tAccDetail.setUserCode(mUserDetail.getUserCode());
	    tAccDetail.setSubAccno("010101");
	    tAccDetail.setBounsSource1("10");
	    tAccDetail.setBounsSource2("1001");
	    tAccDetail.setRelaUsercode(mUserDetail.getRefPhone());
	    tAccDetail.setRelaUserlevel("");
	    try{
	    	String rsAmnt="0";
	    	if(tSysGencodeBNDMZC5.getCodeValue()==null||
	    			tSysGencodeBNDMZC5.getCodeValue().equals("")||
	    			tSysGencodeBNDMZC5.getCodeValue().equals("null"))
	    	{
	    		logger.info("calPtMemberRegitBouns fail ,tSysGencodeBNDMZC5 is null !");
	    	}else{
	    		rsAmnt = tSysGencodeBNDMZC5.getCodeValue();
	    		
	    	}
	    	BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
	    	bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
	    	tAccDetail.setAmnt(bdAmnt);	
	    }catch(Exception e){
	    	logger.error("calPtMemberRegitBouns fail ,translate string to decimal fail!"+e.getMessage());
	    }
	    tAccDetail.setCaldate(DateUtil.getCurrentDate());
	    tAccDetail.setStatus("1");
	    tAccDetail.setCreateTime(DateUtil.getCurrentDate());
	    tAccDetail.setModifyTime(DateUtil.getCurrentDate());
	    
	    return accService.addAccDetail(tAccDetail,Constant.VERSION_NO);
	    
	}*/
	
	
	
	/**
	 * 计算普通会员注册奖励关系
	 * 
	 * */
	/*private void  calPtMemberRecmBouns(UserDetail mUserDetail){	
		SysGencode tSysGencodeBNTJDM1 = new   SysGencode();
		SysGencode tSysGencodeBNTJDM2 = new   SysGencode();
		SysGencode tSysGencodeBNTJDM3 = new   SysGencode();
		if(tSysGencodeAll!=null&&tSysGencodeAll.size()>0){
			for(SysGencode tSysGencode:tSysGencodeAll){
				if(tSysGencode.getGroupCode().equals("BOUND_DEF")){//获取奖金定义
					if(tSysGencode.getCodeValue().equals("TJDM1")){
						tSysGencodeBNTJDM1= tSysGencode;
					}else if(tSysGencode.getCodeValue().equals("TJDM2")){
						tSysGencodeBNTJDM2= tSysGencode;
					}else if(tSysGencode.getCodeValue().equals("TJDM3")){
						tSysGencodeBNTJDM3= tSysGencode;
					}					
				}
			}
		}
		
		String refPhone =mUserDetail.getRefPhone(); 
		String UserCode = mUserDetail.getUserCode();
		String userType = mUserDetail.getUserType();
		
		User tRefUser =  userService.findbyPhone(refPhone, Constant.VERSION_NO);
				
		 boolean tUpflag = userService.findIsExistUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
		 UserFriendship fUserFriendship= new UserFriendship();//推荐人上级关系
		 boolean tSvSelShipFlag =true;			 
		 String tReflvl ="";
		 String sefLev="";
		 if(!tUpflag){//如果推荐人没有上级会员，则自己为A级会员
			 tReflvl="0";
			 sefLev="1";
		 }
		 if(tUpflag){
			 fUserFriendship =  userService.findUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
			 if(fUserFriendship.getRelaLevel().equals("A")){
				 sefLev="2";
			 }else if(fUserFriendship.getRelaLevel().equals("B")){
				 sefLev="3";
			 }else if(fUserFriendship.getRelaLevel().equals("C")){
				 sefLev="4";
			 }else{
				 sefLev="4";					
			 }
		 }
		 
		 //推荐奖励 A级别会员
		 AccDetail tAccDetail = new AccDetail();
		 tAccDetail.setUserCode(tRefUser.getUserCode());		
		 tAccDetail.setSubAccno("010101");
		 tAccDetail.setBounsSource1("11");//普通用户推荐会员PDM		 
		 tAccDetail.setBounsSource2("1101");//普通用户推荐A级会员
		 tAccDetail.setRelaUsercode(UserCode);
		 tAccDetail.setRelaUserlevel("A");
		  try{
		    	String rsAmnt="0";
		    	if(tSysGencodeBNTJDM1.getCodeValue()==null||
		    			tSysGencodeBNTJDM1.getCodeValue().equals("")||
		    			tSysGencodeBNTJDM1.getCodeValue().equals("null"))
		    	{
		    		logger.info("calPtMemberRecmBouns fail ,tSysGencodeBNTJDM1 is null !");
		    	}else{
		    		rsAmnt = tSysGencodeBNTJDM1.getCodeValue();
		    		
		    	}
		    	BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
		    	bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
		    	tAccDetail.setAmnt(bdAmnt);	
		    }catch(Exception e){
		    	logger.error("calPtMemberRecmBouns fail ,translate string to decimal fail!"+e.getMessage());
		    }
		 tAccDetail.setCaldate(DateUtil.getCurrentDate());
		 tAccDetail.setStatus("1");
		 tAccDetail.setCreateTime(DateUtil.getCurrentDate());
		 tAccDetail.setModifyTime(DateUtil.getCurrentDate());
		 tAccDetail.setOperator("sys");
		 if(! accService.addAccDetail(tAccDetail,Constant.VERSION_NO)){
			 logger.info("addAccDetail fail,tAccDetail is "+tAccDetail.toString());
	     };	
		 
		 
		 		 			 			 
		 if(sefLev.equals("2")){//总共有2层				
			 AccDetail tAccDetail2 = new AccDetail();
			 tAccDetail2.setUserCode(fUserFriendship.getUserCode());		
			 tAccDetail2.setSubAccno("010101");
			 tAccDetail2.setBounsSource1("11");//普通用户推荐会员参考PDM		 
			 tAccDetail2.setBounsSource2("1102");//普通用户推荐B级会员
			 tAccDetail2.setRelaUsercode(UserCode);
			 tAccDetail2.setRelaUserlevel("B");
			  try{
			    	String rsAmnt="0";
			    	if(tSysGencodeBNTJDM2.getCodeValue()==null||
			    			tSysGencodeBNTJDM2.getCodeValue().equals("")||
			    			tSysGencodeBNTJDM2.getCodeValue().equals("null"))
			    	{
			    		logger.info("calPtMemberRecmBouns fail ,tSysGencodeBNTJDM2 is null !");
			    	}else{
			    		rsAmnt = tSysGencodeBNTJDM2.getCodeValue();
			    		
			    	}
			    	BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
			    	bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
			    	tAccDetail2.setAmnt(bdAmnt);	
			    }catch(Exception e){
			    	logger.error("B calPtMemberRecmBouns fail ,translate string to decimal fail!"+e.getMessage());
			    }
			 tAccDetail2.setCaldate(DateUtil.getCurrentDate());
			 tAccDetail2.setStatus("1");
			 tAccDetail2.setCreateTime(DateUtil.getCurrentDate());
			 tAccDetail2.setModifyTime(DateUtil.getCurrentDate());
			 tAccDetail2.setOperator("sys");
			 if(! accService.addAccDetail(tAccDetail2,Constant.VERSION_NO)){
				 logger.info("addAccDetail fail,tAccDetail2 is "+tAccDetail2.toString());
		     };					 
		 }
		 if(sefLev.equals("3")){//总共有3层
			 UserFriendship	 fUserFriendship3 =  userService.findUpFriendship(fUserFriendship.getUserCode(), Constant.VERSION_NO); 
			 AccDetail tAccDetail3 = new AccDetail();
			 tAccDetail3.setUserCode(fUserFriendship3.getUserCode());		
			 tAccDetail3.setSubAccno("010101");
			 tAccDetail3.setBounsSource1("11");//普通用户推荐会员参考PDM		 
			 tAccDetail3.setBounsSource2("1103");//普通用户推荐B级会员
			 tAccDetail3.setRelaUsercode(UserCode);
			 tAccDetail3.setRelaUserlevel("C");
			  try{
			    	String rsAmnt="0";
			    	if(tSysGencodeBNTJDM3.getCodeValue()==null||
			    			tSysGencodeBNTJDM3.getCodeValue().equals("")||
			    			tSysGencodeBNTJDM3.getCodeValue().equals("null"))
			    	{
			    		logger.info("calPtMemberRecmBouns fail ,tSysGencodeBNTJDM3 is null !");
			    	}else{
			    		rsAmnt = tSysGencodeBNTJDM3.getCodeValue();
			    		
			    	}
			    	BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
			    	bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
			    	tAccDetail3.setAmnt(bdAmnt);	
			    }catch(Exception e){
			    	logger.error("C calPtMemberRecmBouns fail ,translate string to decimal fail!"+e.getMessage());
			    }
			 tAccDetail3.setCaldate(DateUtil.getCurrentDate());
			 tAccDetail3.setStatus("1");
			 tAccDetail3.setCreateTime(DateUtil.getCurrentDate());
			 tAccDetail3.setModifyTime(DateUtil.getCurrentDate());
			 tAccDetail3.setOperator("sys");
			 if(! accService.addAccDetail(tAccDetail3,Constant.VERSION_NO)){
				 logger.info("addAccDetail fail,tAccDetail3 is "+tAccDetail3.toString());
		     };	
		
		 }
	
	}*/
	
	
	
	/**
	 * 计算普通会员注册奖励关系
	 * 
	 * */
	/*private boolean   calPtMemberRegitBouns(UserDetail mUserDetail){				
		SysGencode tSysGencodeBNDMZC5 = new SysGencode();
		if(tSysGencodeAll!=null&&tSysGencodeAll.size()>0){
			for(SysGencode tSysGencode:tSysGencodeAll){
				if(tSysGencode.getGroupCode().equals("BOUND_DEF")){//获取奖金定义
					if(tSysGencode.getCodeValue().equals("DMZC")){
						tSysGencodeBNDMZC5= tSysGencode;
						break;
					}					
				}
			}
		}		
		//根据tb_acc_def的定义，设置子账号
	    AccDetail tAccDetail = new AccDetail();
	    tAccDetail.setUserCode(mUserDetail.getUserCode());
	    tAccDetail.setSubAccno("010101");
	    tAccDetail.setBounsSource1("10");
	    tAccDetail.setBounsSource2("1001");
	    tAccDetail.setRelaUsercode(mUserDetail.getRefPhone());
	    tAccDetail.setRelaUserlevel("");
	    try{
	    	String rsAmnt="0";
	    	if(tSysGencodeBNDMZC5.getCodeValue()==null||
	    			tSysGencodeBNDMZC5.getCodeValue().equals("")||
	    			tSysGencodeBNDMZC5.getCodeValue().equals("null"))
	    	{
	    		logger.info("calPtMemberRegitBouns fail ,tSysGencodeBNDMZC5 is null !");
	    	}else{
	    		rsAmnt = tSysGencodeBNDMZC5.getCodeValue();
	    		
	    	}
	    	BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
	    	bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
	    	tAccDetail.setAmnt(bdAmnt);	
	    }catch(Exception e){
	    	logger.error("calPtMemberRegitBouns fail ,translate string to decimal fail!"+e.getMessage());
	    }
	    tAccDetail.setCaldate(DateUtil.getCurrentDate());
	    tAccDetail.setStatus("1");
	    tAccDetail.setCreateTime(DateUtil.getCurrentDate());
	    tAccDetail.setModifyTime(DateUtil.getCurrentDate());
	    
	    return accService.addAccDetail(tAccDetail,Constant.VERSION_NO);
	    
	}*/
	
	
	
	
	/**
	 * 创建用户关系
	 * */
	/*private void   createUserFriendship(UserDetail mUserDetail){
		String refPhone = mUserDetail.getRefPhone(); 
		String UserCode = mUserDetail.getUserCode();
		String userType = mUserDetail.getUserType();
		
		User tRefUser =  userService.findbyPhone(refPhone, Constant.VERSION_NO);
				
		 boolean tUpflag = userService.findIsExistUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
		 UserFriendship fUserFriendship= new UserFriendship();//推荐人上级关系
		 boolean tSvSelShipFlag =true;			 
		 String tReflvl ="";
		 String sefLev="";
		 if(!tUpflag){//如果推荐人没有上级会员，则自己为A级会员
			 tReflvl="0";
			 sefLev="1";
		 }
		 if(tUpflag){
			 fUserFriendship =  userService.findUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
			 if(fUserFriendship.getRelaLevel().equals("A")){
				 sefLev="2";
			 }else if(fUserFriendship.getRelaLevel().equals("B")){
				 sefLev="3";
			 }else if(fUserFriendship.getRelaLevel().equals("C")){
				 sefLev="4";
			 }else{
				 sefLev="4";					
			 }
		 }
		 
		 UserFriendship tUserFriendship = new UserFriendship();
		 tUserFriendship.setUserCode(tRefUser.getUserCode());
		 tUserFriendship.setRecomuserCode(UserCode);	
		 if(userType.equals("1")){
		 tUserFriendship.setRelaLevel("A");//
		 }
		 tUserFriendship.setUserType(userType);
		 tUserFriendship.setCalflag("0");
		 tUserFriendship.setCreateTime(DateUtil.getCurrentDate());
		 tUserFriendship.setModifyTime(DateUtil.getCurrentDate());
		 if(!userService.addUserFriendShip(tUserFriendship, Constant.VERSION_NO)){
				 logger.info("addUserFriendShip fail,tUserFriendship is "+tUserFriendship.toString());
		 };		
		 if(userType.equals("1")){//普通会员		 
			 if(sefLev.equals("2")){//总共有2层				
				 UserFriendship tUserFriendship2 = new UserFriendship();
				 tUserFriendship2.setUserCode(fUserFriendship.getUserCode());
				 tUserFriendship2.setRecomuserCode(UserCode);
				 tUserFriendship2.setRelaLevel("B");//只要有
				 tUserFriendship2.setUserType(userType);
				 tUserFriendship2.setCalflag("0");
				 tUserFriendship2.setCreateTime(DateUtil.getCurrentDate());
				 tUserFriendship2.setModifyTime(DateUtil.getCurrentDate());
				 if(!userService.addUserFriendShip(tUserFriendship2, Constant.VERSION_NO)){
					 logger.info("addUserFriendShip fail,tUserFriendship2 is "+tUserFriendship2.toString());
				 };					 
			 }
			 if(sefLev.equals("3")){//总共有3层
				 UserFriendship	 fUserFriendship3 =  userService.findUpFriendship(fUserFriendship.getUserCode(), Constant.VERSION_NO); 
				 UserFriendship tUserFriendship3 = new UserFriendship();
				 tUserFriendship3.setUserCode(fUserFriendship3.getUserCode());
				 tUserFriendship3.setRecomuserCode(UserCode);
				 tUserFriendship3.setRelaLevel("C");//只要有
				 tUserFriendship3.setUserType(userType);
				 tUserFriendship3.setCalflag("0");
				 tUserFriendship3.setCreateTime(DateUtil.getCurrentDate());
				 tUserFriendship3.setModifyTime(DateUtil.getCurrentDate());
				 if(!userService.addUserFriendShip(tUserFriendship3, Constant.VERSION_NO)){
					 logger.info("addUserFriendShip fail,tUserFriendship3 is "+tUserFriendship3.toString());
				 };
			 }
		 }
		
	}*/
	
	
	
	
	
	/**
	 * 修改密码
	 * <p> 修改密码  </p>
	 * @Title: modifypwd 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月23日
	 */
	@RequestMapping(value="/modifypwd", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse modifypwd(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
		    User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		    String userCode="";
			if(user==null){
				userCode =request.getParameter("userCode");
			}else{
				userCode =user.getUserCode();
			}		    
			String oldPassWord  =request.getParameter("oldpassword");
			String passWord  =request.getParameter("newpassword");
			String cfPassWord  =request.getParameter("resnewpassword");

			
			/*if(!isMobile(userName)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}*/
			
			if(StringUtil.isEmpty(passWord)||StringUtil.isEmpty(cfPassWord)){
				ar.setSuccess(false);
				ar.setMessage("密码不能为空!");
				return ar;
			}					
			
			if(!passWord.equals(cfPassWord)){
				ar.setSuccess(false);
				ar.setMessage("两次密码输入不一致！");
				return ar;
			}
			
//		    User fUser =userService.findbyPhone(userName, Constant.VERSION_NO);
			User fUser = userService.findUserByUserCode(userCode);
		    if(fUser!=null&&!MD5Util.getMd5Code(oldPassWord).equals(fUser.getPwdhash())){
		    	ar.setSuccess(false);
				ar.setMessage("输入原密码错误,请重新输入！");
				return ar;
		    }

			if(!userService.modifypwdByUserCode(userCode, MD5Util.getMd5Code(passWord), Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("修改密码失败！");
				return ar;
			}
				ar.setSuccess(true);
				ar.setMessage("修改密码成功！");
				return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}
		return ar;
	}
	
	/**
	 * 忘记密码
	 * <p> 忘记密码  </p>
	 * @Title: forgetpwd 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月23日
	 */
	@RequestMapping(value="/forgetpwd", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse forgetpwd(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			String userName  =request.getParameter("phone");
			String passWord  =request.getParameter("password");
			String cfPassWord  =request.getParameter("resPassword");
			String vcode  =request.getParameter("yzm");
			
			if(!Validator.isMobile(userName)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}
			
			if(StringUtil.isEmpty(passWord)||StringUtil.isEmpty(cfPassWord)){
				ar.setSuccess(false);
				ar.setMessage("密码不能为空!");
				return ar;
			}					
			
			if(!passWord.equals(cfPassWord)){
				ar.setSuccess(false);
				ar.setMessage("两次密码输入不一致！");
				return ar;
			}
					
			//1小时之内的短信验证码有效
			String tVcode =smsService.findSendsmsDetail(userName,Constant.CUR_SYS_CODE); 
			if(!vcode.equals(tVcode.trim())){
				ar.setSuccess(false);
				ar.setMessage("验证码输入不正确！");
				return ar;
			}

			if(!userService.modifyPwd(userName, MD5Util.getMd5Code(passWord),Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("修改密码失败！");
				return ar;
			}
				ar.setSuccess(true);
				ar.setMessage("修改密码成功！");
				return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}
		return ar;
	}
	
	/**
	 * 用户实名
	 * <p>用户实名  </p>
	 * @Title: realname 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月23日
	 */
	@RequestMapping(value="/realname", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse realname(HttpServletRequest request,HttpServletResponse response){
	    try {
		    User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			String realName  = request.getParameter("realName");
			if(!StringUtil.isEmpty(realName)){
			    realName = URLDecoder.decode(realName, "UTF-8");
			}
			String idno  =request.getParameter("idno");
	
			
			/*if(!isMobile(userName)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}*/
			
			if(StringUtil.isEmpty(realName)||StringUtil.isEmpty(idno)){
				ar.setSuccess(false);
				ar.setMessage("真实姓名、身份证号不能为空！");
				return ar;
			}
					
			if(!IdcardUtils.validateCard(idno)){
				ar.setSuccess(false);
				ar.setMessage("身份证格式不正确！");
				return ar;
			};
			

			if(!userService.realUser(user.getPhone(), realName, idno, Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("用户实名失败！");
				return ar;
			}
				ar.setSuccess(true);
				ar.setMessage("用户实名成功！");
				return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}
		return ar;
	}
	
	/**
	 * 用户实名
	 * <p>用户实名  </p>
	 * @Title: realname 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月23日
	 */
	@RequestMapping(value="/modifyphone", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse modifyphone(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			String oldPhone  =request.getParameter("oldphone");
			String phone  =request.getParameter("phone");
			String vcode  =request.getParameter("yzm");
			if(StringUtil.isEmpty(phone)||StringUtil.isEmpty(vcode)){
				ar.setSuccess(false);
				ar.setMessage("手机号、验证码不能为空！");
				return ar;
			}
			
			if(!Validator.isMobile(phone)){
				ar.setSuccess(false);
				ar.setErrorCode(CodeConstant.MOBILE_ERROR);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}
			

			//判断用户是否已存在
	        if(userService.findIsExist(phone, Constant.VERSION_NO)){
	        	ar.setSuccess(false);
				ar.setMessage("手机号已存在！");
				ar.setErrorCode(CodeConstant.MOBILE_EXISTS);
				return ar;
	        }
			
			
			//1小时之内的短信验证码有效
			String tVcode =smsService.findSendsmsDetail(phone,Constant.CUR_SYS_CODE); 
			if(!vcode.equals(tVcode.trim())){
				ar.setSuccess(false);
				ar.setErrorCode(CodeConstant.SMS_ERROR);
				ar.setMessage("验证码输入不匹配！");
				return ar;
			}
			
			User fUser = userService.findbyPhone(oldPhone, Constant.VERSION_NO);
			if(fUser==null){
				ar.setSuccess(false);
				logger.info("修改手机号功能,根据原手机号"+oldPhone+"未查询用户信息！");
				ar.setMessage("系统异常！");
				return ar;
			}
			String tUserCode =fUser.getUserCode();
			
		
			User tUser = new User();
			tUser.setUserCode(tUserCode);
			tUser.setPhone(phone);
			tUser.setModifyTime(DateUtil.getCurrentDate());
		
			UserDetail tUserDetail = new UserDetail();
			tUserDetail.setUserCode(tUserCode);
			tUserDetail.setPhone(phone);
			tUserDetail.setModifyTime(DateUtil.getCurrentDate());
			
			
			if(!userService.modifyPhone(tUserCode, phone, Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("修改手机号失败！");
				return ar;
			}
			
				ar.setSuccess(true);
				ar.setMessage("修改手机号成功！");
				return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}
		return ar;
	}
	
	/**
	 * 发送短信
	 * <p> 发送短信  </p>
	 * @Title: sendsms 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月18日
	 */
	@RequestMapping(value="/sendsms", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse sendsms(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			String phone  =request.getParameter("phone");
			if(!Validator.isMobile(phone)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}
			Map<String, Object> smsmap = userService.findLockSmsStatus(phone,smsInterval, Constant.VERSION_NO);
			String tSmsLockStatus = 
					smsmap==null?Constant.UN_LOCKED:smsmap.get("is_smslocked")==null?Constant.UN_LOCKED:(String) smsmap.get("is_smslocked");
			Date tSmsLockTime =
					smsmap==null?null:smsmap.get("smslock_time")==null? null: (Date) smsmap.get("smslock_time");
			if(tSmsLockTime!=null&&
					tSmsLockTime.compareTo(Constant.SYS_BUILDDATE)>0
					&&(DateUtil.getCurrentDate().getTime() -tSmsLockTime.getTime())/1000<=smsInterval){
				ar.setSuccess(false);
				ar.setMessage("短信发送太频繁，请稍后重试!");
				return ar;
			}
			
			 List<Map<String,Object>> tSysGencodeList =sysGenCodeService.findByGroupCode("SENDSMS_FLAG", Constant.VERSION_NO);
			 String smsflag ="";
	            for(Map<String,Object> mapObj:tSysGencodeList){
	                if("SENDSMS_FLAG".equals(mapObj.get("codeName"))){
	                	smsflag = mapObj.get("codeValue").toString();
	                }
	              
	            }
	        String vCode = "";
			if("0".equals(smsflag)){
			    vCode = "888888";
			}else{
				vCode =SmsSend.sendSms(phone);
			}
//			String vCode =SmsSend.sendSms(phone);			
//			String vCode =String.valueOf((int)((Math.random()*9+1)*100000));
			if(vCode.equals("0")){				
				ar.setSuccess(false);
				ar.setMessage("短信发送失败！");
			    return ar;
			}			
			int sendcnt= smsService.findSendCntByPhone(phone,smsInterval);
		
			Sendsms sendsmsInfo = new Sendsms();
			sendsmsInfo.setPhone(phone);
			sendsmsInfo.setSendsmsCount(sendcnt+1);
			sendsmsInfo.setSendTime(DateUtil.getCurrentDate());						
			if(sendcnt==0){				
				smsService.addSendsmsInfo(sendsmsInfo);				
			}else if(sendcnt<smsLimit-1&&tSmsLockStatus.equals(Constant.UN_LOCKED)){
				if(!smsService.modifySendsmsInfo(sendsmsInfo)){
					logger.info("modifySendsmsInfo fail,sendcnt is "+sendcnt+1);
				};
				if(tSmsLockStatus.equals(Constant.LOCKED)&&
				!userService.modifyLockSmsStatus(phone, Constant.UN_LOCKED,DateUtil.getCurrentDate(), Constant.VERSION_NO)){
					logger.info("modifyLockSmsStatus fail,phone is "+phone +"unlock fail!");
				}				
			}else if(tSmsLockStatus.equals(Constant.LOCKED)){//超过系统设定的时间，可以再次申请发送短信，次数记为1
				sendsmsInfo.setSendsmsCount(1);
				if(!smsService.modifySendsmsInfo(sendsmsInfo)){
					logger.info("modifySendsmsInfo fail,reset sendcnt fail, sendcnt is  "+1);
				};
				if(!userService.modifyLockSmsStatus(phone,  Constant.UN_LOCKED,null, Constant.VERSION_NO)){
					logger.info("modifyLockSmsStatus fail ,phone is "+phone +"lock fail!");
				}
				
			}else{
				sendsmsInfo.setLockedTime(DateUtil.getCurrentDate());
				if(!smsService.modifySendsmsInfo(sendsmsInfo)){
					logger.info("modifySendsmsInfo fail,sendcnt is "+sendcnt+1);
				};
				if(!userService.modifyLockSmsStatus(phone,  Constant.LOCKED,DateUtil.getCurrentDate(), Constant.VERSION_NO)){
					logger.info("modifyLockSmsStatus fail ,phone is "+phone +"lock fail!");
				}
				
			}
			SendsmsDetail tSendsmsDetail = new SendsmsDetail();
			tSendsmsDetail.setPhone(phone);
			tSendsmsDetail.setSendTime(DateUtil.getCurrentDate());
			tSendsmsDetail.setVcode(vCode);
			tSendsmsDetail.setSysCode(Constant.CUR_SYS_CODE);
			if(!smsService.addSendsmsDetail(tSendsmsDetail)){
				logger.info("addSendsmsDetail fail ,phone is "+phone +"!");				
			}
			ar.setSuccess(true);
			ar.setMessage("发送短信成功！");
			return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
		}
		return ar;
	}
	
	/**
	 * 退出
	 * @param request
	 * @return
	 */
	/*@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResponse logout(HttpServletRequest request) {
		request.getSession().setAttribute(Constant.LOGIN_USER, null);
		ar.setSuccess(true);
		ar.setMessage("成功退出！");
		return ar;
	}*/
	/**
	 * 退出
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
	    request.getSession().setAttribute(Constant.LOGIN_USER, null);
	    response.sendRedirect(request.getContextPath() + "/view/login.html");
	}
	
	/*@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("/index");
	}*/
	
	
	
	@RequestMapping(value="/upload")
    public void upload(HttpServletRequest request,HttpServletResponse response,
            @RequestParam(value="tp",required=false) MultipartFile tp){
         // 判断文件是否为空  
        logBefore(logger, "上传头像");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter out  = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        PageData pdfile = this.getPageData();
        Map<String,String> map = new HashMap<String, String>();
        map.put("jpg","jpg");
        map.put("png","png");
        map.put("jpeg","jpeg");
        map.put("png","png");
        map.put("gif","gif");
        map.put("bmp","bmp");
        try {
            String filepath = pdfile.getString("filepath");
            String resources_base = "";
            String resources_local = "";
            String resources_backup = "";
            List<Map<String,Object>> tSysGencodeList =sysGenCodeService.findByGroupCode("RESOURCES_PATH", Constant.VERSION_NO);
            for(Map<String,Object> mapObj:tSysGencodeList){
                if("RESOURCES_BASE".equals(mapObj.get("codeName"))){
                    resources_base = mapObj.get("codeValue").toString();
                }
                if("RESOURCES_LOCAL".equals(mapObj.get("codeName"))){
                    resources_local = mapObj.get("codeValue").toString();
                }
                if("RESOURCES_BACKUP".equals(mapObj.get("codeName"))){
                    resources_backup = mapObj.get("codeValue").toString();
                }
            }
            if("".equals(resources_base)||"".equals(resources_local)||"".equals(resources_backup)){
                logger.info("RESOURCES_BASE or RESOURCES_LOCAL or RESOURCES_BACKUP Error:value may be null");
                out.print("<script>parent.window.alert(\"服务器路径有误，请联系客服！\");</script>");
                return;
            }
            int lastindex = filepath.lastIndexOf(".");
            String imgtype = filepath.substring(lastindex+1,filepath.length());
            System.out.println("----------imgtype:"+imgtype);
            if(!map.containsKey(imgtype)){
                out.print("<script>parent.window.alert(\"请上传jpg、png、jpeg、gif、bmp格式的图片！\");</script>");
                return;
            }
            
//            long currentTimeMillis = System.currentTimeMillis();
            String new_img_name= get32UUID()+"_"+Constant.PIC_HEAD_WIDTH+"_"+Constant.PIC_HEAD_HEIGHT+"."+imgtype;
            String http_img_url = resources_base+Constant.PIC_HEAD_PATH+new_img_name;
            String temp_img_url = resources_local+Constant.PIC_TEMP_PATH;//临时图片路径
            String server_img_url = resources_local+Constant.PIC_HEAD_PATH;//图片保存服务器路径
            String backup_img_url = resources_backup+Constant.PIC_HEAD_PATH;//图片磁盘备份路径
            
            
            System.out.println("-----------temp_img_url----------------->>>>>>>>>>>>>>>>>"+temp_img_url);
            System.out.println("-----------server_img_url----------------->>>>>>>>>>>>>>>>>"+server_img_url);
            System.out.println("-----------http_img_url------------------->>>>>>>>>>>>>>>>>"+http_img_url);
            System.out.println("-----------backup_img_url----------------->>>>>>>>>>>>>>>>>"+backup_img_url);
            
            FileUtil.copyFile(tp.getInputStream(), temp_img_url,new_img_name);
            Map<String, Long> imgInfo = ImgUtil.getImgInfo(temp_img_url+new_img_name);
            Long w = imgInfo.get("w");
            Long h = imgInfo.get("h");
//            Long s = imgInfo.get("s");
            if(w != Constant.PIC_HEAD_WIDTH && h != Constant.PIC_HEAD_HEIGHT){
                FileUtil.deleteFile(temp_img_url+new_img_name);
                out.print("<script>top.alert(\"请上传"+Constant.PIC_HEAD_WIDTH+"*"+Constant.PIC_HEAD_WIDTH+"规格的图片！\");</script>");
                return;
            }
            FileUtil.copyFile(new FileInputStream(temp_img_url+new_img_name), server_img_url,new_img_name);
            FileUtil.copyFile(new FileInputStream(temp_img_url+new_img_name), backup_img_url,new_img_name);
            FileUtil.deleteFile(temp_img_url+new_img_name);
            userService.modifyUserHeadPic(pdfile.getString("userCode"), http_img_url, Constant.VERSION_NO);//修改数据库图片地址
            out.print("<script>parent.document.getElementById('headPicId').src=\""+http_img_url+"\";parent.document.getElementById('left_headPicId').src=\""+http_img_url+"\";parent.$('#imgAddrss').val('"+http_img_url+"')</script>"); 
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString(), e);
            out.print("<script>parent.alert('系统异常，上传失败！');</script>");
        }finally{
            out.print("<script>parent.$(\"input[type='file']\").val('');</script>");
            out.close();
            logAfter(logger);
        }
        return;
    }
	
	@RequestMapping(value="delPic",method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse delPic(){
	    logBefore(logger, "删除图片");
        try{
            pd = this.getPageData();
            String imgAddrss = pd.getString("imgAddrss");
            if(!StringUtil.isEmpty(imgAddrss)){
                //删除硬盘上的文件 start
                String img_name = imgAddrss.substring(imgAddrss.lastIndexOf("/")+1); 
                String resources_server = "";
                String resources_backup = "";
                List<Map<String,Object>> tSysGencodeList =sysGenCodeService.findByGroupCode("RESOURCES_PATH", Constant.VERSION_NO);
                for(Map<String,Object> mapObj:tSysGencodeList){
                    if("RESOURCES_LOCAL".equals(mapObj.get("codeName"))){
                        resources_server = mapObj.get("codeValue").toString();
                    }
                    if("RESOURCES_BACKUP".equals(mapObj.get("codeName"))){
                        resources_backup = mapObj.get("codeValue").toString();
                    }
                }
                String server_img_url = resources_server+Constant.PIC_HEAD_PATH+img_name;//图片存放在服务器路径
                String backup_img_url = resources_backup+Constant.PIC_HEAD_PATH+img_name;//图片存放在磁盘路径
                File serverFile = new File(server_img_url.trim()); 
                File backupFile = new File(backup_img_url.trim()); 
                if(serverFile.exists()){
                    serverFile.delete();
                }else{
                    logger.info("服务器文件不存在");
                }
                if(backupFile.exists()){
                    serverFile.delete();
                }else{
                    logger.info("磁盘文件不存在");
                }
                //删除硬盘上的文件 end
                userService.modifyUserHeadPic(pd.getString("userCode"), null, Constant.VERSION_NO);//删除数据库图片地址
                ar.setSuccess(true);
                ar.setMessage("删除成功！");
            }   
                
        }catch(Exception e){
            logger.error(e.toString(), e);
            ar.setSuccess(false);
            ar.setMessage("删除失败！");
        }
        logAfter(logger);
        return ar;
    }
	
	private UserDetail findUserDetail(HttpServletRequest request){
		
	    User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
	    String userCode ="";
	    if(user==null){
	    	userCode =request.getParameter("userCode");
	    }else{
	    	userCode = user.getUserCode();
	    }
	    if(userCode == null||userCode.equals(""))
	        return null;
	    UserDetail userDetail = userService.findUserDetailByUserCode(userCode, Constant.VERSION_NO);
	    return userDetail;
	}
	
	/**
	 * @describe:跳往普通会员个人中心
	 * @author: zhangchunming
	 * @date: 2016年9月2日上午10:08:58
	 * @params: @return
	 * @return: AjaxResponse
	 */
	@RequestMapping(value="/toMyself", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResponse toMyself(HttpServletRequest request){
	    logBefore(logger,"去往用户中兴");
	    /*UserDetail userDetail = JSON.parseObject(params,UserDetail.class);
	    if(userDetail==null||StringUtil.isEmpty(userDetail.getUserCode())){
	        ar.setSuccess(false);
	        ar.setErrorCode(CodeConstant.PARAM_ERROR);
	        ar.setMessage("请求参数有误！");
	    }*/
	    UserDetail userDetail = findUserDetail(request);
	    if(userDetail != null){
            ar.setSuccess(true);
            ar.setData(userDetail);
            ar.setMessage("查询数据成功！");
        }else{
            ar.setSuccess(false);
            ar.setMessage("数据不存在！");
        }
	    logAfter(logger);
	    return ar;
	}
	
	@RequestMapping(value="/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResponse getHeadPic(HttpServletRequest request){
        logBefore(logger,"获取用户信息");
        Map<String,String> map = new HashMap<String,String>();
        UserDetail userDetail = findUserDetail(request);
        if(userDetail != null){
            ar.setSuccess(true);
            map.put("imgAddrss", userDetail.getImgAddrss());
            map.put("userType", userDetail.getUserType());
            map.put("phone", userDetail.getPhone());
            map.put("realName", userDetail.getRealName());
            ar.setData(map);
            ar.setMessage("查询数据成功！");
        }else{
            ar.setSuccess(false);
            ar.setMessage("数据不存在！");
        }
        logAfter(logger);
        return ar;
    }
	/**
	 * @describe:判断用户手机号是否存在
	 * @author: zhangchunming
	 * @date: 2016年9月20日下午6:36:46
	 * @return: AjaxResponse
	 */
	@RequestMapping(value="/isExistPhone", method=RequestMethod.POST)
    @ResponseBody
    public AjaxResponse isExistPhone(){
	    pd = this.getPageData();
	    String phone = pd.getString("phone").trim();
	    if(StringUtil.isEmpty(phone)){
	        ar.setSuccess(false);
	        ar.setMessage("请求参数有误！");
	        return ar;
	    }
	    User user = userService.findbyPhone(phone, Constant.VERSION_NO);
	    if(user==null){
	        ar.setSuccess(false);
            ar.setMessage("该手机号未注册！");
            return ar;
	    }else{
	        ar.setMessage("该手机号已注册！");
	        ar.setSuccess(true);
	    }
	    return ar;
	}
}
