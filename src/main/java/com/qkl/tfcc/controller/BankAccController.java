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
import com.qkl.tfcc.api.service.acc.api.BankAccService;
import com.qkl.tfcc.api.service.sys.api.SysGenCodeService;
import com.qkl.tfcc.api.service.trade.api.TradeService;
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
	
	@RequestMapping(value="/searchSel",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findTradeInfolist(HttpServletRequest request,Page page){
		AjaxResponse ar = new AjaxResponse();
		Map<String,Object> map = new HashMap<String, Object>();
		List<PageData> tradeInfo=null;
		try {
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			 pd=this.getPageData();
			 //String parameter = request.getParameter("searchSel");
			 pd.put("userCode", user.getUserCode());
			 page.setPd(pd);
			 tradeInfo = tradeService.findTradeInfo(page,Constant.VERSION_NO);
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
			
			int tradeCount = tradeService.findTradeCount(pd, Constant.VERSION_NO);
			if (tradeCount>=5) {
				ar.setSuccess(false);
				ar.setMessage("购买次数已达上限");
				return ar;
			}else{
				String object = pd.getString("txamnt");
				BigDecimal txamnt1=new BigDecimal(object);
				double txamnt = txamnt1.doubleValue();
				if (txamnt>=1000) {
					tradeService.addTradeDetail(pd, Constant.VERSION_NO);
					ar.setSuccess(true);
					ar.setMessage("购买成功"); 
				}else{
					ar.setSuccess(false);
					ar.setMessage("购买金额低于下限");
					return ar;
				}
				if (txamnt>10000) {
					ar.setSuccess(false);
					ar.setMessage("购买金额高于上限");
					return ar;
				}else {
					tradeService.addTradeDetail(pd, Constant.VERSION_NO);
					ar.setSuccess(true);
					ar.setMessage("购买成功"); 
				}
				
			}	
			
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
	
	
	
}
