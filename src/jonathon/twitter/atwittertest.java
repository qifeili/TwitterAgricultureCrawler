package jonathon.twitter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jonathon.dao.ConnectJDBC;
import twitterUntil.atwitteryichang;

public class atwittertest {

	public static void main(String[] args) {  
	
	
	     //**********************************用户读取和抓取内容分界线*********************************	       	       
        String ss="652176936932913152";
        String  commentgroup = "" ;   String  LastId = null;  String time =null;   String artical =null;      
   
        Connection conna = ConnectJDBC.getConnection();  
		try {       
			  // 1、通过HttpGet获取到response对象      
	   HttpClientBuilder    builder = HttpClients.custom();  //防止403forbidden的手段。    
     builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");   //设置UA字段，UA标识浏览器身份	       
     CloseableHttpClient   httpClient = builder.build(); 
	       
	  HttpHost   proxy = new HttpHost("127.0.0.1", 8118);  
	  RequestConfig  config = RequestConfig.custom().setProxy(proxy).build(); 
	       
  	 HttpGet  httpGet = new HttpGet("https://mobile.twitter.com/anyuser/status/"+ss); 
	      
	 httpGet.setConfig(config); 	       
  
	 CloseableHttpResponse response = httpClient.execute(httpGet);  
                   System.out.println(response.getStatusLine().getStatusCode());
                   
                   if (response.getStatusLine().getStatusCode()==404) {
                  	 PreparedStatement	 sql=conna.prepareStatement("insert into Twitter values(? ,? , ? ,? ,? )");     	                                           
          		     sql.setString(1, ss);  sql.setString(2, time); sql.setString(3, artical); sql.setString(4, commentgroup);sql.setString(5, "1");  sql.executeUpdate();	      
          		    	System.out.println(" 404 网络正常但是该网页不存在，该用户id已存入数据库，其他为空值。");
          		    //	continue;
                   }         
			    
                   if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   }	
			      else {   int dds=8/0; } //如果网络不通返回503，或者被服务端禁止访问返回403则存入erroruser数据库 。
				        
	                // 2、获取response的entity。  
	           HttpEntity  entity = response.getEntity();  
	            String  rawHtml = EntityUtils.toString(entity);

	        Document     doc = Jsoup.parseBodyFragment(rawHtml);
	            
		//	doc = Jsoup.connect("https://mobile.twitter.com/anyuser/status/"+ss).get();		  

	             //处理第一个意外情况
	             Elements  yichangs=doc.getElementsByClass("timeline inreplytos");
		          
	                String yichan =yichangs.text();    String[] yagns =yichan.split(" "); 
	                int yichang =yagns.length;				       
		             if(yichang==1){
		                         //如果是正常的空值，什么也不用做继续就好
		             }
	                else{
						String[] yics = yichangs.html().split("data-id=\"");
						 String[] yiss =yics[1].split("\">");
						String chus=ss;   ss=yiss[0];							
					//	atwitteryichang.yichang(chus,ss);        //发现异常网页后，把原网页id和跳转网页id传值过去。     
					
						PreparedStatement yichangss =conna.prepareStatement("insert into yichang values(?)");
						    yichangss.setString(1, chus);
						    yichangss.executeUpdate();
						    yichangss.close();
						    
						 int mmdd=8/0;  //激发trycatch打断该数据的for循环。
		              }		
	             
		   
		     
		 
		    
	 Elements  	body = doc.getElementsByClass("tweet-text");	     
	 Elements   Times =doc.getElementsByClass("metadata");
		    
		    
		    String[] mts = body.html().split("<\\/div>"); 
		    String[] times = Times.select("a").text().split("V");
		    
		               //用户推特发表的内容
		     Document docmain = Jsoup.parseBodyFragment(mts[0]);
	    	 Element bodymain = docmain.body();	    	
	    	 artical = bodymain.text(); 
	    //	 System.out.println("***发表的文章内容："+artical);          //用户的文章题目
	    
	    	   time =times[0];     
	   //	 System.out.println(time);	                                                         //文章发表时间
		   
	    	
	 
	    	 for (int i = 1; i < mts.length; i++) {
		   Document docm = Jsoup.parseBodyFragment(mts[i]);
		    	 Element bodyc = docm.body();
		    	 String commbe = bodyc.text(); 
		    
		    	 System.out.println("*******评论："+ commbe);                //第一页用户评论 
               commentgroup = commentgroup+ ("<<o>>" + commbe); 	
			}

	    	 if(mts.length>=12){
	    	 
           //截取最后一个评论的id，用于拼接url请求
    	     Elements bods = doc.getElementsByClass("tweet-content");
       	 Element coo3 = bods.last(); 
       	
       	String[] coo5 = coo3.html().split("\">"); 
       	String[] coo6 = coo5[0].split("\"");
       
           LastId =coo6[coo6.length-1] ;

   //    	 System.out.println(LastId);
		    
		    //####################判断是否有未加载评论############################使用了httpclient技术######################################	
       	 while(true){
		      
       		 HttpClientBuilder    builder2 = HttpClients.custom(); 
       		 // 1、通过HttpGet获取到response对象  
       		 CloseableHttpClient httpClient2 = builder2.build(); 
       		 
       		 HttpHost proxy2 = new HttpHost("127.0.0.1", 8118);  
       	     RequestConfig config2 = RequestConfig.custom().setProxy(proxy2).build(); 

		          HttpGet   httpGet2 = new HttpGet("https://mobile.twitter.com/i/rw/conversation/"+ss+"/"+ss+"/descendants/"+LastId); 
	              
		          httpGet2.setConfig(config2);                   //3.设置代理

	            CloseableHttpResponse   response2 = httpClient2.execute(httpGet2);
 
		        if (response2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
		            
		                // 2、获取response的entity。  
		              HttpEntity  entity2 = response2.getEntity();  
		  
		                // 3、获取到InputStream对象，并对内容进行处理                 
		                InputStream inputStream=entity2.getContent();
		          
		                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		                BufferedReader reader = new BufferedReader(inputStreamReader);

		                String s =null;         String jsonm=null;
		            
		                while ((s=reader.readLine())!=null) {  
                    		jsonm =s;//这个不能省，因为后面判断是否有下一个json用。					
		                	
						String[] pir1= s.split("ine-item");
				//		System.out.println("此段的评论数量："+(pir1.length-1));						
						
						for (int i = 1; i < pir1.length; i++) {
				           
							if (i<pir1.length-1) {

							    	String   meetjson = "{'html':'"+pir1[i]+"'}";
					              	JSONObject djson  =new JSONObject(meetjson);		
					              //	System.out.println(djson.get("html"));
							
					              	Document docum= Jsoup.parseBodyFragment(djson.get("html").toString());
								
					              	String[] comtext3 = docum.text().split(">"); String comtext = comtext3[1];
					 			
					       //       	System.out.println(comtext);                          //获取的用户评论内容  
					              	 commentgroup = commentgroup+ ("<<o>>" + comtext);
						     	}
				              	
				              	if(i==pir1.length-1){    //获取第二个json数组最后一个用户id        
				              		String   meetjson = "{'html':'"+pir1[i]+"'}";
					              	JSONObject djson  =new JSONObject(meetjson);		
					              //	System.out.println(djson.get("html"));
							
					              	Document docum= Jsoup.parseBodyFragment(djson.get("html").toString());
								
					              	String[] comtext3 = docum.text().split(">");    String[] comtextss = comtext3[1].split("\"}");  
					              	 String comtext = comtextss[0];
					 			
					              //	 System.out.println(comtext);                          //获取的用户评论内容  
					              	 commentgroup = commentgroup+ ("<<o>>" + comtext);
				              		
				              	String[] lastid2 = docum.html().split("id=\""); String[] lastId2 = lastid2[1].split("\"");
				              	 LastId = lastId2[0];		
				              	 
				                                                  	} //获取第二个json数组最后一个用户id
				              	   
						}     //获取js请求后的每一个单独的评论
					
					
   }          //读取获取json转html的一行		
						
						System.out.println(LastId);			 
                  //对json数组进行判断，然后提取评论内容
                     if(!jsonm.contains("nextCursorEndpoint")){
   	                      
                   	                    break;       //判断是否有下一个ajax加载，没有则终止while(ture)循环。
                   	  }
		                   
	
		              if (response != null) {  
		                       	response.close();			          //关闭响应。
	                        }  
		  
		        }          //   if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  的结尾 }。
		else{        //如果第二个url连接状态Http State失败，那就要打断该url进程。       
		        	break;
		        }
		                
        	}   //while(true)语句的括号
}//if判断需不需要请求httpclient。
       	 
		System.out.println(time);   System.out.println(artical);
		System.out.println("评论："+commentgroup);					
		        
/*					if(yiwai1=0){};
		if(yiwai1=1){ss=chushi}
*/
		PreparedStatement	 sql=conna.prepareStatement("insert into Twitter values(? ,? , ? ,? ,?  )");     	                                    
           
		     sql.setString(1, ss);
		     sql.setString(2, time); 
			 sql.setString(3, artical);
			 sql.setString(4, commentgroup);
			 sql.setString(5, "1");
	         sql.executeUpdate();	      
	      
	      System.out.println(Thread.currentThread().getName()+"线程已经把此数据存储");   
	     //   System.out.println("****************************************#################****************************************！");
	        sql.close();    
	       
		}catch(Exception e){    //e.printStackTrace();  
			    e.printStackTrace();                   		
			         try {  	PreparedStatement	errorid = conna.prepareStatement("insert into erroruser values( ? )");             
			                           errorid.setString(1, ss);             
		                               errorid.executeUpdate();	    		
		                               System.out.println("该条数据已存入ErrorUser数据库。");
	   		             } catch (SQLException e1) {   e.printStackTrace();	}     	    
	         };	
	
	
	}
	
}
