<!-- 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String webpath = request.getContextPath();
	String serverPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(); 
	String webbasePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + webpath + "/";
    String version = "20150416";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<#-- 
<script type="text/javascript">
	var lh = lh || {};
	lh.contextPath = '<%=webpath%>';
	lh.basePath = '<%=webbasePath%>';
	lh.version = '<%=version%>';
	lh.pixel_0 = '<%=webpath%>/jslib/extEasyUI/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>
-->
<#assign ctx="/SSHE">
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/jquery-form-2.33.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/jquery.blockUI.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/jquery.loading.js"></script>


<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/extJquery.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/extEasyUI.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/json2.js"></script>

<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/jquery.jdirk.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/jeasyui.extensions.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/jeasyui.extensions.menu.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/jeasyui.extensions.panel.js"></script>
<script type="text/javascript" src="${ctx}/jslib/extEasyUI/js/jeasyui.extensions.datagrid.js"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-portal/js/jquery.portal.js"></script>

<!-- 引入plupload -->
<script type="text/javascript" src="${ctx}/jslib/plupload/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${ctx}/jslib/plupload/js/i18n/zh_CN.js"></script>
<!-- 引入Highcharts -->
<script src="${ctx}/jslib/Highcharts-4.1.5/js/highcharts.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/jslib/Highcharts-4.1.5/js/modules/exporting.js" type="text/javascript" charset="utf-8"></script>
<!-- 引入Highcharts扩展 -->
<script src="${ctx}/jslib/extEasyUI/js/extHighcharts.js?version=<%=version%>" type="text/javascript" charset="utf-8"></script>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${ctx}/jslib/jquery-easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/jquery-easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/extEasyUI/icons/icon-all.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/extEasyUI/css/exteasyui.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/extEasyUI/css/extIcon.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/extEasyUI/css/icon-all.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/extEasyUI/css/jeasyui.extensions.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/jslib/jquery-easyui-portal/css/portal.css"/>
<link rel="stylesheet" href="${ctx}/jslib/extEasyUI/css/main.css" type="text/css"></link>
<link rel="stylesheet" href="${ctx}/jslib/extEasyUI/css/style.css" type="text/css"></link>
<!-- 指定页面的图标 -->
<!-- 
<link href="http://localhost:8080/ssheUploadFile/userPhoto/2015/06/28/32a6fba6e9144b6bb80bd87684213317.png" rel="shortcut icon" >
-->
<#--
<link href="${ctx}>/${siteInfo.icofile}" rel="shortcut icon" >
-->
<!-- 
<link rel="icon" href="${ctx}/jslib/extEasyUI/icons/16X16/38.ico" mce_href="${ctx}/jslib/extEasyUI/icons/16X16/38.ico" type="image/x-icon">
<link rel="shortcut icon" href="${ctx}/jslib/extEasyUI/icons/32X32/star-on.png" mce_href="${ctx}/jslib/extEasyUI/icons/32X32/star-on.png" type="image/x-icon">
-->
<#--
http://localhost:8080/SSHE/jslib/jquery-easyui/jquery-1.8.0.min.js
<title>${siteInfo.title}</title>
-->


