package com.qkl.tfcc.controller.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.po.sys.SysMaxnum;
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
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.DateUtil;
import com.qkl.util.help.IdcardUtils;
import com.qkl.util.help.MD5Util;
import com.qkl.util.help.StringUtil;
import com.qkl.tfcc.api.po.sys.SysGencode;

import java.util.List;
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
	public AjaxResponse login(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			String userName  =request.getParameter("phone");
			String passWord  =request.getParameter("password");
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
		return ar;
	}
	
	/**
	 * 用户修改
	 * <p> 用户修改  </p>
	 * @Title: modifyuser 
	 * @return  json格式的
	 * @create author kezhiyi
	 * @create date 2016年8月18日
	 */
	@RequestMapping(value="/modifyuser", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse modifyuser(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			String phone  =request.getParameter("phone");
			String wxnum = request.getParameter("wxnum");
			String bankaccno = request.getParameter("bankaccno");
			String mailAddrss =request.getParameter("mailaddrss"); 
			String zipCode =request.getParameter("zipcode"); 			
			String imgAddrss =request.getParameter("imgaddrss");
			
			
			//头像处理
	
			UserDetail tUserDetail = new UserDetail();
			tUserDetail.setPhone(phone);
			tUserDetail.setWxnum(wxnum);
			tUserDetail.setBankaccno(bankaccno);
			tUserDetail.setMailAddrss(mailAddrss);
			tUserDetail.setZipCode(zipCode);
			tUserDetail.setImgAddrss(imgAddrss);
			tUserDetail.setModifyTime(DateUtil.getCurrentDate());
			if(userService.modifyUserDetail(tUserDetail, Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("个人信息修改失败！");
				return ar;
			}
				ar.setSuccess(true);
				ar.setMessage("个人信息修改成功！");
				return ar;
			
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("系统异常");
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
			String cfPassWord  =request.getParameter("respassword");
			String vcode  =request.getParameter("yzm");
			String refPhone  =request.getParameter("phone1");
			String userType =request.getParameter("usertype");
			String branchName =request.getParameter("branchname"); 
			String realName =request.getParameter("realname");  
			String idno =request.getParameter("idno");
			String cropName  =request.getParameter("cropname");
			String cropPerson  =request.getParameter("cropperson");
			
			userName=userName==null?"":userName.trim();
			passWord=passWord==null?"":passWord.trim();
			cfPassWord=cfPassWord==null?"":cfPassWord.trim();
			refPhone=refPhone==null?"":refPhone.trim();
			vcode=vcode==null?"":vcode.trim();
			branchName=branchName==null?"":branchName.trim();
			realName=realName==null?"":realName.trim();
			idno=idno==null?"":idno.trim();
			cropName=cropName==null?"":cropName.trim();
			cropPerson=cropPerson==null?"":cropPerson.trim();
			
			if(!isMobile(userName)){
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
			if(!vcode.equals(tVcode.trim())){
				ar.setSuccess(false);
				ar.setMessage("验证码输入不正确！");
				return ar;
			}
			
			Long tMaxno =sysMaxnumService.findMaxNo(notype, Constant.VERSION_NO);
			if(tMaxno==null){
				logger.info("tSysMaxnum findMaxNo  is null!");
				ar.setSuccess(false);
				ar.setMessage("系统麻烦,请稍后重试！");
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
			if(userType.equals("2")){
			tUser.setStatus("0");
			}else {
			tUser.setStatus("1");
			}		
			tUser.setCreateTime(DateUtil.getCurrentDate());
			tUser.setModifyTime(DateUtil.getCurrentDate());
		
			UserDetail tUserDetail = new UserDetail();
			tUserDetail.setUserCode(UserCode);
			tUserDetail.setPhone(userName);
			tUserDetail.setRefPhone(refPhone);
			tUserDetail.setBranchName(branchName);
			tUserDetail.setRealName(realName);
			tUserDetail.setIdno(idno);
			tUserDetail.setCropName(cropName);
			tUserDetail.setCropPerson(cropPerson);
			tUserDetail.setQrCode("");
			tUserDetail.setUserType(userType);
			if(realName!=null&&!realName.equals("")&&idno!=null&&!idno.equals("")){
				tUserDetail.setRealStat("1");	
			}			
			tUserDetail.setCreateTime(DateUtil.getCurrentDate());
			tUserDetail.setModifyTime(DateUtil.getCurrentDate());
			tUserDetail.setOperator("sys");
			if(userService.addUser(tUser,tUserDetail,Constant.VERSION_NO)){			
				ar.setSuccess(false);
				ar.setMessage("注册失败！");
				return ar;
			}
			
			//添加用户关系
			createUserFriendship(tUserDetail);
			
			//计算注册奖励
			
			
			 
			 
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
	private void   calRegitAccDetail(UserDetail mUserDetail){
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
		
	}
	
	
	/**
	 * 计算网点用户注册奖励关系
	 * 
	 * */
	private boolean   calWDMemberRegitBouns(UserDetail mUserDetail){				
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
	    
	}
	
	
	
	/**
	 * 计算普通会员注册奖励关系
	 * 
	 * */
	private void  calPtMemberRecmBouns(UserDetail mUserDetail){	
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
	
	}
	
	
	
	/**
	 * 计算普通会员注册奖励关系
	 * 
	 * */
	private boolean   calPtMemberRegitBouns(UserDetail mUserDetail){				
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
	    
	}
	
	
	
	
	/**
	 * 创建用户关系
	 * */
	private void   createUserFriendship(UserDetail mUserDetail){
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
		
	}
	
	
	
	
	
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
			String userName  =request.getParameter("phone");
			String oldPassWord  =request.getParameter("oldpassword");
			String passWord  =request.getParameter("newpassword");
			String cfPassWord  =request.getParameter("resnewpassword");

			
			if(!isMobile(userName)){
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
			
		    User fUser =userService.findbyPhone(userName, Constant.VERSION_NO);

		    if(fUser!=null&&!MD5Util.getMd5Code(oldPassWord).equals(fUser.getPwdhash())){
		    	ar.setSuccess(false);
				ar.setMessage("输入原密码错误,请重新输入！");
				return ar;
		    }

			if(userService.modifyPwd(userName, MD5Util.getMd5Code(passWord),Constant.VERSION_NO )){			
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
			String cfPassWord  =request.getParameter("respassword");
			String vcode  =request.getParameter("yzm");
			
			if(!isMobile(userName)){
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

			if(userService.modifyPwd(userName, MD5Util.getMd5Code(passWord),Constant.VERSION_NO )){			
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
		AjaxResponse ar = new AjaxResponse();
		try {
			String userName  =request.getParameter("phone");
			String realName  =request.getParameter("realname");
			String idno  =request.getParameter("idno");
	
			
			if(!isMobile(userName)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}
			
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
			

			if(userService.realUser(userName, realName, idno, Constant.VERSION_NO)){			
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
			
			if(!isMobile(phone)){
				ar.setSuccess(false);
				ar.setMessage("手机号格式不正确！");
				return ar;
			}
			

			//判断用户是否已存在
	        if(userService.findIsExist(phone, Constant.VERSION_NO)){
	        	ar.setSuccess(false);
				ar.setMessage("手机号已存在！");
				return ar;
	        }
			
			
			//1小时之内的短信验证码有效
			String tVcode =smsService.findSendsmsDetail(phone,Constant.CUR_SYS_CODE); 
			if(!vcode.equals(tVcode.trim())){
				ar.setSuccess(false);
				ar.setMessage("验证码输入不正确！");
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
			
			
			if(userService.modifyPhone(tUserCode, phone, Constant.VERSION_NO)){			
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
			if(!isMobile(phone)){
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
//			String vCode =SmsSend.sendSms(phone);
			String vCode =String.valueOf((int)((Math.random()*9+1)*100000)) ;
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
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().setAttribute(Constant.LOGIN_USER, null);
		return new ModelAndView("/login");
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("/index");
	}
	
	public static boolean isMobile(String str) {   
	        Pattern p = null;  
	        Matcher m = null;  
	        boolean b = false;   
	        str = str.trim();
	        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
	        m = p.matcher(str);  
	        b = m.matches();   
	        return b;  
	} 
	
	
	
	
	
}
