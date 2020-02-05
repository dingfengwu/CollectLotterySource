package lottery.domains.content.dao;

import java.util.Date;

import lottery.domains.content.entity.IssueInfo;

public interface IssueInfoDao {

	boolean update(String Code,String LotteryId,String Issue);
	
	IssueInfo getIssueInfo(Date time);
}
