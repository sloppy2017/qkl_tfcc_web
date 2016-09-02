package com.qkl.tfcc.provider.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.provider.dao.UserDao;
import com.qkl.tfcc.provider.dao.UserDetailDao;
import com.qkl.tfcc.provider.dao.UserFriendshipDao;
import com.qkl.tfcc.provider.dao.UserLoginErrDao;
import com.qkl.util.help.MD5Util;

@Service
public class UserServiceImpl implements UserService {

	private Logger loger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserDetailDao userDetailDao;	
	@Autowired
	private UserFriendshipDao userFriendshipDao;
	@Autowired
	private UserLoginErrDao userLoginErrDao;
	
	@Override
	@Transactional(propagation =Propagation.SUPPORTS)
	public Map<String, Object> login(String phone, String password,
			String systemCode,String versionNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		User user = userDao.findUserByPhone(phone);
		if (null != user) {
			if (user.getPwdhash().equals(MD5Util.getMd5Code(password))) {
				if (user.getStatus() == Constant.USING) {					
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
			userFriendshipDao.insert(userFriendship);			
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


}
