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

<title>My JSP 'index_item.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<%@ include file="../public/commons.jspf"%>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	<script type="text/javascript">
				var portal = $("#jsps_layout_index_item_pp");
				$(function() {
					$('#jsps_layout_index_item_pp').portal({
						border : false,
						fit : true
					});
					portal_add();
					$("#A1").click(function () {
			            portal.portal("addColumn");
						var p = $('<div/>').appendTo('body');
						p.panel({
								title:'Title'+0,
								content:'<div style="padding:5px;">Content'+(0)+'</div>',		
								height:200,
								closable:true,
								collapsible:true
							});
					    portal.portal('add', {
								panel:p,
								columnIndex:0
							});
			        });
				});
				function portal_add() {
					for ( var i = 0; i < 3; i++) {
						portal.portal("addColumn");
						var p = $('<div/>').appendTo('#jsps_layout_index_item_pp');
						p.panel({
							title : 'Title' + i,
							iconCls : 'icon-save',
							href : '',
							//content:'<div ><a href="http://www.jeasyui.com/tutorial/datagrid/datagrid1.php">Build border layout for Web Pages</a></div> <div ><a href="http://www.jeasyui.com/tutorial/datagrid/datagrid1.php">Build border layout for Web Pages</a></div><div ><a href="http://www.jeasyui.com/tutorial/datagrid/datagrid1.php">Build border layout for Web Pages</a></div>',
							height : 250,
							//closable : true,
							collapsible : true,
							tools : [{
										iconCls:'icon-add',
										handler:function(b){alert('add');test('asdf')}
									 },{
										iconCls:'icon-edit',
										handler:function(){alert('edit')}
										}],
							footer : "<div style=\"padding:5px;\"><a href=\"javascript:addTab('jsp_layout_center_tabs','jsp_layout_center_tabsRigthMenu',{'title':'用户管理','href':'/SSHE/jsps/admin/yhgl.jsp','iconCls':'ext-icon-user_suit'});\">>>更多</a></div>",
						});
						$('#jsps_layout_index_item_pp').portal('add', {
							panel : p,
							columnIndex : i
						});
					}
					$('#jsps_layout_index_item_pp').portal('resize');
				}
				function test(a){
					alert(a);
					/*$.ajax({
						type : 'POST',
						url : '${pageContext.request.contextPath}/userAction!grantRole.action',
						data : {},
						dataType:'json',
						success : function(data) {
							//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
							if (data.success) {
								$('#jsp_admin_yhgl_datagrid').datagrid('updateRow',{
									index: index,
									row: data.obj
								});
								$('#jsp_admin_grantRole_dialog').dialog('close');
								$('#jsp_admin_yhgl_datagrid').datagrid('uncheckRow',index);
							}
							// 消息将显示在顶部中间
							$.messager.show({
								title : '提示消息',
								msg : data.msg,
								showType : 'show'
							});
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							$.messager.alert('错误',XMLHttpRequest.responseText,'error');
						}
					});*/
				}
				
			</script>
		<div id="jsps_layout_index_item_pp" style="position:relative;height:100%">
		</div>
	<div id="jsps_layout_index_item_tools">
		<a href="#" class="icon-add" onclick="javascript:alert('add')"></a>
		<a href="#" class="icon-edit" onclick="javascript:alert('edit')"></a>
	</div>
</body>
</html>
