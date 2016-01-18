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

<title>品牌列表</title>

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
	<div class="main">
		<div class="easyui-panel" style="border-style: none; height: 600px">
			<div style="padding: 10px 0 10px 20px">
				<form id="brandForm" method="post" class="validate">
					<table>
						<tr>
							<td>品牌名称</td>
							<td><input class="input_text easyui-validatebox" type="text"
								id="name" name="brand.name" data-options="required:true"></input>
							</td>
						</tr>
						<tr>
							<td>品牌网址：</td>
							<td><input class="input_text easyui-validatebox" type="text"
								id="url" value="http://" name="brand.url"
								data-options="required:true,validType:'url'"></input>
							</td>
						</tr>
						<tr>
							<td>品牌LOGO：</td>
							<td><input class="easyui-validatebox" type="file" id="logo"
								name="logo" data-options="required:false"></input>
							</td>
						</tr>
						<tr>
							<td>详细说明</td>
							<td><textarea id="brief" name="brand.brief"></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
