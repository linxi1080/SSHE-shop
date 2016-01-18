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

<title>My JSP 'resource.jsp' starting page</title>

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
		$(function() {
			$('.iconImg').attr('src', lh.pixel_0);
			var treegrid = $('#jsp_admin_resource_treegrid');
			var searchForm = $('#jsp_admin_resource_searchForm');
					admin_resource_obj = {
						editRow : undefined,//控制第一行没有保存不能继续添加
						search : function() {
							treegrid.datagrid('load', $
									.serializeObject(searchForm.form()));
						},
						clean : function() {
							searchForm.find('input').val('');
							treegrid.datagrid('load', {});
						},
						add : function() {
							$('#jsp_admin_resource_addform').find('input').val('');
							$('#jsp_admin_resource_addform').form('load',{modifydatetime:'',pwd:'123456'});
							$('#jsp_admin_resource_adddialog').dialog('open');
						},
						remove : function() {
							var rows = treegrid.treegrid("getSelections"); //选择了getSelected的行
							if (rows.length > 0) {
		                        $.messager.confirm('确定操作', '您真的要删除选择的记录吗？', function(flag){
		                            if (flag) {
		                                var ids = [];
		                                for (var i = 0; i < rows.length; i++) {
		                                    ids.push(rows[i].id);
		                                }
										ids = ids.join(',');
										//delete
										$.ajax({
											type : 'POST',
											url:  '${pageContext.request.contextPath}/menuAction!delete.action',
											data:{
												ids:ids //异步传入的数据
											},
											dataType : 'json',
											beforeSend: function(){
												treegrid.treegrid('loading');//显示进度提示信息
											},
											success:function(data){//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
												//var obj = jQuery.parseJSON(data);
												//console.info(obj);
												if(data.success){
													treegrid.treegrid('loaded');//隐藏进度提示信息
													treegrid.treegrid('reload');//加载当前页
													treegrid.treegrid('clearSelections');//所有行取消clearSelections
													$.messager.show({
														msg: data.msg,
													});
												}else{
													treegrid.treegrid('loaded');//隐藏进度提示信息
													$.messager.show({
														msg: data.msg,
													});
												}
											},
											error : function(XMLHttpRequest, textStatus, errorThrown) {
												treegrid.treegrid('loaded');//隐藏进度提示信息
												$.messager.alert('错误',XMLHttpRequest.responseText,'error');
											}
										});
		                            }
		                        });
		                    }
		                    else {
		                        $.messager.alert('提示', '请勾选至少一行数据', 'info');
		                    }
						},
						edit:function(){
							var rows = treegrid.datagrid("getSelected"); //选择了需要修改的行
							if (rows) {
								d=$('<div id="jsp_admin_resourceEdit_dialog"/>').dialog({
									title: '资源修改',    
									iconCls:'icon-add',
									modal:true,
									resizable:true,
									width: 680,    
									height: 400,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsp/admin/resourceEdit.jsp',  
									buttons:[{
										text:'修改',
										iconCls:'icon-add',
										handler:function(){
											editResource(d);
										}
									},{
										text:'取消',
										iconCls:'icon-cancel',
										handler:function(){
											d.dialog('close');
											//$('#jsp_admin_yhglEdit_dialog').dialog('close');
										}
									}],
									//注意如果使用动态加载dialog后一定要写onClose事件，否则可能造成关闭不销毁DOM节点的问题，造成内存问题
									onClose:function(){
										$(this).dialog('destroy');
									},
									onLoad :function(){
										$('#jsp_admin_resourceEdit_editForm').form('load',rows);
										if ($('#jsp_admin_resourceEdit_iconCls').val()) {
											$('#jsp_admin_resourceEdit_iconCls').attr('class',$('#jsp_admin_resourceEdit_iconCls').val());
										}
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑', 'info');
							}
						},
						unCheck :function(){
							treegrid.datagrid("clearChecked");
						},
						//折叠
						collapse:function(){
							var jsp_admin_resource_treegrid = $('#jsp_admin_resource_treegrid');
							var node = jsp_admin_resource_treegrid.treegrid('getSelected');							
							if(node){
								jsp_admin_resource_treegrid.treegrid('collapse',node.id);
							}else{
								jsp_admin_resource_treegrid.treegrid('collapseAll');
							}
						},
						//展开
						expand:function(){
							var jsp_admin_resource_treegrid = $('#jsp_admin_resource_treegrid');
							var node = jsp_admin_resource_treegrid.treegrid('getSelected');	
							console.info(node);
							if(node){
								jsp_admin_resource_treegrid.treegrid('expand',node.id);
							}else{
								jsp_admin_resource_treegrid.treegrid('expandAll');
							}
						}
					},
					treegrid.treegrid(
									{
										animate:true,
										collapsible:true,
										method: 'post',
										treeField:'text',
										//
										url : '${pageContext.request.contextPath}/menuAction!getTreeGrid.action',
										fit : true,
										title : '资源列表',
										iconCls : 'icon-search',
										striped : true, //显示记录斑马线（单双行记录分色显示）
										nowrap : true,//是否禁止文字自动换行 true禁止，false不禁止，适用中文
										rownumbers : true,//是否显示行号
										showHeader : true,//显示头
										showFooter : false,//显示页脚
										loadMsg : "努力加载中。。。",//加载时显示的信息,默认有可以不定义
										fitColmums : true,//分隔列
										idField : "id",//主键
										sortName : "text",
										setOrder : "asc",
										resizeHandle : 'right',
										checkOnSelect : false,
										singleSelect : true,
										border : false,
										columns : [ [ {
											field : 'text',
											title : '资源名称',
											width : 200,
											sortable : true,
											formatter : function(value,
													row, index) {
												return '<span title="['+row.id+']'+value+'">'
														+ value
														+ '</span>';
											}
										},{
											field : 'iconCls',
											title : '图标名称',
											sortable : true,
											//hidden:true,
											width : 100,
											align : 'left',
											/*
											formatter:function(value,row,index){
												if(value){
													return '<img class="iconImg '+value+'" value="'+value+'" src="'+lh.pixel_0+'" />';
												}
											}
											*/
										}, {
											field : 'url',
											title : '资源路径',
											width : 150,
											sortable : true,
											align : 'left',
										}, {
											field : 'useFlag',
											title : '使用标志',
											sortable : true,
											width : 60,
											align : 'center',
										}, {
											field : 'tresourceTypeId',
											title : '资源类型',
											sortable : true,
											width : 150,
											align : 'left',
											formatter : function(value, row) {
												//console.info(value);
												//console.info(row);
												if(value){
													return row.tresourceTypeName;
												}
											}
										}, {
											field : 'notes',
											title : '资源描述',
											width : 200,
											align : 'left'
										}, {
											field : 'seqNo',
											title : '序号',
											hidden : true,
											width : 200,
											align : 'left'
										} ] ],
										toolbar :'#jsp_admin_resource_toolbar',
										/*
											[ {
											text : "增加",
											iconCls : "icon-add",
											handler : function() {
												admin_resource_obj.add();
											}
										}, '-', {
											text : "删除",
											iconCls : "icon-remove",
											handler : function() {
												admin_resource_obj.remove();
											}
										}, '-', {
											text : "修改",
											iconCls : "icon-edit",
											handler : function() {
												admin_resource_obj.edit();
											}
										}, '-', {
											text : "保存",
											iconCls : "icon-save",
											handler : function() {
												admin_resource_obj.save();
											}
										}, '-', {
											text : "取消编辑",
											iconCls : "icon-redo",
											handler : function() {
												//obj.redo();
												admin_resource_obj.unCheck();
											}
										}, '-',{
											text:"展开",
											iconCls:"ext-icon-resultset_next",
											handler:function(){
												admin_resource_obj.expand();
											}
										},{
											text :"折叠",
											iconCls:"ext-icon-resultset_previous",
											handler:function(){
												admin_resource_obj.collapse();
											}
										},'-' ],
										*/
										pagination : true,//页面显示导航条
										pageSize : 10,//和pageList互相呼应
										pageList : [ 10, 20, 30, 40, 50 ],//和pageSize互相呼应
										pageNumber : 1, //起始页
										pagePosition : 'bottom',//页导航条位置 top 上,bottom 下,both 上下 默认bottom
										onLoadSuccess : function(row, data) {
											/*
											if (data.total == 0) {
												var body = $(this).data().treegrid.dc.body2;
												body.find('table tbody').append(
																'<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据  </td></tr>');
											}
											*/
										},
									}).datagrid("columnMoving");
			//使datagrid的header部分居中
			$(".datagrid-header div").css("textAlign", 'center');
		});
		function editResource(d) {
			var treegrid = $('#jsp_admin_resource_treegrid');
			var editform = $('#jsp_admin_resourceEdit_editForm');
			if(editform.form('validate')){
				var url = '${pageContext.request.contextPath}/menuAction!edit.action';
				$.post(url,$.serializeObject(editform),function(result){
					if(result.success){
						if (result.success) {
							treegrid.treegrid("unselectAll");
							treegrid.treegrid('updateRow',{
								index: result.obj.id,
								row: result.obj
							});
							d.dialog('close');
						}
						$.messager.show({
							title : '提示消息',
							msg : result.msg,
							showType : 'show'
						});
					}
				},'json');
			}
			/*
			editform.form('submit', {
				url : '${pageContext.request.contextPath}/menuAction!edit.action',
				success : function(data) {
					console.info(data);
					try{
						var obj = jQuery.parseJSON(data);
						if (obj.success) {
							treegrid.treegrid("unselectAll");
							treegrid.treegrid('updateRow',{
								index: obj.obj.id,
								row: obj.obj
							});
							d.dialog('close');
						}
						$.messager.show({
							title : '提示消息',
							msg : obj.msg,
							showType : 'show'
						});
					}catch(e){
						$.messager.alert('错误',data + e.message + e.lineNumber+'行','error');
						//alert('错误' + e.message + '发生在' + e.lineNumber + '行');
					}
				}
			});
			*/
		};
		function addResource() {
			var jsp_admin_resource_addform = $('#jsp_admin_resource_addform');
			var jsp_admin_resource_adddialog = $('#jsp_admin_resource_adddialog');
			var treegrid = $('#jsp_admin_resource_treegrid');
			if(jsp_admin_resource_addform.form('validate')){
				var url = '${pageContext.request.contextPath}/menuAction!add.action';
				$.post(url,$.serializeObject(jsp_admin_resource_addform),function(result){
					if (result.success) {
						// 插入一个新节点到选择的节点之前
						var node = treegrid.treegrid('find',result.obj.pid);
						if (node){
							treegrid.treegrid('append', {
								parent: node.id,
								data: [result.obj]
							});
						}
						jsp_admin_resource_adddialog.dialog('close');
					}
					$.messager.show({
						title : '提示消息',
						msg : result.msg,
						showType : 'show'
					});
				},'json');
			}
			//
			/*
			jsp_admin_resource_addform.form('submit', {
				url : '${pageContext.request.contextPath}/menuAction!add.action',
				success : function(data) {
					try{
						var obj = jQuery.parseJSON(data);
						if (obj.success) {
							// 插入一个新节点到选择的节点之前
							var node = treegrid.treegrid('find',obj.obj.pid);
							console.info(obj.obj);
							if (node){
								treegrid.treegrid('append', {
									parent: node.id,
									data: [obj.obj]
								});
							}
							jsp_admin_resource_adddialog.dialog('close');
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
						dialog.dialog('close');
						//$('#jsp_admin_iconsSelect_dialog').dialog('close');
					}
				}],
				//注意如果使用动态加载dialog后一定要写onClose事件，否则可能造成关闭不销毁DOM节点的问题，造成内存问题
				onClose:function(){
					$(this).dialog('destroy');
				},
			});
		};
		var selectIcon1 = function() {
			var a = selectIcon();
			$('#jsp_admin_resource_iconCls').attr('class',a);
			$('#jsp_admin_resource_iconCls').val(a);
			$('#jsp_admin_iconsSelect_dialog').dialog('close');
		};
	</script>
	<div id="jsp_admin_resource_layout" class="easyui-layout"
		data-options="fit:true,border:false" style="width:600px;height:400px;">
		<div data-options="region:'center', border:false"
			style="background:#eee;">
			<table id=jsp_admin_resource_treegrid>
			</table>
		</div>
	</div>
	<!-- class="datagrid-btn-separator" -->
	<div id="jsp_admin_resource_adddialog"  class="easyui-dialog"
		data-options="title:'资源添加',iconCls:'icon-add',modal:true,closed:true,resizable:true,buttons:[{
				text:'确定',
				iconCls:'icon-add',
				handler:function(){
					addResource();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#jsp_admin_resource_adddialog').dialog('close');
				}
			}]" style="width: 680px;height: :500px;">
		<form id="jsp_admin_resource_addform" method="post">
		<fieldset>
				<legend>资源基本信息</legend>
				<table style="margin-top:8px" class="table">
					<tr>
						<th style="font-size:14px;font-family: 微软雅黑;" >编 &nbsp;
							&nbsp;码</th>
						<td style="font-size:14px;font-family: 微软雅黑;"><input 
							style="width:150px;" name="id" class="easyui-validatebox"
							data-options="required:true, missingMessage:'请输入编码'" /></td>
						<th style="font-size:14px;font-family: 微软雅黑">资源名称</th>
						<td style="font-size:14px;font-family: 微软雅黑"><input
							style="width:150px;" name="text" class="easyui-validatebox"
							data-options="required:true, missingMessage:'请输入资源名称'" /></td>
					</tr>
					<tr>
						<th style="font-size:14px;font-family: 微软雅黑">资源路径</th>
						<td><input style="width:150px;"
							type="text" name="url"
							data-options="required:true, missingMessage:'请输入资源路径'"
							/></td>
						<th style="font-size:14px;font-family: 微软雅黑">资源类型</th>
						<td><input style="width:150px;" type="text"
							name="tresourceTypeId" class="easyui-combobox"
							data-options="required:true,editable:false,valueField:'id',textField:'name',url:'${pageContext.request.contextPath}/resourceTypeAction!doNotNeedGrant_find.action',panelHeight:'auto'"
							/></td>
					</tr>
					<tr>
						<th>上级资源</th>
						<td>
							<select id="pid" name="pid" class="easyui-combotree" data-options="editable:false,idField:'id',textField:'text',parentField:'pid',url:'<%=path%>/menuAction!doNotNeedGrant_getAllTreeNode.action'" style="width: 155px;">
							</select>
							<img class="iconImg ext-icon-cross" onclick="$('#syresource_id').combotree('clear');" title="清空" /></td>
						<th>资源图标</th>
						<td><input id="jsp_admin_resource_iconCls" name="iconCls" readonly="readonly" style="padding-left: 18px; width: 134px;" />
						<img class="iconImg ext-icon-zoom" onclick="showIcons();" title="浏览图标" />&nbsp;
						<img class="iconImg ext-icon-cross" onclick="$('#iconCls').val('');$('#iconCls').attr('class','');" title="清空" />
						</td>
					</tr>
					<tr>
						<th>顺序</th>
						<td><input name="seqNo" class="easyui-numberspinner" data-options="required:true,min:0,max:100000,editable:true" style="width: 155px;" value="100" /></td>
						<th>资源描述</th>
						<td><textarea name="notes"></textarea></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="jsp_admin_resource_toolbar" style="display: none;">
		<table>
			<tr>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/menuAction!add.action')">
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="admin_resource_obj.add();">增加</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/menuAction!delete.action')">
				<td><a onclick="admin_resource_obj.remove();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/menuAction!edit.action')">
				<td>
				<a onclick="admin_resource_obj.edit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<td>
			<a onclick="admin_resource_obj.expand();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_next'">展开</a>
			</td>
			<td><div class="datagrid-btn-separator"></div></td>
			<td>
			<a onclick="admin_resource_obj.collapse();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_previous'">折叠</a>
			</td>
			<td><div class="datagrid-btn-separator"></div></td>
			</tr>
		</table>
	</div>
</body>
</html>
