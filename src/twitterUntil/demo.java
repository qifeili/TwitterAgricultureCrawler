package twitterUntil;

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

public class demo implements Runnable {

public void run() {

    Logger logger =Logger.getLogger(demo.class);    //配置日志
    Connection conna = ConnectJDBC.getConnection();  
    
    FileReader frea;  BufferedReader bufaa;   int zongii = 1  ;  int cuo=1;         //读取文件
	try {
		frea = new FileReader("E:/twitter.txt");
	   bufaa =new BufferedReader(frea);

	   
	    String ssid = null ;     int  mii =1  ;   int cunzai = 1,  nocun=1;    PreparedStatement csql = null ;  PreparedStatement    ssql =null;  String ss=null;
	 while ((ssid=bufaa.readLine())!=null) {
	//	System.out.println("第 "+(mii++)+" 行全部数据："+ssid);    
		String[] ssa=ssid.split(":");
		String[] ssb=ssa[2].split("	");
		String[] ssc =ssb[1].split(" ");
                     

		for (int iaa = 0; iaa < ssc.length; iaa++ ) {
			  try {	  
				  System.out.println(ssc[iaa]+"***第多少个："+(zongii++));
	            	ss = ssc[iaa]; 		
		
				     	 csql=conna.prepareStatement("select userid from user  where userId = ? ");     	                                    
					     csql.setString(1, ss);
		                 ResultSet cssl=csql.executeQuery();	    
		                               
		                 if (cssl.next()) {		
		                	 cunzai++;
		                	 System.out.println(Thread.currentThread().getName()+"线程user表已有该数据！");
		                	 continue;           
						}
		                 	              
		                 else{        
						          nocun++;
		                	 	 ssql = conna.prepareStatement("insert into  UserID  values(?)");     	                                    
							     ssql.setString(1, ss); 					 
						         ssql.executeUpdate();	    						 	                	
		                      System.out.println("该数据不存在，已经存入userID表格。");   
		                 }
		     	
				
				 
				  
      } catch (Exception e) { e.printStackTrace();
    	            try { 
    	            	PreparedStatement	errorid = conna.prepareStatement("insert into erroruser values( ? )");             
                         errorid.setString(1, ss);             
                         errorid.executeUpdate();	    		
                         System.out.println("该条数据出错已存入ErrorUser数据库。重复数据："+ (mii++));
                        } catch (SQLException e1) {  
                        	e.printStackTrace();	cuo++;
                        	}     
    	  System.out.println("跳过该数据，不能让线程轻易停止。"); }//主要是防止同步块出问题。   
}		//遍历每一个用户id；
	
}	   //读取每一行用户id；
	 
	 System.out.println("重复数据总数为："+mii);
	 System.out.println("存在得数据总数："+cunzai+"不存在得数据总数："+nocun);
	 System.out.println("错误数据："+cuo);
          conna.close();
}catch (Exception e) {           //这个是文件读取时候必须得try catch ，不能删
	e.printStackTrace();
}
	
}//run方法名。


public static void main(String[] args){
	System.out.println("Start~");
	
	   demo  atw1 =new demo();
	   demo  atw2 =new demo();
	   demo  atw3 =new demo();
	   demo  atw4 =new demo();
	   demo  atw5=new demo();
	   demo  atw6 =new demo();
	   demo  atw7 =new demo();
	   demo  atw8 =new demo();
	 
	   
	   
	   Thread a1 =new Thread(atw1, "First");
	   Thread b1 =new Thread(atw2,"Second");
	   Thread c1 =new Thread(atw3,"Three");
	   Thread d1 =new Thread(atw4,"Four");
	   Thread e1 =new Thread(atw5,"Five");
	   Thread f1 =new Thread(atw6,"Six");
	   Thread g1 =new Thread(atw7,"Seven");
	   Thread h1 =new Thread(atw8,"Eight");
	   
	   a1.start();//b1.start();//c1.start();//d1.start();e1.start();f1.start();g1.start();h1.start();
	   
}
}

