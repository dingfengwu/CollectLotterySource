package lottery.domains.capture.jobs;

import javautils.StringUtil;
import lottery.domains.capture.sites.opencai.EnZuoLottery;
import lottery.domains.content.dao.IssueInfoDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 时时彩
 */
@Component
public class OpenCaiJob {

	private static final Logger logger = LoggerFactory.getLogger(OpenCaiJob.class);

	@Autowired
	private IssueInfoDao issueDao;

	boolean isRunningOpenCai = true;

	@Scheduled(cron = "0/8 * * * * ? ")
	public void execute() throws IOException {
		if ( isRunningOpenCai) {
			isRunningOpenCai = false;
			try {
				while (true) {
					ExecutorService newFixedThreadPool = Executors
							.newFixedThreadPool(1);

					logger.debug("OpenCaiJob execute");
					Callable<boolean[]> aCallable = new Callable<boolean[]>() {
						public boolean[] call() throws Exception {
							boolean start = http_get_fast();

							boolean[] arr = new boolean[] { start };
							return arr;
						}
					};
					Future<?> submit = newFixedThreadPool.submit(aCallable);
					newFixedThreadPool.shutdown();
					boolean[] arr = (boolean[]) submit.get();
					if (arr[0]) {
						break;
					}
				}
			} catch (Exception e) {
				logger.error("抓取OpenCai开奖数据出错", e);
			} finally {
				isRunningOpenCai = true;
			}
		}
	}

	public boolean http_get_fast() throws InterruptedException, IOException {//5779228d092748c1  fffec93eb36e7f1a
		 Properties properties = new Properties();
			File file = new File("url.properties");
			FileInputStream fis = new FileInputStream(file);
			properties.load(fis);
			fis.close();
			String charset = "UTF-8";
			String url = properties.getProperty("url");
			String string = get(url, charset);
			handleDataNew(string);
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean handleDataNew(String data) throws IOException {
		if (StringUtil.isNotNull(data)) {
			Object object = new Object();
			try {
			object = JSONObject.fromObject(data).get("data");
			}catch (Exception e) {
				logger.debug("采集的数据有误！");
			}
			if(object.toString().startsWith("[")) {
				JSONArray array = JSONArray.fromObject(object);
				logger.error(array.toString());
				List<EnZuoLottery> list = new ArrayList<EnZuoLottery>();
				for (Iterator iter = array.iterator(); iter.hasNext();) {
					JSONObject jsonObject = (JSONObject) iter.next();
					 JsonConfig config = new JsonConfig();
				        config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
				          @Override
				          public String transformToJavaIdentifier(String str) {
				            char[] chars = str.toCharArray();
				            chars[0] = Character.toLowerCase(chars[0]);
				            return new String(chars);
				          }
				        });
				        config.setRootClass(EnZuoLottery.class);
				        EnZuoLottery bean = (EnZuoLottery) JSONObject.toBean(jsonObject,
							config);
				        Properties properties = new Properties();
						File file = new File("lottery.properties");
						FileInputStream fis = new FileInputStream(file);
						properties.load(fis);
						fis.close();
						for(String p : properties.stringPropertyNames()) {
							if(bean.getCode().equals(p)) {
								bean.setLotteryId(properties.getProperty(p));
								list.add(bean);
									}
								}
				}
				Collections.reverse(list);
				for (EnZuoLottery bean : list) {
					boolean succes = handleOpenCai(bean);
				}
			}else {
				logger.debug(".....错啦.........."+object.toString());
				return false;
			}
		}
		return true;
	}

	
	private boolean handleOpenCai(EnZuoLottery bean) {
		System.out.println("号码:"+bean.getOpencode().toString().replace("[", "").replace("]", "")+"彩票id:"+bean.getLotteryId()+"彩票奖期:"+bean.getExpect());
		boolean add = issueDao.update(bean.getOpencode().toString().replace("[", "").replace("]", ""), bean.getLotteryId(),bean.getExpect());
		if (add) {
				logger.debug("更新成功...");
				}
		return true;
	}

	public static String get(String urlAll, String charset) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";// 模拟浏览器
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent", userAgent);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
			logger.debug("成功获取OpenCai开奖数据为" + result);
		} catch (Exception e) {
			logger.error("请求OpenCai出错", e);
			e.printStackTrace();
		}

		if (result == null) {
			logger.error("没有从OpenCai获取到任何数据");
		}
		else if(result.indexOf("已经超出每日") > -1) {
			logger.error(result);
		}
		else {
			logger.debug("成功获取OpenCai开奖数据，长度为" + (result == null ? 0 : result.length()));
		}
		return result;
	}
}