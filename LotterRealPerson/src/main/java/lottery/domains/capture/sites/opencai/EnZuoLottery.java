package lottery.domains.capture.sites.opencai;

import java.util.List;

public class EnZuoLottery {

	private String lotteryId;

	private String code;
	
	private String expect;
	
	private List<String> opencode;
	
	private String opentime;


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

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}

	public List<String> getOpencode() {
		return opencode;
	}

	public void setOpencode(List<String> opencode) {
		this.opencode = opencode;
	}

	public String getOpentime() {
		return opentime;
	}

	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	
	 
}
