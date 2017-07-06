package crawler.sina.craw;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import crawler.sina.login.Constant;
import crawler.sina.login.LoginSina;
import crawler.sina.parser.ParserBangList;
import crawler.sina.parser.ParserFansList;
import crawler.sina.parser.ParserUserInfo;
import crawler.sina.utils.HttpUtils;


public class CrawSina {
	
	public static String CookieTC = "";
	public static String Cookie = "";
	public static String ticket = "";
	public static String loginUrl = "http%3A%2F%2Fweibo.com%2Fajaxlogin.php%3Fframelogin%3D1%26callback%3Dparent.sinaSSOController.feedBackUrlCallBack%26sudaref%3Dweibo.com";
//	public static HttpClient client = new DefaultHttpClient();
	
	/**
	 * @author whp
	 * @param uid 用户uid
	 * @param page 页码
	 * @param times 当前页第几次抓取
	 * @return 抓取用户发布的信息
	 */
	public String getUserMessage(String uid, String page, String times)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		String url = "";
		if(times.equals("1"))
			url = "http://weibo.com/u/" + uid
				+ "?page="+page+"&is_search=0&_t=FM_143723067216932&ajaxpagelet_v6=1";
		if(times.equals("2"))
			url = "http://weibo.com/u/"+ uid +"?page="+ page +"&pagebar=0&pre_page="+page;
		if(times.equals("3"))
			url = "http://weibo.com/u/"+ uid +"?page="+ page +"&pagebar=1&pre_page="+page;
		HttpGet request = new HttpGet(url);
		request.setHeader("Cookie", CrawSina.Cookie);
		HttpResponse response = client.execute(request);
		String responseText = HttpUtils.getStringFromResponse(response);
		client.getConnectionManager().shutdown();
		return responseText;
	}
	/**
	 * @author whp
	 * @param uid 用户uid
	 * @return 抓取该用户主页信息
	 */
	public String getUserMain(String uid) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url = "http://weibo.com/u/"+uid;
		HttpGet request = new HttpGet(url);
		request.setHeader("Cookie", CrawSina.Cookie);
		HttpResponse response = client.execute(request);
		String responseText = HttpUtils.getStringFromResponse(response);
		client.getConnectionManager().shutdown();
		return responseText;
	}
	/**
	 * @author whp
	 * @param bangType风云榜类别,分别是renwu yisheng shishang jiaoyu licai jianshen lvxing qinggan dianying 
	 * @param dataType排行时间类型,分三种:day week month
	 * @return 返回风云榜排行榜100人
	 */
	public List<String> getBangList(String bangType, String dataType) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String data = getBangTime(dataType);
		String url = "http://bang.weibo.com/aj/getrank";
		HttpPost request = new HttpPost(url);
		request.setHeader("Referer", "http://bang.weibo.com/"+bangType);
		request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		request.setHeader("Host", "bang.weibo.com");
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:39.0) Gecko/20100101 Firefox/39.0");
		request.setHeader("Connection", "keep-alive");
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("ctime", data));
		parameters.add(new BasicNameValuePair("page", "1"));
		parameters.add(new BasicNameValuePair("pagesize", "100"));
		parameters.add(new BasicNameValuePair("space", dataType));
		parameters.add(new BasicNameValuePair("type", "3"));
		UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(parameters);
		request.setEntity(formEntiry);
		HttpResponse response = client.execute(request);
		String responseText = HttpUtils.getStringFromResponse(response);
		client.getConnectionManager().shutdown();
		return ParserBangList.parserBangList(responseText);
	}
	/**
	 * @author whp
	 * @param uid 用户uid
	 * @param page 页码
	 * @return 抓取用户关注的人列表
	 */
	public String getFollowListByUid(String uid, String page) throws ClientProtocolException, IOException{
		HttpClient client = new DefaultHttpClient();
		String url = "http://weibo.com/"+ uid +"/follow?page="+page;
		HttpGet request = new HttpGet(url);
		request.setHeader("Cookie", CrawSina.Cookie);
		HttpResponse response = client.execute(request);
		String responseText = HttpUtils.getStringFromResponse(response);
		client.getConnectionManager().shutdown();
		return responseText;
	}
	/**
	 * @author whp
	 * @param uid 用户uid
	 * @param page 页码 (最大数为5)
	 * @return 抓取用户粉丝列表
	 */
	public List<String> getFansListByUid(String uid, String page) throws IOException{
		List<String> uidList = null;
		try {
			Random random = new Random();
			long s = random.nextInt(9)+1;
			Thread.sleep(s*1000);
			HttpClient client = new DefaultHttpClient();
			String url = "http://weibo.com/"+ uid +"/fans?&uid=&tag=&page="+ page;
			HttpGet request = new HttpGet(url);
			request.setHeader("Cookie", CrawSina.Cookie + CrawSina.CookieTC);
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:39.0) Gecko/20100101 Firefox/39.0");
			request.setHeader("Connection", "keep-alive");
			request.setHeader("Host", "weibo.com");
			request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			HttpResponse response = client.execute(request);
	//		String responseText = HttpUtils.getStringFromResponse(response);
			if(response.getLastHeader("Set-Cookie") != null){
				CrawSina.CookieTC = response.getLastHeader("Set-Cookie").getValue();
			}
			String responseText = EntityUtils.toString(response.getEntity());
			System.out.println(responseText);
			uidList = ParserFansList.getFansList(responseText);
			client.getConnectionManager().shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uidList;
	}
	/**
	 * @author whp
	 * @param uid 用户uid
	 * @return 抓取用户基本信息页面 将信息传递给ParserUserInfo.userInfo中,并存入数据库
	 */
	public String getUserInfo(String uid) {
		String url = "http://weibo.com/"+ uid +"/info";
		try {
			Random random = new Random();
			long s = random.nextInt(9)+1;
			Thread.sleep(s*1000);
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			request.setHeader("Cookie", CrawSina.Cookie);
			request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:39.0) Gecko/20100101 Firefox/39.0");
			request.setHeader("Connection", "keep-alive");
			request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			request.setHeader("Host", "weibo.com");
			HttpResponse response;
			response = client.execute(request);
			String responseText = HttpUtils.getStringFromResponse(response);
			if(!responseText.contains("error_back") && !responseText.contains("http://weibo.com/sorry"))
				ParserUserInfo.userInfo(responseText, uid);
			client.getConnectionManager().shutdown();
			return responseText;
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("连接错误:"+url);
			getUserInfo(uid);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "error.";
	}
	/**
	 * @author whp
	 * @param dataType:风云榜时间类型 day week month
	 * @return 具体时间 yyyyMMdd
	 */
	public String getBangTime(String dataType){
		String data = "";
		if(dataType.equals("day")){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			data = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		else if(dataType.equals("week")){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1*7);
			cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			data = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		else{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.MONTH, -1);
	        data = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		}
		return data;
	}
}
