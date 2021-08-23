package com.ngc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ngc.entity.User;
import com.ngc.entity.UserTrajectory;
import com.ngc.service.UserService;
import com.ngc.service.UtilService;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	private UtilService utilService;
	
	@ResponseBody
	@RequestMapping("/unsalfCheckUserName")
	public Object checkUserName(String username){
		Map<String,Object> result = new HashMap<String,Object>();
		if(StringUtils.isEmpty(username)){
			result.put("message", "用户名不可为空");
			result.put("result", false);
			return result;
		}
		List<User> users = this.userService.queryUserByPar(
				new User().setUserName(username));
		if(users.isEmpty()){
			result.put("result", true);
		}else{
			result.put("message", "用户名已存在");
			result.put("result", false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/unsalfGetCheckNumber")
	public Object getCheckNumber(HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		String checkNumber = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		
		System.out.println("发送短信验证码："+checkNumber);//发短信
		session.setAttribute("checkNumber", checkNumber);//暂时先放着，要转移到缓存
		
		return result;
	}
	
	@RequestMapping("/unsalfReg")
	public String reg(HttpSession session,User user,String checkNumber,ModelMap map){
		String sessionCheckNumber = (String) session.getAttribute("checkNumber");
		//验证码验证
		if(!checkNumber.equals(sessionCheckNumber)){
			map.put("message", "手机验证码错误");
			map.put("user", user);
			return "reg";
		}
		//敏感信息验证
		if(StringUtils.isEmpty(user.getUserName())
				|| StringUtils.isEmpty(user.getPassWord())){
			map.put("message", "用户名和密码格式错误");
			map.put("user", user);
			return "reg";
		}
		if(StringUtils.isEmpty(user.getPhoneNumber())){
			map.put("message", "手机号码格式错误");
			map.put("user", user);
			return "reg";
		}
		
		String APPID = new Date().getTime() + "" + user.getUserName();
		int i = this.userService.insertUser(user.setAppID(APPID));
		if(i != 1){
			map.put("message", "注册失败，请联系管理员");
			map.put("user", user);
			return "reg";
		}else{
			List<User> us = this.userService.queryUserByPar(new User()
					.setUserName(user.getUserName())
						.setPassWord(user.getPassWord()));
			APPID = us.get(0).getAppID();
			utilService.insertUserTrajectory(
					new UserTrajectory()
						.setAppID(APPID)
							.setTrajectoryType(1)
								.setExplain(user.getUserName() + ":注册于:"+us.get(0).getCreateTime()));
		}
		
		return "login";
	}
	
	@RequestMapping("/unsalfLogin")
	public String login(HttpSession session,User user ,ModelMap map){
		if(StringUtils.isEmpty(user.getUserName()) || 
				StringUtils.isEmpty(user.getPassWord())){
			map.put("message", "用户名或密码不可为空");
			return "login";
		}
		
		List<User> us = userService.queryUserByPar(user);
		if(us.size() != 1){
			us = userService.queryUserByPar(
					user.setPhoneNumber(user.getUserName())
						.setUserName(null)
						);
			if(us.size() != 1){
				map.put("message", "用户名或密码错误");
				utilService.insertUserTrajectory(
						new UserTrajectory()
							.setAppID(us.get(0).getAppID())
								.setTrajectoryType(2)
									.setExplain(user.getUserName() + ":登入[失败]于:"+us.get(0).getCreateTime()));
				return "login";
			}
		}
		
		session.setAttribute(us.get(0).getAppID(), us.get(0));//暂时先放着，要转移到缓存
		map.put("appID", us.get(0).getAppID());//从此不离不弃~！！！获取用户独立缓存的唯一凭据
		utilService.insertUserTrajectory(
				new UserTrajectory()
					.setAppID(us.get(0).getAppID())
						.setTrajectoryType(2)
							.setExplain(user.getUserName() + ":登入于:"+us.get(0).getCreateTime()));
		return "index";
	}
}
