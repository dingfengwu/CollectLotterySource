package lottery.domains.content.dao;

import lottery.domains.content.entity.TXonline;

public interface TXonlineDao {

	boolean add(TXonline entity);
	
	TXonline get(String issue);
}
