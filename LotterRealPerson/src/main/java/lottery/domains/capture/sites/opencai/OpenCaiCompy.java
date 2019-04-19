package lottery.domains.capture.sites.opencai;

public class OpenCaiCompy {
	
	private String lotteryId;

	private String period;

	private String code;

	private String begin_time;

	private String end_time;

	private String fb_time;

	public String getLotteryId() {
		return lotteryId;
	}


	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}


	public String getPeriod() {
		return period;
	}
	
	
	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getFb_time() {
		return fb_time;
	}

	public void setFb_time(String fb_time) {
		this.fb_time = fb_time;
	}


	@Override
	public String toString() {
		return "OpenCaiCompy [lotteryId=" + lotteryId + ", period=" + period + ", code=" + code + ", begin_time="
				+ begin_time + ", end_time=" + end_time + ", fb_time=" + fb_time + "]";
	}

	

}
