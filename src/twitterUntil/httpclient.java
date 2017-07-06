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
import org.apache.solr.client.solrj.request.CoreAdminRequest.WaitForState;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
  
public class httpclient{  
  
    public static void downloadPagebyGetMethod() throws IOException { 
    	
    	
try {
          //   for (int i = 0; i < 10; i++) {

    	  // 1、通过HttpGet获取到response对象      
        HttpClientBuilder builder = HttpClients.custom();  //防止403forbidden的手段。
       //设置UA字段，UA标识浏览器身份
       builder.setUserAgent("User-Agent:Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) App leWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D257 Safari/9537.53");
       //谷歌：("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
       
       CloseableHttpClient httpClient = builder.build(); 
       
    //   HttpHost proxy = new HttpHost("127.0.0.1", 8118);  
      // RequestConfig config = RequestConfig.custom().setProxy(proxy).build(); 
       
       HttpGet httpGet = new HttpGet("https://mobile.twitter.com/anyuser/status/625660485065510912");  
     
    //   httpGet.setConfig(config); 
       
        CloseableHttpResponse response = httpClient.execute(httpGet);  

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     }
        else{    int md=9/0;   }
        
                // 2、获取response的entity。  
                HttpEntity entity = response.getEntity();  

             String rawHtml = EntityUtils.toString(entity);
              
        //    System.out.println(rawHtml);
              
         	FileWriter file =new FileWriter("E:\\ce.html");		          
			 file.write(rawHtml);
			 file.flush();
		//	System.out.println(rawHtml);
        
                if (response != null) {  
                    response.close();  
                                         }    	             			
                // 		}//for语句
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