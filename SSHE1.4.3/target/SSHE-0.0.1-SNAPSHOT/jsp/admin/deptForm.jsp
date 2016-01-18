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
		/*
		var selectIcon1 = function() {
			var a = selectIcon();
			$('#jsp_admin_deptForm_iconCls').attr('class',a);
			$('#jsp_admin_deptForm_iconCls').val(a);
			$('#jsp_admin_iconsSelect_dialog').dialog('close');
		};
		*/
		var showIcons = function() {
			var dialog=$('<div id="jsp_admin_iconsSelect_dialog"/>').dialog({
				title: '浏览小图标',    
				iconCls:'icon-search',
				modal:true,
				resizable:true,
				width: 680,    
				height: 400,    
				closed: false,    
				cache: false,    
				href: '${pageContext.request.contextPath}/jsp/public/iconsSelect.jsp',
				buttons:[{
					text:'确定',
					iconCls:'icon-add',
					handler:function(){
						selectIcon1();
					}
				},{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$(this).dialog('close');
						//$('#jsp_admin_iconsSelect_dialog').dialog('close');
					}
				}],
				//注意如果使用动态加载dialog后一定要写onClose事件，否则可能造成关闭不销毁DOM节点的问题，造成内存问题
				onClose:function(){
					$(this).dialog('destroy');
				},
			});
		};
		function checkChangedVal(){
			if($("#jsp_admin_deptForm_stateIdCheckbox").attr("checked")){
				$("#jsp_admin_deptForm_stateId").val("Y");
			}else{
				$("#jsp_admin_deptForm_stateId").val("N");
			}
		}
	</script>
		<form id="jsp_admin_deptForm_form" method="post">
		    <fieldset>
				<legend>部门信息</legend>
				<table style="margin-top:8px" class="table">
					<tr>
						<th style="font-size:14px;font-family: 微软雅黑;" >编 &nbsp;
							&nbsp;码</th>
						<td style="font-size:14px;font-family: 微软雅黑;"><input 
							style="width:150px;" name="id" class="easyui-validatebox"
							readonly="readonly" /></td>
						<th style="font-size:14px;font-family: 微软雅黑">部门名称</th>
						<td style="font-size:14px;font-family: 微软雅黑"><input
							style="width:150px;" name="name" class="easyui-validatebox"
							data-options="required:true, missingMessage:'请输入部门名称'" /></td>
					</tr>
					<tr>
						<th style="font-size:14px;font-family: 微软雅黑">部门编码</th>
						<td><input style="width:150px;"
							type="text" name="deptNo"
							data-options="required:true, missingMessage:'请输入部门编码'"
							/></td>
						<th>资源图标</th>
						<td><input id="jsp_admin_deptForm_iconCls" name="iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" />
							<img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;
							<img class="iconImg ext-icon-cross" onclick="$('#jsp_admin_deptForm_iconCls').val('');$('#jsp_admin_deptForm_iconCls').attr('class','');" title="清空" />
						</td>
					</tr>
					<tr>
						<th>上级部门</th>
						<td>
							<select id="jsp_admin_deptForm_pid" name="pid" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'<%=path%>/deptAction!getAllTreeNode.action'" style="width: 155px;">
							</select>
							<img class="iconImg ext-icon-cross" onclick="$('#jsp_admin_deptForm_pid').combotree('clear');" title="清空" /></td>
						<th>部门地址</th>
						<td><textarea name="address"></textarea></td>
					</tr>
					<tr>
						<th>启用</th>
						<td><input id="jsp_admin_deptForm_stateIdCheckbox" type="checkbox"
							style="width:150px;" onclick="checkChangedVal();" checked/>
							<input id="jsp_admin_deptForm_stateId" type="hidden" value='Y' name="stateId" />
						</td>
						<th>备注</th>
						<td><textarea name="notes"></textarea></td>
						
					</tr>
				</table>
			</fieldset>
		</form>
</body>
</html>
