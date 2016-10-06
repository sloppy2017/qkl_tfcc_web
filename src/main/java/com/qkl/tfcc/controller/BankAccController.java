package com.qkl.tfcc.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.BankAccInfo;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.service.acc.api.BankAccService;
import com.qkl.tfcc.api.service.sms.api.SmsService;
import com.qkl.tfcc.api.service.sys.api.SysGenCodeService;
import com.qkl.tfcc.api.service.trade.api.TradeService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.sms.SmsSend;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.DateUtil;
import com.qkl.util.help.OrderGenerater;
import com.qkl.util.help.pager.PageData;


@Controller
@RequestMapping("/service/bankaccinfo")
public class BankAccController extends BaseAction {
	
	@Autowired
	private BankAccService bankAccService ;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private SysGenCodeService sysGenCodeService;
	@Autowired
	private UserService userService;
	@Autowired
	private SmsService smsService;
	
	/*@Autowired
	private  UserDetailServiceImpl userDetailServiceImpl;*/
	
	@RequestMapping(value="/info",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findBankInfo(HttpServletRequest request,HttpServletResponse response){
		AjaxResponse ar = new AjaxResponse();
		try {
			//String orgName = request.getParameter("");
			BankAccInfo bankAccInfo = bankAccService.findBankInfo(Constant.VERSION_NO);
			ar.setSuccess(true);
			ar.setMessage("查询成功");
			ar.setData(bankAccInfo);
		} catch (Exception e) {
			ar.setSuccess(false);
			ar.setMessage("查询失败");
			e.printStackTrace();
		}
	   return ar;
	}
	

	/*@RequestMapping(value="/bankaccno",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findBankaccNo(HttpServletRequest request,HttpServletResponse response){//查询购买人的支付宝账号
		try {
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			//UserDetail findUserDetailByUserCode = userDetailServiceImpl.findUserDetailByUserCode(user.getUserCode(), Constant.VERSION_NO);
			ar.setSuccess(true);
			ar.setMessage("查询成功");
			//ar.setData(findUserDetailByUserCode);
		} catch (Exception e) {
			ar.setSuccess(false);
			ar.setMessage("查询失败");
			e.printStackTrace();
		}
	   return ar;
	}*/
	
	
	@RequestMapping(value="/searchSel",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findTradeInfolist(HttpServletRequest request,Page page){
		AjaxResponse ar = new AjaxResponse();
		Map<String,Object> map = new HashMap<String, Object>();
		List<PageData> tradeInfo=null;
		try {
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			String userCode ="";
			if(user==null){
				userCode =request.getParameter("userCode");
			}else{
				userCode =user.getUserCode();
			}
			 pd=this.getPageData();
			 //String parameter = request.getParameter("searchSel");
			 pd.put("userCode", userCode);
			 page.setPd(pd);
			 tradeInfo = tradeService.findTradeInfo(page,Constant.VERSION_NO);
			 for (PageData pageData : tradeInfo) {
				 int status = Integer.parseInt( pageData.getString("status"));
				 String txamnt1 = pageData.get("txamnt").toString();
				 String txnum1 = pageData.get("txnum").toString();
				 String paytime = pageData.getString("paytime");
				 BigDecimal decimal=null;
				 BigDecimal decima2=null;
				 if (!"".equals(txamnt1)||txamnt1!=null) {
					 decimal = new BigDecimal(txamnt1);
				}else {
					  decimal = new BigDecimal(0);
				}
				 if (!"".equals(txnum1)||txnum1!=null) {
					 decima2 = new BigDecimal(txnum1);
				}else {
					  decima2 = new BigDecimal(0);
				}
				if (paytime==null) {
					pageData.put("paytime", "");
				}
				 String txnum = String .format("%.4f",decima2);
				 String txamnt = String .format("%.4f",decimal);
				pageData.put("txamnt", txamnt);
				pageData.put("txnum", txnum);
				 if (status==0) {
					 pageData.put("status", "待付款");	
				}
				 if (status==1) {
					 pageData.put("status", "已完成");
				}
				 if (status==9) {
					 pageData.put("status", "已取消");
				}
			}
			 
			 
			 map.put("tradeInfo", tradeInfo);
			 map.put("page", page);
			 ar.setData(map);
			 ar.setSuccess(true);
		     ar.setMessage("查询成功");
			 
		} catch (Exception e) {
			ar.setSuccess(false);
			ar.setMessage("查询失败");
			e.printStackTrace();
		}
		
		return ar;
	}
	

	
	@RequestMapping(value="/tradebuy",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse buyTfcc(HttpServletRequest request){//购买tfcc
		User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		
		try {
			pd=this.getPageData();
			BankAccInfo bankAccInfo = bankAccService.findBankInfo(Constant.VERSION_NO);
			pd.put("order_no", OrderGenerater.generateOrderNo());
			pd.put("revorgname",bankAccInfo.getOrgName());
			pd.put("revbankdepname", bankAccInfo.getDepositBankname());
			pd.put("revbankaccno", bankAccInfo.getBankaccno());
			//pd.put("user_code", user.getUserCode());
			pd.put("txtype", "1");
			pd.put("cuy_type", "1");
			pd.put("txdate", DateUtil.getCurrentDate());
			pd.put("cntflag", "0");
			pd.put("source_system", Constant.CUR_SYS_CODE);
			pd.put("status", "0");
			pd.put("create_time", DateUtil.getCurrentDate());
			pd.put("modify_time", DateUtil.getCurrentDate());
			
			//pd.put("userCode", user.getUserCode());
			String userCode="";
			if(user==null){
				userCode =request.getParameter("userCode");
			}else{
				userCode =user.getUserCode();
			}	
			UserDetail userDetail = userService.findUserDetailByUserCode(userCode, Constant.VERSION_NO);
			pd.put("operator", userDetail.getPhone());
			String buyFlag = userDetail.getBuyFlag();
			//String freezeFlag =userDetail.getFreezeFlag();//获取冻结标识
			/*if ("1".equals(freezeFlag)) {//判断是否为冻结的用户
*/				if ("1".equals(buyFlag)) {//判断此用户能否购买
				pd.put("userCode", userCode);
				BigDecimal findAnmt = tradeService.findAnmt(userCode, Constant.VERSION_NO);//获取数据库中此用户的交易金额数量
				String txamnt1 = pd.getString("txamnt");//获取再次购买的金额
				BigDecimal txamnt2=new BigDecimal(txamnt1);
				if(findAnmt==null){
					findAnmt = new BigDecimal(0);
				}
				//double value = findAnmt.add(txamnt2).doubleValue();//计算再次购买和数据库中的交易金额之和
					//if (value<=50000.00) {
						boolean addTradeDetail = tradeService.addTradeDetail(pd, Constant.VERSION_NO);
						if (addTradeDetail) {
							
							int num = smsService.getBlackPhone(userDetail.getPhone());
							if (num==0) {
								String content = "尊敬的【"+userDetail.getPhone()+"】会员，您提交购买【"+txamnt2+"】三界宝数字资产订单提交成功，请在24小时内付款，否则您的订单将会自动取消。如有疑问请联系在线客服，祝您生活愉快！";
								SmsSend.sendSms(userDetail.getPhone(), content);
							}else {
								logger.debug("此人已进入短信黑名单");
							//	System.out.println("此人已进入短信黑名单");
							}
							ar.setSuccess(true);
							ar.setMessage("订单已生成，请及时付款");
						}else {
							ar.setSuccess(false);
							ar.setMessage("网络异常，购买失败！");
						}
					//}else {
						//ar.setSuccess(false);
						//ar.setMessage("购买金额已经超过规定额度");
					//}
					
				}else {
					ar.setSuccess(false);
					ar.setMessage("您没有购买资格");
				}
										
			/*}else {
				ar.setSuccess(false);
				ar.setMessage("您没有购买资格");	
			}*/
			
			
			
		} catch (Exception e) {
			ar.setSuccess(false);
			ar.setMessage("购买失败");
			e.printStackTrace();
		}
		
		return ar;	
	}
	
	
	@RequestMapping(value="/PayMoney",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse requirePayMoney(HttpServletRequest request){//获取购买金额
		//User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		try {
			pd=this.getPageData();
			String txnums = pd.getString("txnum");
			
			String tmpprice ="";
			 List<Map<String,Object>> tSysGencodeList =sysGenCodeService.findByGroupCode("PRICE", Constant.VERSION_NO);
			 for(Map<String,Object> mapObj:tSysGencodeList){
			     if("PRICE".equals(mapObj.get("codeName"))){
			    	 tmpprice = mapObj.get("codeValue").toString();
			     }
			  
			 }
			BigDecimal tmpprices=new BigDecimal(tmpprice);//每股单价
			
			BigDecimal txnum=null;
			if(txnums!=null&&!"".equals(txnums)&&txnums.length()>0){
				 txnum=new BigDecimal(txnums);//购买数量
			}else{
				ar.setMessage("请选择购买数量");
				return ar;
			}
			BigDecimal multiply = tmpprices.multiply(txnum);//计算应付金额
			String txamnt = String .format("%.2f",multiply);
			
			
			ar.setSuccess(true);
			ar.setMessage("获取购买金额成功");
			ar.setData(txamnt);
		} catch (Exception e) {
			ar.setSuccess(false);
			ar.setMessage("获取购买金额失败");
			e.printStackTrace();
		}
		return ar;
	}
	
	@RequestMapping(value="/getPayList",method=RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getPayList(HttpServletRequest request,HttpServletResponse response){
        AjaxResponse ar = new AjaxResponse();
        List<Map<String,String>> resList = new ArrayList<Map<String,String>>();
        try {
            List<Map<String,Object>> payList = sysGenCodeService.findByGroupCode("PAY_TYPE", Constant.VERSION_NO);
            for(Map<String,Object> payMap:payList){
                Map<String,String> map = new  HashMap<String,String>();
                map.put("name", payMap.get("description").toString());
                map.put("value", payMap.get("codeValue").toString());
                resList.add(map);
            }
            ar.setSuccess(true);
            ar.setMessage("查询成功");
            ar.setData(resList);
        } catch (Exception e) {
            ar.setSuccess(false);
            ar.setMessage("查询失败");
            e.printStackTrace();
        }
       return ar;
    }
    
}
