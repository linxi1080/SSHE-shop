<!-- 
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>商品分类</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
<%@ include file="../../../public/header.jsp"%>
	-->
	<#include '../../../public/header.jsp' >
</head>
<body>
	<div class="main" style="background-color: white;">
		<div class="easyui-panel" style="border-style: none;">
			<form id="jsps_shop_admin_category_categoryEdit_addForm" method="post" class="validate" enctype="multipart/form-data" >
				<table>
					<tr>
						<th>分类名称</th>
						<td><input class="input_text easyui-validatebox" type="text"
							id="note" name="model.text"
							data-options="required:true"></input>
						</td>

					</tr>
					<tr>
						<th>分类图片</th>
						<td><input class="easyui-validatebox validatebox-text" type="file" id="image"
							name="image"></input></td>
					</tr>
					<tr>
						<th></th>
						<td><img alt="" src="" height="120px"></td>
					</tr>
					<tr>
						<th>是否为顶级分类</th>
						<td><input type="radio" name="cattype" class="cattype"
							value="0" checked="checked">否
							&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="cattype"
							class="cattype" value="1">是</td>
					</tr>
					<tr class="addtr">
						<th>上级分类</th>
						<td>
						<input class="easyui-combotree combo"
							value="" name="model.pid"
							data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'${ctx}/common/categoryAction!getAllTreeNode.action',panelHeight:'auto'"
							style="width: 200px;">
							</td>

					</tr>
					<tr>
						<th>商品类型</th>
						<td><input class="easyui-combobox combo" type="text"
							name="typeId"
							data-options="editable:false,valueField:'id',textField:'note',url:'${ctx}/common/typeAction!find.action',required:true,panelHeight:'auto'"
							style="width: 200px;"></td>
							
					</tr>
					<tr>
						<th>排序</th>
						<td><input class="input_text" type="text" id="order"
							name="model.categoryOrder"
							data-options="required:true"></input></td>
					</tr>
					<tr>
						<th>列表中显示</th>
						<td><input type="radio" name="model.listShow" value="1"
							checked="checked" />是&nbsp; <input type="radio"
							name="model.listShow" value="0" />否</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<script>
		$(function() {
			$(".cattype").click(function() {
				if ($(this).val() == 1) {
					$(".addtr").hide();
				}
				if ($(this).val() == 0) {
					$(".addtr").show();
				}
			})
		});
	</script>
</body>
</html>
