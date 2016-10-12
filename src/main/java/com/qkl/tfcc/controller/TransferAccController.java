package com.qkl.tfcc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.common.CodeConstant;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.service.acc.api.AccOutdetailService;
import com.qkl.tfcc.api.service.sys.api.SysGenCodeService;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.APIHttpClient;
import com.qkl.util.help.AjaxResponse;
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
@RequestMapping("/transfer")
public class TransferAccController extends BaseAction{
    
    private final Logger logger = LoggerFactory.getLogger(TransferAccController.class);
    
    @Autowired
    private SysGenCodeService sysGenCodeService;
    @Autowired
    private AccOutdetailService accOutdetailService;
    
    /**
     * @describe:转账回调接口
     * @author: zhangchunming
     * @date: 2016年10月10日上午10:11:41
     * @param request
     * @param response
     * @return: AjaxResponse
     */
    @RequestMapping(value="/callback", method=RequestMethod.POST)
    @ResponseBody
    public AjaxResponse callback(HttpServletRequest request,HttpServletResponse response){
        logBefore(logger,"TransferAccController.callback()");
        try {
            pd = this.getPageData();
            if(pd.get("orderId")==null||pd.get("status")==null||pd.get("value")==null||pd.get("ts")==null||pd.get("sign")==null){
                ar.setErrorCode(CodeConstant.PARAM_ERROR);
                ar.setMessage("传递参数有误");
                ar.setSuccess(false);
                logger.info("---------转账回调----------传递参数有误：orderId="+pd.get("orderId")+"---status="+pd.get("status")+"--ts="+pd.get("ts")+"--sign="+pd.get("sign"));
                return ar;
            }
            String pri = null;
            List<Map<String, Object>> codeList =  sysGenCodeService.findByGroupCode("DIGITAL_SIGN", Constant.VERSION_NO);
            for(Map<String, Object> codeMap:codeList){
                if("PRI".equals(codeMap.get("codeName"))){
                    pri = codeMap.get("codeValue").toString();
                }
            }
            //签名认证
            boolean signResult = APIHttpClient.validSign(pd.getString("orderId"), pd.getString("status"), pd.getString("value"), pd.getString("ts"), pd.getString("sign"), pri);
            if(signResult){//签名认证成功
                boolean transferResult = accOutdetailService.transferCallBack(pd, Constant.VERSION_NO);
                if(transferResult){
                    ar.setMessage("转账成功");
                    ar.setSuccess(true);
                    logger.info("转账回调------转账成功success---------");
                    return ar;
                }else{
                    ar.setMessage("转账失败");
                    ar.setSuccess(false);
                    logger.info("转账回调------转账失败fail---------");
                    return ar;
                }
            }else{
                ar.setSuccess(false);
                ar.setErrorCode(CodeConstant.SIGN_ERROR);
                ar.setMessage("签名认证失败");
                logger.info("转账回调------签名认证失败fail---------");
                return ar; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            ar.setSuccess(false);
            ar.setErrorCode(CodeConstant.SYS_ERROR);
            ar.setMessage("系统异常");
            logger.info("转账回调------系统异常---------");
        }finally{
            logAfter(logger);
        }   
        return ar;
    }
}
