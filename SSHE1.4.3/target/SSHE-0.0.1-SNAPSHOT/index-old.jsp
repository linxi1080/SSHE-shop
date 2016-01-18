<%@page import="org.apache.struts2.components.Include"%>
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

<title>SSHE-LH-DEMO</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%@ include file="jsp/public/commons.jspf"%>
<script type="text/javascript">
	$(function() {
		//调用tab页的右键菜单事件函数
		tabRigthMenuEven();
		$("#blayout").layout();
		$("#blayout").layout('collapse', 'south');
		$('#menu').tree({
			lines : true,
			//url : 'department_list.action',
			//		checkbox: true,
			//		url:'tree_data.json'
			/**
			 * 双击展开树
			 * @param {Object} node
			 */
			onDblClick : function(node) {
				console.info(node);
				if (node.state == 'closed') {
					$('#menu').tree('expand', node.target);
				} else {
					$('#menu').tree('collapse', node.target);
				}
			},
			onClick : function(node) {
				var _title = node.text;
				console.info(_title);

			}
		});
		//导航菜单鼠标点击事件绑定，并在点击后调用函数addTab
		$('.cs-navi-tab').click(function() {
			var $this = $(this);
			var href = $this.attr('src');
			var title = $this.text();
			addTab(title, href);
		});
		//主题单击
		//	$('.theme-skinitem').click(function(){
		//		var currtab_title = $('#themeLists').data("currtab");
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
	function getCentenPanel() {
		var panel = $("#blayout").layout('panel', 'west');
		console.info(panel.panel('options').title);
	};
	function showAbout() {
		//	$("#blayout").layout('expand','south'); 
		$.messager.show({
			title : '关于...',
			msg : $('.footer').text().substring(0, 100),
			timeout : 4000,
			showType : 'slide'
		});
	};
</script>
</head>

<body id="blayout" class="easyui-layout">
	<div data-options="region:'north',split:false,iconCls:'icon-tip'"
		style="height: 28px; overflow: hidden;">
		<div id="topbar" class="title">
			<div id="topbar-logon" class="right">
				<a id="logout" data-options="icon:'icon-remove',plain:'true'"
					class="easyui-linkbutton">注销</a> <a
					data-options="icon:'icon-help',menu:'#topbar-menu'" href="#"
					class="easyui-menubutton">管理</a>
				<div id="topbar-menu" style="width:150px;">
					<div data-options="iconCls:''">修改个人信息</div>
					<div data-options="iconCls:''">修改口令</div>
					<div class="menu-sep"></div>
					<div data-options="iconCls:'icon-reload'">刷新系统缓存</div>
					<!--
						<div id="themeLists" iconCls="icon-save">更换主题
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
			<div id="topbar-info" class="left">&nbsp;&nbsp;&nbsp;</div>
			<div id="topbar-info" class="left">欢迎 【 admin 】</div>
			<div class="left">
				<img src="jslib/jquery-easyui/themes/icons/man.png" alt="登录用户"
					title="登录用户" />
			</div>
		</div>
	</div>
	<div id="showAbout"
		data-options="region:'south',title:'关于...',split:false,collapsed:false,border:false,iconCls:'icon-tip'"
		style="height:80px;">
		<div id="about-text" class="footer">
			@2015-2024 Copyright: linxi1080 Personal.&nbsp;&nbsp;|&nbsp;&nbsp; <a
				href="#" target="_blank" style="text-decoration: none;">关于
				linxi1080</a><br /> 建议使用&nbsp; <a
				href="http://windows.microsoft.com/zh-CN/internet-explorer/products/ie/home"
				target="_blank" style="text-decoration: none;">IE(9/10/11)</a>/ <a
				href="https://www.google.com/intl/zh-CN/chrome/browser/"
				target="_blank" style="text-decoration: none;">Chrome</a>/ <a
				href="http://firefox.com.cn/download/" target="_blank"
				style="text-decoration: none;">Firefox</a> &nbsp;系列浏览器。
			<script type="text/javascript">
				var cnzz_protocol = (("https:" == document.location.protocol) ? " https://"
						: " http://");
				document
						.write(unescape("%3Cspan id='cnzz_stat_icon_5654850'%3E%3C/span%3E%3Cscript src='"
								+ cnzz_protocol
								+ "s9.cnzz.com/stat.php%3Fid%3D5654850%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));
			</script>
		</div>
	</div>
	<div data-options="region:'east',title:'East',split:true"
		style="width:100px;"></div>
	<div id="west-menu"
		data-options="region:'west',title:'导航菜单',split:true,iconCls:'icon-search',
			tools: [{iconCls:'icon-reload', 
				        handler:function(){
							var node = $('#menu').tree('getSelected');
							if(node){
								$('#menu').tree('expandAll',node.target);
							}else{
								$('#menu').tree('expandAll');
							}
						}    
				    },{    
				        iconCls:'icon-undo',    
				        handler:function(){
							var node = $('#menu').tree('getSelected');
							if(node){
								$('#menu').tree('collapseAll',node.target);
							}else{
								$('#menu').tree('collapseAll');
							}
						}    
				    }] "
		style="width:200px;">
		<ul id="menu">
			<li><span>Folder</span>
				<ul>
					<li><span>Sub Folder 1</span>
						<ul>
							<li><span><a href="javascript:void(0);"
									src="tree1.jsp" class="cs-navi-tab">File 11</a> </span></li>
							<li><span><a href="javascript:void(0);"
									src="user.html" class="cs-navi-tab">easyloader</a> </span></li>
							<li><span>File 13</span></li>
						</ul></li>
					<li><span>File 2</span></li>
					<li><span>File 3</span></li>
				</ul></li>
			<li><span>File21</span></li>
		</ul>
	</div>
	<div data-options="region:'center',iconCls:'icon-add'"
		style="padding:5px;background:#eee;">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="Home">
				<div class="cs-home-remark">
					<script type="text/javascript">
						$(function() {
							//addTab("主页", "1.html");
						});
					</script>
					<h1>jQuery EasyUI 1.3.4 Demo</h1>
					<br> 制作：purediy <br> 博客：<a
						href="http://www.cnblogs.com/purediy" target="_blank">http://www.cnblogs.com/purediy</a><br>
					说明：jQuery EasyUI 1.3.4 Demo分类整理。
				</div>
			</div>
		</div>

	</div>
	<!--tab页右键菜单-->
	<div id="mm" class="easyui-menu cs-tab-menu">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div id="mm-tabcloseall">关闭全部</div>
	</div>
	<!-- 用户登录 -->
	<jsp:include page="jsp/user/login-20150527.jsp"></jsp:include>
	<!-- 用户注册 -->
	<jsp:include page="jsp/user/reg-20150527.jsp"></jsp:include>
</body>
</html>
