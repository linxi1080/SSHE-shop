package lh.dao.impl;

import org.springframework.stereotype.Repository;

import lh.base.dao.impl.BaseDaoImpl;
import lh.dao.RoleDaoI;
import lh.entity.Trole;
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Trole> implements RoleDaoI {

}
