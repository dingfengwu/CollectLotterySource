package lottery.domains.capture.sites.opencai;

public class OpenCai {

	private String issueId;

	private String lotteryId;

	private String code;

	private String issue;

	private String saleStart;

	private String writeTime;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public String getSaleStart() {
		return saleStart;
	}

	public void setSaleStart(String saleStart) {
		this.saleStart = saleStart;
	}

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}

	@Override
	public String toString() {
		return "OpenCai [issueId=" + issueId + ", lotteryId=" + lotteryId + ", code=" + code + ", issue=" + issue
				+ ", saleStart=" + saleStart + ", writeTime=" + writeTime + "]";
	}

}
