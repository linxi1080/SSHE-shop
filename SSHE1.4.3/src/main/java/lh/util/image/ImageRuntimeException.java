package lh.util.image;
/**
 * 图片异常类
 * @author cocim-liuhui 2015-12-30上午8:21:23
 */
public class ImageRuntimeException extends RuntimeException {
	public ImageRuntimeException(String path, String proesstype){
		super("对图片" + path + "进行" + proesstype + "出错");
	}
}
