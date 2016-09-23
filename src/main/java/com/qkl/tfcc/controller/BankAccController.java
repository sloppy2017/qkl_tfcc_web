package com.qkl.tfcc.controller;

import java.math.BigDecimal;
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
import com.qkl.tfcc.api.service.sys.api.SysGenCodeService;
import com.qkl.tfcc.api.service.trade.api.TradeService;
import com.qkl.tfcc.provider.user.service.impl.UserDetailServiceImpl;
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
				 String string2 = pageData.get("txamnt").toString();
				 BigDecimal decimal = new BigDecimal(string2);
				 String format = String .format("%.4f",decimal);
				//double txamnt = decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				//System.out.println(txamnt+"=============");
				pageData.put("txamnt", format);
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
			pd.put("user_code", user.getUserCode());
			pd.put("txtype", "1");
			pd.put("cuy_type", "1");
			pd.put("txdate", DateUtil.getCurrentDate());
			pd.put("cntflag", "0");
			pd.put("source_system", Constant.CUR_SYS_CODE);
			pd.put("status", "0");
			pd.put("create_time", DateUtil.getCurrentDate());
			pd.put("modify_time", DateUtil.getCurrentDate());
			pd.put("operator", user.getPhone());
			pd.put("userCode", user.getUserCode());
			
			String txamnt1 = pd.getString("txamnt");
			if (txamnt1!=null&&!"".equals(txamnt1)) {
					BigDecimal txamnt2=new BigDecimal(txamnt1);
					double txamnt = txamnt2.doubleValue();
					if (txamnt>=1000.00&&txamnt<=10000.00) {
						int tradeCount = tradeService.findTradeCount(pd, Constant.VERSION_NO);
							if (tradeCount<5) {
								boolean addTradeDetail = tradeService.addTradeDetail(pd, Constant.VERSION_NO);
								if (addTradeDetail) {
									ar.setSuccess(true);
									ar.setMessage("订单已生成，请及时付款");
								}else {
									ar.setSuccess(false);
									ar.setMessage("网络异常，购买失败！");
								}
								
							}else{
								ar.setSuccess(false);
								ar.setMessage("购买次数已达5次");
								return ar;		
							}
						
						}else {
							if(txamnt<1000.00){
								ar.setMessage("单次购买金额不得低于1000.00元");	
							}
							if (txamnt>10000.00) {
								ar.setMessage("单次购买金额不得高于10000.00元");
							}
							ar.setSuccess(false);
							return ar;
						}
				
			}else{
				ar.setSuccess(false);
				ar.setMessage("请选择购买数量");
				return ar;
			}
			
			
			
			
			/*int tradeCount = tradeService.findTradeCount(pd, Constant.VERSION_NO);
			if (tradeCount>=5) {
				ar.setSuccess(false);
				ar.setMessage("购买次数已达5次");
				return ar;
			}else{
				String txamnt1 = pd.getString("txamnt");
				
				if(txamnt1!=null&&!"".equals(txamnt1)&&txamnt1.length()>0){
					BigDecimal txamnt2=new BigDecimal(txamnt1);
					double txamnt = txamnt2.doubleValue();
					if (txamnt>=1000) {
						tradeService.addTradeDetail(pd, Constant.VERSION_NO);
						ar.setSuccess(true);
						ar.setMessage("恭喜您，购买成功！"); 
					}else{
						ar.setSuccess(false);
						ar.setMessage("单次购买金额不得低于1000.00元");
						return ar;
					}
					if (txamnt>10000) {
						ar.setSuccess(false);
						ar.setMessage("单次购买金额不得高于10000.00元");
						return ar;
					}
					
				}else{
					ar.setMessage("请选择购买数量");
					return ar;
				}
				
				
				
				
				
			}	*/
			
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
			double doubleValue = tmpprices.multiply(txnum).doubleValue();//计算应付金额
			String txamnt = String .format("%.2f",doubleValue);
			
			
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
	
	/*public static void main(String[] args) {
		String str = "152.00";
		PageData pdData = new PageData();
		pdData.put("str", str);
		System.out.println(pdData.getString("str"));
		BigDecimal bigDecimal = new BigDecimal(pdData.getString("str"));
		System.out.println(bigDecimal);
	}*/
	
}
