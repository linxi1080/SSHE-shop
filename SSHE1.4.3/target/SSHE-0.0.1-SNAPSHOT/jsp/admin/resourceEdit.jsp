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
		$(function() {
			$('.iconImg').attr('src', lh.pixel_0);
		});
		var selectIcon1 = function() {
			var a = selectIcon();
			$('#jsp_admin_resourceEdit_iconCls').attr('class',a);
			$('#jsp_admin_resourceEdit_iconCls').val(a);
			$('#jsp_admin_iconsSelect_dialog').dialog('close');
		};
	</script>
		<form id="jsp_admin_resourceEdit_editForm" method="post">
		    <fieldset>
				<legend>资源基本信息</legend>
				<table style="margin-top:8px" class="table">
					<tr>
						<th style="font-size:14px;font-family: 微软雅黑;" >编 &nbsp;
							&nbsp;码</th>
						<td style="font-size:14px;font-family: 微软雅黑;"><input 
							style="width:150px;" name="id" class="easyui-validatebox"
							data-options="required:true, missingMessage:'请输入编码'" /></td>
						<th style="font-size:14px;font-family: 微软雅黑">资源名称</th>
						<td style="font-size:14px;font-family: 微软雅黑"><input
							style="width:150px;" name="text" class="easyui-validatebox"
							data-options="required:true, missingMessage:'请输入资源名称'" /></td>
					</tr>
					<tr>
						<th style="font-size:14px;font-family: 微软雅黑">资源路径</th>
						<td><input style="width:150px;"
							type="text" name="url"
							data-options="required:true, missingMessage:'请输入密码'"
							/></td>
						<th style="font-size:14px;font-family: 微软雅黑">资源类型</th>
						<td><input style="width:150px;" type="text"
							name="tresourceTypeId" class="easyui-combobox"
							data-options="required:true,editable:false,valueField:'id',textField:'name',url:'<%=path%>/resourceTypeAction!doNotNeedGrant_find.action',panelHeight:'auto'"
							/></td>
					</tr>
					<tr>
						<th>上级资源</th>
						<td>
							<select id="jsp_admin_resourceEdit_pid" name="pid" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'<%=path%>/menuAction!doNotNeedGrant_getAllTreeNode.action'" style="width: 155px;">
							</select>
							<img class="iconImg ext-icon-cross" onclick="$('#jsp_admin_resourceEdit_pid').combotree('clear');" title="清空" /></td>
						<th>资源图标</th>
						<td><input id="jsp_admin_resourceEdit_iconCls" name="iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" />
						<img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;
						<img class="iconImg ext-icon-cross" onclick="$('#jsp_admin_resourceEdit_iconCls').val('');$('#jsp_admin_resourceEdit_iconCls').attr('class','');" title="清空" />
						</td>
					</tr>
					<tr>
						<th>顺序</th>
						<td><input name="seqNo" class="easyui-numberspinner" data-options="required:true,min:0,max:100000,editable:true" style="width: 155px;" value="100" /></td>
						<th>资源描述</th>
						<td><textarea name="notes"></textarea></td>
					</tr>
				</table>
			</fieldset>
		</form>
</body>
</html>
