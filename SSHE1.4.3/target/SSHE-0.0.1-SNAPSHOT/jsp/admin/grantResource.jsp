<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'grantRole.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <script type="text/javascript">
	    $(function() {
			$('#jsp_admin_grantResource_Tree').tree({
				url : '${pageContext.request.contextPath}/menuAction!getAllTreeNode.action',
				parentField : 'pid',
				checkbox : true,
				formatter : function(node) {
					return node.text;
				},
				onLoadSuccess : function(node, data) {
					$.post('${pageContext.request.contextPath}/menuAction!doNotNeedGrant_getRoleByRoleId.action', {
						id : $('#jsp_admin_grantResource_form :input[name="id"]').val()
					}, function(result) {
						console.info(result);
						if (result) {
							for (var i = 0; i < result.menuIds.length; i++) {
								var node = $('#jsp_admin_grantResource_Tree').tree('find', result.menuIds[i]);
								if (node) {
									var isLeaf = $('#jsp_admin_grantResource_Tree').tree('isLeaf', node.target);
									if (isLeaf) {
										$('#jsp_admin_grantResource_Tree').tree('check', node.target);
									}
								}
							}
						}
						parent.$.messager.progress('close');
					}, 'json');
				}
			});
		});
    </script>
  	<form id="jsp_admin_grantResource_form" method="post">
	    <input name="id"  readonly="readonly" type="hidden" />
		<fieldset>
			<legend>所属资源</legend>
			<ul id="jsp_admin_grantResource_Tree"></ul>
		</fieldset>
	</form>
  </body>
</html>
