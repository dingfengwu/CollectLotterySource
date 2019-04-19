package lottery.domains.content.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.TXonlineDao;
import lottery.domains.content.entity.TXonline;

@Repository
public class TXonlineDaoImpl implements TXonlineDao {

	private final String tab = TXonline.class.getSimpleName();
	
	@Autowired
	private HibernateSuperDao<TXonline> superDao;
	
	@Override
	public boolean add(TXonline entity) {
		// TODO Auto-generated method stub
		TXonline online = get(entity.getIssue());
		if(online==null) {
			return superDao.save(entity);
		}
		return false;
	}

	@Override
	public TXonline get(String issue) {
		String hql = "from " + tab + " where issue = ?0 ";
		Object[] values = {issue};
		return (TXonline) superDao.unique(hql, values);
	}

}
