package lh.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import lh.entity.Tuser;
import lh.pageModel.SessionInfo;
import lh.util.ConfigUtil;
import lh.util.SecurityUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 权限拦截器
 * 
 * @author 孙宇
 * 
 */
public class SecurityInterceptor extends MethodFilterInterceptor {

	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);
	protected PrintWriter out;
	protected String doIntercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		//ActionContext actionContext = actionInvocation.getInvocationContext();
		SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getFrontendSessionInfoName());
		String servletPath = ServletActionContext.getRequest().getServletPath();
		logger.info("SecurityInterceptor----------"+ServletActionContext.getRequest().getRequestURI());
//		servletPath = StringUtils.substringBeforeLast(servletPath, ".");// 去掉后面的后缀 *.sy *.action之类的

		logger.info("进入权限拦截器->访问的资源为：[" + servletPath + "]");
		logger.info(this.getExcludeMethodsSet());
		logger.info(this.getIncludeMethodsSet());
		
//		Set<Syrole> roles = sessionInfo.getUser().getSyroles();
//		for (Syrole role : roles) {
//			for (Syresource resource : role.getSyresources()) {
//				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
//					return actionInvocation.invoke();
//				}
//			}
//		}
//		Set<Syorganization> organizations = sessionInfo.getUser().getSyorganizations();
//		for (Syorganization organization : organizations) {
//			for (Syresource resource : organization.getSyresources()) {
//				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
//					return actionInvocation.invoke();
//				}
//			}
//		}
		Tuser tuser = sessionInfo.getUser();
		if(tuser != null){//判断是否登录
			if(sessionInfo.getSecurityUtil().havePermission(servletPath)){//判断是否有对应权限
				return actionInvocation.invoke();  
			}else{
				session.put("error", "your user or pwd is error, please login again...");  
	            String errMsg = "您已登录但是没有访问此功能的权限！功能路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
	            logger.info(errMsg+"已登录，没有权限");
	            ServletActionContext.getRequest().setAttribute("msg", errMsg);
	            return "noSecurity"; 
			}
        }else{  
            session.put("error", "your user or pwd is error, please login again...");  
            String errMsg = "您没有登录系统，没有访问此功能的权限！功能路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
            logger.info(errMsg+"没有登录，没有权限");
            ServletActionContext.getRequest().setAttribute("msg", errMsg);
            return "noSecurity"; 
        }
	}
	 private static final long serialVersionUID = 1L;  
	  
//	    protected String doIntercept(ActionInvocation action) throws Exception {
//	    	//return ServletActionContext.getRequest().getSession();
//	    	//getSession().setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
//	    	SessionInfo sessionInfo = (SessionInfo) ServletActionContext.getRequest().getSession().getAttribute(ConfigUtil.getSessionInfoName());
//	        Map<String, Object> session = ActionContext.getContext().getSession();
//	        String user = (String)session.get("user");  
//	        String servletPath = ServletActionContext.getRequest().getServletPath();
//	        logger.info("进入权限拦截器->访问的资源为：[" + servletPath + "]");
//	        logger.info(this.getExcludeMethodsSet());
//	        if(user != null && !"".equals(user)){  
//	            return action.invoke();  
//	        }else{  
//	            session.put("error", "your user or pwd is error, please login again...");  
//	            String errMsg = "您没有访问此功能的权限！功能路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
//	            logger.info(errMsg);
//	            ServletActionContext.getRequest().setAttribute("msg", errMsg);
////	            HttpServletRequest req = ServletActionContext.getRequest();
////	            req.setAttribute("msg", errMsg);
////	            req.getRequestDispatcher("/jsp/error/noSecurity.jsp").forward(req, ServletActionContext.getResponse());
//	            return "noSecurity"; 
//	        }  
//	        /*
//	         * HttpServletRequest req = (HttpServletRequest) request;
//	         * ServletRequest request,
//		Object user = req.getSession().getAttribute("sessionUser");
//		if(user == null) {
//			req.setAttribute("code", "error");//为了显示X图片
//			req.setAttribute("msg", "您还没有登录，不能访问本资源");
//			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
//		} else {
//			chain.doFilter(request, response);//放行
//		}
//	         */
//	    }
	    public void writeJson(Object object) {
			String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
			out.print(json);
			out.flush();
			out.close();
		}

}
