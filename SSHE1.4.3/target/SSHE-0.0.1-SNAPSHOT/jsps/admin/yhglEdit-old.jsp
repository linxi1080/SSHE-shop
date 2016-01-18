<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'yhglEdit.jsp' starting page</title>

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
	var uploader;//上传对象
	$(function() {
		var uploader;//上传对象
		
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(sy.contextPath + '/base/syuser!getById.sy', {
				id : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'data.id' : result.id,
						'data.name' : result.name,
						'data.loginname' : result.loginname,
						'data.sex' : result.sex,
						'data.age' : result.age,
						'data.photo' : result.photo
					});
					if (result.photo) {
						$('#photo').attr('src', sy.contextPath + result.photo);
					}
				}
				parent.$.messager.progress('close');
			}, 'json');
		}

		uploader = new plupload.Uploader({//上传插件定义
			browse_button : 'pickfiles',//选择文件的按钮
			container : 'container',//文件上传容器
			runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : sy.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : sy.contextPath + '/plupload?fileFolder=/userPhoto',//上传文件路径
			max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
			chunk_size : '10mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			// 如果可能的话，压缩图片大小
			/*resize : {
				width : 320,
				height : 240,
				quality : 90
			},*/
			// 指定要浏览的文件类型
			filters : [ {
				title : '图片文件',
				extensions : 'jpg,gif,png'
			} ]
		});
		uploader.bind('Init', function(up, params) {//初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#filelist').html("");
		});
		uploader.bind('BeforeUpload', function(uploader, file) {//上传之前
			if (uploader.files.length > 1) {
				parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
				uploader.stop();
				return;
			}
			$('.ext-icon-cross').hide();
		});
		uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
			});
			up.refresh();
		});
		uploader.bind('UploadProgress', function(up, file) {//上传进度改变
			var msg;
			if (file.percent == 100) {
				msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');

			parent.sy.progressBar({//显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			});
		});
		uploader.bind('Error', function(up, err) {//出现错误
			$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			up.refresh();
		});
		uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				//console.info(response.fileUrl);
				//console.info(file.name);
				//$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
				//$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
				$(':input[name="data.photo"]').val(response.fileUrl);
			}
		});
		uploader.init();
	});
		function addUser() {
			//var addform = $('#jsp_admin_yhglEdit_editForm');
			//var adddialog = $('#jsp_admin_yhglEdit_editdialog');
			//
			jsp_admin_yhgl_addform
					.form(
							'submit',
							{
								url : '${pageContext.request.contextPath}/userAction!addUser.action',
								success : function(data) {
									var obj = jQuery.parseJSON(data);
									if (obj.success) {
										//$('#jsp_admin_yhgl_datagrid').datagrid('load');
										//$('#jsp_admin_yhgl_datagrid').datagrid('appendRow',obj.obj);
										$('#jsp_admin_yhgl_datagrid').datagrid(
												'insertRow', {
													index : 0, // 索引从0开始
													row : obj.obj
												});
										jsp_admin_yhgl_adddialog
												.dialog('close');
									}
									$.messager.show({
										title : '提示消息',
										msg : obj.msg,
										showType : 'show'
									});
								}
							});
		};
		
	</script>
		<form id="jsp_admin_yhglEdit_editForm" method="post">
			<table style="margin-top:8px">
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
						style="width:150px;" name="name" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入用户名'" /></td>
					<th style="font-size:14px;font-family: 微软雅黑">密 &nbsp; &nbsp;
						&nbsp;&nbsp;码：</th>
					<td><input style="width:150px;" id="user_reg_password"
						type="password" name="pwd" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入密码'"
						value="123456" readonly="readonly"/></td>
				</tr>
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">建立时间：</th>
					<td><input style="width:150px;" type="text"
						name="createdatetime" class="easyui-datetimebox"
						data-options="required:false,editable:false,missingMessage:'请输入建立时间'"
						readonly="readonly" /></td>
					<th style="font-size:14px;font-family: 微软雅黑">修改时间：</th>
					<td><input style="width:150px;" type="text"
						name="modifydatetime" class="easyui-datetimebox"
						data-options="required:true,editable:false,missingMessage:'请输入修改时间'" />
					</td>
				</tr>
				<tr>
					<th>照片上传</th>
					<td><div id="container">
							<a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
							<div id="filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
						</div></td>
					<th></th>
					<td><input name="photo" readonly="readonly" style="display: none;" /> 
					<img id="photo" src="" style="width: 200px; height: 200px;"></td>
				</tr>
			</table>
		</form>
</body>
</html>
