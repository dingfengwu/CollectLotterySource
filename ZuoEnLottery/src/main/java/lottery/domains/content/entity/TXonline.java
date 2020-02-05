package lottery.domains.content.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TXonline")
public class TXonline {
	
	private static final long serialVersionUID = 1L;
	
	private String issue;
	
	private String count;
	
	private String time;

	@Id
	@Column(name = "issue", unique = true, nullable = false)
	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}
	@Column(name = "count")
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	@Column(name = "time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String ToString() {
		return "期数："+this.issue+"  在线人数:"+this.count+"  时间:"+this.time;
	}
}
