<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript">
    $(function(){
    	//主题单击
		//	$('.theme-skinitem').click(function(){
		//		var currtab_title = $('#jsps_layout_north_themeLists').data("currtab");
		//		//$this.attr('src');
		//		var easyuiTheme = $(this).attr('rel');
		//		changeTheme(easyuiTheme);				
		//	});
		//	//打开页面初始化主题
		//	if(getCookie('easyuiThemeName')) {
		//		var easyuiTheme = getCookie('easyuiThemeName');
		//		console.info(easyuiTheme);
		//		changeTheme(easyuiTheme);
		//	}
    });
	function showAbout() {
		//	$("#jsps_layout_north_blayout").layout('expand','south');
		//console.info("${sessionInfo.getUser().getName()}");
		console.info("${sessionInfo.getUser().getName()}");
		$.messager.show({
			title : '关于...',
			msg : $('.footer').text().substring(0, 100),
			timeout : 4000,
			showType : 'slide'
		});
	};
	/*
	注销
	*/
	var logoutFun = function() {
		$.post('<%=path%>'+'/userAction!logout.action', function(result) {
			location.replace('<%=path%>' + '/index.jsp');
		}, 'json');
	};
</script>
<div id="jsps_layout_north_topbar" class="title">
	<div id="jsps_layout_north_topbar_logon" class="right">
		<a href="<%=basePath %>" title="浏览网站" target="_blank" data-options="iconCls:'icon-website',plain:'true'"
			class="easyui-linkbutton">浏览网站</a> 
		<a id="jsps_layout_north_logout" href="javascript:void(0);" data-options="iconCls:'ext-icon-disconnect',plain:'true'"
			class="easyui-linkbutton" onclick="logoutFun();">注销</a>
		<a data-options="iconCls:'icon-help',menu:'#jsps_layout_north_topbar-menu'" href="#"
			class="easyui-menubutton">管理</a>
		<div id="jsps_layout_north_topbar-menu" style="width:150px;">
			<div data-options="iconCls:''">修改个人信息</div>
			<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#jsps_admin_changePasswordDialog_dialog').dialog('open');">修改口令</div>
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-reload'">刷新系统缓存</div>
			<!--
				<div id="jsps_layout_north_themeLists" iconCls="icon-save">更换主题
					<div>
						<div class="theme-skinitem" iconCls="icon-edit" rel="gray">灰色</div>
						<div class="theme-skinitem" iconCls="icon-remove" rel="bootstrap">蓝色</div>
						<div class="theme-skinitem" iconCls="icon-add" rel="black">黑色</div>
						<div class="theme-skinitem" iconCls="icon-defaul" rel="default">默认</div>
						<div class="theme-skinitem" iconCls="" rel="metro">metro</div>
						
					</div>
				</div>	
				-->
			<div class="menu-sep"></div>
			<div data-options="iconCls:'icon-help'" onclick="showAbout()">关于</div>
		</div>
	</div>
	<div id="jsps_layout_north_topbar-info1" class="left topbar-info">&nbsp;&nbsp;&nbsp;</div>
	<!-- ${sessionScope.username} -->
	<div id="jsps_layout_north_topbar-info" class="left topbar-info">欢迎【<span id="jsps_layout_north_topbar-info_name">${sessionScope.sessionInfo.getUser().getName()}</span>】登录;IP地址:【${sessionScope.sessionInfo.getUser().getIp()}】</div>
	<div class="left">
		<!-- 
		${sessionInfo.getUser().getName()}
		#session.sessionInfo.getSecurityUtil().havePermission('/roleAction!addRole.action')
		${sessionInfo.getUser().getPhoto()}
		-->
		<img src="${sessionInfo.getUser().getPhoto()}" alt="登录用户" title="${sessionInfo.getUser().getName()}"/>
	</div>
</div>
