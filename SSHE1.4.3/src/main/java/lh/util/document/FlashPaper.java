package lh.util.document;
import java.io.BufferedInputStream;  
import java.io.BufferedOutputStream;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import javax.servlet.http.HttpServletRequest;  

import org.apache.struts2.ServletActionContext;
/** 
 * @说明 将文件转为SWF 
 * @author cuisuqiang 
 * 使用falshpaper转换
 * @version 1.0 
 * @since  关注新浪微博：@java小强 
 */  
public class FlashPaper extends Thread {
	public static void main(String args[]) {
		File file = new File("1301291936853trhkt");
		converter(file, ".doc","1301291936853trhkt.pdf", ServletActionContext.getRequest());
//		converter("1301291936853trhkt", "1301291936853trhkt.pdf", 100);
	}
//
//	public static boolean converter(String swfName, String documentName,
//			int fileSize) {
//		int sleeptime = 0;
//		boolean st_return = true;
//		Runtime pro = Runtime.getRuntime();
//		try {
//			String converter = "C:\\Macromedia\\FlashPaper 2\\FlashPrinter.exe -o C:\\struts\\swf\\"
//					+ swfName + ".swf C:\\struts\\document\\" + documentName;
//			pro.exec(converter);
//			Thread.sleep(sleeptime);
//		} catch (Exception e) {
//			System.out.println("执行失败");
//			e.printStackTrace();
//		}
//		return st_return;
//	}
	@SuppressWarnings("deprecation")  
    public static String converter(File srcFile,String fileContentType,String fileFileName, HttpServletRequest request) {  
//        String uploadPath = request.getRealPath("/");
		String uploadPath = request.getSession().getServletContext().getRealPath("/");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String dataFile = format.format(new Date());  
        uploadPath += "swfTemp\\" + dataFile;  
        File f = new File(uploadPath);  
        if (!f.exists() && !f.isDirectory()) {  
            f.mkdirs();  
        }  
        // SWF 放到服务器下  
        String filename = uploadPath + "\\" + fileFileName + ".swf";  
        File file = new File(filename);  
        if(file.exists()){  
            return "swfTemp/" + dataFile + "/" + fileFileName + ".swf";  
        }  
        // 要转换的文件 放到临时目录  
        String docTempFileName = "C:\\" + String.valueOf(new Date().getTime()) + fileContentType;  
        File docTempFile = new File(docTempFileName);  
        copyFile(srcFile, docTempFile);  
        // 转换后的文件 放到临时目录  
        String swfTempFileName = "C:\\" + String.valueOf(new Date().getTime()) + ".swf";  
        File swfTempFile = new File(swfTempFileName);  
        try {  
            String converter = "D:\\Macromedia\\FlashPaper 2\\FlashPrinter.exe -o " + swfTempFile.getAbsolutePath() + " " + docTempFile.getAbsolutePath();  
            Runtime pro = Runtime.getRuntime();  
            pro.exec(converter);  
            // 注意，为了测试，这里只要没有转换工程，就一直等待  
            while(true){  
                if(!swfTempFile.exists()){  
                    Thread.sleep(1000);  
                    continue;  
                }  
                copyFile(swfTempFile, file);  
                // 删除临时文件  
                swfTempFile.delete();  
                docTempFile.delete();  
                return "swfTemp/" + dataFile + "/" + fileFileName + ".swf";  
            }  
        } catch (Exception e) {  
            System.out.println("执行失败");  
            e.printStackTrace();  
        }  
        return "";  
    }  
    private static final int BUFFER_SIZE = 102400;  
    private static void copyFile(File src, File dir) {  
        try {  
            InputStream input = null;  
            OutputStream output = null;  
            try {  
                input = new BufferedInputStream(new FileInputStream(src),  
                        BUFFER_SIZE);  
                output = new BufferedOutputStream(new FileOutputStream(dir),  
                        BUFFER_SIZE);  
                byte[] buffer = new byte[BUFFER_SIZE];  
                while (input.read(buffer) > 0) {  
                    output.write(buffer);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (null != input) {  
                    input.close();  
                }  
                if (null != output) {  
                    output.close();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
