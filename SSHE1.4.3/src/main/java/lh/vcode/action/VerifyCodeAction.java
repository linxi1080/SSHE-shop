package lh.vcode.action;

import java.awt.image.BufferedImage;
import java.io.IOException;

import lh.vcode.util.VerifyCode;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("public")
@Namespace("/")
//@Namespace("/nofilter")
@Action(value = "verifyCodeAction")
public class VerifyCodeAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5536700165356989844L;
	VerifyCode vc = new VerifyCode();
	BufferedImage image = vc.getImage();//获取一次性验证码图片
	// 该方法必须在getImage()方法之后来调用
//	System.out.println(vc.getText());//获取图片上的文本
//	VerifyCode.output(image, response.getOutputStream());//把图片写到指定流中
	// 把文本保存到session中，为LoginServlet验证做准备
//	request.getSession().setAttribute("vCode", vc.getText());
	public void getVerifyCode(){
		try {
			VerifyCode.output(image, ServletActionContext.getResponse().getOutputStream());
			// 把文本保存到session中，为LoginServlet验证做准备
			ServletActionContext.getRequest().getSession().setAttribute("vCode",vc.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}//把图片写到指定流中
	}
}
