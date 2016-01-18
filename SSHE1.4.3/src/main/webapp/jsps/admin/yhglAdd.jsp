<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'yhglAdd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <script type="text/javascript">
	var jsp_admin_yhglAdd_uploader;//上传对象
	$(function() {
		jsp_admin_yhglAdd_uploader = new plupload.Uploader({/*上传插件定义*/
			browse_button : 'jsp_admin_yhglAdd_pickfiles',/*选择文件的按钮*/
			container : 'jsp_admin_yhglAdd_container',//文件上传容器
			runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : sy.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : '<%=basePath%>plupload?fileFolder=/userPhoto',//上传文件路径
			
			max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
			chunk_size : '10mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			// 如果可能的话，压缩图片大小
			//*/
			/*resize : {
				width : 320,
				height : 240,
				quality : 90
			},*/
			///*
			// 指定要浏览的文件类型
			filters : [ {
				title : '图片文件',
				extensions : 'jpg,gif,png'
			} ]
		});
		jsp_admin_yhglAdd_uploader.bind('Init', function(up, params) {//初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#jsp_admin_yhglAdd_filelist').html("");
		});
		jsp_admin_yhglAdd_uploader.bind('BeforeUpload', function(jsp_admin_yhglAdd_uploader, file) {//上传之前
			if (jsp_admin_yhglAdd_uploader.files.length > 1) {
				parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
				jsp_admin_yhglAdd_uploader.stop();
				return;
			}
			$('.ext-icon-cross').hide();
		});
		jsp_admin_yhglAdd_uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				$('#jsp_admin_yhglAdd_filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="jsp_admin_yhglAdd_uploader.removeFile(jsp_admin_yhglAdd_uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
			});
			up.refresh();
		});
		jsp_admin_yhglAdd_uploader.bind('UploadProgress', function(up, file) {//上传进度改变
			var msg;
			if (file.percent == 100) {
				msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');
			/*
			parent.sy.progressBar({//显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			});
			*/
		});
		jsp_admin_yhglAdd_uploader.bind('Error', function(up, err) {//出现错误
			$('#jsp_admin_yhglAdd_filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			up.refresh();
		});
		jsp_admin_yhglAdd_uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				//console.info('yhglAdd');
				//console.info(response.fileUrl);
				console.info($(':input[name="photo"]').val());
				console.info(response.fileUrl);
				$(':input[name="photo"]').val(response.fileUrl);
			}
		});
		jsp_admin_yhglAdd_uploader.init();
	});
	</script>
		<form id="jsp_admin_yhglAdd_editForm" method="post">
			<table style="margin-top:8px" class="table">
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑;display:none;" >编 &nbsp;
						&nbsp;码：</th>
					<td style="font-size:14px;font-family: 微软雅黑;display:none;"><input 
						style="width:150px;" name="id" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入编码'" readonly="readonly" /></td>
				</tr>
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">登 &nbsp;录
						&nbsp;名：</th>
					<td style="font-size:14px;font-family: 微软雅黑"><input
						style="width:150px;" name="loginName" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入用户名'" /></td>
					<th style="font-size:14px;font-family: 微软雅黑">用户名：</th>
					<td><input style="width:150px;" 
						type="text" name="name" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入用户名'"
						/></td>
					<!-- 
					<th style="font-size:14px;font-family: 微软雅黑">密 &nbsp; &nbsp;
						&nbsp;&nbsp;码：</th>
					<td><input style="width:150px;" id="user_reg_password"
						type="password" name="pwd" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入密码'"
						value="123456" readonly="readonly"/></td>
					 -->
				</tr>
				<tr>
					<th>性别</th>
					<td>
						<select class="easyui-combobox" name="gender" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								<option value="1">男</option>
								<option value="0">女</option>
						</select>
					</td>
					<th style="font-size:14px;font-family: 微软雅黑">修改时间：</th>
					<td><input style="width:150px;" type="text"
						name="modifydatetime" class="easyui-datetimebox"
						id="jsp_admin_yhglAdd_modifydatetime"
						data-options="required:true,editable:true,missingMessage:'请输入修改时间'" />
					</td>
				</tr>
				<tr>
					<th>照片上传</th>
					<td><div id="jsp_admin_yhglAdd_container">
							<a id="jsp_admin_yhglAdd_pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
							<div id="jsp_admin_yhglAdd_filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
						</div></td>
					<th></th>
					<td><input name="photo" readonly="readonly" style="display: none;" /> 
					<img id="jsp_admin_yhglAdd_photo" src="" style="width: 200px; height: 200px;"></td>
				</tr>
			</table>
		</form>
  </body>
</html>
