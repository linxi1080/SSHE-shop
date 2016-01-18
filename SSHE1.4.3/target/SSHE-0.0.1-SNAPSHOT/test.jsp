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

<title>My JSP 'test.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%@ include file="/jsp/public/commons.jspf"%>
<script type="text/javascript">
	//初始化form
	$(function() {
		//初始化form
		$('#index_regform').form({
			url : '',
			onSubmit : function() {
				// do some check    
				// return false to prevent submit;    
			},
			success : function(data) {
				//alert(data);
			}
		});
	});
</script>
</head>

<body>
	<div id="index_regdialog" class="easyui-dialog" ,style="margin-top:8px"
		data-options="title:'用户注册',iconCls:'icon-save',closed:false,resizable:true,buttons:[{
				text:'注册',
				iconCls:'icon-edit',
				handler:function(){
					// submit the form    
					$('#index_regform').submit();  
				}
			}]">
		<form id="index_regform" method="post">
			<table style="margin-top:8px">
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">登 &nbsp;录
						&nbsp;名：</th>
					<td style="font-size:14px;font-family: 微软雅黑"><input
						name="username" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入用户名'" />
					</td>
				</tr>
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">密 &nbsp; &nbsp;
						&nbsp;&nbsp;码：</th>
					<td><input id="index_password" type="password" name="username"
						class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入密码'" />
					</td>
				</tr>
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">确认密码：</th>
					<td><input type="index_repassword" name="username"
						class="easyui-validatebox"
						data-options="required:true ,missingMessage:'请确认密码'"
						validType="eqPwd['#index_password']" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
