package lh.converter;

import org.apache.log4j.Logger;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.springframework.web.util.HtmlUtils;
/**
 * 把html元素进行转换，用于防止XSS攻击
 * @author cocim-liuhui
 *StrutsTypeConverter简化了类型转换代码的编写，
 *StrutsTypeConverter继承DefaultTypeConverter,
 *提供了两个抽象的方法convertFromString()和convertToString,
 *分别表示从页面的字符串转换为后台对象以及从后台对象转为页面的字符串，
 *我们只需要实现这两个抽象方法即可实现类型转换。
 */
public class EscapeConverter extends StrutsTypeConverter {
	
	private static final Logger logger = Logger.getLogger(EscapeConverter.class);

	@Override
	public String convertToString(Map context, Object o) {
		if (o != null) {
			if (o instanceof String[]) {
				String[] str = (String[]) o;
				if (str != null && !StringUtils.isBlank(str[0])) {
//					System.out.println("HtmlUtils "+HtmlUtils.htmlEscape(str[0]));
//					System.out.println("htmlUnescape "+HtmlUtils.htmlUnescape(HtmlUtils.htmlEscape(str[0])));
					return HtmlUtils.htmlEscape(str[0]);
				}
			} else if (o instanceof String) {
				String str = (String) o;
				if (!StringUtils.isBlank(str)) {
//					System.out.println("HtmlUtils "+HtmlUtils.htmlEscape(str));
//					System.out.println("htmlUnescape "+HtmlUtils.htmlUnescape(HtmlUtils.htmlEscape(str)));
					return HtmlUtils.htmlEscape(str);
				}
			}
		}
		return "";
	}

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
//		System.out.println("values "+values);
		return values;
	}
}
