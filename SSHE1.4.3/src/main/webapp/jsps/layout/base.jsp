<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'base.jsp' starting page</title>
    
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
    <%@ include file="../public/taglibs.jsp"%>
<table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tbody>        
          <tr>
            <th width="80px"><span>站点名称：</span></th>
            <td><div style="width:120px;height:18px;overflow:hidden"><a title="${siteInfo.sitename}" class="imp" href="${ctx }/core/admin/user/userSite!edit.do?id=${site.id}">${ site.sitename}</a></div></td>
          </tr>
          <tr>
            <th><span>最后登录：</span></th>
            <td><span><html:dateformat time="${siteInfo.lastlogin*1000}" pattern="yyyy-MM-dd HH:mm:ss"></html:dateformat></span></td>
          </tr>
          <tr>
            <th><span>创建时间：</span></th>
            <td><span><html:dateformat time="${siteInfo.createtime*1000}" pattern="yyyy-MM-dd HH:mm:ss"></html:dateformat></span></td>
          </tr>
          <tr>
            <th style="height:21px"><span>&nbsp;</span></th>
            <td><span>&nbsp;</span></td>
          </tr>          
        </tbody>
 </table>
  </body>
</html>
