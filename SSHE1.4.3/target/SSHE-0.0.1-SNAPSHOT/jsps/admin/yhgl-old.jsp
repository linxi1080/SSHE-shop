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
		var uploader;//上传对象
		$(function() {
			uploader = new plupload.Uploader({/*上传插件定义*/
				browse_button : 'pickfiles',/*选择文件的按钮*/
				container : 'container',//文件上传容器
				runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
				//flash_swf_url : sy.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
				url : '<%=basePath%>plupload?fileFolder=/userPhoto',//上传文件路径
				
				max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
				chunk_size : '10mb',//分块大小，小于这个大小的不分块
				unique_names : true,//生成唯一文件名
				// 如果可能的话，压缩图片大小
				//*/
				/*resize : {
					width : 320,
					height : 240,
					quality : 90
				},*/
				///*
				// 指定要浏览的文件类型
				filters : [ {
					title : '图片文件',
					extensions : 'jpg,gif,png'
				} ]
			});
			uploader.bind('Init', function(up, params) {//初始化时
				//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
				$('#filelist').html("");
			});
			uploader.bind('BeforeUpload', function(uploader, file) {//上传之前
				if (uploader.files.length > 1) {
					parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
					uploader.stop();
					return;
				}
				$('.ext-icon-cross').hide();
			});
			uploader.bind('FilesAdded', function(up, files) {//选择文件后
				$.each(files, function(i, file) {
					$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
				});
				up.refresh();
			});
			uploader.bind('UploadProgress', function(up, file) {//上传进度改变
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
			uploader.bind('Error', function(up, err) {//出现错误
				$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
				up.refresh();
			});
			uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
				var response = $.parseJSON(info.response);
				if (response.status) {
					$('#' + file.id + '>strong').html("100%");
					console.info(response.fileUrl);
					//console.info(file.name);
					//$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
					//$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
					console.info(response.fileUrl);
					$('#jsp_admin_yhglEdit_editForm :input[name="photo"]').val(response.fileUrl);
					$(':input[name="photo"]').val(response.fileUrl);
				}
			});
			uploader.init();
			var datagrid = $('#jsp_admin_yhgl_datagrid');
			var searchForm = $('#jsp_admin_yhgl_searchForm');
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
							$('#jsp_admin_yhgl_addform').find('input').val('');
							$('#jsp_admin_yhgl_addform').form('load',{modifydatetime:'',pwd:'123456'});
							$('#jsp_admin_yhgl_adddialog').dialog('open');
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
												}
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
							if (rows.length == 1) {
								d=$('<div id="jsp_admin_yhglEdit_dialog"/>').dialog({
									title: '用户修改',    
									iconCls:'icon-add',
									modal:true,
									resizable:true,
									width: 500,    
									height: 300,    
									closed: false,    
									cache: false,    
									href: '${pageContext.request.contextPath}/jsp/admin/yhglEdit.jsp',  
									buttons:[{
										text:'修改',
										iconCls:'icon-add',
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
									}
								});
							}else{
								$.messager.alert('提示', '请勾选一行数据进行编辑', 'info');
							}
						},
						unCheck :function(){
							datagrid.datagrid("clearChecked");
						}
					},
					datagrid.datagrid(
									{
										url : '${pageContext.request.contextPath}/userAction!find.action',
										fit : true,
										title : '用户列表',
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
										frozenColumns : [ [
												{
													field : 'id',
													title : '编号',
													checkbox : true
												},
												{
													field : 'name',
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
															missingMessage : '请输入用户名'
														}
													}
												} ] ],
										border : false,
										columns : [ [ {
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
											align : 'center'
										} ] ],
										toolbar : [ {
											text : "增加",
											iconCls : "icon-add",
											handler : function() {
												obj.add();
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
												obj.remove();
											}
										}, '-', {
											text : "修改",
											iconCls : "icon-edit",
											handler : function() {
												obj.edit();
											}
										}, '-', {
											text : "保存",
											iconCls : "icon-save",
											handler : function() {
												obj.save();
											}
										}, '-', {
											text : "取消编辑",
											iconCls : "icon-redo",
											handler : function() {
												//obj.redo();
												obj.unCheck();
											}
										}, '-' ],
										pagination : true,//页面显示导航条
										pageSize : 10,//和pageList互相呼应
										pageList : [ 10, 20, 30, 40, 50 ],//和pageSize互相呼应
										pageNumber : 1, //起始页
										pagePosition : 'bottom',//页导航条位置 top 上,bottom 下,both 上下 默认bottom
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
									}).datagrid("columnMoving");
			//使datagrid的header部分居中
			$(".datagrid-header div").css("textAlign", 'center');
			;
		});
		function editUser(d) {
			var editform = $('#jsp_admin_yhglEdit_editForm');
			//var editdialog = $('#jsp_admin_yhglEdit_dialog');
			//$('#jsp_admin_yhgl_datagrid').datagrid("clearChecked");
			editform.form('submit', {
				url : '${pageContext.request.contextPath}/userAction!editUser.action',
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
		};
		function addUser() {
			console.info("addUser");
			var jsp_admin_yhgl_addform = $('#jsp_admin_yhgl_addform');
			var jsp_admin_yhgl_adddialog = $('#jsp_admin_yhgl_adddialog');
			//
			jsp_admin_yhgl_addform.form('submit', {
				url : '${pageContext.request.contextPath}/userAction!addUser.action',
				success : function(data) {
					var obj = jQuery.parseJSON(data);
					if (obj.success) {
						//$('#jsp_admin_yhgl_datagrid').datagrid('load');
						//$('#jsp_admin_yhgl_datagrid').datagrid('appendRow',obj.obj);
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
				}
			});
			/*
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
					console.info(data);
					//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
					//var obj = jQuery.parseJSON(data);
					if (data.success) {
						jsp_admin_yhgl_adddialog.dialog('close');
					}
					// 消息将显示在顶部中间
					$.messager.show({
						title : '提示消息',
						msg : data.msg,
						showType : 'show'
					});
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$.messager.show({
						title : '提示消息',
						msg : XMLHttpRequest.status+' '+textStatus,
						showType : 'show'
					});
				}
			});
			*/
		};
		//
		var submitForm = function($dialog, $grid, $pjq) {
			if ($('form').form('validate')) {
			    console.info("uploader before");
				console.info(uploader);
				if (uploader.files.length > 0) {
					console.info("if");
					uploader.start();
					uploader.bind('StateChanged', function(uploader) {// 在所有的文件上传完毕时，提交表单
						if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
							editUser(d);
							//submitNow($dialog, $grid, $pjq);
						}
					});
				} else {
					console.info("else");
					editUser(d);
					//submitNow($dialog, $grid, $pjq);
				}
			}
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
					style="width:150px" /> <a href="#" class="easyui-linkbutton"
					data-options="iconCls:'icon-search', plain:true"
					onclick="obj.search();">查询</a> <a href="#"
					class="easyui-linkbutton"
					data-options="iconCls:'icon-undo', plain:true"
					onclick="obj.clean();">清空</a>
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
				text:'注册',
				iconCls:'icon-add',
				handler:function(){
					addUser();
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$('#jsp_admin_yhgl_adddialog').dialog('close');
				}
			}]" style="width: 500px;height: :500px;">
		<form id="jsp_admin_yhgl_addform" method="post">
		<table style="margin-top:8px">
			<tr>
				<th style="font-size:14px;font-family: 微软雅黑">登 &nbsp;录 &nbsp;名：</th>
				<td style="font-size:14px;font-family: 微软雅黑"><input
					style="width:150px;" name="name" class="easyui-validatebox"
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
				<th style="font-size:14px;font-family: 微软雅黑">建立时间：</th>
				<td><input style="width:150px;" type="text"
					name="createdatetime" class="easyui-datetimebox"
					data-options="required:false,editable:false,missingMessage:'请输入建立时间'" readonly="readonly" />
				</td>
				<th style="font-size:14px;font-family: 微软雅黑">修改时间：</th>
				<td><input style="width:150px;" type="text"
					name="modifydatetime" class="easyui-datetimebox"
					data-options="required:true,editable:false,missingMessage:'请输入修改时间'" />
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>
