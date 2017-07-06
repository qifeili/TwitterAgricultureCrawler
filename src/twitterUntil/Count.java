package twitterUntil;

import java.io.BufferedReader;
import java.io.FileReader;


public class Count {


	
		public static void main(String[] args) {
			// TODO 自动生成的方法存根

			  
			
			 //   Logger logger =Logger.getLogger(Count.class);    //配置日志
			    FileReader frea;  BufferedReader bufaa;   int zongii = 1  ;           //读取文件
				try{
					frea = new FileReader("E:/fengeTP.txt");
				   bufaa =new BufferedReader(frea);
			
				   float n=0;
				    String ssid = null ;     int  mii =1  ;    String ss=null;
				 while ((ssid=bufaa.readLine())!=null) {
					System.out.println("第 "+(mii++)+" 行全部数据："+ssid);    
					String[] ssa=ssid.split(":");
					String[] ssb=ssa[2].split("	");
					String[] ssc =ssb[1].split(" ");
			              
			                    n+=	ssc.length	;
			                    System.out.println(ssc.length);
			                    System.out.println("当前总个数："+n);
			       //             logger.error(n);
			                    
			                 /*   if (n>=170000) {
									System.err.println(n);
								}*/
				 }

			   }catch (Exception e) {}
				
		}
}
