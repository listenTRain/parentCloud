package com.java.admin.controller;

import com.java.admin.util.MD5;
import com.java.admin.util.VerifyCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.java.admin.model.AdminUsersEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author djin
 *   AdminUsers控制器
 * @date 2020-03-07 10:04:53
 */
@Controller
@RequestMapping("/adminusers")
public class AdminUsersController extends BaseController<AdminUsersEntity>{

	//获取用户登陆时的验证码
	@RequestMapping("/getVerifyCode")
	public void getVerifyCode(HttpSession session, HttpServletResponse response) throws Exception{
		//1.通过工具类产生随机验证码  3DcfG
		String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
		//2.将服务器端产生的随机验证码中的英文字母转为小写并放在session容器中   3dcfg
		System.out.println("服务器端产生的随机验证码:"+ verifyCode);
		session.setAttribute("verifyCode",verifyCode.toLowerCase());
		//3.将产生的验证码转为图片显示（响应）到页面中   3DcfG
		VerifyCodeUtils.outputImage(250,35,response.getOutputStream(),verifyCode);
	}

	//验证用户输入的验证码
	@RequestMapping("/checkVerifyCode")
	public @ResponseBody
	String checkVerifyCode(HttpSession session, String verifyCodeIpt){
		//1.从session容器中取出之前装入的验证码
		String verifyCode = (String)session.getAttribute("verifyCode");
		//2.此时将用户输入的验证码与session中取出的验证码进行比较

		System.out.println("sssssssssssssssss");
		System.out.println("前台接受的：" + verifyCodeIpt);
		System.out.println("系统产生的：" + verifyCode);
		if(verifyCodeIpt.equals(verifyCode)){
			System.out.println("aaaaaaaaaaaaa");
			return "success";
		}else {
			return "fail";
		}
	}

	//执行登陆
	@RequestMapping("/login")
	public @ResponseBody String login(AdminUsersEntity user,HttpSession session){
		//将用户输入的登陆密码进行加密
		user.setPwd(MD5.md5crypt(user.getPwd()));
		try {
			//执行登陆
			AdminUsersEntity loginUser = baseService.findObjectByPramas(user);
			//判断登陆是否成功
			if(loginUser!=null){ //有此用户，登陆成功
				session.setAttribute("loginUser",loginUser);  //将登陆的用户对象装入到session容器中
				return "success";
			}else {
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	//执行退出
	@RequestMapping("/exitUser")
	public @ResponseBody String exitUser(HttpSession session){
		try {
			//移除session容器中的用户登陆对象
			session.removeAttribute("loginUser");
			System.out.println("执行退出");
			return "success";
		}catch (Exception e){
			return "error";
		}

	}
}
