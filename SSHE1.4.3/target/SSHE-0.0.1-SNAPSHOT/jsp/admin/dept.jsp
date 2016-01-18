<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dept.jsp' starting page</title>
    
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
			var treegrid = $('#jsp_admin_dept_treegrid');
					admin_dept_obj = {
						/*
						add : function() {
							$('#jsp_admin_deptForm_addform').find('input').val('');
							$('#jsp_admin_deptForm_addform').form('load',{modifydatetime:'',pwd:'123456'});
							$('#jsp_admin_deptForm_adddialog').dialog('open');
						},
						*/
						add:function(){
								d=$('<div id="jsp_admin_deptForm_dialog"/>').dialog({
									title: '部门添加',    
									iconCls:'icon-add',
									modal:true,
									resizable:true,
									width: 680,    
									height: 400,    
									closed: false,
									closable:true,
									cache: false,    
									href: '${pageContext.request.contextPath}/jsp/admin/deptForm.jsp',  
									buttons:[{
										text:'保存',
										iconCls:'icon-save',
										handler:function(){
											addDept(d);
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
										$('#jsp_admin_deptForm_addform').find('input').val('');
										if ($('#jsp_admin_deptEdit_iconCls').val()) {
											$('#jsp_admin_deptEdit_iconCls').attr('class',$('#jsp_admin_deptEdit_iconCls').val());
										}
									}
								});
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
											url:  '${pageContext.request.contextPath}/deptAction!delete.action',
											data:{
												ids:ids //异步传入的数据
											},
											dataType : 'json',
											beforeSend: function(){
												treegrid.treegrid('loading');//显示进度提示信息
											},
											success:function(data){//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
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
								d=$('<div id="jsp_admin_deptForm_dialog"/>').dialog({
									title: '部门修改',    
									iconCls:'icon-edit',
									modal:true,
									resizable:true,
									width: 680,    
									height: 400,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsp/admin/deptForm.jsp',  
									buttons:[{
										text:'修改',
										iconCls:'icon-add',
										handler:function(){
											editDept(d);
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
										//console.info(rows);
										$('#jsp_admin_deptForm_form').form('load',rows);
										if(rows.stateId==='Y'){
											$('#jsp_admin_deptForm_stateId').val('Y');
											$('#jsp_admin_deptForm_stateIdCheckbox').attr("checked",true);
										}else{
											$('#jsp_admin_deptForm_stateId').val('N');
											$('#jsp_admin_deptForm_stateIdCheckbox').removeAttr("checked",false);
										}
										if ($('#jsp_admin_deptEdit_iconCls').val()) {
											$('#jsp_admin_deptEdit_iconCls').attr('class',$('#jsp_admin_deptEdit_iconCls').val());
										}
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑', 'info');
							}
						},
						//折叠
						collapse:function(){
							var jsp_admin_dept_treegrid = $('#jsp_admin_dept_treegrid');
							var node = jsp_admin_dept_treegrid.treegrid('getSelected');							
							if(node){
								jsp_admin_dept_treegrid.treegrid('collapse',node.id);
							}else{
								jsp_admin_dept_treegrid.treegrid('collapseAll');
							}
						},
						//展开
						expand:function(){
							var jsp_admin_dept_treegrid = $('#jsp_admin_dept_treegrid');
							var node = jsp_admin_dept_treegrid.treegrid('getSelected');							
							if(node){
								jsp_admin_dept_treegrid.treegrid('expand',node.id);
							}else{
								jsp_admin_dept_treegrid.treegrid('expandAll');
							}
						}
					},
					treegrid.treegrid(
									{
										animate:true,
										collapsible:true,
										method: 'post',
										treeField:'name',
										//
										url : '${pageContext.request.contextPath}/deptAction!getTreeGrid.action',
										fit : true,
										title : '部门机构列表',
										iconCls : 'icon-search',
										striped : true, //显示记录斑马线（单双行记录分色显示）
										nowrap : true,//是否禁止文字自动换行 true禁止，false不禁止，适用中文
										rownumbers : true,//是否显示行号
										showHeader : true,//显示头
										showFooter : false,//显示页脚
										loadMsg : "努力加载中。。。",//加载时显示的信息,默认有可以不定义
										fitColmums : true,//分隔列
										idField : "id",//主键
										sortName : "name",
										setOrder : "asc",
										resizeHandle : 'right',
										checkOnSelect : false,
										singleSelect : true,
										border : false,
										columns : [ [ {
											field : 'name',
											title : '部门名称',
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
										}, {
											field : 'deptNo',
											title : '部门编码',
											width : 100,
											sortable : true,
											align : 'left',
										}, {
											field : 'address',
											title : '部门机构地址',
											sortable : true,
											width : 100,
											align : 'center',
										}, {
											field : 'notes',
											title : '备注',
											sortable : true,
											width : 150,
											align : 'left'
										}, {
											field : 'stateId',
											title : '状态',
											width : 60,
											align : 'center',
											formatter : function(value,
													row, index) {
												var stateId = row.stateId;
				                                var id = stateId + index;
				                                if(value==='Y'){
				                                	return '<input type="checkbox" readonly="readonly" id='+id+' checked "/>';
				                                }else{
													return '<input type="checkbox" readonly="readonly" id='+id+'/>';
				                                }
											}
										} ] ],
										toolbar : 'jsp_admin_dept_toolbar',
											/*
											[ {
											text : "增加",
											iconCls : "icon-add",
											handler : function() {
												admin_dept_obj.add();
											}
										}, '-', {
											text : "删除",
											iconCls : "icon-remove",
											handler : function() {
												admin_dept_obj.remove();
											}
										}, '-', {
											text : "修改",
											iconCls : "icon-edit",
											handler : function() {
												admin_dept_obj.edit();
											}
										}, '-', {
											text : "保存",
											iconCls : "icon-save",
											handler : function() {
												admin_dept_obj.save();
											}
										}, '-',{
											text:"展开",
											iconCls:"ext-icon-resultset_next",
											handler:function(){
												admin_dept_obj.expand();
											}
										},{
											text :"折叠",
											iconCls:"ext-icon-resultset_previous",
											handler:function(){
												admin_dept_obj.collapse();
											}
										}],
										*/
										onLoadSuccess : function(row, data) {
											///*
											if (data.total == 0) {
												$.messager.show({
													title : '提示消息',
													msg : "没有查询到数据",
													showType : 'show'
												});
												var body = $(this).data().datagrid.dc.body2;
												body.find('#jsp_admin_dept_treegrid tbody').append(
																'<tr><td width="' + body.width() + '" style="height: 25px; text-align: center;">没有数据  </td></tr>');
											}
											//*/
										},
									}).datagrid("columnMoving");
			//使datagrid的header部分居中
			$(".datagrid-header div").css("textAlign", 'center');
		});
	  var selectIcon1 = function() {
			var a = selectIcon();
			$('#jsp_admin_deptForm_iconCls').attr('class',a);
			$('#jsp_admin_deptForm_iconCls').val(a);
			$('#jsp_admin_iconsSelect_dialog').dialog('close');
		};
	  function addDept(d){
		  var treegrid = $('#jsp_admin_dept_treegrid');
			var editform = $('#jsp_admin_deptForm_form');
			if(editform.form('validate')){
				var url = '${pageContext.request.contextPath}/deptAction!add.action';
				$.post(url,$.serializeObject(editform),function(result){
					if(result.success){
						if (result.success) {
							// 插入一个新节点到选择的节点之前
							var node = treegrid.treegrid('find',result.obj.pid);
							if (node){
								treegrid.treegrid('append', {
									parent: node.id,
									data: [result.obj]
								});
							}
							d.dialog('close');
						}
					}
					$.messager.show({
						title : '提示消息',
						msg : result.msg,
						showType : 'show'
					});
				},'json');
			}
	  };
	  function editDept(){
		  var treegrid = $('#jsp_admin_dept_treegrid');
			var editform = $('#jsp_admin_deptForm_form');
			if(editform.form('validate')){
				var url = '${pageContext.request.contextPath}/deptAction!edit.action';
				$.post(url,$.serializeObject(editform),function(result){
					if(result.success){
						if (result.success) {
							treegrid.treegrid("unselectAll");
							treegrid.treegrid('updateRow', {
								index: result.obj.id,
								row: result.obj
							});
							d.dialog('close');
						}
					}
					$.messager.show({
						title : '提示消息',
						msg : result.msg,
						showType : 'show'
					});
				},'json');
			}
	  }
	  </script>
    <div id="jsp_admin_dept_layout" class="easyui-layout"
		data-options="fit:true,border:false" style="width:600px;height:400px;">
		<div data-options="region:'center', border:false"
			style="background:#eee;">
			<table id=jsp_admin_dept_treegrid>
			 <tbody></tbody>
			</table>
		</div>
	</div>
	<div id="jsp_admin_dept_toolbar" style="display: none;">
		<table>
			<tr>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/deptAction!addUser.action')">
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="admin_dept_obj.add();">增加</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/deptAction!delete.action')">
				<td><a onclick="admin_dept_obj.remove();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/deptAction!editUser.action')">
				<td>
				<a onclick="admin_dept_obj.edit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<td>
			<a onclick="admin_dept_obj.expand();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_next'">展开</a>
			</td>
			<td><div class="datagrid-btn-separator"></div></td>
			<td>
			<a onclick="admin_dept_obj.collapse();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-resultset_previous'">折叠</a>
			</td>
			<td><div class="datagrid-btn-separator"></div></td>
			</tr>
		</table>
	</div>
  </body>
</html>
