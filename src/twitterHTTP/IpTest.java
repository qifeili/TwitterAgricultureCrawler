package twitterHTTP;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jonathon.dao.ConnectJDBC;

public class IpTest {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		   Connection conna = ConnectJDBC.getConnection();  
		 CloseableHttpResponse response =null;//因为logger 要用到。
          String URL = "";
			try {       
				  // 1、通过HttpGet获取到response对象      
		   HttpClientBuilder    builder = HttpClients.custom();  //防止403forbidden的手段。    
	      builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36");   //设置UA字段，UA标识浏览器身份	       
	      CloseableHttpClient   httpClient = builder.build(); 
		       if (!(URL.contains("http"))) {
				URL ="http://"+URL;
			}
	   	 HttpGet  httpGet = new HttpGet("https://fb.me/7NFVm3hHM");    
	   
	           response = httpClient.execute(httpGet);  
	     
       System.out.println("响应状态码："+response.getStatusLine().getStatusCode());
	    
       if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   	
		        
	       // 2、获取response的entity。  
		           HttpEntity  entity = response.getEntity();  
		            String  rawHtml = EntityUtils.toString(entity);

		        Document     doc = Jsoup.parseBodyFragment(rawHtml);
		
		Elements title =doc.getElementsByTag("title");
		
		System.out.println(title.get(0).text());
	//	System.out.println(doc.html());
/*		try {
	    	 FileWriter file =new FileWriter("F:\\test1.html");		          
				file.write(doc.html());
				 file.flush();
			} catch (IOException e) {	e.printStackTrace();   }*/
       }
	}catch(Exception e){  
		e.printStackTrace();  }

}
}
