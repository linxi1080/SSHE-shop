package lh.pageModel;

import lh.entity.Tuser;
import lh.util.SecurityUtil;

/**
 * sessionInfo模型，只要登录成功，就需要设置到session里面，便于系统使用
 * 
 * @author 孙宇
 * 
 */
public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8364167355835692329L;
	private Tuser user;
	private SecurityUtil securityUtil;
	public Tuser getUser() {
		return user;
	}

	public void setUser(Tuser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return user.getLoginName();
	}

	public SecurityUtil getSecurityUtil() {
		return securityUtil;
	}

	public void setSecurityUtil(SecurityUtil securityUtil) {
		this.securityUtil = securityUtil;
	}

}
