package lh.util.image.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import lh.util.file.FileUtil;
import lh.util.image.ThumbnailCreatorI;

public class JavaImageIOCreator implements ThumbnailCreatorI {
	private String srcFile;
	private String destFile;
	private static Map<String, String> extMap = new HashMap<String, String>(5);

	public JavaImageIOCreator(String sourcefile, String targetFile) {
		srcFile = sourcefile;
		destFile = targetFile;
	}

	public void resize(int w, int h) {
		String ext = FileUtil.getFileExt(srcFile).toLowerCase();
		try {
			BufferedImage image = ImageIO.read(new File(srcFile));
			ImageIO.write(Lanczos.resizeImage(image, w, h), ext, new File(
					destFile));
		} catch (IOException e) {
			throw new RuntimeException(
					"生成缩略图错误", e);
		}
	}

	public static void main(String[] args) {
		JavaImageIOCreator creator = new JavaImageIOCreator("d:/1.jpg",
				"d:/1_j_180.jpg");
		creator.resize(180, 180);
	}

	static {
		extMap.put("jpg", "JPEG");
		extMap.put("jpeg", "JPEG");
		extMap.put("gif", "GIF");
		extMap.put("png", "PNG");
		extMap.put("bmp", "BMP");
	}
}
