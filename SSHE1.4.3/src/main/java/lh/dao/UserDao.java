package lh.dao;

import lh.base.dao.BaseDaoI;
import lh.entity.Tuser;

public interface UserDao extends BaseDaoI<Tuser> {

	Tuser login(Tuser user);

}
