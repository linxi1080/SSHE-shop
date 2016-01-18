package lh.base;

import org.apache.log4j.Logger;
/**
 * 日志基础类
 * @author cocim-liuhui
 *
 */
public class BaseLog {
	/** 取得日志记录器Logger */
	private static final Logger logger = Logger.getLogger(BaseLog.class);

	public static Logger getLogger() {
		return logger;
	}
}
