package twitterUntil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jonathon.dao.ConnectJDBC;

public class Check {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		 Connection conna = ConnectJDBC.getConnection();  
		    
		 FileReader frea;  BufferedReader bufaa;   int zongii = 1  ;           //读取文件
			try {
				frea = new FileReader("E:/fengeTP.txt");
			   bufaa =new BufferedReader(frea);

			   
			    String ssid = null ;     int  mii =0  ,geshu=0;      PreparedStatement csql = null ;  PreparedStatement    ssql =null;   String ss=null;
			 while ((ssid=bufaa.readLine())!=null) {
			//	System.out.println("第 "+(mii++)+" 行全部数据："+ssid);    
				String[] ssa=ssid.split(":");
				String[] ssb=ssa[2].split("	");
				String[] ssc =ssb[1].split(" ");
		                      	mii++;		
				for (int iaa = 0; iaa < ssc.length; iaa++ ) {
					     	geshu++;
			            	ss = ssc[iaa]; 		
		
			            	 csql=conna.prepareStatement("select * from twitter where userId = ? ");     	                                    
						     csql.setString(1, ss);
			                 ResultSet cssl=csql.executeQuery();	    
			                               
			                 if (cssl.next()) {				                	 
			                	 System.out.println(mii+"行"+(geshu)+"个数据："+ss+"已经存在。");
			                	 continue;              //如果查到了该数据，说明已经访问过，跳过本次for循环；
							}
		
			                 else{
			                	 ssql = conna.prepareStatement("insert  into  adduser  values(?)");     	                                    
							     ssql.setString(1, ss); 					 
						         ssql.executeUpdate();	    						      		                	
		                  System.out.println( "第"+mii+"行第"+geshu+"个数据："+ss+"已经存到add表");   			                	 
			                 }            					  	    	  
	
			 }		//遍历每一个用户id；	
		}	   //读取每一行用户id；
		          conna.close();  csql.close();  ssql.close();
		}catch (Exception e) {           //这个是文件读取时候必须得try catch ，不能删
			e.printStackTrace();		
	      }
   }
}
