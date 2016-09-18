package com.qkl.tfcc.provider.user.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.po.acc.AccDetail;
import com.qkl.tfcc.api.po.sys.SysGencode;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.api.po.user.UserLevelcnt;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.provider.dao.SysGencodeDao;
import com.qkl.tfcc.provider.dao.UserDao;
import com.qkl.tfcc.provider.dao.UserDetailDao;
import com.qkl.tfcc.provider.dao.UserFriendshipDao;
import com.qkl.tfcc.provider.dao.UserLevelcntDao;
import com.qkl.tfcc.provider.dao.UserLoginErrDao;
import com.qkl.util.help.DateUtil;
import com.qkl.util.help.MD5Util;
import com.qkl.util.help.pager.PageData;

@Service
public class UserServiceImpl implements UserService {

	private Logger loger = LoggerFactory.getLogger(UserServiceImpl.class);
	private  List<SysGencode> tSysGencodeAll = new  ArrayList<SysGencode>();
	
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserDetailDao userDetailDao;	
	@Autowired
	private UserFriendshipDao userFriendshipDao;
	@Autowired
	private UserLoginErrDao userLoginErrDao;
	@Autowired
    private AccService accService;
	@Autowired
	private SysGencodeDao sysGencodeDao;
	@Autowired
	private UserLevelcntDao userLevelcntDao;
	
	
	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public Map<String, Object> login(String phone, String password,
			String systemCode,String versionNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userDao.findUserByPhone(phone);
		if (null != user) {
			if (user.getPwdhash().equals(MD5Util.getMd5Code(password))) {
				if (user.getStatus().equals(Constant.USING)) {					
						result.put("status", Constant.SUCCESS);
						result.put(Constant.LOGIN_USER, user);						
						result.put("msg", "ok");
					
				} else {
					result.put("status", Constant.FAIL);
					result.put("msg", "该用户已被禁用");
				}
			} else {
				result.put("status", Constant.FAIL);
				result.put("msg", "密码错误");
			}
		} else {
			result.put("status", Constant.FAIL);
			result.put("msg", "该用户不存在");
		}
		return result;
	
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addUser(User user, UserDetail userDetail,String versionNo) {
		try{			
			userDao.addUser(user);
			userDetailDao.addUserDetail(userDetail);
			tSysGencodeAll =sysGencodeDao.findAll();
	         //添加用户关系
          createUserFriendship(userDetail);
            
            //计算注册奖励
          calRegitAccDetail(userDetail);
			return true;
		}catch(Exception e){
			loger.debug("addUser fail,reason is "+e.getMessage());
			return false;
		}
		
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean addUserFriendShip(UserFriendship userFriendship,String versionNo) {
		try{			
			userFriendshipDao.addUserFriendship(userFriendship);			
			return true;
		}catch(Exception e){
			loger.debug("addUserFriendShip fail,reason is "+e.getMessage());
			return false;
		}

	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean realUser(String phone, String realName, String idNo,String versionNo) {
		try{			
			userDetailDao.modifyRealStatus(phone, realName, idNo);			
			return true;
		}catch(Exception e){
			loger.debug("realUser fail,reason is "+e.getMessage());
			return false;
		}

	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyPwd(String phone, String password,String versionNo) {
		try{			
			userDao.modifyPwd(phone, password);		
			return true;
		}catch(Exception e){
			loger.debug("modifyPwd fail,reason is "+e.getMessage());
			return false;
		}

	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyPhone(String userCode, String phone,String versionNo) {
		try{			
			userDao.modifyPhone(userCode, phone);	
			userDetailDao.modifyPhone(userCode, phone);
			return true;
		}catch(Exception e){
			loger.debug("modifyPwd fail,reason is "+e.getMessage());
			return false;
		}

	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyUserDetail(UserDetail userDetail,String versionNo) {
		try{					
			userDetailDao.modifyUserDetail(userDetail);
			userDao.modifyPhone(userDetail.getUserCode(), userDetail.getPhone());
			return true;
		}catch(Exception e){
			loger.debug("modifyUserDetail fail,reason is "+e.getMessage());
			return false;
		}

	}


	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyLockLoginStatus(String phone, String status,Date locktime,String versionNo) {
		try{					
			userDao.modifyLoginLockStaus(phone, status);
			return true;
		}catch(Exception e){
			loger.debug("modifyLockLoginStatus fail,reason is "+e.getMessage());
			return false;
		}
		
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public boolean modifyLockSmsStatus(String phone, String status,Date locktime,String versionNo) {
		try{					
			userDao.modifySmsLockStaus(phone, status);
			return true;
		}catch(Exception e){
			loger.debug("modifyLockSmsStatus fail,reason is "+e.getMessage());
			return false;
		}
		
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public Map<String, Object> findLockLoginStatus(String phone,long second, String versionNo) {
		return null;
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public Map<String, Object> findLockSmsStatus(String phone,long second, String versionNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userDao.findUserByPhone(phone);
		if (null != user) {
			result.put("is_smslocked", user.getIsSmslocked());
			result.put("smslock_time", user.getSmslockTime());
		
		} else {
			result.put("is_smslocked", null);
			result.put("smslock_time", null);
		}
		return result;
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public User findbyPhone(String phone, String versionNo) {
		User fUser = userDao.findUserByPhone(phone);
		return fUser;
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public boolean findIsExist(String phone, String versionNo) {
		 int eCnt = userDao.findUserIsExist(phone);
		 if(eCnt>0){
			 return true;
		 }		
		return false;
	}

	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public boolean findIsExistUpFriendship(String recomusercode, String versionNo) {
		 int eCnt = userFriendshipDao.findIsExistUpFriendship(recomusercode);
		 if(eCnt>0){
			 return true;
		 }		
		return false;
	}

	@Override
	@Transactional(propagation =Propagation.REQUIRED)
	public UserFriendship findUpFriendship(String recomusercode, String versionNo) {
		UserFriendship fUserFriendship = userFriendshipDao.findUpFriendship(recomusercode);
		return fUserFriendship;
	}

    @Override
    public boolean modifyUserHeadPic(String userCode, String imgAddrss, String versionNo) {
        try{                    
            userDetailDao.modifyUserHeadPic(userCode, imgAddrss);
            return true;
        }catch(Exception e){
            loger.debug("modifyUserHeadPic fail,reason is "+e.getMessage());
            return false;
        }

    }

    @Override
    public UserDetail findUserDetailByUserCode(String userCode, String versionNo) {
        return userDetailDao.findUserDetailByUserCode(userCode);
    }

    @Override
    public User findUserByUserCode(String userCode) {
        return userDao.findUserByUserCode(userCode);
    }

    @Override
    public boolean modifypwdByUserCode(String userCode, String password, String versionNo) {
        try{                    
            userDao.modifypwdByUserCode(userCode, password);
            return true;
        }catch(Exception e){
            loger.debug("modifypwdByUserCode fail,reason is "+e.getMessage());
            return false;
        }
    }
    
    /**
     * 创建用户关系
     * */
    private void   createUserFriendship(UserDetail mUserDetail){
        String refPhone = mUserDetail.getRefPhone(); 
        String UserCode = mUserDetail.getUserCode();
        String userType = mUserDetail.getUserType();
        
        User tRefUser =  findbyPhone(refPhone, Constant.VERSION_NO);
                
         boolean tUpflag = findIsExistUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
         UserFriendship fUserFriendship= new UserFriendship();//推荐人上级关系
         boolean tSvSelShipFlag =true;           
         String tReflvl ="";
         String sefLev="";
         if(!tUpflag){//如果推荐人没有上级会员，则自己为A级会员
             tReflvl="0";
             sefLev="1";
         }
         if(tUpflag){
             fUserFriendship =  findUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
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
         if(!addUserFriendShip(tUserFriendship, Constant.VERSION_NO)){
             loger.info("addUserFriendShip fail,tUserFriendship is "+tUserFriendship.toString());
         }; 
         
         if(userType.equals("1")){
        	 //用户关系计算器            
             PageData pd1=new PageData();
             pd1.put("userCode", tRefUser.getUserCode());
             pd1.put("syscode", Constant.CUR_SYS_CODE);
             pd1.put("relaLevel","A" );
             UserLevelcnt tUserLevelcnt= userLevelcntDao.findUserLevelcnt(pd1);            
             if(tUserLevelcnt==null){
            	 UserLevelcnt tUserLevelcntA = new UserLevelcnt();
            	 tUserLevelcntA.setUserCode(tRefUser.getUserCode());
            	 tUserLevelcntA.setRelaLevel("A");
            	 tUserLevelcntA.setLevcnt(1);
            	 tUserLevelcntA.setSyscode(Constant.CUR_SYS_CODE);
            	 tUserLevelcntA.setCreateTime(DateUtil.getCurrentDate());
            	 tUserLevelcntA.setModifyTime(DateUtil.getCurrentDate());
            	 tUserLevelcntA.setOperator("sys");
            	 userLevelcntDao.addUserLevelcnt(tUserLevelcntA);
             }else{
            	 UserLevelcnt tUserLevelcntA = new UserLevelcnt();
            	 tUserLevelcntA.setUserCode(tRefUser.getUserCode());
            	 tUserLevelcntA.setRelaLevel("A");            	 
            	 tUserLevelcntA.setSyscode(Constant.CUR_SYS_CODE);
            	 tUserLevelcntA.setModifyTime(DateUtil.getCurrentDate());
            	 tUserLevelcntA.setOperator("sys");
            	 userLevelcntDao.modifyUserLevelcnt(tUserLevelcntA);
             }
             
         }
         
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
                 if(!addUserFriendShip(tUserFriendship2, Constant.VERSION_NO)){
                     loger.info("addUserFriendShip fail,tUserFriendship2 is "+tUserFriendship2.toString());
                 }; 
               //用户关系计算器            
                 PageData pd2=new PageData();
                 pd2.put("userCode", tRefUser.getUserCode());
                 pd2.put("syscode", Constant.CUR_SYS_CODE);
                 pd2.put("relaLevel","B" );
                 UserLevelcnt tUserLevelcnt2= userLevelcntDao.findUserLevelcnt(pd2);            
                 if(tUserLevelcnt2==null){
                	 UserLevelcnt tUserLevelcntB = new UserLevelcnt();
                	 tUserLevelcntB.setUserCode(tRefUser.getUserCode());
                	 tUserLevelcntB.setRelaLevel("B");
                	 tUserLevelcntB.setLevcnt(1);
                	 tUserLevelcntB.setSyscode(Constant.CUR_SYS_CODE);
                	 tUserLevelcntB.setCreateTime(DateUtil.getCurrentDate());
                	 tUserLevelcntB.setModifyTime(DateUtil.getCurrentDate());
                	 tUserLevelcntB.setOperator("sys");
                	 userLevelcntDao.addUserLevelcnt(tUserLevelcntB);
                 }else{
                	 UserLevelcnt tUserLevelcntB = new UserLevelcnt();
                	 tUserLevelcntB.setUserCode(tRefUser.getUserCode());
                	 tUserLevelcntB.setRelaLevel("B");            	 
                	 tUserLevelcntB.setSyscode(Constant.CUR_SYS_CODE);
                	 tUserLevelcntB.setModifyTime(DateUtil.getCurrentDate());
                	 tUserLevelcntB.setOperator("sys");
                	 userLevelcntDao.modifyUserLevelcnt(tUserLevelcntB);
                 }                
             }
             if(sefLev.equals("3")){//总共有3层
                 UserFriendship  fUserFriendship3 =  findUpFriendship(fUserFriendship.getUserCode(), Constant.VERSION_NO); 
                 UserFriendship tUserFriendship3 = new UserFriendship();
                 tUserFriendship3.setUserCode(fUserFriendship3.getUserCode());
                 tUserFriendship3.setRecomuserCode(UserCode);
                 tUserFriendship3.setRelaLevel("C");//只要有
                 tUserFriendship3.setUserType(userType);
                 tUserFriendship3.setCalflag("0");
                 tUserFriendship3.setCreateTime(DateUtil.getCurrentDate());
                 tUserFriendship3.setModifyTime(DateUtil.getCurrentDate());
                 if(!addUserFriendShip(tUserFriendship3, Constant.VERSION_NO)){
                     loger.info("addUserFriendShip fail,tUserFriendship3 is "+tUserFriendship3.toString());
                 };
               //用户关系计算器            
                 PageData pd3=new PageData();
                 pd3.put("userCode", tRefUser.getUserCode());
                 pd3.put("syscode", Constant.CUR_SYS_CODE);
                 pd3.put("relaLevel","C" );
                 UserLevelcnt tUserLevelcnt3= userLevelcntDao.findUserLevelcnt(pd3);            
                 if(tUserLevelcnt3==null){
                	 UserLevelcnt tUserLevelcntC = new UserLevelcnt();
                	 tUserLevelcntC.setUserCode(tRefUser.getUserCode());
                	 tUserLevelcntC.setRelaLevel("C");
                	 tUserLevelcntC.setLevcnt(1);
                	 tUserLevelcntC.setSyscode(Constant.CUR_SYS_CODE);
                	 tUserLevelcntC.setCreateTime(DateUtil.getCurrentDate());
                	 tUserLevelcntC.setModifyTime(DateUtil.getCurrentDate());
                	 tUserLevelcntC.setOperator("sys");
                	 userLevelcntDao.addUserLevelcnt(tUserLevelcntC);
                 }else{
                	 UserLevelcnt tUserLevelcntC = new UserLevelcnt();
                	 tUserLevelcntC.setUserCode(tRefUser.getUserCode());
                	 tUserLevelcntC.setRelaLevel("C");            	 
                	 tUserLevelcntC.setSyscode(Constant.CUR_SYS_CODE);
                	 tUserLevelcntC.setModifyTime(DateUtil.getCurrentDate());
                	 tUserLevelcntC.setOperator("sys");
                	 userLevelcntDao.modifyUserLevelcnt(tUserLevelcntC);
                 }
                 
             }
         }
        
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
                    if(tSysGencode.getGroupCode().equals("BOUND_DEF")){//获取推荐奖金定义
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
                        
                    }else if(tSysGencode.getGroupCode().equals("REWARD_DEF")){//获取购买奖金定义
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
            loger.info("sysGenCodeService.findAll fail ! reason is "+e.getMessage());
        }
                
        if(userType.equals("1")){//普通用户
            //普通会员注册奖励计算
            if(mUserDetail.getRealStat().equals("1")){
                if(!calPtMemberRegitBouns(mUserDetail)){
                    loger.error("calPtMemberRegitBouns fail ! ");
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
                loger.info("calPtMemberRegitBouns fail ,tSysGencodeBNDMZC5 is null !");
            }else{
                rsAmnt = tSysGencodeBNDMZC5.getCodeValue();
                
            }
            BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
            bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
            tAccDetail.setAmnt(bdAmnt); 
        }catch(Exception e){
            loger.error("calPtMemberRegitBouns fail ,translate string to decimal fail!"+e.getMessage());
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
        
        String refPhone = mUserDetail.getRefPhone(); 
        String UserCode = mUserDetail.getUserCode();
        String userType = mUserDetail.getUserType();
        
        User tRefUser =  findbyPhone(refPhone, Constant.VERSION_NO);
                
         boolean tUpflag = findIsExistUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
         UserFriendship fUserFriendship= new UserFriendship();//推荐人上级关系
         boolean tSvSelShipFlag =true;           
         String tReflvl ="";
         String sefLev="";
         if(!tUpflag){//如果推荐人没有上级会员，则自己为A级会员
             tReflvl="0";
             sefLev="1";
         }
         if(tUpflag){
             fUserFriendship =  findUpFriendship(tRefUser.getUserCode(), Constant.VERSION_NO);
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
                    loger.info("calPtMemberRecmBouns fail ,tSysGencodeBNTJDM1 is null !");
                }else{
                    rsAmnt = tSysGencodeBNTJDM1.getCodeValue();
                    
                }
                BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
                bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
                tAccDetail.setAmnt(bdAmnt); 
            }catch(Exception e){
                loger.error("calPtMemberRecmBouns fail ,translate string to decimal fail!"+e.getMessage());
            }
         tAccDetail.setCaldate(DateUtil.getCurrentDate());
         tAccDetail.setCntflag("0");//未纳入计算
         tAccDetail.setStatus("1");
         tAccDetail.setCreateTime(DateUtil.getCurrentDate());
         tAccDetail.setModifyTime(DateUtil.getCurrentDate());
         tAccDetail.setOperator("sys");
         if(! accService.addAccDetail(tAccDetail,Constant.VERSION_NO)){
             loger.info("addAccDetail fail,tAccDetail is "+tAccDetail.toString());
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
                        loger.info("calPtMemberRecmBouns fail ,tSysGencodeBNTJDM2 is null !");
                    }else{
                        rsAmnt = tSysGencodeBNTJDM2.getCodeValue();
                        
                    }
                    BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
                    bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
                    tAccDetail2.setAmnt(bdAmnt);    
                }catch(Exception e){
                    loger.error("B calPtMemberRecmBouns fail ,translate string to decimal fail!"+e.getMessage());
                }
             tAccDetail2.setCaldate(DateUtil.getCurrentDate());
             tAccDetail2.setCntflag("0");
             tAccDetail2.setStatus("1");
             tAccDetail2.setCreateTime(DateUtil.getCurrentDate());
             tAccDetail2.setModifyTime(DateUtil.getCurrentDate());
             tAccDetail2.setOperator("sys");
             if(! accService.addAccDetail(tAccDetail2,Constant.VERSION_NO)){
                 loger.info("addAccDetail fail,tAccDetail2 is "+tAccDetail2.toString());
             };                  
         }
         if(sefLev.equals("3")){//总共有3层
             UserFriendship  fUserFriendship3 =  findUpFriendship(fUserFriendship.getUserCode(), Constant.VERSION_NO); 
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
                        loger.info("calPtMemberRecmBouns fail ,tSysGencodeBNTJDM3 is null !");
                    }else{
                        rsAmnt = tSysGencodeBNTJDM3.getCodeValue();
                        
                    }
                    BigDecimal bdAmnt=new BigDecimal(rsAmnt);  
                    bdAmnt.setScale(2, BigDecimal.ROUND_HALF_UP);//四舍五入
                    tAccDetail3.setAmnt(bdAmnt);    
                }catch(Exception e){
                    loger.error("C calPtMemberRecmBouns fail ,translate string to decimal fail!"+e.getMessage());
                }
             tAccDetail3.setCaldate(DateUtil.getCurrentDate());
             tAccDetail3.setCntflag("0");
             tAccDetail3.setStatus("1");
             tAccDetail3.setCreateTime(DateUtil.getCurrentDate());
             tAccDetail3.setModifyTime(DateUtil.getCurrentDate());
             tAccDetail3.setOperator("sys");
             if(! accService.addAccDetail(tAccDetail3,Constant.VERSION_NO)){
                 loger.info("addAccDetail fail,tAccDetail3 is "+tAccDetail3.toString());
             }; 
        
         }
    
    }

	
}
