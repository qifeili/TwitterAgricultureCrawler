package LateTwitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import jonathon.dao.ConnectJDBC;

public class yichangtwitter {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		Connection conna =ConnectJDBC.getConnection();
		
		try {
			
			PreparedStatement  yct =conna.prepareStatement("select * from twitter where time=''");
			
			ResultSet ycts = yct.executeQuery();
			
			String ss=null;  String ycid=null; int i=1;
			while (ycts.next()) {
				
				if (ycts.getString(3)==""||ycts.getString(3)==null||ycts.getString(3).contains("Listening to My Mind (I'm")) {
					continue;//这种是那个错误的页面。
				}
				
				 ycid=ycts.getString(1);
					
				 
				 System.out.println("第"+(i++)+"个用户："+ycid);
				 
				 
				 
				 
				
				 
				 
				 
				 
			/*	 //删除该id在twitter的数据。
				 PreparedStatement del =conna.prepareStatement("delete from twitter where userid=?");
			             del.setString(1, ycid);
			             del.executeUpdate();*/
			
			             
			}//取出该id的while循环。
				

	
		} catch (Exception e) {e.printStackTrace();}
		
	}

}
