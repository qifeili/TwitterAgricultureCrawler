package weiBo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import jonathon.dao.ConnectJDBC;

public class xinLang1 implements Runnable {

	public void run() {
	 	 Connection conna = ConnectJDBC.getConnection();   
		    
				try {       
					  // 1、通过HttpGet获取到response对象      
			  HttpClientBuilder    builder = HttpClients.custom();  //防止403forbidden的手段。    
		      builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");   //设置UA字段，UA标识浏览器身份	  
		                 // wap：Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1    
		      CloseableHttpClient   httpClient = builder.build(); 		    
		   
		//	  HttpHost   proxy = new HttpHost("127.0.0.1", 8118);  
		//	  RequestConfig  config = RequestConfig.custom().setProxy(proxy).build(); 
			       
		   	 HttpGet  httpPost = new HttpGet("http://weibo.cn/u/2932880775?page=3"); 
			      
		      String cookie="_T_WM=2ce043d1933424907dfb5ad2a182a6c9; SUB=_2A2514oG7DeRhGedI7lIQ8S7JzjiIHXVXLC_zrDV6PUJbkdANLWXnkW0Uy2hPV4RxRBfoc6fg1YvTi7WwHw..; SUHB=08JKyVHwkxbzc5; SCF=AqgLHlaKv_JfgfST508IBu5F3ftt9J7JrBFrpu7GK_S7ZPcI8qIdNPAmnYgjCSKUUbtJ6hVDgc2RZTWymUEJtKE.; SSOLoginState=1491530219; OUTFOX_SEARCH_USER_ID_NCOO=1759299166.6346223; _T_WL=1; _WEIBO_UID=1650110554; M_WEIBOCN_PARAMS=featurecode%3D20000180%26oid%3D4093831345030958%26luicode%3D10000011%26lfid%3D1076032233736904";
		      httpPost.setHeader("Cookie",cookie);
	
		      //	 httpGet.setConfig(config); 	       
		   
		  
		   	 
			  CloseableHttpResponse response = httpClient.execute(httpPost);  
		     
              System.out.println("响应状态码："+response.getStatusLine().getStatusCode());
                 
			           HttpEntity  entity = response.getEntity();  
			            String  rawHtml = EntityUtils.toString(entity);

			        Document     doc = Jsoup.parseBodyFragment(rawHtml);
			            
				//	doc = Jsoup.connect("https://mobile.twitter.com/anyuser/status/"+ss).get();		  
			        						
			        System.out.println(doc.html());
			        try {
			        	  FileWriter file =new FileWriter("D:\\te.html");		          
			        					file.write(doc.html());
			        					file.flush();
			        		 } catch (IOException e) {e.printStackTrace();}

				}catch(Exception e){    //e.printStackTrace();  
				    e.printStackTrace();                   		 	    
		             };	 	  
			  

}//run方法名。

	
	
	public static void main(String[] args){
		System.out.println("Start~");
		
		 xinLang1  atw =new xinLang1();
		 
		   Thread a1 =new Thread(atw, "First");
		   Thread b1 =new Thread(atw,"Second");
		   Thread c1 =new Thread(atw,"Three");
		   Thread d1 =new Thread(atw,"Four");
		   Thread e1 =new Thread(atw,"Five");
		   Thread f1 =new Thread(atw,"Six");
		   Thread g1 =new Thread(atw,"Seven");
		   Thread h1 =new Thread(atw,"Eight");
		   
		   a1.start();//b1.start();c1.start();d1.start();e1.start();//f1.start();g1.start();h1.start();
		   
	}
}


