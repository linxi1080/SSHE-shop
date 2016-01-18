package lh.dao.impl;

import lh.base.dao.impl.BaseDaoImpl;
import lh.dao.DeptDaoI;
import lh.entity.Tdept;

import org.springframework.stereotype.Repository;
@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Tdept> implements DeptDaoI {

}
