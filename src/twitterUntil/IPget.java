package twitterUntil;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;  
import java.io.Writer;  
import java.util.Scanner;  
  
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;  
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.request.CoreAdminRequest.WaitForState;
import org.aspectj.weaver.ast.Var;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class IPget{  
  
    public static void downloadPagebyGetMethod() throws IOException { 
    	
    	  Logger logger =Logger.getLogger(IPget.class); 
    	  int zong=0  ;  //访问总次数
try {
           //  for (int i = 0; i < 3; i++) {
          while(true){
        	  
    	  // 1、通过HttpGet获取到response对象      
        HttpClientBuilder builder = HttpClients.custom();  //防止403forbidden的手段。
       //设置UA字段，UA标识浏览器身份
       builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
       
       CloseableHttpClient httpClient = builder.build(); 
       
      // HttpHost proxy = new HttpHost("127.0.0.1", 8118);  
     //  RequestConfig config = RequestConfig.custom().setProxy(proxy).build(); 
       
     //  HttpGet httpGet = new HttpGet("http://ip.cn");  
       HttpGet httpGet = new HttpGet("http://tanmeitu.cn");  
       
      // httpGet.setConfig(config); 
       
        CloseableHttpResponse response = httpClient.execute(httpGet);  

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     }
        else{    int md=9/0;   }
        
                // 2、获取response的entity。  
                HttpEntity entity = response.getEntity();  

             String rawHtml = EntityUtils.toString(entity);
              
            System.out.println(rawHtml);
           System.out.println("访问次数："+(++zong));
          /*   Document document=Jsoup.parse(rawHtml);
              Elements ips =document.getElementsByClass("well");
              System.out.println(ips.html());
               logger.warn(ips.html());*/
            
		    	/*FileWriter file =new FileWriter("E:\\xunhuan2.html");		          
					 file.write(rawHtml);
					 file.flush();*/
	    Thread.sleep(10000); //延迟10秒
        
                if (response != null) {  
                    response.close();  
                                         }    	             			
                		}//for语句
  } catch (Exception e) {  System.out.println("激活了try");  }
  
    }//方法名



    private static void While(boolean b) {
		// TODO 自动生成的方法存根
		
	}

	public static void main(String[] args) {  
        try {  
            downloadPagebyGetMethod();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  