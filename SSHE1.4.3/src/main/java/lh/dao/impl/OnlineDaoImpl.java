package lh.dao.impl;

import org.springframework.stereotype.Repository;

import lh.base.dao.impl.BaseDaoImpl;
import lh.dao.OnlineDaoI;
import lh.entity.Tonline;
@Repository("onlineDao")
public class OnlineDaoImpl extends BaseDaoImpl<Tonline> implements OnlineDaoI {

}
