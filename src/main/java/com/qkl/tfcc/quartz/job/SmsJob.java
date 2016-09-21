package com.qkl.tfcc.quartz.job;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.util.help.pager.PageData;
/**
 * @author zhangchunming
 * @描述：发送短信定时任务
 */
@Component
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
	@Scheduled(cron="0 0/5 * * * ?")		//配置时间表达式，每5分钟执行一次任务
	public void sendSms() throws Exception{
	    List<PageData> smsList = smsService.getSmsNoSend(null);
	    PageData pd = new PageData();
        for(PageData tpd:smsList){
            boolean result = SmsSend.sendSms(tpd.getString("ls_phone"), tpd.getString("ls_content"));
            if(result){
                pd.put("logSmsId", tpd.get("log_sms_id").toString());
                pd.put("lsIsSuccess", 1);
                pd.put("lsIsSend", 1);
                
            }else{
                pd.put("logSmsId", tpd.get("log_sms_id").toString());
                pd.put("lsIsSuccess", 0);
                pd.put("lsIsSend", 1);
            }
            smsService.updateSms(pd);
        }
	}
	public static void main(String[] args) {
        PageData pd = new PageData();
        pd.put("str", "123456.00");
        System.out.println(pd.getString("str"));
    }
}
