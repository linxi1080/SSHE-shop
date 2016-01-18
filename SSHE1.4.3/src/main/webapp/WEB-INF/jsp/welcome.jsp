<!-- 
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'welcome.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<!-- 
	<%@ include file="../../jsps/public/commons.jspf"%>
	-->
  </head>
  
  <body>
   登陆11<br>  
	<@s.form action="/common/login!login.action">  
	 <@s.textfield name="username" label="用户名"/>  
	 <@s.textfield name="password" label="密码"/>  
	 <@s.submit value="提交"/>  
	</@s.form>  
	提示：${name1}=${name2} <br/>
	提示2：${username!"空用户"}=${password!"空密码"} <br/>
	<#--
	-->
    ${username!?length} <br/>
	<#--Welcome ${user}<#if user == "Big Joe">, our beloved leader</#if>!
    -->
	提示3：<#if (username!?length==0)>
	      	空用户名 <br/>
	      <#else>
	      	${username}  名<br/>
	      </#if>
	提示33：<#if username??>
	        <#--username有值-->
	      	${username}  名<br/>
	      <#else>
	        <#--username无值-->
	      	空用户名 <br/>
	      </#if>
	 <#---->
	 提示4：<#if (2>3)>
	         2大于3
	      <#else>
	         2小于3
	      </#if>
	<br/>
	提示5:
	 <#list roleList as role>
        ${role.name} '|'
        ${role.notes} <br/>
 	 </#list>
  </body>
</html>
