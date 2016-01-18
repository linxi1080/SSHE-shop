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

<title>My JSP 'yhgl.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
  <link rel="stylesheet" type="text/css" href="styles.css">
  -->
<%@ include file="../public/commons.jspf"%>
</head>

<body>
	<script type="text/javascript">
		var d;
		var datagrid;
		var searchForm;
		var addForm;
		var role_addDialog;
		var editDialog;
		$(function() {
			datagrid = $('#jsp_admin_role_datagrid');
			searchForm = $('#jsp_admin_role_searchForm');
			addForm = $('#jsp_admin_role_addform');
			role_addDialog = $('#jsp_admin_role_adddialog');
				obj = {
						editRow : undefined,//控制第一行没有保存不能继续添加
						search : function() {
							datagrid.datagrid('load', $
									.serializeObject(searchForm.form()));
						},
						clean : function() {
							searchForm.find('input').val('');
							datagrid.datagrid('load', {});
						},
						add : function() {
							addForm.find('input').val('');
							addForm.form('load',{modifydatetime:'',pwd:'123456'});
							role_addDialog.dialog('open');
						},
						remove : function() {
							var rows = datagrid.datagrid("getChecked"); //选择了checkbox的行
							if (rows.length > 0) {
		                        $.messager.confirm('确定操作', '您真的要删除选择的'+rows.length+'记录吗？', function(flag){
		                            if (flag) {
		                                var ids = [];
		                                for (var i = 0; i < rows.length; i++) {
		                                    ids.push(rows[i].id);
		                                }
										ids = ids.join(',');
										//delete
										$.ajax({
											type : 'POST',
											url:  '${pageContext.request.contextPath}/roleAction!delete.action',
											data:{
												ids:ids //异步传入的数据
											},
											beforeSend: function(){
												datagrid.datagrid('loading');//显示进度提示信息
											},
											success:function(data){//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
												var obj = jQuery.parseJSON(data);
												if(obj.success){
													datagrid.datagrid('loaded');//隐藏进度提示信息
													datagrid.datagrid('load');//加载当前页
													datagrid.datagrid('clearChecked');//所有行取消checkbox
													$.messager.show({
													msg: obj.msg,
													});
												}else{
													datagrid.datagrid('loaded');//隐藏进度提示信息
													$.messager.show({
														msg: obj.msg,
													});
												};
											},
										});
		                            };
		                        });
		                    }else {
		                        $.messager.alert('提示', '请勾选至少一行数据', 'info');
		                    };
						},
						edit:function(){
							var rows = datagrid.datagrid("getChecked"); //选择了checkbox的行
							if (rows.length == 1) {
								editDialog=$('<div />').dialog({
									title: '角色修改',    
									iconCls:'icon-add',
									modal:true,
									resizable:true,
									width: 500,    
									height: 300,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsps/admin/roleEdit.jsp',
									buttons:[{
										text:'修改',
										iconCls:'icon-add',
										handler:function(){
											editRole();
										}
									},{
										text:'取消',
										iconCls:'icon-cancel',
										handler:function(){
											editDialog.dialog('destroy');
										}
									}],
									//注意如果使用动态加载dialog后一定要写onClose事件，否则可能造成关闭不销毁DOM节点的问题，造成内存问题
									onClose:function(){
										$(this).dialog('destroy');
									},
									onLoad :function(){
										console.info(rows[0]);
										$('#jsp_admin_roleEdit_editForm').form('load',rows[0]);
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑', 'info');
							}
						},
						unCheck :function(){
							datagrid.datagrid("clearChecked");
						},
						grantResource :function(){
							var rows = datagrid.datagrid("getChecked");
							if (rows.length == 1) {
								d=$('<div id="jsps_admin_grantResource_dialog"/>').dialog({
									title: '角色资源修改',    
									iconCls:'ext-icon-user',
									modal:true,
									resizable:true,
									width: 600,    
									height: 400,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsps/admin/grantResource.jsp',  
									buttons:[{
										text:'修改',
										iconCls:'icon-add',
										handler:function(){
											grantResource();
										}
									},{
										text:'取消',
										iconCls:'icon-cancel',
										handler:function(){
											d.dialog('close');
										}
									}],
									//注意如果使用动态加载dialog后一定要写onClose事件，否则可能造成关闭不销毁DOM节点的问题，造成内存问题
									onClose:function(){
										$(this).dialog('destroy');
									},
									onLoad :function(){
										$('#jsp_admin_grantResource_form').form('load',rows[0]);
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑用户角色', 'info');
							}
						}
					},
					datagrid.datagrid(
									{
										url : '${pageContext.request.contextPath}/roleAction!find.action',
										fit : true,
										title : '角色列表',
										iconCls : 'icon-search',
										striped : true, //显示记录斑马线（单双行记录分色显示）
										nowrap : false,//是否禁止文字自动换行 true禁止，false不禁止，适用中文
										rownumbers : true,//是否显示行号
										showHeader : true,//显示头
										showFooter : true,//显示页脚
										loadMsg : "努力加载中。。。",//加载时显示的信息,默认有可以不定义
										fitColmums : true,//分隔列
										idField : "id",//主键
										sortName : "name",
										setOrder : "asc",
										resizeHandle : 'right',
										checkOnSelect : false,
										selectOnCheck : false,
										border : false,
										columns : [ [ {
											field : 'id',
											title : '编号',
											checkbox : true
										},{
											field : 'name',
											title : '角色名称',
											sortable : true,
											//hidden:true,
											width : 100,
											align : 'right',
										}, {
											field : 'notes',
											title : '备注',
											width : 150,
											sortable : true,
											align : 'right',
										}, {
											field : 'createdatetime',
											title : '创建时间',
											sortable : true,
											width : 150,
											align : 'center',
											editor: {
												type: 'datatimebox',
											}
										}, {
											field : 'modifydatetime',
											title : '修改时间',
											sortable : true,
											width : 150,
											align : 'center',
											editor: {
						                        type: 'datetimebox',
						                    },
										}, {
											field : 'menuTexts',
											title : '资源',
											sortable : false,
											width : 150,
											align : 'center',
										} ] ],
										toolbar : '#jsp_admin_role_toolbar',
										enableHeaderClickMenu: true,        //此属性开启表头列名称右侧那个箭头形状的鼠标左键点击菜单
							            enableHeaderContextMenu: true,      //此属性开启表头列名称右键点击菜单
							            enableRowContextMenu: true,         //数据行右键菜单
							            moveMenu: true,
										//
										/*[{
											text : "增加",
											iconCls : "icon-add",
											handler : function() {
												obj.add();
											}
										}, '-', {
											text : "删除",
											iconCls : "icon-remove",
											handler : function() {
												obj.remove();
											}
										}, '-', {
											text : "修改",
											iconCls : "icon-edit",
											handler : function() {
												obj.edit();
											}
										}, '-'*/ 
										//],
										pagination : true,//页面显示导航条
										pageSize : 10,//和pageList互相呼应
										pageList : [ 10, 20, 30, 40, 50 ],//和pageSize互相呼应
										pageNumber : 1, //起始页
										pagePosition : 'bottom',//页导航条位置 top 上,bottom 下,both 上下 默认bottom
										onLoadSuccess : function(data) {
											/*
											if (data.total == 0) {
												var body = $(this).data().datagrid.dc.body2;
												body.find('table tbody').append('<tr><td width="'+ body.width()+ '" style="height: 25px; text-align: center;">没有数据  </td></tr>');
											};
											*/
										},
									}).datagrid("columnMoving");
			//使datagrid的header部分居中
			$(".datagrid-header div").css("textAlign", 'center');
			/*
			$('#jsp_admin_role_datagrid').datagrid({
			  view: myview,
			  emptyMsg: '没有记录'
			});
			*/
		});
		function editRole() {
			editForm = $('#jsp_admin_roleEdit_editForm');
			if(editForm.form('validate')){
				var url = '${pageContext.request.contextPath}/roleAction!editRole.action';
				$.post(url,$.serializeObject(editForm),function(result){
					if(result.success){
						var index = datagrid.datagrid('getRowIndex',result.obj.id);
						datagrid.datagrid("clearChecked");
						datagrid.datagrid('updateRow',{
							index: index,
							row: result.obj
						});
						editDialog.dialog('close');
					}
					$.messager.show({
						title : '提示消息',
						msg : result.msg,
						showType : 'show'
					});
				},'json');
			};
			/*
			editForm = $('#jsp_admin_roleEdit_editForm');
			editForm.form('submit', {
				url : '${pageContext.request.contextPath}/roleAction!editRole.action',
				success : function(data) {
					console.info(data);
					var obj = jQuery.parseJSON(data);
					//var obj = data;
					console.info(obj);
					var index = datagrid.datagrid('getRowIndex',obj.obj.id);
					if (obj.success) {
						datagrid.datagrid("clearChecked");
						datagrid.datagrid('updateRow',{
							index: index,
							row: obj.obj
						});
						editDialog.dialog('close');
					}
					$.messager.show({
						title : '提示消息',
						msg : obj.msg,
						showType : 'show'
					});
				}
			});
			*/
		};
		function addRole() {
			if(addForm.form('validate')){
				var url = '${pageContext.request.contextPath}/roleAction!addRole.action';
				$.post(url,$.serializeObject(addForm),function(result){
					if(result.success){
						if(datagrid.datagrid('getRows').length > 0){
							datagrid.datagrid('insertRow',{
								index: 0,	// 索引从0开始
								row: result.obj
							});
						}else{
							datagrid.datagrid('load');
						}
						role_addDialog.dialog('close');
					}
					$.messager.show({
						title : '提示消息',
						msg : result.msg,
						showType : 'show'
					});
				},'json');
			};
			/*
			addForm.form('submit', {
				url : '${pageContext.request.contextPath}/roleAction!addRole.action',
				success : function(data) {
					try{
						var obj = jQuery.parseJSON(data);
						if (obj.success) {
							if(datagrid.datagrid('getRows').length > 0){
								datagrid.datagrid('insertRow',{
									index: 0,	// 索引从0开始
									row: obj.obj
								});
							}else{
								datagrid.datagrid('load');
							}
							role_addDialog.dialog('close');
						}
						$.messager.show({
							title : '提示消息',
							msg : obj.msg,
							showType : 'show'
						});
					}catch(e){
						$.messager.alert('错误',data + e.message + e.lineNumber+'行','error');
					}
				}
			});
			*/
		};
		var grantResource = function(){
			var nodes = $('#jsp_admin_grantResource_Tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
			var ids = [];
			for (var i = 0; i < nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/roleAction!grantResource.action',
				data : {
					id : $('#jsp_admin_grantResource_form :input[name="id"]').val(),
					menuIds : ids.join(',')
				},
				dataType:'json',
				beforeSend : function() {
					//验证数据
					return $('#jsp_admin_grantResource_form').form('validate');
				},
				success : function(data) {
					//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
					var index = $('#jsp_admin_role_datagrid').datagrid('getRowIndex',data.obj.id);
					datagrid.datagrid("clearChecked");
					if (data.success) {
						$('#jsp_admin_role_datagrid').datagrid('updateRow',{
							index: index,
							row: data.obj
						});
						$('#jsp_admin_grantResource_dialog').dialog('close');
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
			});
		};
	</script>
	<div id="jsp_admin_role_layout" class="easyui-layout"
		data-options="fit:true,border:false" style="width:600px;height:400px;">
		<div
			data-options="region:'north',title:'查询条件',split:true,border:false"
			style="height:65px;">
			<form id="jsp_admin_role_searchForm"
				style="padding:5px 10px;background:#eee;" method="post">
				角色名：<input type="text" class="textbox" name="name"style="width:110px" />
					 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="obj.search();">查询</a> 
					 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-undo', plain:true" onclick="obj.clean();">清空</a>
			</form>
		</div>
		<div data-options="region:'center', border:false"
			style="background:#eee;">
			<table id="jsp_admin_role_datagrid">
			</table>
		</div>
	</div>
	<!-- class="datagrid-btn-separator" -->
	<div id="jsp_admin_role_adddialog"  class="easyui-dialog"
		data-options="title:'角色添加',iconCls:'icon-add',modal:true,closed:true,resizable:true,buttons:[{
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					addRole();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					role_addDialog.dialog('close');
				}
			}]" style="width: 500px;height: :500px;">
		<form id="jsp_admin_role_addform" method="post">
			<table style="margin-top:8px">
				<tr>
					<th style="font-size:14px;font-family: 微软雅黑">角 &nbsp;色 &nbsp;名：</th>
					<td style="font-size:14px;font-family: 微软雅黑"><input
						style="width:150px;" name="name" class="easyui-validatebox"
						data-options="required:true, missingMessage:'请输入角色名'" />
					</td>
					<th style="font-size:14px;font-family: 微软雅黑">备 &nbsp; &nbsp;
						&nbsp;&nbsp;注：</th>
					<td><input style="width:150px;"
						type="text" name="notes" class="easyui-validatebox"
						data-options="required:false, missingMessage:'请输入备注'" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="jsp_admin_role_toolbar" style="display: none;">
		<table>
			<tr>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/roleAction!addRole.action')">
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="obj.add();">增加</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/roleAction!delete.action')">
				<td><a onclick="obj.remove();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/roleAction!editRole.action')">
				<td>
				<a onclick="obj.edit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/roleAction!grantResource.action')">
				<td>
				<a onclick="obj.grantResource();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-key'">授权</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			</tr>
		</table>
	</div>
</body>
</html>
