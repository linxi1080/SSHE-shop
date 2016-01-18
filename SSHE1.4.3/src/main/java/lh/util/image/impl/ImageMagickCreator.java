package lh.util.image.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lh.util.image.ImageRuntimeException;
import lh.util.image.ThumbnailCreatorI;
import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

public class ImageMagickCreator implements ThumbnailCreatorI {
	private String source;
	private String target;
	private ImageInfo info;
	private MagickImage image;
	private double width;
	private double height;

	public ImageMagickCreator(String _source, String _target)
			throws IOException {
		source = _source;
		target = _target;
		File f = new File(_source);
		FileInputStream fis = new FileInputStream(f);
		try {
			byte[] b = new byte[(int) f.length()];
			fis.read(b);
			info = new ImageInfo();
			image = new MagickImage(info, b);

			width = image.getDimension().getWidth();
			height = image.getDimension().getHeight();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ImageRuntimeException(source, "构造jmagickutils");
		} finally {
			if (fis != null)
				fis.close();
			fis = null;
		}
	}
	/**
	 * 按照给定的宽高生成缩略图，如果比例不一致，多出的部分填白
	 */
	public void resize(int w, int h) {
		int x = 0;
		int y = 0;
		x = y = 0;
		int target_w = w;
		int target_h = h;

		MagickImage scaled = null;
		try {
			if (width / height > w / h) {
				//如果要转换的宽高和原图片比例不一致，换算出对应的图片高度
				target_w = w;
				target_h = (int) (target_w * height / width);
				x = 0;
				y = (h - target_h) / 2;
			}

			if (width / height < w / h) {
				//如果要转换的宽高和原图片比例不一致，换算出对应的图片宽度
				target_h = h;
				target_w = (int) (target_h * width / height);
				y = 0;
				x = (w - target_w) / 2;
			}
			MagickImage thumb_img = image.scaleImage(target_w, target_h);
			MagickImage blankImage = new MagickImage();

			byte[] pixels = new byte[w * h * 4];
			for (int i = 0; i < w * h; i++) {
				pixels[(4 * i)] = -1;
				pixels[(4 * i + 1)] = -1;
				pixels[(4 * i + 2)] = -1;
				pixels[(4 * i + 3)] = -1;
			}
			/**
			 * mapThis string reflects the expected ordering of the pixel array. 
			 * It can be any combination or order of R = red, G = green, B = blue, A = alpha (0 is transparent), 
			 * O = opacity (0 is opaque), C = cyan, Y = yellow, M = magenta, K = black, 
			 * I = intensity (for grayscale), P = pad. 
			 */
			blankImage.constituteImage(w, h, "RGBA", pixels);
			blankImage.compositeImage(3, thumb_img, x, y);

			blankImage.setFileName(target);
			blankImage.writeImage(info);
		} catch (MagickApiException ex) {
			ex.printStackTrace();
			throw new ImageRuntimeException(source,
					"生成缩略图");
		} catch (MagickException ex) {
			ex.printStackTrace();
			throw new ImageRuntimeException(source,
					"生成缩略图");
		} finally {
			if (scaled != null) {
				scaled.destroyImages();
			}
			if (image != null)
				image.destroyImages();
		}
	}

	public static void main(String[] args) throws IOException {
		ImageMagickCreator creator = new ImageMagickCreator("d:/1.jpg",
				"d:/2.jpg");
		creator.resize(200, 200);
	}

	static {
		System.setProperty("jmagick.systemclassloader", "no");
	}
}
