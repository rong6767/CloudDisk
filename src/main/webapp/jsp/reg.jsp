<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link rel="stylesheet" type="text/css" href="css/normalize.css" />
		<%--		<link rel="stylesheet" type="text/css" href="css/htmleaf-demo.css">--%>

		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
		<style type="text/css">
			.login-page {
				width: 360px;
				padding: 8% 0 0;
				margin: auto;
			}
			.form {
				position: relative;
				z-index: 1;
				background: #FFFFFF;
				max-width: 360px;
				margin: 0 auto 100px;
				padding: 45px;
				text-align: center;
				box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
			}
			.form input {
				font-family: "Roboto", sans-serif;
				outline: 0;
				background: #f2f2f2;
				/*background: black;*/
				width: 100%;
				border: 0;
				margin: 0 0 15px;
				padding: 15px;
				box-sizing: border-box;
				font-size: 14px;
			}
			#checkNumber {
				width: 50%;
				margin: 0 0 0;
			}
			#getCheckNumber{
				width: 50%;
			}
			.form button {
				font-family: "Microsoft YaHei","Roboto", sans-serif;
				text-transform: uppercase;
				outline: 0;
				background: #4CAF50;
				width: 100%;
				border: 0;
				padding: 15px;
				color: #FFFFFF;
				font-size: 14px;
				-webkit-transition: all 0.3s ease;
				transition: all 0.3s ease;
				cursor: pointer;
			}
			.form button:hover,.form button:active,.form button:focus {
				background: #43A047;
			}
			.form .message {
				margin: 15px 0 0;
				color: #b3b3b3;
				font-size: 12px;
			}
			.form .message a {
				color: #4CAF50;
				text-decoration: none;
			}
			.container {
				position: relative;
				z-index: 1;
				max-width: 300px;
				margin: 0 auto;
			}
			.container:before, .container:after {
				content: "";
				display: block;
				clear: both;
			}
			.container .info {
				margin: 50px auto;
				text-align: center;
			}
			.container .info h1 {
				margin: 0 0 15px;
				padding: 0;
				font-size: 36px;
				font-weight: 300;
				color: #1a1a1a;
			}
			.container .info span {
				color: #4d4d4d;
				font-size: 12px;
			}
			.container .info span a {
				color: #000000;
				text-decoration: none;
			}
			.container .info span .fa {
				color: #EF3B3A;
			}
			body {
				background: #76b852; /* fallback for old browsers */
				background: -webkit-linear-gradient(right, #76b852, #8DC26F);
				background: -moz-linear-gradient(right, #76b852, #8DC26F);
				background: -o-linear-gradient(right, #76b852, #8DC26F);
				background: linear-gradient(to left, #76b852, #8DC26F);
				font-family: "Roboto", sans-serif;
				-webkit-font-smoothing: antialiased;
				-moz-osx-font-smoothing: grayscale;
			}
			.shake_effect{
				-webkit-animation-name: shake;
				animation-name: shake;
				-webkit-animation-duration: 1s;
				animation-duration: 1s;
			}
			@-webkit-keyframes shake {
				from, to {
					-webkit-transform: translate3d(0, 0, 0);
					transform: translate3d(0, 0, 0);
				}

				10%, 30%, 50%, 70%, 90% {
					-webkit-transform: translate3d(-10px, 0, 0);
					transform: translate3d(-10px, 0, 0);
				}

				20%, 40%, 60%, 80% {
					-webkit-transform: translate3d(10px, 0, 0);
					transform: translate3d(10px, 0, 0);
				}
			}

			@keyframes shake {
				from, to {
					-webkit-transform: translate3d(0, 0, 0);
					transform: translate3d(0, 0, 0);
				}

				10%, 30%, 50%, 70%, 90% {
					-webkit-transform: translate3d(-10px, 0, 0);
					transform: translate3d(-10px, 0, 0);
				}

				20%, 40%, 60%, 80% {
					-webkit-transform: translate3d(10px, 0, 0);
					transform: translate3d(10px, 0, 0);
				}
			}
			p.center{
				color: #fff;font-family: "Microsoft YaHei";
			}
		</style>
		<script type="text/javascript" src="js/jquery-1.8.3.min.js" ></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="js/reg.js" ></script>
		<script type="text/javascript">
			$(function(){
				var message = "${message}";
				if(message!=""){
					alert(message)
				}
			})
		</script>
	</head>
	<body>
		<div class="body">
			<div id="wrapper" class="login-page">
				<div id="login_form" class="form">
					<form class="register-form" action="user/unsalfReg.do" method="post">
						<input type="text" placeholder="用户名" id="r_user_name" class="" name="userName" value="${user.userName}"/>
						<input type="hidden" id="usernametemp"
							   checkresult="0"/>
						<input type="password" placeholder="密码" id="r_password" data="" name="passWord"/>
						<input type="password" placeholder="重复密码" id="repassword" data="" />
						<input placeholder="手机号"
							   id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}"/>
						<div class="inputDiv checkNumber btn-group">
							<input class="checkNumberInput" placeholder="验证码"
								   name="checkNumber" id="checkNumber" value=""/>
							<button type="button" id="getCheckNumber" class="checkNumberButton">获取短信验证码<span></span></button>
						</div>
						<div style="display: flex; justify-content: flex-start;">
							<input id="agree" type="checkbox" style="width: 20px !important;"/>
							<label for="agree">
								阅读并接受《用户协议》及《用户隐私权保护声明》
							</label>
						</div>
						<button id="create" type="submit">创建账户</button>
						<p class="message">已经有了一个账户? <a href="jsp/login.jsp">立刻登录</a></p>
					</form>
			<%--<div class="login">
				<!-- 
					验证：1.前端验证（不安全的，是可以规避的，也是必须的）
						2.服务端验证（安全的，针对敏感信息是必须的）
						3.绝对不可以在前端做数据验算
						4.验重（登入唯一凭据）
				 -->
				<form class="regForm" action="user/unsalfReg.do" method="post">
					<div class="title">
						用户注册
					</div>
					<div class="inputDiv">
						<input placeholder="用户名[6-18位字母开头，无特殊符号的字符串]" 
							id="username" class="" name="userName" value="${user.userName}"/>
						<input type="hidden" id="usernametemp" 
							checkresult="0"/>
					</div>
					<div class="inputDiv" >
						<input placeholder="密码[6-18位字母开头，无特殊符号的字符串]" 
							id="password" data="" name="passWord"/>
					</div>
					<div class="inputDiv" >
						<input placeholder="重复密码" id="repassword" data="" />
					</div>
					<div class="inputDiv">
						<input placeholder="手机号" 
							id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}"/>
					</div>
					<div class="inputDiv checkNumber btn-group">
						<input class="checkNumberInput" placeholder="验证码" 
							name="checkNumber" value=""/>
						<a id="getCheckNumber" class="btn checkNumberButton">获取短信验证码<span></span></a>
					</div>
					<div class="textDiv">
						<input type="checkbox"/>
						阅读并接受《杨先生协议》及《杨先生隐私权保护声明》
					</div>
					<div class="submitDiv">
						<input type="submit" class="btn btn-info"/>
					</div>
				</form>--%>
			</div>
		</div>
		</div>
	</body>
</html>

