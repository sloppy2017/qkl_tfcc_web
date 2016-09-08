package com.qkl.tfcc.controller.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.qkl.tfcc.api.po.acc.LPTeam;
import com.qkl.tfcc.api.service.user.api.LPTeamService;
import com.qkl.tfcc.web.BaseAction;
import com.qkl.util.help.AjaxResponse;


@Controller
@RequestMapping("/service/lpteam")
public class LPTeamController extends BaseAction {
	
	@Autowired
	private LPTeamService service;
	
	@RequestMapping(value="/getcount",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse findlpc(HttpServletRequest request){
		long count=0;
		try {
			String userCode=request.getParameter("userCode");
			count = service.findLPcount(userCode);
			ar.setSuccess(true);
			ar.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
			ar.setMessage("查询失败");
		}
		ar.setData(count);
		return ar;
	}
	
	
	@RequestMapping(value="/getlist",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse find(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		List<LPTeam> find=null;
		try {
			String phone = request.getParameter("phone");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			map.put("phone", phone);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
		    find = service.find(map);
		    ar.setSuccess(true);
		    ar.setMessage("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			ar.setSuccess(false);
		    ar.setMessage("查询失败");
		}
		ar.setData(find);
		return ar;
	}

}
