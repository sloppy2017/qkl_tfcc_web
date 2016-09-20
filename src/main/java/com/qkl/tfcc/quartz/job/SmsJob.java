package com.qkl.tfcc.quartz.job;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.util.help.pager.PageData;
/**
 * @author zhangchunming
 * @描述：发送短信定时任务
 */
public class SmsJob {
	@Autowired
	private SmsService smsService;
	/**
	 * 日期：2016-9-19<br>
	 * 版本：v1.0<br>
	 * 描述：sendSms 每10分钟检查一下短信，如果没有发送，就发送
	 * 创建日期：2016-9-19 下午18:27:20 <br>
	 * 创建人 zhangchunming<br>
	 */
	@Scheduled(cron="0 0/1 * * * ?")		//配置时间表达式，每3分钟执行一次任务
	public void sendSms() throws Exception{
	    List<PageData> smsList = smsService.getSmsNoSend(null);
	    PageData pd = new PageData();
        for(PageData tpd:smsList){
            boolean result = SmsSend.sendSms(tpd.getString("ls_phone"), tpd.getString("ls_content"));
            if(result){
                pd.put("logSmsId", tpd.getString("log_sms_id"));
                pd.put("lsIsSuccess", 1);
                pd.put("lsIsSend", 1);
                smsService.updateSms(pd);
            }
        }
	}
}
