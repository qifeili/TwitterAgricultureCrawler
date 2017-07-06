package twitterUntil;

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
import org.apache.http.client.config.RequestConfig;
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

import jonathon.dao.ConnectJDBC;

public class yichang2{
            //专门处理异常网页（顶部是评论，点开查询下一页）的一个类。
	public static void main(String[] args){	    
		
	    Connection conna = ConnectJDBC.getConnection();  
	    
	try {
				   
						
					PreparedStatement   csql=conna.prepareStatement("select * from yichang");     	                                    	   
			                 
					ResultSet cssl=csql.executeQuery();	    
			                               
			                 while(cssl.next()) {		
			                	 
			                	System.out.println(cssl.getString(1));
							
			                	String ss=cssl.getString(1);
			                	ss="661332321220456448";
			                	

								try {         String commentgroup=null;
									  // 1、通过HttpGet获取到response对象      
							   HttpClientBuilder    builder = HttpClients.custom();  //防止403forbidden的手段。    
						      builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");   //设置UA字段，UA标识浏览器身份	       
						      CloseableHttpClient   httpClient = builder.build(); 
							       
					//		  HttpHost   proxy = new HttpHost("127.0.0.1", 8118);  
					//		  RequestConfig  config = RequestConfig.custom().setProxy(proxy).build(); 
							       
						   	 HttpGet  httpGet = new HttpGet("https://mobile.twitter.com/anyuser/status/"+ss); 
							      
						   	//		 httpGet.setConfig(config); 	       
						   	CloseableHttpResponse response = null;
			try {	    response = httpClient.execute(httpGet);  
		                             	} catch (Exception e) {
		                          System.out.println("网络连接失败。");		                     
			}		
							      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   }
							  else{  System.out.println("网络状态不行，连接错误。");  int md=9/0;  }//如果连接状态不成功，比如说没有网络，那么就激活异常存入erroruser数据库。
						       
							                // 2、获取response的entity。  
							           HttpEntity  entity = response.getEntity();  
							            String  rawHtml = EntityUtils.toString(entity);

							        Document     doc = Jsoup.parseBodyFragment(rawHtml);
							            
								//	doc = Jsoup.connect("https://mobile.twitter.com/anyuser/status/"+ss).get();		  

							             //处理第一个意外情况
							             Elements  yichangs=doc.getElementsByClass("timeline inreplytos");
								          
							                String yichan =yichangs.text();    String[] yagns =yichan.split(" "); 
							                int yichang =yagns.length;				       
								           
											String[] yics = yichangs.html().split("data-id=\"");
											 String[] yiss =yics[1].split("\">");
											String chus=ss;    ss=yiss[0];							       
											
							             //进行第二次重新获取页面。*************************
											    builder = HttpClients.custom();  //防止403forbidden的手段。    
											      builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");   //设置UA字段，UA标识浏览器身份	       
											   httpClient = builder.build(); 
												       
										//		  HttpHost   proxy = new HttpHost("127.0.0.1", 8118);  
										//		  RequestConfig  config = RequestConfig.custom().setProxy(proxy).build(); 
												       
											 httpGet = new HttpGet("https://mobile.twitter.com/anyuser/status/"+ss); 
												      
											   	//		 httpGet.setConfig(config); 	       
											     response = httpClient.execute(httpGet);  

												      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {   }
												  else{    int md=9/0;  }//如果连接状态不成功，比如说没有网络，那么就激活异常存入erroruser数据库。
											       
												                // 2、获取response的entity。  
												           entity = response.getEntity();  
												        rawHtml = EntityUtils.toString(entity);

												        doc = Jsoup.parseBodyFragment(rawHtml);
								     
								     
							 Elements  	body = doc.getElementsByClass("tweet-text");	     
							 Elements   Times =doc.getElementsByClass("metadata");
								    
								    
								    String[] mts = body.html().split("<\\/div>"); 
								    String[] times = Times.select("a").text().split("V");
								    
								               //用户推特发表的内容
								     Document docmain = Jsoup.parseBodyFragment(mts[0]);
							    	 Element bodymain = docmain.body();	    	
							    String	 artical = bodymain.text(); 
							    	 System.out.println("***发表的文章内容："+artical);          //用户的文章题目
							    
							    String   time =times[0];     
							   	 System.out.println(time);	                                                         //文章发表时间
								   
							    	
							 
							    	 for (int i = 1; i < mts.length; i++) {
								   Document docm = Jsoup.parseBodyFragment(mts[i]);
								    	 Element bodyc = docm.body();
								    	 String commbe = bodyc.text(); 
								    
								    	 System.out.println("*******评论："+ commbe);                //第一页用户评论 
						                commentgroup = commentgroup+ ("<<o>>" + commbe); 	
									}
						
							    //	 if(mts.length>=12){
							    	 
							    	 /*   	 
						           //截取用户id
								String  link = doc.getElementsByTag("link").first().toString();  		
								String[] links = link.split("status\\/"); 
							    String[] starts = null ;  String UserId =null;
									try{	  starts = links[1].split("\""); 
									  UserId = starts[0];           }catch(Exception e){};
						       //       System.out.println(UserId);             	   
					*/				  
							    	 
						            //截取最后一个评论的id，用于拼接url请求
						     	     Elements bods = doc.getElementsByClass("tweet-content");
						        	 Element coo3 = bods.last(); 
						        	
						        	String[] coo5 = coo3.html().split("\">"); 
						        	String[] coo6 = coo5[0].split("\"");
						        
						       String  LastId =coo6[coo6.length-1] ;

						       	 System.out.println(LastId);
								    
								    //####################判断是否有未加载评论############################使用了httpclient技术######################################	
						        	 while(true){
								      
						        		 HttpClientBuilder    builder2 = HttpClients.custom(); 
						        		 // 1、通过HttpGet获取到response对象  
						        		 CloseableHttpClient httpClient2 = builder2.build(); 
						        		 
						        	//	 HttpHost proxy2 = new HttpHost("127.0.0.1", 8118);  
						        	  //   RequestConfig config2 = RequestConfig.custom().setProxy(proxy2).build(); 

								          HttpGet   httpGet2 = new HttpGet("https://mobile.twitter.com/i/rw/conversation/"+ss+"/"+ss+"/descendants/"+LastId); 
							              
								    //      httpGet2.setConfig(config2);                   //3.设置代理

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
								 System.out.println("第二个httpclient请求失败。");    
									break;
								        }
								                
						         	}   //while(true)语句的括号
				
						        	 
								System.out.println(time);   System.out.println(artical);
								System.out.println("评论："+commentgroup);					
								        				
								PreparedStatement	 sql=conna.prepareStatement("insert into Twitteryichang values(? ,? , ? ,?  )");     	                                    
						            
								     sql.setString(1, chus);
								     sql.setString(2, time); 
									 sql.setString(3, artical);
									 sql.setString(4, commentgroup);
							         sql.executeUpdate();	      

							        sql.close();    
							            				             												             
							       
								}catch(Exception e){    //e.printStackTrace();  
							                                                       
									PreparedStatement errorid;     
									try {          
										
										errorid = conna.prepareStatement("insert into erroruser values( ? )");             
									    errorid.setString(1, ss);             
								        errorid.executeUpdate();	    
															     
									} catch (SQLException e1) {	e1.printStackTrace();		}     	  
										}
			                       	
			                	
			                	
			                	
			                	
			                 }//while next方法。
	                  
	
	}catch (Exception e) {           //这个是文件读取时候必须得try catch ，不能删
			e.printStackTrace();
		}
			
	  

	   }  //主方法。
	
}//类方法。
	