package com.qkl.tfcc.controller.acc;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qkl.tfcc.api.common.Constant;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.ComAccMy;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.service.acc.api.ComAccMyService;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;
import com.qkl.util.help.pager.PageData;




@Controller
@RequestMapping("/service/comacc")
public class ComAccMyController extends BaseAction {

	@Autowired
	private ComAccMyService cams;
	
	@RequestMapping(value="/ftb",method=RequestMethod.POST)
	@ResponseBody
	public  AjaxResponse findTB(HttpServletRequest request){
		
		AjaxResponse ar = new AjaxResponse();
		ComAccMy findTB=null;
		try {
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			findTB = cams.findTB(user.getUserCode());
			ar.setSuccess(true);
			ar.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("查询失败");
		}
		ar.setData(findTB);
		return ar;	
	}

	@RequestMapping(value="/fjb",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findJB(HttpServletRequest request){
		AjaxResponse ar = new AjaxResponse();
			ComAccMy fingJB=null;
			try {
				User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
				fingJB = cams.findJB(user.getUserCode());
				ar.setSuccess(true);
				ar.setMessage("查询成功");
				
			} catch (Exception e) {
				e.printStackTrace();
				ar.setSuccess(false);
				ar.setMessage("查询失败");
			}
			ar.setData(fingJB);
		   return ar;
	}
	
	@RequestMapping(value="/fall",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findAll(HttpServletRequest request,Page page){
		AjaxResponse ar = new AjaxResponse();
		List<PageData> findAll=null;
		try {
			
			
			findAll = cams.findAll(page);
			ar.setSuccess(true);
			ar.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("查询失败");
		}
		ar.setData(findAll);
		return ar;
	}
	
	
	@RequestMapping(value="/all",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse find(HttpServletRequest request){
		AjaxResponse ar = new AjaxResponse();
		try {
			pd=this.getPageData();
			User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
			ComAccMy c1 = cams.findReward(user.getUserCode());
			ComAccMy c2 = cams.findWReward(user.getUserCode());
			ComAccMy c3 = cams.findTTReward(user.getUserCode());
			pd.put("c1", c1);
			pd.put("c2", c2);
			pd.put("c3", c3);
			ar.setSuccess(true);
			ar.setMessage("查询成功");
			 ar.setData(pd);
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("查询失败");
		}
       
		return ar;
	}
}
