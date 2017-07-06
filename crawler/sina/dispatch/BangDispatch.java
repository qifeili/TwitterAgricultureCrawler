package crawler.sina.dispatch;

import java.io.IOException;
import java.util.List;

import jdbc.MySQL.control.JdbcConnection;

import org.apache.http.client.ClientProtocolException;

import crawler.sina.craw.CrawSina;

public class BangDispatch {
	/**
	 * 榜单信息爬取过程 主要用于控制整个流程调度
	 * @author whp
	 * @param bangType 榜单类型 eg. renwu shishang
	 * @param dataType 时间类型 eg. day week month
	 */
	public static void bang(String bangType, String dataType){
		CrawSina crawSina = new CrawSina();
		JdbcConnection jdbcConnection = new JdbcConnection();
		try {
			List<String> bangUidList = crawSina.getBangList(bangType, dataType);
			for(int loopInBangList=0; loopInBangList<bangUidList.size(); loopInBangList++){
				for(int pageNum=1; pageNum<=5; pageNum++){
					List<String> fansUidList = crawSina.getFansListByUid(bangUidList.get(loopInBangList), String.valueOf(pageNum));
					jdbcConnection.userFansList(bangUidList.get(loopInBangList), fansUidList);
					if(fansUidList.size() != 0){
						for(int loopInFansList=0; loopInFansList<fansUidList.size(); loopInFansList++){
							crawSina.getUserInfo(fansUidList.get(loopInFansList));
						}
					}
					else{
						break;
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
