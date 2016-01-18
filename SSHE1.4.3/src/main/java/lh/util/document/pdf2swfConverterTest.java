package lh.util.document;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
* PDF转SWF工具
 * @author tangs
 *
 */
public class pdf2swfConverterTest {
	public static int convertPDF2SWF(String sourcePath, String destPath, String fileName) throws IOException {
		//目标路径不存在则建立目标路径
		File dest = new File(destPath);
		if (!dest.exists()) dest.mkdirs();
		
		//源文件不存在则返回
		File source = new File(sourcePath);
		if (!source.exists()) return 0;
		
		//调用pdf2swf命令进行转换
//		String command = "D:\\Program Files\\SWFTools\\pdf2swf.exe" + " -o \"" + destPath + "\\" + fileName + "\"  -s languagedir=D:/xpdfbin-win-3.04/xpdf-chinese-simplified -s flashversion=9 \"" + sourcePath + "\"";
		String command = "D:\\Program Files\\SWFTools\\pdf2swf.exe "+sourcePath + " -o "+ destPath + fileName + " -T 9 -s languagedir=D:/xpdfbin-win-3.04/xpdf-chinese-simplified";
		Process pro = Runtime.getRuntime().exec(command);
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		while (bufferedReader.readLine() != null); 
		
		try {
			pro.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pro.exitValue();
		
	}
	
	public static void main(String []args) throws IOException {
		String sourcePath = "c:\\test.pdf";
		String destPath = "c:\\";
		String fileName = "test.swf";
		pdf2swfConverterTest.convertPDF2SWF(sourcePath, destPath, fileName);
	}
}