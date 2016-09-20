package com.qkl.tfcc.quartz.job;


import org.springframework.scheduling.annotation.Scheduled;
/**
 * @author zhangchunming
 * @描述：解冻定时任务
 */
public class ThawJob {
	
	/**
	 * 日期：2016-9-19<br>
	 * 版本：v1.0<br>
	 * 描述：thaw 每月分钟检查一下短信，如果没有发送，就发送
	 * 创建日期：2016-9-19 下午18:27:20 <br>
	 * 创建人 zhangchunming<br>
	 */
//	@Scheduled(cron="0 1 0 * * ?")		//配置时间表达式，每天凌晨1分钟执行一次任务
	public void thaw() throws Exception{
	    
	}
}
