package lottery.bin;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {

	private static final Logger logger = LoggerFactory.getLogger(StartUp.class);
	private static ApplicationContext applicationContext;

	public static void main(String[] args) throws IOException {
		logger.debug("start up main method.");
		String[] configLocations = {"spring-config.xml" };
		applicationContext = new ClassPathXmlApplicationContext(configLocations);
		ExecuteServer mExecuteServer = (ExecuteServer) applicationContext
				.getBean("executeServer");
		mExecuteServer.execute();
	}
}