<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>SSHE-LH-DEMO-主窗口</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%@ include file="../public/commons.jspf"%>
	<script type="text/javascript">
		$(function() {
			var blayout = $("#index_blayout");
			blayout.layout();
			blayout.layout('collapse', 'south');
			//
			$('#jsp_admin_changePasswordDialog_dialog').show().dialog({
				modal : true,
				closable : true,
				iconCls : 'ext-icon-lock_edit',
				buttons : [ {
					text : '修改',
					handler : function() {
						if ($('#jsp_admin_changePasswordDialog_dialog form').form('validate')) {
							$.post('${pageContext.request.contextPath}/userAction!changedPassword.action', 
									$.serializeObject($('#jsp_admin_changePasswordDialog_dialog form').form()), function(result) {
								if (result.success) {
									$.messager.alert('提示', '密码修改成功！', 'info');
									$('#jsp_admin_changePasswordDialog_dialog').dialog('close');
								}else{
									$.messager.alert('提示', result.msg, 'info');
								}
							}, 'json');
						}
					}
				} ],
				onOpen : function() {
					$('#jsp_admin_changePasswordDialog_dialog form :input').val('');
				}
			}).dialog('close');
		});
		function getCentenPanel() {
			var blayout = $("#index_blayout");
			var panel = blayout.layout('panel', 'west');
			console.info(panel.panel('options').title);
		};
	</script>
  </head>
  
  <body id="index_blayout" class="easyui-layout">
	<div data-options="region:'north',split:false,iconCls:'icon-tip'" style="height: 28px; overflow: hidden;">
		<jsp:include page="north.jsp"></jsp:include>
	</div>
	
	<div id="index_showAbout" data-options="region:'south',title:'关于...',split:false,collapsed:false,border:false,iconCls:'icon-tip'" style="height:80px;">
		<jsp:include page="south.jsp"></jsp:include>
	</div>
	<div data-options="region:'east',title:'East',split:true" style="width:100px;">
	</div>
	<div id="index_west-menu" data-options="region:'west',split:true" style="width:200px;">
		<jsp:include page="west.jsp"></jsp:include>
	</div>
	<div data-options="region:'center',iconCls:'icon-add'" style="padding:5px;background:#eee;">
		<jsp:include page="center.jsp"></jsp:include>
	</div>
	<div>
		<jsp:include page="/jsp/admin/changePasswordDialog.jsp"></jsp:include>
	</div>
</body>
</html>
