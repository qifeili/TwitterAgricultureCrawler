package twitterUntil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jonathon.dao.ConnectJDBC;

//检查异常类。
public class Yeshen {

	public static void main(String[] args) throws SQLException {
		// TODO 自动生成的方法存根

		 Connection conna = ConnectJDBC.getConnection();  
		    
	                     int zongii = 1  ;           //读取文件

		
			            PreparedStatement	 csql=conna.prepareStatement("select * from twitter where time =''");     	              //检测time为空的数据。                      
						  
			                 ResultSet cssl=csql.executeQuery();	    
			                               
			                 while(cssl.next()) {			
			                	                         //判断是否是全部为空或者是listening的特殊情况
			                                   if (cssl.getString(3).equals("")||cssl.getString(3).length()<=0||cssl.getString(3).contains("Listening to My Mind (I'm Los")) { }//满足条件则不予执行任何操作就跳过。
			             
			                                   else {                  //如果不是则检查是否为未处理的异常类。
			                                	   PreparedStatement	 ssql = conna.prepareStatement("insert  into  yichang  values(?)");     	                                    
			      							                ssql.setString(1, cssl.getString(1)); 					 
			      						                     ssql.executeUpdate();	
			      						                     System.out.println("该异常数据id为："+cssl.getString(1)+" 已成功存储到异常表");
								                         }
							}
		
			         		  	    	  
		
   }
}
