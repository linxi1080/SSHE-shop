<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		//调用tab页的右键菜单事件函数,分别传入tabsId和右键菜单id
		//右键菜单中子菜单的id{#mm-tabupdate,#mm-tabclose,#mm-tabcloseall,mm-tabcloseother,#mm-tabcloseright,#mm-tabcloseleft,#mm-exit}
		tabRigthMenuEven('jsp_layout_center_tabs',
				'jsp_layout_center_tabsRigthMenu');
	});
</script>
<div id="jsp_layout_center_tabs" class="easyui-tabs"
	data-options="fit:true,border:true">
	<!-- 
	<div id = "jsps_layout_center_home" style="width:100%;height:100%;" title="主页" data-options="iconCls:'exticon-home'">
		<div class="cs-home-remark">
	-->
			<script type="text/javascript">
				$(function() {
					addTab("jsp_layout_center_tabs","jsp_layout_center_tabsRigthMenu",{"title":"主页","href":"/SSHE/jsps/layout/index_item.jsp","iconCls":"exticon-home"});
					/*
					var tabs = $('#jsp_layout_center_tabs');
					tabs.tabs('add',{
						title:'测试',
						href:'${pageContext.request.contextPath}/jsps/admin/role.jsp',
						closable:true,
						iconCls:'icon-edit'
					});
					 */
				});
			</script>
			<!-- 
			<h1>jQuery EasyUI 1.3.4 Demo</h1>
			<br> 制作：linxi1080 <br> email：<a
				href="mail:linxi1080@sina.com" target="_blank">linxi1080@sina.com</a><br>
			说明：jQuery EasyUI 1.3.4 Demo分类整理。
			<iframe frameborder=0 width=100% height=100% marginheight=0 marginwidth=0 scrolling=no src="jsps/layout/index_item.jsp"></iframe>
			-->
	<!-- 
			</div>
	</div>
	-->
</div>
<!--tab页右键菜单-->
<div id="jsp_layout_center_tabsRigthMenu"
	class="easyui-menu cs-tab-menu">
	<div id="mm-tabupdate" data-options="iconCls:'icon-reload'">刷新</div>
	<div class="menu-sep"></div>
	<div id="mm-tabclose" data-options="iconCls:'icon-no'">关闭</div>
	<div id="mm-tabcloseother" data-options="iconCls:''">关闭其他</div>
	<div id="mm-tabcloseall" data-options="iconCls:'icon-cancel'">关闭全部</div>
</div>
<div id="p-tools">
	<a href="javascript:void(0)" class="icon-mini-add"
		onclick="alert('add')"></a> <a href="javascript:void(0)"
		class="icon-mini-edit" onclick="alert('edit')"></a> <a
		href="javascript:void(0)" class="icon-mini-refresh"
		onclick="alert('refresh')"></a>
</div>
