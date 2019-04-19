package lottery.bin;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExecuteServer {
	private static final Logger mLogger = LoggerFactory.getLogger(ExecuteServer.class);
	// 每1小时点触发
	private static final String CRON = "0 0 0/1 * * *";

	@Autowired
	Catalina mCatalina;

	@Scheduled(cron = CRON)
	public void execute() throws IOException {
		mLogger.info("重启====>>>>>>>>>");
		mCatalina.executeOpenCai();

		
	}

}
