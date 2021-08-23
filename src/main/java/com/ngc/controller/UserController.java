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
			result.put("message", "�û�������Ϊ��");
			result.put("result", false);
			return result;
		}
		List<User> users = this.userService.queryUserByPar(
				new User().setUserName(username));
		if(users.isEmpty()){
			result.put("result", true);
		}else{
			result.put("message", "�û����Ѵ���");
			result.put("result", false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/unsalfGetCheckNumber")
	public Object getCheckNumber(HttpSession session){
		Map<String,Object> result = new HashMap<String,Object>();
		String checkNumber = UUID.randomUUID().toString().split("-")[0].toUpperCase();
		
		System.out.println("���Ͷ�����֤�룺"+checkNumber);//������
		session.setAttribute("checkNumber", checkNumber);//��ʱ�ȷ��ţ�Ҫת�Ƶ�����
		
		return result;
	}
	
	@RequestMapping("/unsalfReg")
	public String reg(HttpSession session,User user,String checkNumber,ModelMap map){
		String sessionCheckNumber = (String) session.getAttribute("checkNumber");
		//��֤����֤
		if(!checkNumber.equals(sessionCheckNumber)){
			map.put("message", "�ֻ���֤�����");
			map.put("user", user);
			return "reg";
		}
		//������Ϣ��֤
		if(StringUtils.isEmpty(user.getUserName())
				|| StringUtils.isEmpty(user.getPassWord())){
			map.put("message", "�û����������ʽ����");
			map.put("user", user);
			return "reg";
		}
		if(StringUtils.isEmpty(user.getPhoneNumber())){
			map.put("message", "�ֻ������ʽ����");
			map.put("user", user);
			return "reg";
		}
		
		String APPID = new Date().getTime() + "" + user.getUserName();
		int i = this.userService.insertUser(user.setAppID(APPID));
		if(i != 1){
			map.put("message", "ע��ʧ�ܣ�����ϵ����Ա");
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
								.setExplain(user.getUserName() + ":ע����:"+us.get(0).getCreateTime()));
		}
		
		return "login";
	}
	
	@RequestMapping("/unsalfLogin")
	public String login(HttpSession session,User user ,ModelMap map){
		if(StringUtils.isEmpty(user.getUserName()) || 
				StringUtils.isEmpty(user.getPassWord())){
			map.put("message", "�û��������벻��Ϊ��");
			return "login";
		}
		
		List<User> us = userService.queryUserByPar(user);
		if(us.size() != 1){
			us = userService.queryUserByPar(
					user.setPhoneNumber(user.getUserName())
						.setUserName(null)
						);
			if(us.size() != 1){
				map.put("message", "�û������������");
				utilService.insertUserTrajectory(
						new UserTrajectory()
							.setAppID(us.get(0).getAppID())
								.setTrajectoryType(2)
									.setExplain(user.getUserName() + ":����[ʧ��]��:"+us.get(0).getCreateTime()));
				return "login";
			}
		}
		
		session.setAttribute(us.get(0).getAppID(), us.get(0));//��ʱ�ȷ��ţ�Ҫת�Ƶ�����
		map.put("appID", us.get(0).getAppID());//�Ӵ˲��벻��~��������ȡ�û����������Ψһƾ��
		utilService.insertUserTrajectory(
				new UserTrajectory()
					.setAppID(us.get(0).getAppID())
						.setTrajectoryType(2)
							.setExplain(user.getUserName() + ":������:"+us.get(0).getCreateTime()));
		return "index";
	}
}
