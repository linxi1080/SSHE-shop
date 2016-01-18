package lh.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lh.dao.OnlineDaoI;
import lh.entity.Tonline;
import lh.service.OnlineServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
@Service("onlineService")
public class OnlineServiceImpl implements
		OnlineServiceI {
	@Resource
	private OnlineDaoI onlineDao;
	@Override
	public Serializable save(Tonline o) {
		return onlineDao.save(o);
	}

	@Override
	public int delete(String id) {
		String[] arrStrings = id.split(",");
		onlineDao.delete(id);
		return arrStrings.length;
	}

	@Override
	public Tonline update(Tonline o) {
		onlineDao.update(o);
		return o;
	}

	@Override
	public void saveOrUpdate(Tonline o) {
		onlineDao.saveOrUpdate(o);
	}

	@Override
	public Tonline getById(Serializable id) {
//		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return onlineDao.getById((String) id);
	}

	@Override
	public List<Tonline> findAll(int page, int rows, String sort, String order) {
		List<Tonline> lists = onlineDao.findAll(page, rows, sort, order);
		if(lists != null && lists.size() > 0){
			for(Tonline t :lists){
				Tonline r = null ;
				BeanUtils.copyProperties(t,r);
				lists.add(r);
			}
			return lists;
		}
		return null;
	}

	@Override
	public Tonline getById(String id) {
		return onlineDao.getById(id);
	}

	@Override
	public List<Tonline> getByIds(String[] ids) {
		List<Tonline> lists = onlineDao.getByIds(ids);
		if(lists != null && lists.size() > 0){
			for(Tonline t :lists){
				Tonline r = null ;
				BeanUtils.copyProperties(t,r);
				lists.add(r);
			}
			return lists;
		}
		return null;
	}

	@Override
	public Tonline get(String hql) {
		Tonline t = onlineDao.get(hql);
		return t;
	}

	@Override
	public Tonline get(String hql, Map<String, Object> params) {
		Tonline t = onlineDao.get(hql, params);
		return t;
	}

	@Override
	public List<Tonline> find(String hql, Map<String, Object> params, int page,
			int rows, String sort, String order) {
		return onlineDao.find(hql, params, page, rows, sort, order);
	}

	@Override
	public List<Tonline> find(String hql) {
		return onlineDao.find(hql);
	}

	@Override
	public List<Tonline> find(String hql, Map<String, Object> params) {
		return onlineDao.find(hql, params);
	}

	@Override
	public List<Tonline> find(String hql, Map<String, Object> params, int page,
			int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tonline> find(int page, int rows) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findCount(Tonline o) {
		return onlineDao.findCount(o);
	}

	@Override
	public Long findCount(String hql) {
		return onlineDao.findCount(hql);
	}

	@Override
	public Long findCount(String hql, Map<String, Object> params) {
		return onlineDao.findCount(hql, params);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return onlineDao.executeHql(hql, params);
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("a", k);
			params.put("b", k + 2);
			k = k + 2;
			l.add(findCount("select count(*) from Tonline t where HOUR(t.createdatetime)>=:a and HOUR(t.createdatetime)<:b and t.type='1'", params));
		}
		return l;
	}
}
