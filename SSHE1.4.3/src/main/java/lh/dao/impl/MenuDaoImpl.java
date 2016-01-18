package lh.dao.impl;

import lh.base.dao.impl.BaseDaoImpl;
import lh.dao.MenuDaoI;
import lh.entity.Tmenu;

import org.springframework.stereotype.Repository;
@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Tmenu> implements MenuDaoI {

}
