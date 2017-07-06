package twitterUntil;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ChromeHttpClient {

	
	public static void main(String[] args){
	
	String ss = "616226219143634944" ;
        //进行第二次重新获取页面。*************************
	  Document doc=null;     String  rawHtml=null;
	try {	
		                    HttpClientBuilder      builder = HttpClients.custom();  //防止403forbidden的手段。    
	 builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");   //设置UA字段，UA标识浏览器身份	       
				           CloseableHttpClient    httpClient = builder.build(); 
									       
							//		  HttpHost   proxy = new HttpHost("127.0.0.1", 8118);  
							//		  RequestConfig  config = RequestConfig.custom().setProxy(proxy).build(); 
									       
				      HttpGet httpGet = new HttpGet("https://mobile.twitter.com/anyuser/status/"+ss); 
									      
								   	//		 httpGet.setConfig(config); 	       
				      CloseableHttpResponse response = null;
				
									response = httpClient.execute(httpGet);
			

									      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   }
									  else{    int md=9/0;  }//如果连接状态不成功，比如说没有网络，那么就激活异常存入erroruser数据库。
								       
									                // 2、获取response的entity。  
								    HttpEntity entity = response.getEntity();  
								  rawHtml = EntityUtils.toString(entity);

							       doc = Jsoup.parseBodyFragment(rawHtml);
	
	                    } catch (IOException e) {	System.out.println(ss+"网络连接失败");          e.printStackTrace();	}  
	
	                  Elements comms = doc.getElementsByClass("TweetPermalink-ancestor Timeline-item" );
	                //       System.out.println(rawHtml);
	                //     System.out.println(doc.html());
	                        System.out.println(comms.html());
	                
	
	}
}
