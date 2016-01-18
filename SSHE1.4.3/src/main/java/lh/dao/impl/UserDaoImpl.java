package lh.dao.impl;

import lh.base.dao.impl.BaseDaoImpl;
import lh.dao.UserDao;
import lh.entity.Tuser;
import lh.util.Encrypt;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDao {

	@Override
	public Tuser login(Tuser user) {
		String md5Digest = Encrypt.e(user.getPwd());
		
		return (Tuser) getSession()//
				.createQuery(" from Tuser a where a.name=:logname and a.pwd = :password")//
				.setParameter("logname",user.getName())//
				.setParameter("password",md5Digest)//
				.uniqueResult();
//		return (Tuser) getSession()//
//				.createQuery(" from Tuser a where a.name=? and a.pwd = ?")//
//				.setParameter(0,user.getName())//
//				.setParameter(1,md5Digest)//
//				.uniqueResult();
	}
}
