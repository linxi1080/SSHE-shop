<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
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
    <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="../../public/commons.jspf"%>
<div class="main">
	<div class="index_nav">欢迎您登录管理后台</div>
	<div id="template" class="indexitem">
		<div class="title"><h3></h3></div>
		<div class="body"></div>
	</div>
	<!-- 首页项  -->
	<div id="index_box">
	    <div id="item1" class="item"></div>
	    <div id="item2" class="item"></div>
	    <div id="item3" class="item" style="clear:both"></div>
	    <div id="item4" class="item"></div>
	    <div id="item5" class="item"></div>
	    <div id="item6" class="item"></div>
	</div>

<script>
function addItem(title,itemurl,sort){
	$.ajax({
		url:itemurl,
		type:'POST',
		data:'ajax=yes',
		dataType:'html',
		success:function(item_html){
			createItem(item_html,title,sort);
		},
		error:function(e){
			alert("error:"+e);
		}
	});
}

function createItem(item_html,title,sort){
	var newitem = $("#template").clone();
	newitem.removeAttr("id");
	newitem.find(".title>h3").append(title);
	newitem.find(".body").append(item_html);
	$("#item"+sort).append(newitem).find("a").each(function(){
		$(this).click(function(){
			return parent.Eop.AdminUI.load($(this));
		});
	});
}
$(function(){
	<c:forEach items="${itemList}" var="item">
		//addItem('${item.title}','${ctx}${item.url}',${item.sort});
	</c:forEach>
});
</script>
  </body>
</html>
