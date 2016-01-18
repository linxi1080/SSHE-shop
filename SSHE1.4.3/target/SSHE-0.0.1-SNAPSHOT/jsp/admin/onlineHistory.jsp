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

<title>My JSP 'onlineHistory.jsp' starting page</title>

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
			var datagrid = $('#jsp_admin_onlineHistory_datagrid');
			var searchForm = $('#jsp_admin_onlineHistory_searchForm');
					onlineHistoryobj = {
						editRow : undefined,//控制第一行没有保存不能继续添加
						search : function() {
							datagrid.datagrid('load', $
									.serializeObject(searchForm.form()));
						},
						clean : function() {
							searchForm.find('input').val('');
							datagrid.datagrid('load', {});
						},
						exportExcle: function(){
							//导出Excel文件
				            //getExcelXML有一个JSON对象的配置，配置项看了下只有title配置，为excel文档的标题
				            var data = datagrid.datagrid('getExcelXml', { title: 'datagrid import to excel' }); //获取datagrid数据对应的excel需要的xml格式的内容
				            //用ajax发动到动态页动态写入xls文件中
				            //'${pageContext.request.contextPath}/onlineAction!delete.action',
				            //excelHelperExportTest
				            var url = '${pageContext.request.contextPath}/onlineAction!doNotNeedGrant_report.action'; //如果为asp注意修改后缀
				            //console.info(data);
				            $.ajax({ url: url, data: { data: data }, type: 'POST', dataType: 'text',
				                success: function (fn) {
				                	//console.info(fn);
				                    alert('导出excel成功！');
				                    //window.location = fn; //执行下载操作
				                },
				                error: function (xhr) {
									//console.info(xhr);
				                    alert('动态页有问题\nstatus：' + xhr.status + '\nresponseText：' + xhr.responseText)
				                }
				            });
				            return false;
						},
						remove : function() {
							var rows = datagrid.datagrid("getChecked"); //选择了checkbox的行
							if (rows.length > 0) {
								$.messager.confirm(
												'确定操作',
												'您真的要删除选择的记录吗？',
												function(flag) {
													if (flag) {
														var ids = [];
														for ( var i = 0; i < rows.length; i++) {
															ids.push(rows[i].id);
														}
														ids = ids.join(',');
														//delete
														$.ajax({
																	type : 'POST',
																	url : '${pageContext.request.contextPath}/onlineAction!delete.action',
																	data : {
																		ids : ids
																	//异步传入的数据
																	},
																	beforeSend : function() {
																		datagrid.datagrid('loading');//显示进度提示信息
																	},
																	success : function(
																			data) {//这里的data是后台返回的值或数据，本例中data返回影响的数据记录行数
																		var obj = jQuery.parseJSON(data);
																		if (obj.success) {
																			datagrid.datagrid('load');//加载当前页
																			datagrid.datagrid('clearChecked');//所有行取消checkbox
																		}
																		datagrid.datagrid('loaded');//隐藏进度提示信息
																		$.messager.show({
																					msg : obj.msg,
																				});
																	},
																});
													}
												});
							} else {
								$.messager.alert('提示', '请勾选至少一行数据', 'info');
							}
						},
					},
					datagrid.datagrid(
									{
										url : '${pageContext.request.contextPath}/onlineAction!find.action',
										fit : true,
										title : '用户登录历史列表',
										iconCls : 'icon-search',
										striped : true, //显示记录斑马线（单双行记录分色显示）
										nowrap : true,//是否禁止文字自动换行 true禁止，false不禁止，适用中文
										rownumbers : true,//是否显示行号
										showHeader : true,//显示头
										showFooter : true,//显示页脚
										loadMsg : "努力加载中。。。",//加载时显示的信息,默认有可以不定义
										fitColmums : true,//分隔列
										idField : "id",//主键
										sortName : "createdatetime",
										setOrder : "desc",
										resizeHandle : 'right',
										checkOnSelect : false,
										selectOnCheck : false,
										border : false,
										columns : [ [
												{
													field : 'id',
													title : '编号',
													checkbox : true
												},
												{
													field : 'loginname',
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
												},
												{
													field : 'createdatetime',
													title : '创建时间',
													width : 150,
													sortable : true,
													align : 'center',
													sortable : true,
													editor : {
														type : 'datetimebox',
														options : {
															required : true,
														}
													},
												},
												{
													field : 'ip',
													title : 'IP地址',
													width : 150,
													sortable : true,
													align : 'center'
												},
												{
													field : 'type',
													title : '类型',
													width : 150,
													align : 'center',
													sortable : true,
													formatter : function(value,
															row, index) {
														if (value === '1') {
															return '登录';
														} else {
															return '注销';
														}
													},
												} ] ],
										toolbar : '#jsp_admin_onlineHistory_toolbar',
										pagination : true,//页面显示导航条
										pageSize : 30,//和pageList互相呼应
										pageList : [ 10, 20, 30, 40, 50,100,150 ],//和pageSize互相呼应
										pageNumber : 1, //起始页
										pagePosition : 'bottom',//页导航条位置 top 上,bottom 下,both 上下 默认bottom
										onLoadSuccess : function(data) {
											if (data.total == 0) {
												var body = $(this).data().datagrid.dc.body2;
												body
														.find('table tbody')
														.append(
																'<tr><td width="'+ body.width()+ '" style="height: 25px; text-align: center;">没有数据  </td></tr>');
											}
										},
									}).datagrid("columnMoving");
			//使datagrid的header部分居中
			$(".datagrid-header div").css("textAlign", 'center');
		});
	</script>
	<div id="jsp_admin_onlineHistory_layout" class="easyui-layout"
		data-options="fit:true,border:false" style="width:600px;height:400px;">
		<div
			data-options="region:'north',title:'查询条件',split:true,border:false,collapsible:false"
			style="height:65px;">
			<form id="jsp_admin_onlineHistory_searchForm"
				style="padding:5px 10px;background:#eee;" method="post">
				<table>
					<tr>
						<td>登录名</td>
						<td><input name="loginname" class="textbox"
							style="width: 80px;" /></td>
						<td>IP地址</td>
						<td><input name="ip" class="textbox" style="width: 80px;" />
						</td>
						<td>类别</td>
						<td><select name="type" class="easyui-combobox"
							data-options="panelHeight:'auto',editable:false"><option
									value="" style="width: 50px;">请选择</option>
								<option value="1">登录</option>
								<option value="0">注销</option>
						</select>
						</td>
						<td>创建时间</td>
						<td><input type="text" name="datefrom"
							class="easyui-datetimebox" data-options="editable:false"
							style="width:150px" />-<input type="text" name="dateto"
							class="easyui-datetimebox" data-options="editable:false"
							style="width:150px" />
						</td>
						<td><a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'ext-icon-zoom',plain:true"
							onclick="onlineHistoryobj.search();">过滤</a><a
							href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'ext-icon-zoom_out',plain:true"
							onclick="onlineHistoryobj.clean();">重置过滤</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center', border:false"
			style="background:#eee;">
			<table id="jsp_admin_onlineHistory_datagrid">
			</table>
		</div>
	</div>
	<div id="jsp_admin_onlineHistory_toolbar" style="display: none;">
		<table>
			<tr>
				<s:if
					test="#session.sessionInfo.getSecurityUtil().havePermission('/onlineAction!delete.action')">
					<td><a onclick="onlineHistoryobj.remove();"
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'icon-remove'">删除</a>
					</td>
					<td><div class="datagrid-btn-separator"></div>
					</td>
				</s:if>
				<td><a onclick="onlineHistoryobj.exportExcle();"
						href="javascript:void(0);" class="easyui-linkbutton"
						data-options="plain:true,iconCls:'ext-icon-page_white_excel'">导出EXCEL</a>
					</td>
					<td><div class="datagrid-btn-separator"></div>
					</td>
			</tr>
		</table>
	</div>
</body>
</html>
