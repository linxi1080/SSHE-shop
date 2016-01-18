package lh.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lh.base.BaseLog;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class RequestParseWrapper extends StrutsPrepareAndExecuteFilter {
//	public void doFilter(ServletRequest request, ServletResponse response,  
//	        FilterChain chain) throws IOException, ServletException {  
//	chain.doFilter(new StrutsRequestWrapper((HttpServletRequest) request), response);  
//	   } 
	 @Override
	    public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req;
	        HttpServletResponse response = (HttpServletResponse) res;
	        //不过滤的url,可以不断添加,如fck可用/fckeditor/editor/filemanager/connectors/fileupload,下面的是flex
	        //这里定义不过滤/SSHE/plupload地址，由于过滤会造成文件上传时List<FileItem> items = upload.parseRequest(request);为空
	        BaseLog.getLogger().info("RequestParseWrapper----"+request.getRequestURI());//包括项目的完整url
	        BaseLog.getLogger().info("RequestParseWrapper----"+(String) request.getAttribute("javax.servlet.include.servlet_path"));
	        BaseLog.getLogger().info("RequestParseWrapper----"+request.getContextPath());//项目SSHE的路径
	        if(this.excludedPatterns != null){
	        	BaseLog.getLogger().info(this.excludedPatterns.toString());
	        }
	        BaseLog.getLogger().info(prepare.isUrlExcluded(request, excludedPatterns));
//	        									  /SSHE/nofilter/verifyCodeAction!doNotNeedGrant_getVerifyCode.action
	        //StringUtils.substringBeforeLast(servletPath, ".")
	        /**
	         * 如果要使用jsp上传并转换时需要把对上传转换的jsp文件的拦截去掉，也就是不拦截该jsp就可以了
	         */
//	        if("/SSHE/jsp/document/docUploadConvertAction.jsp".equals(request.getRequestURI())){
//	        	BaseLog.getLogger().info("/SSHE/jsp/document/docUploadConvertAction.jsp"+"不拦截");
//	        	chain.doFilter(request, response);
//	        }
	        if ("/SSHE/onlineAction".equals(StringUtils.substringBeforeLast(request.getRequestURI(),"!")) ) {
//	            BaseLog.getLogger().info("使用自定义的过滤器");request.getRequestURI()
	        	super.doFilter(req, res, chain);
	        }
	        if ("/SSHE/plupload".equals(request.getRequestURI())) {
//	            BaseLog.getLogger().info("使用自定义的过滤器");
	            chain.doFilter(request, response);
	        }else{
//	            BaseLog.getLogger().info("使用默认的过滤器");
	            super.doFilter(req, res, chain);
	        }
	    }
}
