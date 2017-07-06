package twitterHTTP;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
public class IpTest2 {  
     
  
    public static void main(String[] args) {  
    
        Pattern p = Pattern.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr){0,1}(\\S)+(\\/)(\\S)+" );   
      
        Matcher m = p.matcher(""+
        		"Mass shootings list. Reagan - 11 Bush Sr - 12 Clinton - 23 Bush Jr - 16 Obama - 162 #SanB.erna/dino"
        		);    
          
          if(m.find()){  
        	  System.out.println(m.groupCount());
              System.out.println(m.group());           
          }  
          
          /*            
          m = p.matcher("http://zh.wikipedia.org:80/wiki/Special:Search?search=tielu&go=Go");  
          
          if(m.find()){  
              System.out.println(m.group());  
          }   */      
            
    }  
  
}  
