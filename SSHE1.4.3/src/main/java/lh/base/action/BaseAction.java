package lh.base.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@ParentPackage("basePackage")
@Namespace("/")
//@Action(value="userAction")
public abstract class BaseAction<T> extends ActionSupport implements
		ModelDriven<T>, ServletRequestAware, ServletResponseAware,
		ServletContextAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3804734640847118989L;

	protected T model;

	protected PrintWriter out;
	protected HttpServletResponse response;
	protected ServletContext servletContext;
	protected HttpServletRequest request;

	// 把对象输出
	public void writeJson(Object object) {
		String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
//		out.print(object);
		out.print(json);
		out.flush();
		out.close();
		//
		/*
		String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().write(json);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	//
	public BaseAction() {
		try {
			ParameterizedType pt = (ParameterizedType) this.getClass()
					.getGenericSuperclass();// 获取当前new的对象的泛型的父类类型
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];// 获得第一个类型参数的真实类型
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public T getModel() {
		return model;
	}
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;
		try {
			//转码需要在这里指定，否则使用ajax传递到前台的中文为乱码
			res.setContentType("text/html; charset=UTF-8"); 
			this.out = res.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
    //==========ModelDriven的支持========
	protected int page = 1;//当前默认第一页
	protected int rows = 10;//当前默认10条记录
	protected int pageSize = 10;//当前默认10条记录
	protected String sort; //排序字段字符串
	protected String order;//排序标志(desc,asc)
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获得request
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 获得response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**
	 * 获得session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
}
