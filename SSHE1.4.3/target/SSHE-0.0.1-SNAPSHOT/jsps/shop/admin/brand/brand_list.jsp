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

<title>品牌列表</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%@ include file='../../../public/commons.jspf' %>
</head>

<body>
	<div class="main">
		<form id="jsps_shop_admin_brand_brand_list_brandform">
			<div class='buttonArea'>
				<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!addUI.action') | #session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!savaOrder.action')">
					<div style="float:left">
						<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!addUI.action')">
							<a href="javascript:void(0)" class="button blueButton easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
							onclick="append()">添加</a> 
						</s:if>
						<s:if test="#session.sessionInfo.getSecurityUtil().havePermission('/common/categoryAction!addUI.action')">
							<a href="javascript:void(0)"
								class="button easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="del()">删除</a>
						</s:if>
					</div>
				</s:if>
				<span style="float: right;"> <span id="jsps_shop_admin_brand_brand_list_simpleSearch">
						<a href="javascript:void(0)" class="button b_fr"
						data-options="plain:true" onclick="searchBrand()">搜索</a> <input
						id="keyword" class="input_text b_fr mr5" type="text" value=""
						size="30" placeholder="请输入模糊关键字" name="searchKeyWord"> </span> </span>
				</div>
			<div class="clear height10"></div>
			<div class="shadowBoxWhite tableDiv">
				<table class="easyui-datagrid"
					data-options="url:'brand!listJson.do',pageList: [5,10,15,20],pageSize:${pageSize},fitColumns:'true'"
					pagination="true" id="jsps_shop_admin_brand_brand_list_branddata">
					<thead>
						<tr>
							<th data-options="field:'id',checkbox:true,width:100"></th>
							<th data-options="field:'note',width:200">品牌名称</th>
							<th data-options="field:'sortNo',width:300">排序</th>
							<th data-options="field:'url',width:300">品牌网址</th>
							<th data-options="field:'action',width:25"
								formatter="formatOperation">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</form>
		<div id="jsps_shop_admin_brand_brand_list_addBrand"></div>
		<script type="text/javascript">
		var app_path = "";
		function append(brandid) {
			var map = {};
			if (!brandid) {
				map["href"] = "brand!add.do";
				map["formId"] = "#jsps_shop_admin_brand_brand_list_brandform";
				map["url"] = "brand!save.do?ajax=yes";
				map["title"] = "添加品牌";
			} else {
				map["href"] = "brand!edit.do?brandId=" + brandid;
				map["formId"] = "#jsps_shop_admin_brand_brand_list_brandEditForm";
				map["url"] = "brand!saveEdit.do?ajax=yes";
				map["title"] = "修改品牌";
			}
			map["divDialog"] = "#jsps_shop_admin_brand_brand_list_addBrand";
			map["gridreload"] = "#jsps_shop_admin_brand_brand_list_branddata";

			addDialog(map);
		}
		function addDialog(map) {
			$(map["divDialog"]).show();
			$(map["divDialog"]).dialog({
				title : map["title"],
				width : 800,
				top:10,
				height : 400,
				closed : false,
				cache : false,
				href : map["href"],
				modal : true,
				onLoad : function() {
					var instance = CKEDITOR.instances['brief'];
					if (instance) {
						CKEDITOR.remove(instance);
					}
					$('#brief').ckeditor();
				},
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
				} ]
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
						if (result.result == 1) {
							$.Loading.success(result.message);
							$(map["divDialog"]).dialog('close');
							$(map["gridreload"]).datagrid('reload');
						}
						if (result.result == 0) {
							$.Loading.error(result.message);
						}
						savebtn.linkbutton("enable");
					},
					error : function(e) {
						$.Loading.error("出现错误 ，请重试");
						savebtn.linkbutton("enable");
					}
				};
				$(map["formId"]).ajaxSubmit(options);
			}
		}

		//清空
		function clearForm(map) {
			$(map["formId"]).form('clear');
		}

		//操作
		function formatOperation(value, row, index) {
			var val = "<a class='edit' title='修改' href='javascript:void(0);' onclick='append("
					+ row.brand_id + ")'></a>";
			return val;
		}

		//删除
		function del() {
			var rows = $('#branddata').datagrid("getSelections");
			if (rows.length < 1) {
				$.Loading.error('请选择要删除的品牌');
				return;
			}
			if (!confirm("确认要将这些品牌删除吗？")) {
				return;
			}
			var options = {
				url : "brand!delete.do?ajax=yes",
				type : "POST",
				dataType : 'json',
				success : function(result) {
					if (result.result == 1) {
						$.Loading.success('删除成功');
						$('#branddata').datagrid('reload');
					}
					if (result.result == 0) {
						$.Loading.error(result.message);
					}
				},
				error : function(e) {
					$.Loading.error("出现错误，请重试");
				}
			};
			$('#jsps_shop_admin_brand_brand_list_brandform').ajaxSubmit(options);
		}
		
		function searchBrand(){
			var keyword = $("#keyword").val();

			$("#jsps_shop_admin_brand_brand_list_branddata").datagrid('load', {
				 keyword:keyword
		    });
		}
	</script>
	</div>
</body>
</html>
