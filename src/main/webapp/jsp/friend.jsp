<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
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
    <link rel="stylesheet" href="css/index.css" />
    <script type="text/javascript" src="js/jquery-1.8.3.min.js" ></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js" ></script>
    <script type="text/javascript" src="js/index.js" ></script>
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
    <div class="title">
        <div class="logo col">
            云盘
        </div>
        <div class="menu1 menu col">
					<span class="bottonSpan">
						<a href="fs/gotoIndex.do?appID=${appID}&currentPath=/&parentPath=/&fullPath=/&fileIsHidden=0&fileStatus=0&isFileNameDesc=0">网盘</a>
					</span>
        </div>
        <div class="menu2 menu col">
					<span class="">
						<a href="share/unsalfToSharePage.do?appID=${appID}&currentPath=/&parentPath=/&fullPath=/&fileIsHidden=0&fileStatus=0&isFileNameDesc=0">分享</a>
					</span>
        </div>
        <div class="spa col">

        </div>
        <div class="vip1 col">
            &nbsp;
        </div>
        <div class="vip2 col">
            <a href="vip.html" class="buttonA">会员中心</a>
        </div>
    </div>

    <div class="middel">
        <div class="menuList">
            <div class="showMenu">
                <div class="base menu">
                    <i class="icon-user icon-white"></i>&nbsp;
                    <a href="" target="page">xxxxxx</a>
                </div>
                <div class="base">
                    <i class="icon-user"></i>&nbsp;
                    <a href="" target="page">xxxxxx</a>
                </div>
                <div class="base">
                    <i class="icon-user"></i>&nbsp;
                    <a href="" target="page">xxxxxx</a>
                </div>
            </div>
        </div>
        <div class="ifream">
            <iframe name="page" src="fs/showFileSystem.do?appID=${appID}&currentPath=/&parentPath=/&fullPath=/&fileIsHidden=0&fileStatus=0&isFileNameDesc=0"></iframe>
        </div>
        <div class="clear"></div>
    </div>
</div>
</body>
</html>
