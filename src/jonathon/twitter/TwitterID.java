package jonathon.twitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import LateTwitter.twitterYichang;
import jonathon.dao.ConnectJDBC;
import twitterUntil.atwitteryichang;
import twitterUntil.httpclient;

public class TwitterID implements Runnable {
  
	//获取每个用户ID
	public  void run(){
		    
		    Logger logger =Logger.getLogger(TwitterID.class);    //配置日志
		    Connection conna = ConnectJDBC.getConnection();  
		    
		    FileReader frea;  BufferedReader bufaa;   int zongii = 1  ;          //读取文件
			try {
				frea = new FileReader("E:/fenge.txt");
			   bufaa =new BufferedReader(frea);

			   
			    String ssid = null ;     int  mii =0  ;      PreparedStatement csql = null ;  PreparedStatement    ssql =null;  String ss=null;
			 while ((ssid=bufaa.readLine())!=null) {
			//	System.out.println("第 "+(mii++)+" 行全部数据："+ssid);    
				String[] ssa=ssid.split(":");
				String[] ssb=ssa[2].split("	");
				String[] ssc =ssb[1].split(" ");
		                      	mii++;
			//	System.out.println("该行共有多少数据："+ssc.length);
			  //如果这一行出错，将会跳过这一行。
				for (int iaa = 0; iaa < ssc.length; iaa++ ) {
					  try {	
			            	ss = ssc[iaa]; 		
				
							synchronized (this) {                  //同步锁，只能同时有一个进程进入这个判读程序。 1.判断是否已经存在于twitter数据库。
								                                                        //2.如果没有就查询ss表是否被其他线程读过。 3.没有被其他线程读过，就存入ss表。
							//System.out.println("此次读取数据："+zongii++);
						  	 csql=conna.prepareStatement("select * from twitter where userId = ? ");     	                                    
							     csql.setString(1, ss);
				                 ResultSet cssl=csql.executeQuery();	    
				                               
				                 if (cssl.next()) {						                	
				                	 System.out.println(Thread.currentThread().getName()+"线程twitter数据表已有"+ss+"数据！");
				                	 continue;              //如果查到了该数据，说明已经访问过，跳过本次for循环；
								}
				                 				               
				                 //twitter没存过该数据，就开始读取ss表；
				                 else{        
								  	 csql=conna.prepareStatement("select * from ss where userId = ? ");     	                                    
								     csql.setString(1, ss);
					                 cssl=csql.executeQuery();	    
					                               
					                 if (cssl.next()) {		
					                	 
					                	 System.out.println(Thread.currentThread().getName()+"线程"+ss+"该数据已被其他线程读过！");
					                	 continue;              //如果查到了该数据，说明已经访问过，跳过本次for循环；
									} 	 
				                	 //数据库里如果没有该用户id，那就添加进去，并且继续访问。
				                	 	 ssql = conna.prepareStatement("insert into ss values(?)");     	                                    
									     ssql.setString(1, ss); 					 
								         ssql.executeUpdate();	    						      		                	
				                  System.out.println(Thread.currentThread().getName()+"线程 得到了"+mii+"行第"+(iaa+1)+"个数据："+ss);   
				                 }
				                 zongii++;                //第多少个用户id
		                      //  logger.warn("读取到了："+ss);
				            }    //同步块		
						
						TwitterContent.ContentAnalyse(ss ,conna,zongii);

						  
		      } catch (Exception e) {
		    	            try {   PreparedStatement	errorid = conna.prepareStatement("insert into erroruser values( ? )");             
		                                 errorid.setString(1, ss);             
		                                 errorid.executeUpdate();	    		
		                                 System.out.println("该条数据出错已存入ErrorUser数据库。");
		                        } catch (SQLException e1) {   e.printStackTrace();	}     
		    	                         System.out.println("跳过该数据，不让线程停止。"); }//主要是防止同步块出问题。   
		
				}		//遍历每一个用户id；	
		}	   //读取每一行用户id；
		          conna.close();
		}catch (Exception e) {           //这个是文件读取时候必须得try catch ，不能删
			e.printStackTrace();
		}	


	}	
}
