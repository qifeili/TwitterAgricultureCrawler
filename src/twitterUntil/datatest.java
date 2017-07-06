package twitterUntil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class datatest {

	public static void main(String[] args){
		try {
		
		     SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
		     Date date=simpleDateFormat .parse("2010-06-25");
		     int timeStemp = (int) date.getTime();
		     System.out.println(timeStemp );
	      System.out.print("Format To times:"+date.getTime());  
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}  
		
	}
	
	
}
