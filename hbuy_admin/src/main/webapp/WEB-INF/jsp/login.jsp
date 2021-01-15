<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	//localhost:8081/
String path = request.getContextPath();
String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<link rel="stylesheet" href="lib/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="css/back/login.css" />
</head>

<body >
	<div class="qiqiu1 qiqiu">
		<img src="http://q6eehjkei.bkt.clouddn.com/q2.png" />
		<div class="text">韦氏集团</div>
	</div>
	<div class="qiqiu2 qiqiu">
		<img src="http://q6eehjkei.bkt.clouddn.com/q3.png" />
		<div class="text">韦氏集团</div>
	</div>
	<div class="qiqiu3 qiqiu">
		<img src="http://q6eehjkei.bkt.clouddn.com/q4.png" />
		<div class="text">韦氏集团</div>
	</div>
	<div class="qiqiu4 qiqiu">
		<img src="http://q6eehjkei.bkt.clouddn.com/q5.png" />
		<div class="text">韦氏集团</div>
	</div>
	<div class="qiqiu5 qiqiu">
		<img src="http://q6eehjkei.bkt.clouddn.com/q6.png" />
		<div class="text">韦氏集团</div>
	</div>
	
	<div class="qiqiu6 qiqiu">
		<img src="http://q6eehjkei.bkt.clouddn.com/q2.png" />
		<div class="text">韦氏集团</div>
	</div>


	<div class="login" style="height: 300px;">
		<h1>用户后台登录</h1>
		<!--拦截后的提示-->
		<input type="hidden" id="loginMsg" value="${loginMsg }">
		<form class="layui-form" action="" lay-filter="example">
			<div class="layui-form-item">
				<input id="userName" class="layui-input" name="username" placeholder="用户名"
					lay-verify="required|userName" type="text" autocomplete="off">
			</div>
			<div class="layui-form-item">
				<input id="pwd" class="layui-input" name="pwd" placeholder="密码"
					lay-verify="required|pwd" type="password" autocomplete="off">
			</div>
			<div class="layui-form-item">
			    <input id="yzm" class="layui-input" placeholder="验证码(不区分大小写)"
				  type="text" autocomplete="off" style="margin-bottom: 10px;">
				<p align="center"><img src="adminusers/getVerifyCode" onclick=flushCheckCode(this) alt="点击刷新验证码" style="cursor: hand" /></p>
		    </div>
			<button class="layui-btn login_btn" lay-submit="" lay-filter="login">登录</button>
		</form>
	</div>
	
	<script type="text/javascript">
		function flushCheckCode(obj) {
		    //拼接当前时间使每一次的点击访问路径不一样，让其路径改变方可再次访问
			obj.src = (obj.src + '?' + new Date())
		}
	</script>

<script src="lib/layui/layui.js"></script>
<script src="js/login.js"></script>
</body>
</html>
