package lh.action.fileUpload;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lh.util.document.DocConverter;
import lh.util.document.RenamePolicyCos;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.oreilly.servlet.MultipartRequest;
@ParentPackage("basePackage")
@Namespace("/")
@Action(value="uploadAction",results={@Result(name="documentView",location="/jsp/document/documentView.jsp")})
//@Action(value = "user", results = { @Result(name = "user", location = "/general/admin/user.jsp"), @Result(name = "userAdd", location = "/general/admin/userAdd.jsp"), @Result(name = "userEdit", location = "/general/admin/userEdit.jsp"), @Result(name = "userRoleEdit", location = "/general/admin/userRoleEdit.jsp"), @Result(name = "doNotNeedAuth_userInfo", location = "/general/user/userInfo.jsp"), @Result(name = "doNotNeedAuth_index", location = "/general/index.jsp"), @Result(name = "login", location = "/general/user/login.jsp") })
/**
 * 该action试图把servlet改写成action使用cos上传，但是暂时总是报错，好像是request被struts2拦截并改变了的原因
 * 没有测试通过，使用servlet则可以通过
 * @author cocim-liuhui
 *
 */
public class UploadAction extends ActionSupport implements ServletRequestAware, ServletResponseAware,
ServletContextAware {
	private static final long serialVersionUID = 1L;
	protected HttpServletResponse response;
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	public String doNotNeedGrant_fileUpload() {
		// ServletActionContext.getServletContext().getRealPath(url)
		File fileDir = new File(ServletActionContext.getServletContext()
				.getRealPath("/upload"));
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 设置上传文件的大小，超过这个大小 将抛出IOException异常，默认大小是1M。
//		int inmaxPostSize = 10 * 1024 * 1024;
		//每个文件最大50m  
		int inmaxPostSize = 50 * 1024 * 1024;
		MultipartRequest multirequest = null;
		// 上传文件重命名策略
		RenamePolicyCos myRenamePolicyCos = new RenamePolicyCos();
//		DefaultFileRenamePolicy myRenamePolicyCos = new DefaultFileRenamePolicy();
		try {
			// MultipartRequest()有8种构造函数！
			this.request = ServletActionContext.getRequest();
			System.out.println(request);
			System.out.println(fileDir.getAbsolutePath());
			System.out.println(inmaxPostSize);
			System.out.println(myRenamePolicyCos);
			multirequest = new MultipartRequest(request,
					fileDir.getAbsolutePath(), inmaxPostSize, "UTF-8",
					myRenamePolicyCos); // UTF-8中文编码模式上传文件
			String subject = multirequest.getParameter("subject");// 获取普通信息
			System.out.println(subject);
//			Enumeration<String> filedFileNames = multirequest.getFileNames();
//			String filedName = null;
//			if (null != filedFileNames) {
//				while (filedFileNames.hasMoreElements()) {
//					filedName = filedFileNames.nextElement();// 文件文本框的名称
//					// 获取该文件框中上传的文件，即对应到上传到服务器中的文件
//					File uploadFile = multirequest.getFile(filedName);
//					if (null != uploadFile && uploadFile.length() > 0) {
//						System.out.println(uploadFile.getName());
//						System.out.println(uploadFile.getPath());
//						System.out.println(uploadFile.length());
//					}
//					// 获取未重命名的文件名称
//					String Originalname = multirequest
//							.getOriginalFileName(filedName);
//					System.out.println(Originalname);
//				}
//			}
			//输出反馈信息  
			Enumeration<String> files = multirequest.getFileNames();
			while (files.hasMoreElements()) {
				System.err.println("ccc");
				String name = (String) files.nextElement();
				File f = multirequest.getFile(name);
				if (f != null) {
					String fileName = multirequest.getFilesystemName(name);
					//获取上传文件的扩展名  
					String extName = fileName.substring(fileName
							.lastIndexOf(".") + 1);
					//文件全路径  
					String saveDirectory = fileDir.getAbsolutePath();
					String lastFileName = saveDirectory + "\\" + fileName;
					//获取需要转换的文件名,将路径名中的'\'替换为'/'  
					String converfilename = saveDirectory.replaceAll("\\\\",
							"/") + "/" + fileName;
					System.out.println(converfilename);
					//调用转换类DocConverter,并将需要转换的文件传递给该类的构造方法  
					DocConverter d = new DocConverter(converfilename);
					//调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;  
					d.conver();
					//调用getswfPath()方法，打印转换后的swf文件路径  
					System.out.println(d.getswfPath());
					//生成swf相对路径，以便传递给flexpaper播放器  
					String swfpath = "upload"
							+ d.getswfPath().substring(
									d.getswfPath().lastIndexOf("/"));
					System.out.println(swfpath);
					//将相对路径放入sessio中保存  
					request.getSession().setAttribute("swfpath", swfpath);
					System.out.println("上传的文件:" + lastFileName);
					System.out.println("文件类型" + extName);
					System.out.println("<hr>");
					return "documentView";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
		//转码需要在这里指定，否则使用ajax传递到前台的中文为乱码
		response.setContentType("text/html; charset=UTF-8"); 
//			this.out = response.getWriter();
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

}
