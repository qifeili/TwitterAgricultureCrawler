package twitterUntil;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jonathon.dao.ConnectJDBC;

//采集完数据，把数据库数据转换成txt的一个类。

public class data {

	public static void main(String[] args) throws ParseException  {
		// TODO Auto-generated method stub
    
	 Connection conne = ConnectJDBC.getConnection(); 
	try {
		
		 PreparedStatement sqs	=conne.prepareStatement("select * from twitteryichang");
	                ResultSet resultSet =sqs.executeQuery();
	             
	                while (resultSet.next()) {              
	                	
	           		   String userid =resultSet.getString(1); 
	           		   System.out.println(userid);
	                  File  file =new File("F:/Twitter/"+userid+".txt");
	         		 FileWriter fws = new FileWriter(file);
	        		 BufferedWriter fw =new BufferedWriter(fws);
	                	
	     	//时间板块************************************************************************************************************************
	        		 try {	
	        		 String time =  resultSet.getString(2);
		          //  time="5:47 PM - 18 Jul 2015";
				
						if (time==null||time.equals("")||time.contains("?")) {	}
						
						else{	
					
						SimpleDateFormat format =  new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");  

						String[] times =time.split(" "); 
						
						String shijian=null ,yuefen =times[4];	
					
						if (times[1].equals("PM")){
						String[] jus=	times[0].split(":");
				    	int	xiaoshi2=Integer .parseInt(jus[0]);
				    	int xiaoshi =xiaoshi2+12;
				    	 shijian=Integer.toString(xiaoshi)+":"+jus[1]+":00";
						}
						else {
							shijian =times[0]+":00";
						}
					
						System.out.println("时间："+shijian);
						
						//日期板块
						switch(yuefen){
						case "Jan": yuefen="01";
							break;
						case "Feb": yuefen="02";
							break;
						case "Mar":yuefen="03";
							break;
						case "Apr":yuefen="04";
							break;
						case "May":yuefen="05";
							break;
						case "Jun":yuefen="06";
							break;
						case "Jul":yuefen="07";
							break;
						case "Aug":yuefen="08";
							break;
						case "Sep":yuefen="09";
							break;
						case "Oct":yuefen="10";
							break;
						case "Nov":yuefen="11";
							break;
						case "Dec":yuefen="12";
							break;		
						}
						String riqi = times[5]+"-"+yuefen+"-"+times[3];
						System.out.println("日期："+riqi);
						
						String endtime =" "+ riqi+" "+shijian+" ";
				           // endtime =" 1970-02-06 11:45:55 ";
						System.out.println("最终日期："+endtime);
						
						Date date = format.parse(endtime);  	
//						System.out.print(date);
						long co =date.getTime();
						String chuo =Long.toString(co);
						System.out.println(chuo)	;
						fw.write(chuo);   
						fw.newLine();
						}
	        		 } catch (Exception e) {    fw.newLine();
	        			 e.printStackTrace();	System.out.println("这个时间除了问题，已经跳过。");}			//预防时间问题的try
	        			
	//时间板块************************************************************************************************************************
			String content=null;    		 String comment=null;
	        		 try {				
					 content =  resultSet.getString(3);
						fw.write(content);
					    fw.newLine();			
				} catch (Exception e) {
					      fw.newLine();		
				}		
	    
				try {				
					 comment=  resultSet.getString(4);
						//System.out.println(time);
						System.out.println(content);
						System.out.println(comment);		
						String[] cos =comment.split("<<o>>");	
						for (int i = 1; i < cos.length; i++) {
							System.out.println(cos[i]);
							fw.write(cos[i]);	  
							fw.newLine();	}			
			} catch (Exception e) {
				    fw.newLine();		
			}		
					
			    fw.close(); fws.close();
					
        }			
	System.out.println("恭喜，执行完成");				
              conne.close();      sqs.close(); resultSet.close();

	} catch (SQLException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		
	}

}
