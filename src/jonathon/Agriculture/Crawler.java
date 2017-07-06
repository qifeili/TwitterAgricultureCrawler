package jonathon.Agriculture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.Args;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Statement;

import jonathon.dao.ConnectJDBC;

public class Crawler {

	public static void main(String[] args){
		 try {
			ad();
		     } catch (SQLException e) {e.printStackTrace();} catch (IOException e) {	e.printStackTrace();}
	}
	
	
	public static boolean ad() throws SQLException, IOException{
		
            //连接数据库
		    Connection conn = ConnectJDBC.getConnection();  
		    
   /*****************关于日期的版块*******请注意月份是从0-11*******************/    
              Calendar end = Calendar.getInstance();
                end.set(2016, 10, 19);
              Calendar start = Calendar.getInstance();
                start.set(2014, 0, 1);

              SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
      while(start.compareTo(end) <= 0) {
    	   
               String  dateend  = format.format(end.getTime());                
              
               end.set(Calendar.DATE, end.get(Calendar.DATE) -90);  //循环，每次天数减90天
               
               String datestart = format.format(end.getTime());
			
               System.out.println("起始时间"+datestart+"      终止时间"+dateend);
               
    /****************************查询页数**********************************/
      		PreparedStatement p = conn.prepareStatement("select * from message");           
            ResultSet rs = p.executeQuery();         
            
            //遍历所有农产品
            while(rs.next()){ 
            	         
 
            	 
            String pr=rs.getString(3) ;   //农产品ID
            String aname=rs.getString("name");
            //System.out.println(rs.getString("name")+" : "+rs.getString(3)+"   ");           
            // System.out.print(rs.getString(1)+rs.getString(2)+rs.getString(3));
            //1产品种类 2.产品名字 3.产品id

        	//查询遍历所有省份
        	 PreparedStatement non=conn.prepareStatement("select * from ProvinceAgriculture") ;
        	 ResultSet rs2=non.executeQuery();
        	 
            while(rs2.next()){	
       		
   try{   //捕捉connection异常,并跳过该省份，至下一产品继续执行。
	   
            String aprovince = rs2.getString(1);
            String aprovinceID=rs2.getString(2);           
           	
            URL url=new URL("http://nc.mofcom.gov.cn/channel/gxdj/jghq/jg_list.shtml?par_craft_index=&craft_index="+pr+"&startTime="+datestart+"&endTime="+dateend+"&par_p_index="+aprovinceID+"&p_index=&keyword=&page=1");
       		URLConnection coc=url.openConnection();	
       		    //设置connect time out时间
           		  coc.setConnectTimeout(50* 1000);
           		//设置read timeout时间
           		  coc.setReadTimeout(50* 1000);     
           		  
			
       		
       		
       		
       		BufferedReader bufin=new BufferedReader(new InputStreamReader(coc.getInputStream()));		     		
       		
       		/*正则查询页数*/
       		String line=null;
       		String mailreg="(var v_PageCount =.+)";       		
                         
       		Pattern pa=Pattern.compile(mailreg);
       		
       		while((line=bufin.readLine())!=null){
       			Matcher mr=pa.matcher(line);
       			while(mr.find()){
                     
       			  String[] n1 = mr.group().split(" ");  
       			  String[] n2 = n1[3].split(";");
       			  int  npage =Integer.parseInt(n2[0]);     //这个是当前条件下的总页数!
       			  
       			  System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+aname+"在"+datestart+"-"+dateend+"时间段内    "
       			                                         +aprovince +"  总共页数 : "+npage+"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
       			  
       			  
       			  
       			  
       			/***************** 遍历每一页数据，并最终在这个分区进行抓取******************/

    			  for (int i = 1; i <= npage; i++) {
    					
    		 			int pagecount=i; 
    		 			
    		 		//抓取每个页面里面的价格市场等农产品数据信息
    		 		  Document doc = null;
    		 	        
    			            try {
    							doc = Jsoup.connect("http://nc.mofcom.gov.cn/channel/gxdj/jghq/jg_list.shtml?par_craft_index=&craft_index="+pr+"&startTime="+datestart+"&endTime="+dateend+"&par_p_index="+aprovinceID+"&p_index=&keyword=&page="+pagecount)
    							        .data("query","Java")
    							        .timeout(6000)
    							        .get();
    					
    			           
      System.out.println(">>>正读取的是时间段： "+datestart+"-"+dateend+"  农产品: %  "+aname+"  %在省份  "+aprovince+"  第  @ "+pagecount+" @  页的产品信息<<<");       
    			             
    			            Elements tbody2 =doc.getElementsByTag("tbody").select("td");
    			            String imagepp=tbody2.text();
    			            String[] images=imagepp.split("   ");
    			           
    			            for (int j = 0; j < images.length; j++) {
    						
    							String image=images[j];
    							String[] als=image.split(" ");
    							
    							String name=als[0], price=als[1],place=als[2],date=als[3];
    						//	System.out.println(name+"---"+price+"---"+place+"---"+date);
    			        
    			  //用statement方式向数据库插入产品数据。//因为Connect对象conn不能作为参数传递到其他方法，所以不能分离其他类。
                  //PrepareStatement不需要加单引号，但是这种变量直接写进sql里的是需要加加加的。
    			  	String sql="insert into AgricultureSuccess(农产品名称,日期,省份,市场,价格) values('"+name+"','"+date+"','"+aprovince+"','"+place+"','"+price+"') ";
    			        Statement stmt = (Statement) conn.createStatement();
    			        stmt.executeUpdate(sql);
    			        

    							
    						}
    			         
    			            } catch (IOException e) {
    			   							
    			            	System.out.println("###########时间段： "+datestart+"-"+dateend+"  农产品 "+aname+"  在  "+aprovince +"  第  "+pagecount+"  页出现异常############");
    			   							
    			   						}     	
    			  
         	
         }
       			  
       			  
       		
       			}   //获取page count的while 发现总页数数据 内层while
       		}   ///获取page count的while 读取页面每一行 外层while

	/*在该省份连接url时connection出现异常，然后跳过该省份执行下一省份的操作。*/
       }catch(Exception e){	            
              	e.printStackTrace();
              	System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );
              	System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );             	 
              	System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );  
             System.out.println("###########时间段： "+datestart+"-"+dateend+"  农产品 "+aname+"出现异常############"); 
              	System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );
              	System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );
              	System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );System.out.println( "！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！" );
    
       }
       		
		
               }          // 遍历所有省份
            
        //如果省份遍历一遍则关闭第二个循环的查询语句和结果集
            if(pr.equals("16026950") ){	 
            	  non.close();
                  rs2.close(); 
                 }      
    
            
            }       // 遍历所有农产品
          
            p.close();   //prepare statement对象
            rs.close();  //关闭结果集
            
            
     }//这个是日期循环的括号     
           
            conn.close();  //    关闭数据库连接
              
            return true;
	
	
	}//方法括号
}//类括号
