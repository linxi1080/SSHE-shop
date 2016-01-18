<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>${siteInfo.title}</title>
    
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
			$('#jsps_admin_changePasswordDialog_dialog').show().dialog({
				modal : true,
				closable : true,
				iconCls : 'ext-icon-lock_edit',
				buttons : [ {
					text : '修改',
					handler : function() {
						if ($('#jsps_admin_changePasswordDialog_dialog form').form('validate')) {
							$.post('${pageContext.request.contextPath}/userAction!changedPassword.action', 
									$.serializeObject($('#jsps_admin_changePasswordDialog_dialog form').form()), function(result) {
								if (result.success) {
									$.messager.alert('提示', '密码修改成功！', 'info');
									$('#jsps_admin_changePasswordDialog_dialog').dialog('close');
									//$('#jsps_admin_changePasswordDialog_dialog').dialog('destroy');
								}else{
									$.messager.alert('提示', result.msg, 'info');
								}
							}, 'json');
						}
					}
				} ],
				onOpen : function() {
					$('#jsps_admin_changePasswordDialog_dialog form :input').val('');
				}
			}).dialog('close');
			//
			// 消息将显示在顶部中间
			// \u8D85\u7EA7\u7BA1\u7406\u5458
			//console.info($("#jsps_layout_north_topbar-info_name").text());
			if ($("#jsps_layout_north_topbar-info_name").text()){
				$.messager.show({
					title : '提示消息',
					//msg : {sessionScope.sessionInfo.getUser().getName()},
					//${sessionScope.sessionInfo.getUser().getName()}
					msg:$("#jsps_layout_north_topbar-info_name").text()+"登录成功！",
					showType : 'show',
					position: 'bottomRight'
				});
			}
		});
		function getCentenPanel() {
			var blayout = $("#index_blayout");
			var panel = blayout.layout('panel', 'west');
			console.info(panel.panel('options').title);
		};
	</script>
  </head>
  
  <body id="index_blayout" class="easyui-layout">
	<div class="title" data-options="region:'north',split:false,iconCls:'icon-tip'" style="overflow: hidden;">
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
		<jsp:include page="/jsps/admin/changePasswordDialog.jsp"></jsp:include>
	</div>
</body>
</html>
