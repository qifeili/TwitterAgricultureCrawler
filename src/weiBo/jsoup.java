package weiBo;

import java.io.IOException;

import javax.print.Doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class jsoup {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		try {
			
			Document doc = Jsoup.connect("http://m.weibo.cn/u/1664275343?uid=1664275343&luicode=10000011&lfid=1076031664275343&featurecode=20000180")
				.header("User-Agent","Mozilla/5.0 (Linux; U; Android 2.3.6; en-us; Nexus S Build/GRK39F) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1")
				.get();
	  
			System.out.println(doc.html() );
		
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
	}

}
