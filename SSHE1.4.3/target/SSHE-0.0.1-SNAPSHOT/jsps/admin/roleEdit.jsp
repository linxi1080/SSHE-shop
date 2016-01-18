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
	</script>
		<form id="jsp_admin_roleEdit_editForm" method="post">
			<table style="margin-top:8px">
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑;display:none;" >编 &nbsp;
						&nbsp;码：</th>
					<td style="font-size:14px;font-family: 微软雅黑;display:none;">
					<input style="width:150px;" name="id" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入编码'" readonly="readonly" /></td>
				</tr>
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">角 &nbsp;色
						&nbsp;名：</th>
					<td style="font-size:14px;font-family: 微软雅黑"><input
						style="width:150px;" name="name" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入角色名'" /></td>
					<th style="font-size:14px;font-family: 微软雅黑">备 &nbsp; &nbsp;
						&nbsp;&nbsp;注：</th>
					<td><input style="width:150px;" type="text" name="notes" class="easyui-validatebox"
						data-options="required:false, missingMessage:'请输入备注'"/></td>
				</tr>
			</table>
		</form>
</body>
</html>
