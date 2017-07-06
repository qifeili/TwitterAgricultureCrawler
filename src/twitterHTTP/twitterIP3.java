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


public class twitterIP3  implements Runnable {

	public void run() {
		// TODO 自动生成的方法存根

		 Connection conna = ConnectJDBC.getConnection(); 
		 PreparedStatement  urlsql =null ,csql=null,ssql=null;   
         long count= 0;   String ip="";
         
		 try {			
	   PreparedStatement  ipsql =conna.prepareStatement("select userid , content  from twitterend");
	   
	   ResultSet  ips = ipsql.executeQuery();
	
				while (ips.next()) {
			
					String userid = ips.getString("userid");

						synchronized (this) {           
      
                               csql=conna.prepareStatement("select * from ss where userId = ? ");     	                                    
                               csql.setString(1, userid);
                           ResultSet    cssl=csql.executeQuery();	    

                        if (cssl.next()) {		

                            System.out.println(Thread.currentThread().getName()+"线程"+userid+"该数据已被其他线程读过！");
                             continue;          
                                         } 	 
                //数据库里如果没有该用户id，那就添加进去，并且继续访问。
                          ssql = conna.prepareStatement("insert into ss values(?)");     	                                    
                          ssql.setString(1, userid); 					 
                          ssql.executeUpdate();	    						      		                	
          //         System.out.println(Thread.currentThread().getName()+"线程 得到了数据："+userid);        

                    }    //同步块	
			
	     	System.out.println(Thread.currentThread().getName()+"正在读取的是第"+(++count)+"个数据："+ ips.getString("userid"));
						
				}//while循环
		
				
		 } catch (SQLException e) {
				e.printStackTrace();
			}
		
		
		
		
	}
	
	public static void main(String[] args){
		System.out.println("Start~");
		
		Runnable atw =new twitterIP3();
		
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
		   
		   a.start(); b.start(); c.start(); //d.start(); e.start(); f.start(); g.start(); h.start(); i.start(); j.start();
		 
	}

}
