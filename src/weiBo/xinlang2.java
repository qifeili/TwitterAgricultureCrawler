package weiBo;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class xinlang2{
	
	public static void main(String[] args) {
		 getMCookies("716716fei@sina.com", "716716feiA");
	}
	
	public static Cookie[] getMCookies(String username, String password){
        Cookie[] cookies = null;
        HttpClient client;
        PostMethod post = null;
        try{
            Document doc = Jsoup.connect("https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2Fcompose%2Frepost%3Fid%3D4093465341972005").get();
            Elements elements = doc.getElementsByTag("postfield");
        
            String randUrl = doc.getElementsByTag("go").first().attr("href");        
            String pwName = elements.get(1).attr("name");
            String vkValue = elements.get(2).attr("value");
            String backURL = elements.get(4).attr("value");
            String backTitle = elements.get(5).attr("value");
            
            String url = "http://3g.sina.com.cn/prog/wapsite/sso/"+randUrl;
    
            post = new PostMethod(url);
            
            post.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.77 Safari/537.1");
            post.setRequestHeader("Referer", "http://weibo.com/");
            post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            
            post.setParameter("mobile", username);
            post.setParameter(pwName,password);
            post.setParameter("vk", vkValue);
            post.setParameter("remember", "on");
            post.setParameter("backURL", backURL);
            post.setParameter("backTitle", backTitle);
            post.setParameter("submit", "1");            
            
            client = new HttpClient();            
            client.executeMethod(post);
            cookies = client.getState().getCookies();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            post.abort();
        }
            
        return cookies; 
    }
}