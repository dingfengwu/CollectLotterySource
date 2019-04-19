package lottery.bin;

import java.util.concurrent.ExecutorService;

//import lottery.domains.capture.jobs.K3CrawlerJob;
import lottery.domains.capture.jobs.OpenCaiJob;
//import lottery.domains.capture.jobs.OthersCrawlerJob;
//import lottery.domains.capture.jobs.SSCCrawlerJob;
//import lottery.domains.capture.jobs.X511CrawlerJob;
//import lottery.domains.content.global.JobConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Catalina {

	ExecutorService newFixedThreadPool;

	@Autowired
	private OpenCaiJob mOpenCaiJob;

	/**
	 * OpenCai
	 */
	public void executeOpenCai() {
	
		mOpenCaiJob.execute();

	}	
}