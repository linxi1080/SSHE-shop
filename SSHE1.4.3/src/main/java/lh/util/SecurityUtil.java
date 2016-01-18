package lh.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import lh.entity.Tmenu;
import lh.entity.Trole;
import lh.pageModel.SessionInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 用于前台页面判断是否有权限的工具类
 * 
 * @author 孙宇
 * 
 */
public class SecurityUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SecurityUtil.class);
	
	private HttpSession session;

	public SecurityUtil(HttpSession session) {
		this.session = session;
	}

	/**
	 * 判断当前用户是否可以访问某资源
	 * 
	 * @param url
	 *            资源地址
	 * @return
	 */
	public boolean havePermission(String url) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getFrontendSessionInfoName());
		List<Tmenu> resources = new ArrayList<Tmenu>();
		for (Trole role : sessionInfo.getUser().getTroles()) {
			resources.addAll(role.getTmenus());
		}
//		for (Syorganization organization : sessionInfo.getUser().getSyorganizations()) {
//			resources.addAll(organization.getSyresources());
//		}
		resources = new ArrayList<Tmenu>(new HashSet<Tmenu>(resources));// 去重(这里包含了当前用户可访问的所有资源)
		for (Tmenu resource : resources) {
			if (StringUtils.equals(resource.getUrl(), url)) {// 如果有相同的，则代表当前用户可以访问这个资源
				logger.info("true "+url);
				return true;
			}
		}
		logger.info("false "+url);
		return false;
	}
//	@Test
//	public void test(){
//		havePermission("/roleAction!addRole.action");
//	}
}
