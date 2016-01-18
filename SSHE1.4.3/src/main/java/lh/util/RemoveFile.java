package lh.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class RemoveFile {
	private static final Logger logger = Logger.getLogger(RemoveFile.class);
	private final static String FS = System.getProperty("file.separator");
	private static String TOMCAT_HOME = System.getProperty("catalina.home");
	private static String PATH = "temp";
	private static final String TEMP_PATH = TOMCAT_HOME + FS + PATH + FS;
	private static boolean isRunning = false;
	public void run() {
		if(!isRunning){
			logger.info("删除/Tomcat/Temp/目录下过时图片执行开端...");
			isRunning = true;
			logger.info("开端执行删除tomcat下temp文件夹png文件职务");
			logger.info("要删除图片文件所在途径为" + TEMP_PATH);
			//File fileTemp = new File(TEMP_PATH);
			String ls_path = "F:\\apache-tomcat-7.0.54\\temp";
			File fileTemp = new File(ls_path);
			// 判别文件能不能存在
			boolean falg = false;
			falg = fileTemp.exists();
			if(falg){
				logger.info("temp文件存在");
				if(true == fileTemp.isDirectory()){
					logger.info("temp文件是个目录");
					String[] png = fileTemp.list();
					for(int i=0;i<png.length;i++){
						if(true==png[i].endsWith("png") || (true==png[i].endsWith("jpg"))){
//							File file = new File(TEMP_PATH + FS + png[i]);
							File file = new File(ls_path + FS + png[i]);
							if(true==file.isFile()){
								boolean flag = false;
								flag = file.delete(); 
								if (flag) {
									logger.info("成功删除无效图片文件:" + file.getName());
								}
							}
						}
					}
				}
			}else{
				logger.info("未找到tomcat/temp文件夹，执行失败，请手动删除temp下一切png文件");
			}
			isRunning = false;
			logger.info("删除/Tomcat/Temp/目录下过时图片执行完毕...");
		}
	}
//	@Test
//	public void test(){
//		run();
//	}
	public static void removeFile(HttpServletRequest request,String strPath){
		String webParentPath = new File(request.getSession().getServletContext().getRealPath("/")).getParent();// 当前WEB环境的上层目录
		String realPath = webParentPath + strPath;
		File file = new File(realPath);
		if(true==file.isFile()){
			boolean flag = false;
			flag = file.delete(); 
			if (flag) {
				logger.info("删除文件成功:" + file.getName());
			}else{
				logger.info("删除文件失败:" + file.getName());
			}
		}
	}
}
