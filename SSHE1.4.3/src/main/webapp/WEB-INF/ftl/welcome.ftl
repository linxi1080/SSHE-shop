<#--
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />  
-->
<html>  
<head>  
<title>登录页面</title>  
</head>  
<body>  
登陆<br>  
<@s.form action="/common/login.action">  
 <@s.textfield name="username" label="用户名"/>  
 <@s.textfield name="password" label="密码"/>  
 <@s.submit value="提交"/>  
</@s.form>  
提示：${name1}=${name2}  
提示3：<#if (2>3)>
	         2大于3
	      <#else>
	         2小于3
	      </#if>
</body>  
</html>  