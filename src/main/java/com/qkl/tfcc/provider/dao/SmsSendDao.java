package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.user.Sendsms;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface SmsSendDao extends DAO<Sendsms> {

	
	/** 查看是否存在该手机号
	 * @return findPhoneIsExist  查看是否存在该手机号
	 * @create author kezhiyi
	 * @create date 2016年8月19日
	 */ 
	public int findPhoneIsExist(String phone);
	/** 根据userCode查用户短信发送次数信息
	 * @return findUserByUserCode  根据userCode查用户短信发送次数信息
	 * @create author kezhiyi
	 * @create date 2016年8月17日
	 */ 
	public Sendsms findByUserCode(String userCode);
	/** 根据phone查用户短信发送次数信息
	 * @return findUserByUserCode  根据userCode查用户短信发送次数信息
	 * @create author kezhiyi
	 * @create date 2016年8月17日
	 */ 
	public int findByPhone(String phone,long second);
	
	/** 添加用户短信发送次数信息
	 * @return addSmsSend 用户短信发送次数信息
	 * @create author kezhiyi
	 * @create date 2016年8月17日
	 */ 
	public void addSmsSend(Sendsms sendsms);
	/** 编辑用户短信发送次数信息
	 * @return addSmsSend编辑短信发送次数信息
	 * @create author kezhiyi
	 * @create date 2016年8月17日
	 */ 
	public void modifySmsSend(Sendsms sendsms);
	
	
	
}
