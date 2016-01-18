package lh.util.uploadServerlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lh.util.ConfigUtil;
import lh.util.DateUtil;
import lh.util.document.DocConverter;
import lh.util.document.RenamePolicyCos;

import com.oreilly.servlet.MultipartRequest;

/**
 * 使用cos组件实现文件上传
 * 
 * @author mingxue.zhang@163.com
 * 
 */
public class CosUploadServlet extends HttpServlet {

	private static final long serialVersionUID = -9116026245071140150L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		File fileDir = new File(this.getServletContext()
				.getRealPath("/upload"));
		/*
		 *使用这个上传文件到当前WEB环境的上层目录
		 *原来的上传到了项目的upload目录下了
		String webParentPath = new File(request.getSession().getServletContext().getRealPath("/")).getParent();// 当前WEB环境的上层目录
//		String realPath = webParentPath + ConfigUtil.get("uploadPath") + fileDir;// 文件上传到服务器的真实路径
		String datefolder = "/" + DateUtil.dateToString(new Date(), "yyyy") + "/" + DateUtil.dateToString(new Date(), "MM") + "/" + DateUtil.dateToString(new Date(), "dd");// 日期命名的文件夹
		String realPath = webParentPath + "/upload" + datefolder;// 文件上传到服务器的真实路径
		fileDir = new File(realPath);
		*/
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		// 设置上传文件的大小，超过这个大小 将抛出IOException异常，默认大小是1M。
		int inmaxPostSize = 10 * 1024 * 1024;
		MultipartRequest multirequest = null;
		// 上传文件重命名策略
		RenamePolicyCos myRenamePolicyCos = new RenamePolicyCos();
//		DefaultFileRenamePolicy myRenamePolicyCos = new DefaultFileRenamePolicy();
		try {
			// MultipartRequest()有8种构造函数！
//			System.out.println(request);
//			System.out.println(fileDir.getAbsolutePath());
//			System.out.println(inmaxPostSize);
//			System.out.println(myRenamePolicyCos);
			multirequest = new MultipartRequest(request, fileDir
					.getAbsolutePath(), inmaxPostSize, "UTF-8", myRenamePolicyCos); // GBK中文编码模式上传文件
			String subject = multirequest.getParameter("subject");// 获取普通信息
//			System.out.println(subject);
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
//				System.err.println("ccc");
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
//					System.out.println(converfilename);
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
//					System.out.println(swfpath);
					//将相对路径放入session中保存  
					request.getSession().setAttribute("swfpath", swfpath);
					System.out.println("上传的文件:" + lastFileName);
					System.out.println("文件类型" + extName);
					System.out.println("<hr>");
					//
					request.getRequestDispatcher("/jsp/document/documentView.jsp").forward(request, response);
					//
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
