package lh.util.image;

import java.io.IOException;

import lh.util.image.impl.ImageMagickCreator;
import lh.util.image.impl.JavaImageIOCreator;


public class ThumbnailCreatorFactory {
	public static String CREATORTYPE = "javaimageio";
	
	/**
	 * 返回缩略图生成器
	 * @param source 图片原文件路径
	 * @param target 图片缩略图路径
	 * @return 
	 * 当{@link #CREATORTYPE} 为javaimageio时使用{@link JavaImageIOCreator }生成器<br>
	 * 当{@link #CREATORTYPE} 为imagemagick时使用{@link ImageMagickCreator }生成器<br>
	 */
	public static final ThumbnailCreatorI getCreator(String source,String target){
		if(CREATORTYPE.equals("javaimageio")){
			return new JavaImageIOCreator(source, target);
		}
		
		if(CREATORTYPE.equals("imagemagick")){
			try{
			return new ImageMagickCreator(source, target);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return new JavaImageIOCreator(source, target);
	}

}
