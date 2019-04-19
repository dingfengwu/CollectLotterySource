package lottery.domains.content.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "IssueInfo")
public class IssueInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	private int IssueId;
	private int LotteryId;
	private String Code;
	private String Issue;
	private Date BelongDate;
	private Date SaleStart;
	private Date SaleEnd;
	private Date WriteTime;
	private int StatusFetch;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "IssueId", unique = true, nullable = false)
	public int getIssueId() {
		return IssueId;
	}
	public void setIssueId(int issueId) {
		IssueId = issueId;
	}
	
	@Column(name = "LotteryId")
	public int getLotteryId() {
		return LotteryId;
	}
	public void setLotteryId(int lotteryId) {
		LotteryId = lotteryId;
	}
	
	@Column(name = "Code")
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	
	@Column(name = "Issue")
	public String getIssue() {
		return Issue;
	}
	public void setIssue(String issue) {
		Issue = issue;
	}
	
	@Column(name = "BelongDate")
	public Date getBelongDate() {
		return BelongDate;
	}
	public void setBelongDate(Date belongDate) {
		BelongDate = belongDate;
	}
	
	@Column(name = "SaleStart")
	public Date getSaleStart() {
		return SaleStart;
	}
	public void setSaleStart(Date saleStart) {
		SaleStart = saleStart;
	}
	
	@Column(name = "SaleEnd")
	public Date getSaleEnd() {
		return SaleEnd;
	}
	public void setSaleEnd(Date saleEnd) {
		SaleEnd = saleEnd;
	}
	
	@Column(name = "WriteTime")
	public Date getWriteTime() {
		return WriteTime;
	}
	public void setWriteTime(Date writeTime) {
		WriteTime = writeTime;
	}
	
	@Column(name = "StatusFetch")
	public int getStatusFetch() {
		return StatusFetch;
	}
	public void setStatusFetch(int statusFetch) {
		StatusFetch = statusFetch;
	}	
}
