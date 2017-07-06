package twitterUntil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jonathon.dao.ConnectJDBC;

public class testdata {
	
	public static void main(String[] args){
		// TODO 自动生成的方法存根
		   Connection conna = ConnectJDBC.getConnection();  
	try {
		
		  PreparedStatement csql =conna.prepareStatement("select * from twitter where userId = ? ");     	                                    
		     csql.setString(1, "615011286041120769");
             ResultSet cssl=csql.executeQuery();	    
                           
             if (cssl.next()) {		 
		   System.out.println(cssl.getString(3));
             }
            	 
             } catch (Exception e) {
         		// TODO: handle exception
         	}	
	}
}
