<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
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
    <link rel="stylesheet" href="zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="css/basicePage.css" />
    <script type="text/javascript" src="js/jquery-1.8.3.min.js" ></script>
    <script type="text/javascript" src="js/jquery.form.js" ></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js" ></script>
    <script type="text/javascript" src="zTree_v3/js/jquery.ztree.core.min.js"></script>
    <script type="text/javascript" src="js/basicePage.js" ></script>
    <script type="text/javascript" src="js/allFile.js" ></script>
    <script type="text/javascript">
        $(function(){
            $('body').off('.data-api')
            $(".nav_address input").each(function(index,ele){
                $(ele).val($(ele).val().replace(/'/g,""));
            })
        })
    </script>
</head>
<body>
<div class="body">
    <input id="appID" type="hidden" value="${result.appID}"/>
    <div class="util">
        <form id="uploadForm" method="post" enctype="multipart/form-data">
            <a class="deleteCheckBox">
                <i class="icon-trash"></i>彻底删除
            </a>
            <a class="restoreCheckBox">
                <i class="icon-trash"></i>恢复
            </a>
        </form>
    </div>
    <div class="navigation">
        <div class="nav_btns ele">
            <a href="fs/showUpFileSystem.do?appID=${result.appID}&id=${result.parentId}&fileIsHidden=1&fileStatus=0&isFileNameDesc=0" class="mvLeft">
                <i class="icon-arrow-left"></i>
            </a>
            <a href="" class="mvRight">
                <i class="icon-arrow-right"></i>
            </a>
            <a id="pageRefresh" class="pageRefresh">
                <i class="icon-refresh"></i>
            </a>
        </div>
        <div class="nav_address ele">
            <input id="path" type="text" value="${result.fileFullName}"/>
            <input id="parentId" type="hidden" value="${result.parentId}"/>
            <input id="currentPath" type="hidden" value="${result.currentPath}"/>
            <input id="parentPath" type="hidden" value="${result.partenPath}"/>
            <input id="fullPath" type="hidden" value="${result.fullPath}"/>
        </div>
        <div class="nav_select ele">
            <input type="text" value="" placeholder="搜索我的网盘"/>
            <a href="" class="pageRefresh">
                <i class="icon-search"></i>
            </a>
        </div>
    </div>
    <script type="text/javascript">
        $(function(){
            $(".sort").click(function(){
                var url = "fs/showFileSystem.do?appID=${result.appID}&currentPath=${result.currentPath}&parentPath=${result.partenPath}&fullPath=${result.fullPath}&fileIsHidden=0&fileStatus=0&isFileNameDesc="
                if($(this).hasClass("down")){
                    $(this).removeClass("down");
                    $(this).removeClass("icon-arrow-down");
                    $(this).addClass("up");
                    $(this).addClass("icon-arrow-up");
                    url+="0";
                }else if($(this).hasClass("up")){
                    $(this).addClass("down");
                    $(this).addClass("icon-arrow-down");
                    $(this).removeClass("up");
                    $(this).removeClass("icon-arrow-up");
                    url+="1";
                }
                console.log(url)
                window.location.href = url;
            });
        })
    </script>
    <div class="list">
        <script type="text/javascript">
            $(function(){
                $(".shareButton").click(function(){
                    var appID = $("#appID").val();
                    var fsid = $(this).attr("fsid");
                    $.post("share/createShare.do",
                        {
                            "appID":appID ,
                            "id":fsid
                        },
                        function(data){
                            if(data.result=="success"){
                                $("#messageAlert .message").text(
                                    "请求名：http://127.0.0.1:8080/cloudDisk/unsalfToSharePage.do?token="
                                    + data.pw.token + "<br/>" +
                                    "密码：" +data.pw.pw
                                )
                                $('#messageAlert').modal('show')
                            }
                        });
                });
                $(".deleteCheckBox").click(function(){
                    var appID = $("#appID").val();
                    var fsidList = document.getElementsByName("selectCheckBox");
                    var check_val = [];
                    for(k in fsidList){
                        if(fsidList[k].checked)
                            check_val.push(fsidList[k].value);
                    }
                    for(itemToDelete in check_val){
                        $.post("fs/deleteTotally.do",
                            {
                                "appID":appID ,
                                "id":check_val[itemToDelete]
                            },
                            function(data){
                                if(data.result=="success"){
                                    window.location.reload(true)
                                }
                            });
                    }
                })
                $(".restoreCheckBox").click(function(){
                    var appID = $("#appID").val();
                    var fsidList = document.getElementsByName("selectCheckBox");
                    var check_val = [];
                    for(k in fsidList){
                        if(fsidList[k].checked)
                            check_val.push(fsidList[k].value);
                    }
                    console.log(fsidList)
                    console.log(check_val)
                    for(itemToRestore in check_val){
                        $.post("fs/restoreItem.do",
                            {
                                "appID":appID ,
                                "id":check_val[itemToRestore]
                            },
                            function(data){
                                if(data.result=="success"){
                                    window.location.reload(true)
                                }
                            });
                    }
                })
            })
        </script>
        <table>
            <tr class="head">
                <td width="780px">
                    <input type="checkbox" />
                    文件名
                    <c:if test="${result.isFileNameDesc == 1}">
                        <i class="sort down icon-arrow-down"></i>
                    </c:if>
                    <c:if test="${result.isFileNameDesc == 0}">
                        <i class="sort up icon-arrow-up"></i>
                    </c:if>
                </td>
                <td>
                    修改时间
                </td>
                <td>
                    大小
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
            <c:forEach items="${fsList }" var="fs">
                <tr class="t_row">
                    <td>
                        <c:if test="${fs.fileType == 0 }">
                            <input name="selectCheckBox" class="dirCheckBox fsCheckBox" type="checkbox" value="${fs.id}"/>
                            <i class="diricon icon-folder-open"></i>
                            <a href="fs/showFileSystem.do?appID=${result.appID}&currentPath=${fs.currentPath}&parentPath=${fs.parentPath}&fullPath=${fs.fileFullName}&fileIsHidden=0&fileStatus=0&isFileNameDesc=0&parentId=${fs.id}">${fs.fileName }</a>
                            <span>
										<a class="shareButton" fsid="${fs.id }">
											<i class="icon-share"></i>
										</a>
										<a href="">
											<i class="icon-trash"></i>
										</a>
									</span>
                        </c:if>
                        <c:if test="${fs.fileType == 1 }">
                            <input name="selectCheckBox" class="fileCheckBox fsCheckBox" type="checkbox" value="${fs.id}"/>
                            <i class="fileicon icon-file"></i>
                            <a href="fs/downloadFile.do?appID=${result.appID}&id=${fs.id }">${fs.fileName }</a>
                            <span>
										<a class="shareButton" fsid="${fs.id }">
											<i class="icon-share"></i>
										</a>
										<a href="">
											<i class="icon-download-alt"></i>
										</a>
										<a href="">
											<i class="icon-trash"></i>
										</a>
									</span>
                        </c:if>
                    </td>
                    <td>
                        <fmt:formatDate value="${fs.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <c:if test="${fs.fileSize<(1024*1024)}">
                            <fmt:formatNumber value="${fs.fileSize/1024}"/>KB
                        </c:if>
                        <c:if test="${fs.fileSize>=(1024*1024)}">
                            <fmt:formatNumber value="${fs.fileSize/(1024*1024)}"/>MB
                        </c:if>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<script type="text/javascript">
    $(function(){
        $(".addDirBtn").click(function(){
            var appID = $("#appID").val();
            var fullPath = $(".nav_address #fullPath").val()
            var parentId = $(".nav_address #parentId").val()
            var currentPath = "/" +$("#dirName").val()
            var parentPath = $(".nav_address #currentPath").val()
            var dirName = $("#dirName").val()
            var isHidden = 0
            $.post("fs/addDir.do",{
                "appID":appID,
                "currentPath":currentPath,
                "parentPath":parentPath,
                "fullPath":fullPath,
                "parentId":parentId,
                "isHidden":isHidden,
                "dirName":dirName
            },function(data){
                if(data.result == "success"){
                    window.location.reload(true)
                }else{
                    $('#addDir').modal('hide')
                    $("#messageAlert .message").text("添加失败")
                    $('#messageAlert').modal('show')
                }
            })
        })
    })
</script>
<div id="addDir" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">添加目录</h3>
    </div>
    <div class="modal-body">
        <!-- 验证格式，验证目录重名 -->
        <input type="text" id="dirName" style="width: 95%;"/>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="addDirBtn btn btn-primary">保存</button>
    </div>
</div>

<div id="messageAlert" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">提示</h3>
    </div>
    <div class="modal-body">
        <h3 id="myModalLabel" class="message"></h3>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
<!-- 移动 -->
<div id="remove" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">移动</h3>
    </div>
    <div class="modal-body">
        <script type="text/javascript">
            $(function(){
                //节点过滤器
                function filter(treeId, parentNode, childNodes) {
                    console.log(treeId);
                    console.log(parentNode);
                    console.log(childNodes);
                    return treeId;
                }
                var zTreeObj;
                // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                var setting = {
                    async: {
                        enable: true,
                        url:"/cloudDisk/fs/getZTreeeNode.do",
                        autoParam:["id"],
                        dataType: 'json',
                        type: 'get'
                        //  , dataFilter: filter
                    },
                    data:{
                        simpleData:{
                            enable: true,
                            idKey:'id',
                            pIdKey:'pid',
                            rootPId: -1
                        }
                    },
                    view:{
                        showIcon: true
                    },
                    callback: { // 对节点操作的回调方法
                        onClick: zTreeOnClick,  // 点击节点后的回调
                        onAsyncSuccess: zTreeOnAsyncSuccess // 异步加载成功的回调，可以用来展开根节点的子节点
                    }};


                var nodeId = null;
                //单击时获取zTree节点的Id,和value的值
                function zTreeOnClick(event, treeId, treeNode, clickFlag) {
                    nodeId = treeNode.id;
                }
                $(".moveBtn").click(function(){
                    if(nodeId == null){
                        $("#messageAlert .message").text("请选择节点")
                        $('#messageAlert').modal('show')
                        $('#remove').modal('hide')
                        return;
                    }
                    var ids = "";
                    var appID = $("#appID").val();
                    $(".fsCheckBox").each(function(index,ele){
                        if($(ele).is(':checked')){
                            ids+=","+$(ele).val();
                        }
                    })
                    var fullPath = $(".nav_address #fullPath").val()
                    $.post(
                        "fs/moveOrCpFs.do",
                        {"appID":appID,"nodeId":nodeId,"ids":ids,"fullPath":fullPath,"flag":0},
                        function(data){
                            if(data.result == "success"){
                                window.location.reload(true);
                            }
                        });


                });


                var treeJsonArray;
                var zTreeObj;
                function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
                    var pnode;
                    for(var i=0;i<treeJsonArray.length;i++){ // 遍历展开
                        var node = zTreeObj.getNodeByParam("id", treeJsonArray[i], null);
                        if(node==null){
                            zTreeObj.expandNode(pnode, true, true, true); // 该方法执行时会进行异步加载（setting中的async）
                        }
                        pnode=node;
                    }
                }
                // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
                var appID = $("#appID").val();
                $("#showRemovePage").click(function(){
                    $('#remove').modal('show')
                    var ids = ""
                    $(".dirCheckBox").each(function(index,ele){
                        if($(ele).is(':checked')){
                            ids+=","+$(ele).val();
                        }
                    })
                    $.post(
                        "fs/getZTreeeRootNode.do",
                        {"appID":appID,"parentId":"-1","ids":ids},
                        function(data){
                            console.log(data)
                            treeJsonArray = data;
                            zTreeObj = $.fn.zTree.init($("#moveTree"),setting, data);
                            //让第一个父节点展开
                            var rootNode_0 = zTreeObj.getNodeByParam('pid',0,null);
                            zTreeObj.expandNode(rootNode_0, true, false, false, false);
                            /*
                            zTreeObj = $.fn.zTree.init(
                                    $("#treeDemo"), setting, zNodes
                            );
                            */
                        })
                })
            })
        </script>
        <ul id="moveTree" class="ztree"></ul>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="moveBtn btn btn-primary">确定</button>
    </div>
