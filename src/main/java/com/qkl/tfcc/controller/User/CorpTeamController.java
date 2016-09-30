package com.qkl.tfcc.controller.User;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.service.user.api.CorpTeamService;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.pager.PageData;


@Controller
@RequestMapping("/service/corp")
public class CorpTeamController extends BaseAction {
	
	@Autowired
	private CorpTeamService corpTeamService;
	
	
	@RequestMapping(value="/lpnum",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findLpNum(HttpServletRequest request){
		AjaxResponse ar = new AjaxResponse();
		long lpNum=0;
		try {
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			String userCode="";
            if(user==null){
                userCode =request.getParameter("userCode");
            }else{
                userCode =user.getUserCode();
            }
			lpNum = corpTeamService.findNum(userCode);
			ar.setSuccess(true);
			ar.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("查询失败");
		}
		ar.setData(lpNum);
		return ar;
	}
	
	@RequestMapping(value="/lpInfo",method=RequestMethod.POST)
//	@RequestMapping(value="/lpInfo",method=RequestMethod.GET)
	@ResponseBody
	public AjaxResponse findLplist(HttpServletRequest request,Page page){
		AjaxResponse ar = new AjaxResponse();
		User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		String userCode="";
        if(user==null){
            userCode =request.getParameter("userCode");
        }else{
            userCode =user.getUserCode();
        }
		//String userCode="10000000001";
		pd = this.getPageData();
		pd.put("userCode", userCode);
		//pd.put("userCode", userCode);
		page.setPd(pd);
		List<PageData> list = corpTeamService.findlplist(page);
		ar.setData(list);
		return ar;
	}
	

}
