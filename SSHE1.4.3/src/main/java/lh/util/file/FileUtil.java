package lh.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;

/**
 * 文件工具类
 * 
 * @author cocim-liuhui 2015-12-27下午2:11:59
 */
public class FileUtil {
	public static void createFile(InputStream in, String filePath) {
		if (in == null)
			throw new RuntimeException("create file error: inputstream is null");
		int potPos = filePath.lastIndexOf('/') + 1;
		String folderPath = filePath.substring(0, potPos);
		createFolder(folderPath);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			byte[] by = new byte[1024];
			int c;
			while ((c = in.read(by)) != -1)
				outputStream.write(by, 0, c);
		} catch (IOException e) {
			e.getStackTrace().toString();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 判断是否是允许上传文件
	 * 2015-12-27下午2:31:31
	 * @param logoFileName
	 * @return boolean值
	 */
	public static boolean isAllowUp(String logoFileName) {
		logoFileName = logoFileName.toLowerCase();
		String allowTYpe = "gif,jpg,bmp,swf,png,rar,doc,docx,xls,xlsx,pdf,zip,ico,txt";
		if ((!logoFileName.trim().equals("")) && (logoFileName.length() > 0)) {
			String ex = logoFileName.substring(
					logoFileName.lastIndexOf(".") + 1, logoFileName.length());

			return allowTYpe.toUpperCase().indexOf(ex.toUpperCase()) >= 0;
		}
		return false;
	}
	/**
	 * 把内容写入文件
	 * 2015-12-27下午2:32:13
	 * @param filePath
	 * @param fileContent
	 */
	public static void write(String filePath, String fileContent) {
		try {
			FileOutputStream fo = new FileOutputStream(filePath);
			OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");

			out.write(fileContent);

			out.close();
		} catch (IOException ex) {
			System.err.println("Create File Error!");
			ex.printStackTrace();
		}
	}
	/**
	 * 读取文件内容 默认是UTF-8编码
	 * 2015-12-27下午2:32:42
	 * @param filePath
	 * @param code
	 * @return 文件内容
	 */
	public static String read(String filePath, String code) {
		if ((code == null) || (code.equals(""))) {
			code = "UTF-8";
		}
		String fileContent = "";
		File file = new File(filePath);
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), code);

			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent = fileContent + line + "\n";
			}
			read.close();
			read = null;
			reader.close();
			read = null;
		} catch (Exception ex) {
			ex.printStackTrace();
			fileContent = "";
		}
		return fileContent;
	}
	/**
	 * 删除文件或文件夹
	 * 2015-12-27下午2:33:20
	 * @param filePath
	 */
	public static void delete(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists())
				if (file.isDirectory())
					FileUtils.deleteDirectory(file);
				else
					file.delete();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * 判断文件或文件夹是否存在
	 * 2015-12-27下午2:33:58
	 * @param filepath
	 * @return
	 */
	public static boolean exist(String filepath) {
		File file = new File(filepath);

		return file.exists();
	}
	/**
	 * 创建文件夹
	 * 2015-12-27下午2:34:49
	 * @param filePath
	 */
	public static void createFolder(String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists())
				file.mkdirs();
		} catch (Exception ex) {
			System.err.println("Make Folder Error:" + ex.getMessage());
		}
	}
	/**
	 * 
	 * 2015-12-27下午2:34:54
	 * @param from
	 * @param to
	 */
	public static void renameFile(String from, String to) {
		try {
			File file = new File(from);
			if (file.exists())
				file.renameTo(new File(to));
		} catch (Exception ex) {
			System.err.println("Rename File/Folder Error:" + ex.getMessage());
		}
	}
	/**
	 * 得到文件的扩展名
	 * 2015-12-27下午2:35:11
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName) {
		int potPos = fileName.lastIndexOf('.') + 1;
		String type = fileName.substring(potPos, fileName.length());
		return type;
	}
	/**
	 * 通过File对象创建文件
	 * 2015-12-27下午2:35:25
	 * @param file
	 * @param filePath
	 */
	public static void createFile(File file, String filePath) {
		int potPos = filePath.lastIndexOf('/') + 1;
		String folderPath = filePath.substring(0, potPos);
		createFolder(folderPath);
		FileOutputStream outputStream = null;
		FileInputStream fileInputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
			fileInputStream = new FileInputStream(file);
			byte[] by = new byte[1024];
			int c;
			while ((c = fileInputStream.read(by)) != -1)
				outputStream.write(by, 0, c);
		} catch (IOException e) {
			e.getStackTrace().toString();
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 读取文件，用文件路径作为输入流读入
	 * 2015-12-27下午2:36:06
	 * @param resource
	 * @return
	 */
	public static String readFile(String resource) {
		InputStream stream = getResourceAsStream(resource);
		String content = readStreamToString(stream);

		return content;
	}
	/**
	 * 把文件路径作为一个输入流读入
	 * 2015-12-27下午2:38:31
	 * @param resource
	 * @return
	 */
	public static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? resource.substring(1)
				: resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}

		return stream;
	}
	/**
	 * 把读入的输入流转换为String
	 * 2015-12-27下午2:38:52
	 * @param stream
	 * @return
	 */
	public static String readStreamToString(InputStream stream) {
		String fileContent = "";
		try {
			InputStreamReader read = new InputStreamReader(stream, "utf-8");
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent = fileContent + line + "\n";
			}
			read.close();
			read = null;
			reader.close();
			read = null;
		} catch (Exception ex) {
			fileContent = "";
		}
		return fileContent;
	}
	/**
	 * 删除文件夹
	 * 2015-12-27下午2:40:16
	 * @param path
	 */
	public static void removeFile(File path) {
		if (path.isDirectory())
			try {
				FileUtils.deleteDirectory(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * 复制文件
	 * 2015-12-27下午2:41:38
	 * @param srcFile
	 * @param destFile
	 */
	public static void copyFile(String srcFile, String destFile) {
		try {
			if (exist(srcFile))
				FileUtils.copyFile(new File(srcFile), new File(destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 复制文件或文件夹
	 * 2015-12-27下午2:42:17
	 * @param sourceFolder
	 * @param destinationFolder
	 */
	public static void copyFolder(String sourceFolder, String destinationFolder) {
		try {
			File sourceF = new File(sourceFolder);
			if (sourceF.exists())
				FileUtils.copyDirectory(new File(sourceFolder), new File(
						destinationFolder));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("copy file error");
		}
	}
	/**
	 * 复制目录下所有文件到新目录
	 * 2015-12-27下午2:43:11
	 * @param sourceFolder
	 * @param targetFolder
	 */
	public static void copyNewFile(String sourceFolder, String targetFolder) {
		try {
			File sourceF = new File(sourceFolder);

			if (!targetFolder.endsWith("/"))
				targetFolder = targetFolder + "/";

			if (sourceF.exists()) {
				File[] filelist = sourceF.listFiles();
				for (File f : filelist) {
					File targetFile = new File(targetFolder + f.getName());

					if (f.isFile()) {
						if ((!targetFile.exists())
								|| (FileUtils.isFileNewer(f, targetFile))) {
							FileUtils.copyFileToDirectory(f, new File(
									targetFolder));
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("copy file error");
		}
	}
	/**
	 * 
	 * 2015-12-27下午2:46:13
	 * @param zipPath
	 * @param targetFolder
	 * @param cleanZip
	 */
	public static void unZip(String zipPath, String targetFolder,
			boolean cleanZip) {
		File folderFile = new File(targetFolder);
		File zipFile = new File(zipPath);
		Project prj = new Project();
		Expand expand = new Expand();
		expand.setEncoding("UTF-8");
		expand.setProject(prj);
		expand.setSrc(zipFile);
		expand.setOverwrite(true);
		expand.setDest(folderFile);
		expand.execute();

		if (cleanZip) {
			Delete delete = new Delete();
			delete.setProject(prj);
			delete.setDir(zipFile);
			delete.execute();
		}
	}

	public static void main(String[] arg) {
	}
}
