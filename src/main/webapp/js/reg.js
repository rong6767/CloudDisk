$(function(){
	init();
	function init(){
		index = 0;
		max = 2;
		imgs=["img/2.jfif","img/3.jfif","img/1.jfif"];
		setInterval(function(){
			if(index > max){
				index = 0;
			}
			path = imgs[index];
			index ++;
			$(".bgimg img").attr("src",path)
		},3500)
	}
	
	//用户名输入验重
	$("#username").keyup(function(){
		var ele = $(this)
		var inputVal = $(this).val();
		var patt = /^[a-zA-Z][a-zA-Z0-9]{3,17}/;
		console.log(patt.test(inputVal))
		if(patt.test(inputVal)){
			$.post(
					"user/unsalfCheckUserName.do",
					{"username":inputVal},
					function(data){
						if(data.result==true){
							$("#usernametemp").attr("checkresult","1");
							ele.addClass("success");
							ele.removeClass("error");
						}else{
							$("#usernametemp").attr("checkresult","0");
							ele.addClass("error");
							ele.removeClass("success");
						}
					})
		}else{
			$("#usernametemp").attr("checkresult","0");
			
			if(inputVal != undefined && inputVal != null && inputVal != ""){
				ele.addClass("error");
				ele.removeClass("success");
			}else{
				ele.removeClass("success");
				ele.removeClass("error");
			}
		}
	});
	
	//获取短信验证码
	var checkNumberTime = 0;
	$("#getCheckNumber").click(function(){
		var ele = $(this)
		if(checkNumberTime == 0){
			$.post(
					"user/unsalfGetCheckNumber.do",
					{},
					function(data){
						checkNumberTime = 60;
						var t = setInterval(function(){
							ele.find("span").text("("+checkNumberTime+")")
							checkNumberTime -- ;
							if(checkNumberTime == 0){
								clearInterval(t)
								ele.find("span").text("");
							}
						}, 1000);
					});
		}
	})
	
	//提交验证
	$(".submitDiv").click(function(e){
		//验证用户名
		var username = $("#username").val();
		if(username == undefined || username == null || username == ""){
			alert("用户名不可为空")
			return false;
		}
		if($("#usernametemp").attr("checkresult")!="1"){
			alert("用户名未通过验证")
			return false;
		}
		var password = $("#password").val();
		if(/^[a-zA-Z][a-zA-Z0-9]{3,17}/.text(password)){
			alert("密码格式错误")
			return false;
		}
		if(password == undefined || password == null || password == ""){
			alert("密码不可为空")
			return false;
		}
		var repassword = $("#repassword").val();
		if(repassword != password){
			alert("两次密码输入不一致")
			return false;
		}
		//电话不可为空
		//电话不可存在
		//验证码不可为空
		//必须接受协议
		var phoneNumber = $("#phoneNumber").val();
		if(phoneNumber == undefined || phoneNumber == null || phoneNumber == ""){
			alert("电话不可为空")
			return false;
		}
		//验证密码
		//验证重复密码
		$(".regForm").submit();//全部通过则提交
	});
})
