<%@ page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>SSHE-LH-DEMO-登录窗口</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
	<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%@ include file="../jsp/public/commons.jspf"%>
<script type="text/javascript">
	$(function() {
		$(function(){
			var blayout = $("#index_blayout");
			blayout.layout();
			blayout.layout('collapse', 'south');
		});
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
		<jsp:include page="../jsp/layout/north.jsp"></jsp:include>
	</div>
	
	<div id="index_showAbout" data-options="region:'south',title:'关于...',split:false,collapsed:false,border:false,iconCls:'icon-tip'" style="height:80px;">
		<jsp:include page="../jsp/layout/south.jsp"></jsp:include>
	</div>
	<div data-options="region:'east',title:'East',split:true" style="width:100px;">
	</div>
	<div id="index_west-menu" data-options="region:'west',split:true" style="width:200px;">
		<jsp:include page="../jsp/layout/west.jsp"></jsp:include>
	</div>
	<div data-options="region:'center',iconCls:'icon-add'" style="padding:5px;background:#eee;">
		<jsp:include page="../jsp/layout/center.jsp"></jsp:include>
	</div>
	<!-- 用户登录 -->
	<jsp:include page="../jsp/user/login.jsp"></jsp:include>
	<!-- 用户注册 -->
	<jsp:include page="../jsp/user/reg.jsp"></jsp:include>
</body>
</html>
