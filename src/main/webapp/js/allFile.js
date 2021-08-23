$(function(){
	
	$("#uploadFile").mouseover(function(){
		$("#fileUploadBrack").css(
				{
					"border":"1px solid #09AAFF",
					"background-color":"#D2E9FF"
				}
		);
	});
	$("#uploadFile").mouseout(function(){
		$("#fileUploadBrack").css(
				{
					"border":"1px solid #F0F0F0",
					"background-color":"#F0F0F0"
				}
		);
	});
	$("#uploadFile").change(function(e){
		var appID = $("#appID").val();
		var currentPath = $(".nav_address #currentPath").val()
		var parentId = $(".nav_address #parentId").val()
		var parentPath = $(".nav_address #parentPath").val()
		var fullPath = $(".nav_address #fullPath").val()
		var options = {
    			url:'fs/fileUpload.do',//表单提交的地址。缺省值： 表单的action的值
    			type:'POST',
    			dataType:'text',
    			data :{
    				"fileName":'uploadFile' ,
    				"appID":appID,
    				"parentId":parentId,
    				"isHidden":0,
    				"currentPath":currentPath,
    				"parentPath":parentPath,
    				"fullPath":fullPath
    			},
    			clearForm: true,//成功提交后，清除所有表单元素的值
    			//timeout: 3000 ,//限制请求的时间，当请求大于3秒后，跳出请求
    			// 从服务传过来的数据显示在这个div内部也就是ajax局部刷新
    			//target: '#output1',
    			// 处理之后的处理
    			success: function(data){
    				eval("data = " + data)
    				if(data.result === "success"){
    					window.location.reload(true)
    				}
    			}
    	};
    	//使用jquery的ajax upload插件可以上传文件
    	$('#uploadForm').ajaxSubmit(options);
	});
	
	$("#pageRefresh").click(function(){
		window.location.reload(true)
	})
})