</div>
<!-- 移动结束 -->

<!-- 复制 -->
<div id="cpPage" class="modal hide fade" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">复制</h3>
    </div>
    <div class="modal-body">
        <script type="text/javascript">
            //节点过滤器
            $(function(){
                function filter(treeId, parentNode, childNodes) {
                    console.log(treeId);
                    console.log(parentNode);
                    console.log(childNodes);
                    return treeId;
                }
                var zTreeObj;
                // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
                var setting = {
                    async: {
                        enable: true,
                        url:"/cloudDisk/fs/getZTreeeNode.do",
                        autoParam:["id"],
                        dataType: 'json',
                        type: 'get'
                        //  , dataFilter: filter
                    },
                    data:{
                        simpleData:{
                            enable: true,
                            idKey:'id',
                            pIdKey:'pid',
                            rootPId: -1
                        }
                    },
                    view:{
                        showIcon: true
                    },
                    callback: { // 对节点操作的回调方法
                        onClick: zTreeOnClick,  // 点击节点后的回调
                        onAsyncSuccess: zTreeOnAsyncSuccess // 异步加载成功的回调，可以用来展开根节点的子节点
                    }};


                var nodeId = null;
                //单击时获取zTree节点的Id,和value的值
                function zTreeOnClick(event, treeId, treeNode, clickFlag) {
                    nodeId = treeNode.id;
                }
                $(".cpBtn").click(function(){
                    if(nodeId == null){
                        $("#messageAlert .message").text("请选择节点")
                        $('#messageAlert').modal('show')
                        $('#cpPage').modal('hide')
                        return;
                    }
                    var ids = "";
                    var appID = $("#appID").val();
                    $(".fsCheckBox").each(function(index,ele){
                        if($(ele).is(':checked')){
                            ids+=","+$(ele).val();
                        }
                    })
                    var fullPath = $(".nav_address #fullPath").val()
                    $.post(
                        "fs/moveOrCpFs.do",
                        {"appID":appID,"nodeId":nodeId,"ids":ids,"fullPath":fullPath,"flag":1},
                        function(data){
                            if(data.result == "success"){
                                window.location.reload(true);
                            }
                        });


                });


                var treeJsonArray;
                var zTreeObj;
                function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
                    var pnode;
                    for(var i=0;i<treeJsonArray.length;i++){ // 遍历展开
                        var node = zTreeObj.getNodeByParam("id", treeJsonArray[i], null);
                        if(node==null){
                            zTreeObj.expandNode(pnode, true, true, true); // 该方法执行时会进行异步加载（setting中的async）
                        }
                        pnode=node;
                    }
                }
                // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
                var appID = $("#appID").val();
                $("#showCpPage").click(function(){
                    $('#cpPage').modal('show')
                    var ids = ""
                    $(".dirCheckBox").each(function(index,ele){
                        console.log(ele)
                        if($(ele).is(':checked')){
                            ids+=","+$(ele).val();
                        }
                    })
                    $.post(
                        "fs/getZTreeeRootNode.do",
                        {"appID":appID,"parentId":"-1","ids":ids},
                        function(data){
                            console.log(data)
                            treeJsonArray = data;
                            zTreeObj = $.fn.zTree.init($("#cpTree"),setting, data);
                            //让第一个父节点展开
                            var rootNode_0 = zTreeObj.getNodeByParam('pid',0,null);
                            zTreeObj.expandNode(rootNode_0, true, false, false, false);
                            /*
                            zTreeObj = $.fn.zTree.init(
                                    $("#treeDemo"), setting, zNodes
                            );
                            */
                        })
                })
            })
        </script>
        <ul id="cpTree" class="ztree"></ul>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="cpBtn btn btn-primary">确定</button>
    </div>
</div>
<!-- 复制结束 -->
</body>
</html>
