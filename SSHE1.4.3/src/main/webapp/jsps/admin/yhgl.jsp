<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="lh.util.SecurityUtil"%>
<%
	String contextPath = request.getContextPath();
	SecurityUtil securityUtil = new SecurityUtil(session);
%>
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
		var jsp_admin_yhgl_uploader;
		function uploadObject(){
			jsp_admin_yhgl_uploader = new plupload.Uploader({/*上传插件定义*/
				browse_button : 'jsp_admin_yhgl_pickfiles',/*选择文件的按钮*/
				container : 'jsp_admin_yhgl_container',//文件上传容器
				runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
				url : '<%=basePath%>plupload?fileFolder=/userPhoto',//上传文件路径
				
				max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
				chunk_size : '10mb',//分块大小，小于这个大小的不分块
				unique_names : true,//生成唯一文件名
				// 指定要浏览的文件类型
				filters : [ {
					title : '图片文件',
					extensions : 'jpg,gif,png'
				} ]
			});
			jsp_admin_yhgl_uploader.bind('Init', function(up, params) {//初始化时
				//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
				$('#jsp_admin_yhgl_filelist').html("");
			});
			jsp_admin_yhgl_uploader.bind('BeforeUpload', function(jsp_admin_yhgl_uploader, file) {//上传之前
				if (jsp_admin_yhgl_uploader.files.length > 1) {
					parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
					jsp_admin_yhgl_uploader.stop();
					return;
				}
				$('.ext-icon-cross').hide();
			});
			jsp_admin_yhgl_uploader.bind('FilesAdded', function(up, files) {//选择文件后
				$.each(files, function(i, file) {
					$('#jsp_admin_yhgl_filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="jsp_admin_yhgl_uploader.removeFile(jsp_admin_yhgl_uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
				});
				up.refresh();
			});
			jsp_admin_yhgl_uploader.bind('UploadProgress', function(up, file) {//上传进度改变
				var msg;
				if (file.percent == 100) {
					msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
				} else {
					msg = file.percent;
				}
				$('#' + file.id + '>strong').html(msg + '%');
				/*
				parent.sy.progressBar({//显示文件上传滚动条
					title : '文件上传中...',
					value : msg
				});
				*/
			});
			jsp_admin_yhgl_uploader.bind('Error', function(up, err) {//出现错误
				$('#jsp_admin_yhgl_filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
				up.refresh();
			});
			jsp_admin_yhgl_uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
				var response = $.parseJSON(info.response);
				if (response.status) {
					$('#' + file.id + '>strong').html("100%");
					//console.info('yhglEdit');
					//console.info(response.fileUrl);
					$(':input[name="photo"]').val(response.fileUrl);
				}
			});
			jsp_admin_yhgl_uploader.init();
		};
		$(function() {
			var datagrid = $('#jsp_admin_yhgl_datagrid');
			var searchForm = $('#jsp_admin_yhgl_searchForm');
					yhglobj = {
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
							$('#jsp_admin_yhgl_addform').find('input').val('');
							$('#jsp_admin_yhgl_addform').form('load',{modifydatetime:'',pwd:'123456'});
							$('#jsp_admin_yhgl_adddialog').dialog('open');
							uploadObject();
							submitFormAdd();
						},
						remove : function() {
							var rows = datagrid.datagrid("getChecked"); //选择了checkbox的行
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
											url:  '${pageContext.request.contextPath}/userAction!delete.action',
											//url:  '${pageContext.request.contextPath}/userAction!removePhoto.action',
											data:{
												ids:ids //异步传入的数据
											},
											beforeSend: function(){
												datagrid.datagrid('loading');//显示进度提示信息
											},
											success:function(data){//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
												var obj = jQuery.parseJSON(data);
												if(obj.success){
													datagrid.datagrid('load');//加载当前页
													datagrid.datagrid('clearChecked');//所有行取消checkbox
												}
												datagrid.datagrid('loaded');//隐藏进度提示信息
												$.messager.show({
													msg: obj.msg,
													});
											},
										});
		                            }
		                        });
		                    }
		                    else {
		                        $.messager.alert('提示', '请勾选至少一行数据', 'info');
		                    }
						},
						edit:function(){
							var rows = datagrid.datagrid("getChecked"); //选择了checkbox的行
							//console.info(rows);
							if (rows.length == 1) {
								d=$('<div id="jsp_admin_yhglEdit_dialog"/>').dialog({
									title: '用户修改',    
									iconCls:'icon-edit',
									modal:true,
									resizable:true,
									width: 650,    
									height: 450,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsps/admin/yhglEdit.jsp',  
									buttons:[{
										text:'修改',
										iconCls:'icon-edit',
										handler:function(){
											//editUser(d);
											submitForm();
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
										$('#jsp_admin_yhglEdit_editForm').form('load',rows[0]);
										if (rows[0].photo){
											$('#jsp_admin_yhglEdit_photo').attr('src', rows[0].photo);
										}
										var datestr = formatDatebox(new Date());
										$('#jsp_admin_yhglEdit_modifydatetime').datetimebox('setValue', datestr);
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑', 'info');
							}
						},
						unCheck :function(){
							datagrid.datagrid("clearChecked");
						},
						grantRole:function(){
							var rows = datagrid.datagrid("getChecked");
							if (rows.length == 1) {
								d=$('<div id="jsp_admin_grantRole_dialog"/>').dialog({
									title: '用户角色修改',    
									iconCls:'ext-icon-user',
									modal:true,
									resizable:true,
									width: 600,    
									height: 400,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsp/admin/grantRole.jsp',  
									buttons:[{
										text:'修改',
										iconCls:'icon-add',
										handler:function(){
											grantRole();
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
										$('#jsp_admin_grantRole_form').form('load',rows[0]);
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑用户角色', 'info');
							}
						}
					},
					datagrid.datagrid(
									{
										url : '${pageContext.request.contextPath}/userAction!find.action',
										fit : true,
										title : '用户列表',
										iconCls : 'icon-search',
										striped : true, //显示记录斑马线（单双行记录分色显示）
										nowrap : true,//是否禁止文字自动换行 true禁止，false不禁止，适用中文
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
										frozenColumns : [ [
												{
													field : 'id',
													title : '编号',
													checkbox : true
												},
												{
													field : 'name',
													title : '用户名',
													width : 100,
													sortable : true,
													formatter : function(value,
															row, index) {
														return '<span title="['+row.id+']'+value+'">'
																+ value
																+ '</span>';
													},
													editor : {
														type : 'validatebox',
														options : {
															required : true,
															missingMessage : '请输入用户名'
														}
													}
												} ] ],
										border : false,
										columns : [ [{
											field : 'loginName',
											title : '登录名',
											width : 100,
											sortable : true,
											formatter : function(value,
													row, index) {
												return '<span title="['+row.id+']'+value+'">'
														+ value
														+ '</span>';
											},
											editor : {
												type : 'validatebox',
												options : {
													required : true,
													missingMessage : '请输入登录名'
												}
											}
										},{
											field : 'pwd',
											title : '密码',
											sortable : true,
											//hidden:true,
											width : 100,
											align : 'right',
											formatter : function(value,
													row, index) {
												return '******';
											},
										}, {
											width : '50',
											title : '性别',
											field : 'gender',
											sortable : true,
											formatter : function(value, row, index) {
												switch (value) {
												case '0':
													return '女';
												case '1':
													return '男';
												}
											}
										}, {
											field : 'createdatetime',
											title : '建立时间',
											width : 150,
											sortable : true,
											align : 'center',
											editor: {
						                        type: 'datetimebox',
						                        options: {
						                            required: true,
						                        }
						                    },
										}, {
											field : 'modifydatetime',
											title : '修改时间',
											sortable : true,
											width : 150,
											align : 'center',
											editor: {
						                        type: 'datetimebox',
						                        options: {
						                            required: true,
						                        }
						                    },
										}, {
											field : 'photo',
											title : '照片',
											sortable : true,
											width : 150,
											align : 'center',
											formatter : function(value,
													row, index) {
												if (value){
													return '<span title="['+value+']'+value+'">' + value + '</span>';
												}
											/*显示图片
												if (value){
													return '<img src="'+value+'" style="width: 200px; height: 200px;">';
												};
											*/
											}
										}, {
											field : 'trolesName',
											title : '角色',
											sortable : true,
											width : 150,
											align : 'center',
										} ] ],
										toolbar : '#jsp_admin_yhgl_toolbar',
											/*
											[ {
											text : "增加",
											iconCls : "icon-add",
											handler : function() {
												yhglobj.add();
												//添加到最后一行
												//            var addRows = $("#dg").datagrid('getRows');
												//            $("#dg").datagrid("appendRow", {
												//                            row: {//代码初始值
												//                            }
												//                        });
												//            console.info(addRows.length);
												//            $("#dg").datagrid('beginEdit',addRows.length - 1);
											}
										}, '-', {
											text : "删除",
											iconCls : "icon-remove",
											handler : function() {
												yhglobj.remove();
											}
										}, '-', {
											text : "修改",
											iconCls : "icon-edit",
											handler : function() {
												
												yhglobj.edit();
											}
										}, '-', {
											text : "保存",
											iconCls : "icon-save",
											handler : function() {
												yhglobj.save();
											}
										}, '-', {
											text : "取消编辑",
											iconCls : "icon-redo",
											handler : function() {
												//obj.redo();
												yhglobj.unCheck();
											}
										}, '-', {
											text : "用户角色",
											iconCls : "ext-icon-user",
											handler : function() {
												yhglobj.grantRole();
											}
										} ],
										*/
										pagination : true,//页面显示导航条
										pageSize : 10,//和pageList互相呼应
										pageList : [ 10, 20, 30, 40, 50 ],//和pageSize互相呼应
										pageNumber : 1, //起始页
										pagePosition : 'bottom',//页导航条位置 top 上,bottom 下,both 上下 默认bottom
										moveMenu :true,
										onLoadSuccess : function(data) {
											if (data.total == 0) {
												var body = $(this).data().datagrid.dc.body2;
												body
														.find('table tbody')
														.append(
																'<tr><td width="'
																		+ body
																				.width()
																		+ '" style="height: 25px; text-align: center;">没有数据  </td></tr>');
											}
										},
									});
			//使datagrid的header部分居中
			$(".datagrid-header div").css("textAlign", 'center');
			datagrid.datagrid("columnMoving");//列移动
		});
		function editUser(d) {
			var editform = $('#jsp_admin_yhglEdit_editForm');
			var datagrid = $('#jsp_admin_yhgl_datagrid');
			if(editform.form('validate')){
				var url = '${pageContext.request.contextPath}/userAction!editUser.action';
				$.post(url,$.serializeObject(editform),function(result){
					if(result.success){
						var index = $('#jsp_admin_yhgl_datagrid').datagrid('getRowIndex',result.obj.id);
						$('#jsp_admin_yhgl_datagrid').datagrid("clearChecked");
						$('#jsp_admin_yhgl_datagrid').datagrid('updateRow',{
							index: index,
							row: result.obj
						});
						d.dialog('close');					
					}
					$.messager.show({
						title : '提示消息',
						msg : result.msg,
						showType : 'show',
					});
				},'json');
			}
			/*
			editform.form('submit', {
				url : '${pageContext.request.contextPath}/userAction!editUser.action',
				success : function(data) {
					console.info(data);
					var obj = jQuery.parseJSON(data);
					console.info(obj);
					if (obj.success) {
						var index = $('#jsp_admin_yhgl_datagrid').datagrid('getRowIndex',obj.obj.id);
						$('#jsp_admin_yhgl_datagrid').datagrid("clearChecked");
						$('#jsp_admin_yhgl_datagrid').datagrid('updateRow',{
							index: index,
							row: obj.obj
						});
						d.dialog('close');
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
		function addUser() {
			var jsp_admin_yhgl_addform = $('#jsp_admin_yhgl_addform');
			var jsp_admin_yhgl_adddialog = $('#jsp_admin_yhgl_adddialog');
			/*
			//
			jsp_admin_yhgl_addform.form('submit', {
				url : '${pageContext.request.contextPath}/userAction!addUser.action',
				success : function(data) {
					try{
						var obj = jQuery.parseJSON(data);
						if (obj.success) {
							$('#jsp_admin_yhgl_datagrid').datagrid('insertRow',{
								index: 0,	// 索引从0开始
								row: obj.obj
							});
							jsp_admin_yhgl_adddialog.dialog('close');
						}
						$.messager.show({
							title : '提示消息',
							msg : obj.msg,
							showType : 'show'
						});
					}catch(e){
						$.messager.alert('错误',data + e.message + e.lineNumber+'行','error');
					}finally{
						jsp_admin_yhgl_uploader.destroy();
					}
				}
			});
			*/
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/userAction!addUser.action',
				data : jsp_admin_yhgl_addform.serialize(),
				dataType:'json',
				beforeSend : function() {
					//验证数据
					return jsp_admin_yhgl_addform.form('validate');
				},
				success : function(data) {
					//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
					//var obj = jQuery.parseJSON(data);
					if (data.success) {
						$('#jsp_admin_yhgl_datagrid').datagrid('insertRow',{
							index: 0,	// 索引从0开始
							row: data.obj
						});
						jsp_admin_yhgl_adddialog.dialog('close');
					}
					// 消息将显示在顶部中间
					$.messager.show({
						title : '提示消息',
						msg : data.msg,
						showType : 'show'
					});
					jsp_admin_yhgl_uploader.destroy();
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.alert('错误',XMLHttpRequest.responseText,'error');
					//$.messager.show({
					//	title : '提示消息',
					//	msg : XMLHttpRequest.status+' '+textStatus,
					//	showType : 'show'
					//});
				}
			});
		};
		//
		var submitForm = function($dialog, $grid, $pjq) {
			if ($('form').form('validate')) {
				if (jsp_admin_yhglEdit_uploader.files.length > 0) {
					jsp_admin_yhglEdit_uploader.start();
					jsp_admin_yhglEdit_uploader.bind('StateChanged', function(uploader) {// 在所有的文件上传完毕时，提交表单
						if (jsp_admin_yhglEdit_uploader.files.length === (jsp_admin_yhglEdit_uploader.total.uploaded + jsp_admin_yhglEdit_uploader.total.failed)) {
							editUser(d);
						}
					});
				} else {
					editUser(d);
				}
			}
		};
		var submitFormAdd = function() {
			if ($('form').form('validate')) {
				if (jsp_admin_yhgl_uploader.files.length > 0) {
					jsp_admin_yhgl_uploader.start();
					jsp_admin_yhgl_uploader.bind('StateChanged', function(uploader) {// 在所有的文件上传完毕时，提交表单
						if (jsp_admin_yhgl_uploader.files.length === (jsp_admin_yhgl_uploader.total.uploaded + jsp_admin_yhgl_uploader.total.failed)) {
							addUser();
						}
					});
				} else {
					addUser();
				}
			}
		};
		var grantRole = function(){
			//var editform = $('#jsp_admin_grantRole_Form');
			var nodes = $('#jsp_admin_grantRole_roleTree').tree('getChecked', [ 'checked', 'indeterminate' ]);
			var ids = [];
			for (var i = 0; i < nodes.length; i++) {
				ids.push(nodes[i].id);
			}
			$.ajax({
				type : 'POST',
				url : '${pageContext.request.contextPath}/userAction!grantRole.action',
				data : {
					id : $('#jsp_admin_grantRole_form :input[name="id"]').val(),
					trolesId : ids.join(',')
				},
				dataType:'json',
				beforeSend : function() {
					//验证数据
					return $('#jsp_admin_grantRole_form').form('validate');
				},
				success : function(data) {
					//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
					console.info(data.obj.id);
					var index = $('#jsp_admin_yhgl_datagrid').datagrid('getRowIndex',data.obj.id);
					console.info(data.obj);
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
			});
			/*
			editform.form('submit', {
				url : '${pageContext.request.contextPath}/userAction!grantRole.action',
				success : function(data) {
					var obj = jQuery.parseJSON(data);
					var index = $('#jsp_admin_yhgl_datagrid').datagrid('getRowIndex',obj.obj.id);
					if (obj.success) {
						$('#jsp_admin_yhgl_datagrid').datagrid("clearChecked");
						$('#jsp_admin_yhgl_datagrid').datagrid('updateRow',{
							index: index,
							row: obj.obj
						});
						d.dialog('close');
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
	</script>
	<div id="jsp_admin_yhgl_layout" class="easyui-layout"
		data-options="fit:true,border:false" style="width:600px;height:400px;">
		<div
			data-options="region:'north',title:'查询条件',split:true,border:false"
			style="height:65px;">
			<form id="jsp_admin_yhgl_searchForm"
				style="padding:5px 10px;background:#eee;" method="post">
				用户名：<input type="text" class="textbox" name="name"
					style="width:110px" /> 创建时间从：<input type="text" name="date_from"
					class="easyui-datetimebox" data-options="editable:false"
					style="width:150px" /> 到：<input type="text" name="date_to"
					class="easyui-datetimebox" data-options="editable:false"
					style="width:150px" /> <a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="iconCls:'icon-search', plain:true"
					onclick="yhglobj.search();">查询</a> <a href="javascript:void(0);"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-undo', plain:true"
					onclick="yhglobj.clean();">清空</a>
			</form>
		</div>
		<div data-options="region:'center', border:false"
			style="background:#eee;">
			<table id="jsp_admin_yhgl_datagrid">
			</table>
		</div>
	</div>
	<!-- class="datagrid-btn-separator" -->
	<div id="jsp_admin_yhgl_adddialog"  class="easyui-dialog"
		data-options="title:'用户添加',iconCls:'icon-add',modal:true,closed:true,resizable:true,buttons:[{
				text:'添加',
				iconCls:'icon-add',
				handler:function(){
					submitFormAdd();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#jsp_admin_yhgl_adddialog').dialog('close');
					jsp_admin_yhgl_uploader.destroy();
				}
			}]" style="width: 650px;height: :450px;">
		<form id="jsp_admin_yhgl_addform" method="post">
		<table style="margin-top:8px;" class="table">
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">登 &nbsp;录 &nbsp;名：</th>
				<td style="font-size:14px;font-family: 微软雅黑"><input
					style="width:150px;" name="loginName" class="easyui-validatebox input_text"
					data-options="required:true, missingMessage:'请输入用户名'" />
				</td>
				<th style="font-size:14px;font-family: 微软雅黑">密 &nbsp; &nbsp;
					&nbsp;&nbsp;码：</th>
				<td><input style="width:150px;" id="user_reg_password"
					type="password" name="pwd" class="easyui-validatebox"
					data-options="required:true, missingMessage:'请输入密码'" 
					value="123456"/>
				</td>
			</tr>
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">用户名：</th>
				<td><input style="width:150px;" type="text"
					name="name" class="easyui-validatebox"
					data-options="required:true,editable:true,missingMessage:'请输入用户名'" />
				</td>
				<th>性别</th>
				<td>
					<select class="easyui-combobox" name="gender" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="1">男</option>
							<option value="0">女</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>照片上传</th>
				<td><div id="jsp_admin_yhgl_container">
						<a id="jsp_admin_yhgl_pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
						<div id="jsp_admin_yhgl_filelist">您的浏览器没有安装Flash插件，或不支持HTML5！</div>
					</div></td>
				<th></th>
				<td><input name="photo" readonly="readonly" style="display: none;" /> 
				<img id="jsp_admin_yhgl_photo" src="" style="width: 200px; height: 200px;"></td>
			</tr>
		</table>
		</form>
	</div>
	<div id="jsp_admin_yhgl_toolbar" style="display: none;">
		<table>
			<tr>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/userAction!addUser.action')">
				<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="yhglobj.add();">增加</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/userAction!delete.action')">
				<td><a onclick="yhglobj.remove();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除</a></td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/userAction!editUser.action')">
				<td>
				<a onclick="yhglobj.edit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">修改</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/userAction!grantRole.action')">
				<td>
				<a onclick="yhglobj.grantRole();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'ext-icon-key'">授权</a>
				</td>
				<td><div class="datagrid-btn-separator"></div></td>
			</s:if>
			</tr>
		</table>
	</div>
</body>
</html>
