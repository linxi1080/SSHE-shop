package lh.util.document;

import org.apache.log4j.Logger;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;

public class TestPrint2Flash {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TestPrint2Flash.class);

	public static void main(String[] args) throws java.io.IOException {
		if (args.length > 0) {
			try {
				ComThread.InitSTA();

				// Create Server object
				ActiveXComponent p2f = new ActiveXComponent(
						"Print2Flash3.Server");

				// Setup interface and protection options
				ActiveXComponent defProfile = new ActiveXComponent(p2f.getProperty("DefaultProfile").toDispatch());
				defProfile.setProperty("InterfaceOptions", P2FConst.INTDRAG | P2FConst.INTSELTEXT | P2FConst.INTZOOMSLIDER | P2FConst.INTZOOMBOX | P2FConst.INTFITWIDTH | P2FConst.INTFITPAGE | P2FConst.INTPREVPAGE | P2FConst.INTGOTOPAGE | P2FConst.INTNEXTPAGE | P2FConst.INTSEARCHBOX | P2FConst.INTSEARCHBUT | P2FConst.INTROTATE |P2FConst.INTFULLSCREEN);
				defProfile.setProperty("ProtectionOptions",P2FConst.PROTDISPRINT | P2FConst.PROTENAPI);

				// Convert document
				p2f.invoke("ConvertFile", new String(args[0]));
//				System.out.println("Conversion completed successfully");
				logger.info("转换成功完成！");
			} catch (Exception e) {
//				System.out.println("An error occurred at conversion: " + e.toString());
				logger.info("在转换时有一个错误："+e.toString());
			} finally {
				ComThread.Release();
			}
		} else{
//			System.out.println("Please provide a document file name as a parameter");
			logger.info("请提供文档文件名作为参数");
		}
//		System.out.println("Press Enter to exit...");
		logger.info("按任意键退出");
		System.in.read();
	}
}
