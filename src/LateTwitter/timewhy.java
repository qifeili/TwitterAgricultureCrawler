package LateTwitter;

import java.io.BufferedReader;
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
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import jonathon.dao.ConnectJDBC;

public class timewhy {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		Connection conna =ConnectJDBC.getConnection();
		
		try {
			
			PreparedStatement  yct =conna.prepareStatement("select * from twitter  where time like '%?%'  or time =''");
			
			ResultSet ycts = yct.executeQuery();
			
			   float i=0;
			while (ycts.next()) {
				System.out.println("第"+(++i)+"个"+ycts.getString(1)+": "+ycts.getString(2)+"********"+ycts.getString(3)+"********"+ycts.getString(4));
				String comt =ycts.getString(3);   //文章。
				
	       	     if (comt==""||comt.length()<=0) {
				  	continue;//这种是那个账号被封404类似的错误的页面。
				       }
				
			     if(comt.contains("Listening to My Mind (I'm")) {
			    	   if (ycts.getString(2)==null||ycts.getString(2).length()<=0) {   }
			    	   else{     
			    		     String ttt=""; 
							 PreparedStatement  sqled= conna.prepareStatement("insert into twitter  values(?, ?, ?, ?, ?)");                                 
						     sqled.setString(1, ycts.getString(1));
						     sqled.setString(2, ttt); 
						     sqled.setString(3, ycts.getString(3));
						     sqled.setString(4,ycts.getString(4));
						     sqled.setString(5, "1");
						     sqled.executeUpdate();	      
					          sqled.close();
					   }
					
				}
			     else {
			    	 PreparedStatement yichang= conna.prepareStatement("insert into yichang values( ? )");                                 
			    	 yichang.setString(1, ycts.getString(1));    
			    	 yichang.executeUpdate();	      
			    	 yichang.close();
			    	
/*			    	 PreparedStatement shanchu= conna.prepareStatement("delete from twitter where userid =? ");
			    	 shanchu.setString(1, ycts.getString(1));
			    	 shanchu.executeUpdate();
			    	 shanchu.close();  */  	 
				}


			             
			}  //取出该id的while循环。
				

	
		} catch (Exception e) {e.printStackTrace();}
		
	}

}
