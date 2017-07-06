package twitterHTTP;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import jonathon.dao.ConnectJDBC;
import twitterUntil.httpclient;


public class twitterIP  implements Runnable {

	public void run() {
		// TODO 自动生成的方法存根

		
		 Connection conna = ConnectJDBC.getConnection(); 
		 PreparedStatement  urlsql =null ,csql=null,ssql=null;   
         long count= 0;   String ip="";
         
		 try {			
	   PreparedStatement  ipsql =conna.prepareStatement("select userid , content  from qifei");
	   
	   ResultSet  ips = ipsql.executeQuery();
		    
	   Pattern pattern =Pattern.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr){0,1}(\\S)+(\\/)(\\S)+" ); 
		
				while (ips.next()) {
				
					try {
					String userid = ips.getString("userid");
						
	                 csql=conna.prepareStatement("select userid  from user_ip  where userid = ? ");     	                                    
	                 csql.setString(1, userid);
	                 ResultSet cssl=csql.executeQuery();	   
	                  
                      if (cssl.next()) {		
                          System.out.println(Thread.currentThread().getName()+"线程user_ip数据表已有"+userid+"数据！");
                          continue;              //如果查到了该数据，说明已经访问过，跳过本次for循环；
  }               

						synchronized (this) {           
      
                               csql=conna.prepareStatement("select * from ss where userId = ? ");     	                                    
                               csql.setString(1, userid);
                               cssl=csql.executeQuery();	    

                        if (cssl.next()) {		

                            System.out.println(Thread.currentThread().getName()+"线程"+userid+"该数据已被其他线程读过！");
                             continue;              //如果查到了该数据，说明已经访问过，跳过本次for循环；
                                         } 	 
                //数据库里如果没有该用户id，那就添加进去，并且继续访问。
                          ssql = conna.prepareStatement("insert into ss values(?)");     	                                    
                          ssql.setString(1, userid); 					 
                          ssql.executeUpdate();	    						      		                	
                   System.out.println(Thread.currentThread().getName()+"线程 得到了数据："+userid);   
                             

                    }    //同步块	
						
				
				String content =	ips.getString("content");
			
				if ((content==null)||(content.length()<=0)) {
					continue;
				}
				  Matcher mr = pattern.matcher(content);
				
				if (mr.find()) {
					System.out.println(userid+"**url: "+mr.group()+"第多少个url:"+(++count));
			    	String URL =mr.group();
    	            String title ="";
			    	
    	            if (URL.contains("…")) {   continue;		}
    	            
    	            
			    	PreparedStatement   chongsql=conna.prepareStatement("select title from user_ip where url = ? ");     	                                    
                   chongsql.setString(1, URL);
                  ResultSet   chongs=chongsql.executeQuery();	    
			    	if(chongs.next()){
			    		title =chongs.getString("title");
						  urlsql=conna.prepareStatement("insert into User_IP values(? ,?, ?)");     	                                    
				            
		                   urlsql.setString(1, userid);
		                   urlsql.setString(2, URL);
		                   urlsql.setString(3, title);
						   urlsql.executeUpdate();  
			    	}
		    	
			    	else{		
			    		try {       
							  // 1、通过HttpGet获取到response对象      
					   HttpClientBuilder    builder = HttpClients.custom();  //防止403forbidden的手段。    
				      builder.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36");   //设置UA字段，UA标识浏览器身份	       
				      CloseableHttpClient   httpClient = builder.build(); 
				   		      
				      if (!(URL.contains("http"))) {
							URL ="http://"+URL;
						}
				      
				   	 HttpGet  httpGet = new HttpGet(URL);    
				    
				   	 RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
				    	                                                                                                   .setConnectionRequestTimeout(3000)  
				    	                                                                                                   .setSocketTimeout(5000)
				    	                                                                                                   .build();  
				    	
				     	httpGet.setConfig(requestConfig); 
				//      HttpGet  httpGet = new HttpGet("http://tinyurl.com/4qhr5a");   
				   	CloseableHttpResponse  response = httpClient.execute(httpGet);  
				     
			       System.out.println("响应状态码："+response.getStatusLine().getStatusCode());
				    
			       if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   	
					        
				       // 2、获取response的entity。  
					           HttpEntity  entity = response.getEntity();  
					            String  rawHtml = EntityUtils.toString(entity);

					        Document     doc = Jsoup.parseBodyFragment(rawHtml);
					
					Elements titles =doc.getElementsByTag("title");
					    title = titles.get(0).text();
				//	System.out.println(doc.html());
					System.out.println(titles.get(0).text());
					 
					urlsql=conna.prepareStatement("insert into User_IP values(? ,?, ?)");     	                                    
			            
	                   urlsql.setString(1, userid);
	                   urlsql.setString(2, URL);
	                   urlsql.setString(3, title);
					   urlsql.executeUpdate();  
			       }
					  }catch(Exception e){  e.printStackTrace();  }
			       }
					
    
					
				}//mr.find			
				
				} catch (Exception e) {     e.printStackTrace();		
				System.out.println("错误的用户id："+ips.getString("userid"));}
			
				}//while循环
		
				
		 } catch (SQLException e) {
				e.printStackTrace();
			}
		
		
		
		
	}
	
	public static void main(String[] args){
		System.out.println("Start~");
		
		Runnable atw =new twitterIP();
		
		   Thread a =new Thread(atw,"first");
		   Thread b =new Thread(atw,"second"); 
		   Thread c =new Thread(atw,"Three");
		   Thread d =new Thread(atw,"Four");
		   Thread e =new Thread(atw,"Five");
		   Thread f =new Thread(atw,"Six");
		   Thread g =new Thread(atw,"Seven");
		   Thread h =new Thread(atw,"Eight");
		   Thread i =new Thread(atw,"nine");
		   Thread j =new Thread(atw,"ten");
		   Thread k =new Thread(atw,"11");
		   Thread l =new Thread(atw,"12");
		   Thread m =new Thread(atw,"13");
		   Thread n =new Thread(atw,"14");
		   Thread o =new Thread(atw,"15");
		   
		   a.start(); b.start(); c.start(); d.start(); e.start(); f.start(); g.start(); h.start(); i.start(); j.start();
		 
	}

}
