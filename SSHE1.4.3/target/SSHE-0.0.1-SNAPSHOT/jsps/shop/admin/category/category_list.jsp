<!--
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>商品分类</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
	<%@ include file='../../../public/commons.jspf' %>
</head>
<body>
	<div class="main">
		<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!addUI.action') | #session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!savaOrder.action')">
			<div class="buttonArea">
				<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!addUI.action')">
					<a href="javascript:void(0)" class="button blueButton easyui-linkbutton"
						data-options="iconCls:'icon-add',plain:true" onclick="append()">添加分类</a>
				</s:if>
				<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!savaOrder.action')">
					<a href="javascript:void(0)" class="button easyui-linkbutton" data-options="iconCls:'ext-icon-control_equalizer_blue',plain:false" id="submitform">保存排序</a>
				</s:if>
			</div>
		</s:if>
		<form action="" id="jsps_shop_admin_category_category_list_catform">
			<table class="easyui-treegrid"
				id="jsps_shop_admin_category_category_list_cattable"
				data-options="url:'${pageContext.request.contextPath}/common/categoryAction!getTreeGrid.action',fitColumns:'true',idField:'id',treeField:'text',rownumbers:true,striped:true,resizeHandle :'right',">
				<thead>
					<tr>
						<th data-options="field:'id',width:80,checkbox:true,">ID</th>
						<th data-options="field:'text',width:200">名称</th>
						<th data-options="field:'typeName',width:100">类型</th>
						<th data-options="field:'categoryOrder',width:80,height:60" formatter="formatGoodscount">排序</th>
						<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!addChildrenUI.action')">
						<th data-options="field:'add',width:25,align:'center'" formatter="formatAdd">添加子</th>
						</s:if>
						<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!editUI.action')">
						<th data-options="field:'edit',width:25,align:'center'" formatter="formatEdit">编辑</th>
						</s:if>
						<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!delete.action')">
						<th data-options="field:'delete',width:25,align:'center'" formatter="formatDelete">删除</th>
						</s:if>
					</tr>
				</thead>
			</table>
		</form>
		<div id="divdia" style="display: none;"></div>
		<script type="text/javascript">
			function formatAdd(value, row, index) {
				var val = "<a href='javascript:void(0);' class='add' onclick='append("
						+ '"'+row.id+'"'
						+ ",1)'><img src='images/transparent.gif'></a>";
				return val;
			}
			function formatEdit(value, row, index) {
				var val = "<a class='edit' title='修改' href='javascript:void(0);' onclick='append("
						+ '"'+row.id+'"' + ",2)' ></a>";
				return val;
			}
			function formatDelete(value, row, index) {
				var val = '<a href="javascript:;" class="delete" onclick="del('
						+ "'"+row.id+"'"
						+ ')"><img catid="'+row.cat_id+'" src="images/transparent.gif"></a>';
				return val;
			}
			function append(id, obj) {
				var map = {}; // Map map = new HashMap();
				if (!id) {
					map["href"] = "${pageContext.request.contextPath}/common/categoryAction!addUI.action";
					map["formId"] = "#jsps_shop_admin_category_categoryEdit_addForm";
					map["url"] = "${pageContext.request.contextPath}/common/categoryAction!add.action";
					map["title"] = "添加分类";
					map["loadshow"] = "正在添加......";
				} else {
					if (obj == 1) {
						map["href"] = "${pageContext.request.contextPath}/common/categoryAction!addChildrenUI.action?id=" + id;
						map["formId"] = "#jsps_shop_admin_category_categoryEdit_addchildrenForm";
						map["url"] = "${pageContext.request.contextPath}/common/categoryAction!add.action";
						map["title"] = "添加子列表";
						map["loadshow"] = "正在添加......";
					} else {
						map["href"] = "${pageContext.request.contextPath}/common/categoryAction!editUI.action?id=" + id;
						map["formId"] = "#jsps_shop_admin_category_categoryEdit_editForm";
						map["url"] = "${pageContext.request.contextPath}/common/categoryAction!edit.action";
						map["title"] = "修改分类";
						map["loadshow"] = "正在修改......";
					}

				}
				map["divDialog"] = "#divdia";
				map["gridreload"] = "#jsps_shop_admin_category_category_list_cattable";
				addDialog(map);
			}
			function addDialog(map) {
				$(map["divDialog"]).show();
				$(map["divDialog"]).dialog({
					title : map["title"],
					width : 520,
					height : 450,
					closed : false,
					cache : false,
					href : map["href"],
					modal : true,
					buttons : [ {
						text : '保存',
						iconCls : 'icon-ok',
						handler : function() {
							var savebtn = $(this);
							var disabled=savebtn.hasClass("l-btn-disabled");
							if(!disabled){
								 submitForm(map,savebtn);
							}
						}
					}, {
						text : '清空',
						iconCls:'icon-clear',
						handler : function() {
							clearForm(map);
						}
					} ],
					//注意如果使用动态加载dialog后一定要写onClose事件，否则可能造成关闭不销毁DOM节点的问题，造成内存问题
					/*onClose:function(){
						$(this).dialog('destroy');
					},*/
				});
			}
			function submitForm(map,savebtn) {
				var formflag = $(map["formId"]).form().form('validate');
				if (formflag) {
					$.Loading.show("正在保存请稍后...");
					savebtn.linkbutton("disable");	
					var options = {
						url : map["url"],
						type : "POST",
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$(map["divDialog"]).dialog('close');
								$(map["gridreload"]).treegrid('reload');
								$.Loading.success(result.msg);
							}
							if (!result.success) {
								$.Loading.error(result.msg);
								/*$.messager.show({
									title : '提示消息',
									msg : result.msg,
									showType : 'show'
								});*/
							}
							savebtn.linkbutton("enable");
						},
						error : function(e) {
							console.info(e);
							$.Loading.error("出现错误 ，请重试"+e);
							savebtn.linkbutton("enable");
						}
					};
					$(map["formId"]).ajaxSubmit(options);
				}else{
					savebtn.linkbutton("enable");
					$.Loading.hide();
				}
			}
			
			function del(id) {
				if (!confirm("确定要删除此类别吗？")) {
					return;
				}
				$.Loading.show("正在删除......");
				$.ajax({
					url : "${pageContext.request.contextPath}/common/categoryAction!delete.action?id=" + id,
					type : "POST",
					dataType : 'json',
					success : function(result) {
						if (result.success) {
							$.Loading.success(result.msg);
							$("#jsps_shop_admin_category_category_list_cattable").treegrid('reload');
						}
						if (!result.success) {
							$.Loading.error(result.message);
						}
					},
					error : function(e) {
						$.Loading.error("出现错误 ，请重试");
					}
				});

			}
			function formatGoodscount(value, row, index) {
				var val = '<input type="text" class="receiptsInputText" autocomplete="off" value="'+value+'" style="width:50px" name="categoryOrders">';
				val+='<input type="hidden" name="ids" value="'+row.id+'" > '
				return val;
			}
			function clearForm(map) {
				$(map["formId"]).form('clear');
			}
			$(function(){
				$("#submitform").click(function(){
					$.Loading.show('正在保存排序，请稍侯...');
					var options = {
							url :"${pageContext.request.contextPath}/common/categoryAction!savaOrder.action",
							type : "POST",
							dataType : 'json',
							success : function(result) {				
							 	if(result.success){
							 		$.Loading.success(result.msg);
							 		$("#jsps_shop_admin_category_category_list_cattable").treegrid('reload');
							 	}else{
							 		alert(result.msg);
							 	}
							},
							error : function(e) {
								$.Loading.hide();
								alert("出错啦:(");
			 				}
			 		};
			 
					$("#jsps_shop_admin_category_category_list_catform").ajaxSubmit(options);
				})
			})
		</script>
	</div>
</body>
</html>
