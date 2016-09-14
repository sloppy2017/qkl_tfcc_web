package com.qkl.tfcc.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.po.user.User;

/**
 * 
 * @author liuzhifang
 * 
 * 权限访问控制器
 *
 */
public class AuthorizationInterceptor implements HandlerInterceptor{
	
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public String[] allowUrls;//不拦截的资源集合
    
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }  

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
	    HttpSession session = request.getSession();
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(String.format("RemoteAddr:[%s]", request.getRemoteAddr()));
		sbUrl.append(String.format(" Method:[%s]", request.getMethod()));
		sbUrl.append(" RequestURI:" + request.getRequestURI());
		sbUrl.append(" QueryString:" + request.getQueryString());

		logger.debug(sbUrl.toString());
		//如果访问路径包含允许通过的路径则放行
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");    
        if(null != allowUrls && allowUrls.length>=1){
            for(String url : allowUrls) {
                if(requestUrl.equals("/")||requestUrl.contains(url)) {    
                    return true;    
                } 
            }
        }  
        if (null == session.getAttribute(Constant.LOGIN_USER)) {
            response.sendRedirect(request.getContextPath() + "/service/user/logout");
            return false;
        }
        User user = (User)session.getAttribute(Constant.LOGIN_USER);
        if("1".equals(user.getUserType())&&(requestUrl.contains("/lp_vip")||requestUrl.contains("/invest_vip"))){//普通会员
            response.sendRedirect(request.getContextPath() + "/service/user/logout");
            return false;
        }else if("3".equals(user.getUserType())&&(requestUrl.contains("/general_vip")||requestUrl.contains("/invest_vip"))){//LP会员
            response.sendRedirect(request.getContextPath() + "/service/user/logout");
            return false; 
        }else if("4".equals(user.getUserType())&&(requestUrl.contains("/general_vip")||requestUrl.contains("/lp_vip"))){//投资公司
            response.sendRedirect(request.getContextPath() + "/service/user/logout");
            return false;
        }   
		return true;
	}
	
	@Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
        
    }
	
	@Override
    public void afterCompletion(HttpServletRequest arg0,
            HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
}
