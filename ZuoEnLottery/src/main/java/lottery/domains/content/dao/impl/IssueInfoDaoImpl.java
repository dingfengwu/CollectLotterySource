package lottery.domains.content.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javautils.jdbc.hibernate.HibernateSuperDao;
import lottery.domains.content.dao.IssueInfoDao;
import lottery.domains.content.entity.IssueInfo;

@Repository
public class IssueInfoDaoImpl implements IssueInfoDao {

private final String tab = IssueInfo.class.getSimpleName();
	
	@Autowired
	private HibernateSuperDao<IssueInfo> superDao;
	
	@Override
	public boolean update(String Code,String LotteryId,String Issue) {
		// TODO Auto-generated method stub
		
		String hql = "update " + tab + " set Code = ?0, WriteTime = GETDATE() , StatusFetch = 2  where Issue = ?1 and LotteryId = ?2 and StatusFetch = 1";
		Object[] values = {Code,Issue,LotteryId};
		return superDao.update(hql, values);
	}

	@Override
	public IssueInfo getIssueInfo(Date time) {
		// TODO Auto-generated method stub
		String hql = "from "+tab+" where SaleEnd = ?0 and LotteryId = 4";
		Object[] values = {time};
		return (IssueInfo)superDao.unique(hql, values);
	}
	

}
