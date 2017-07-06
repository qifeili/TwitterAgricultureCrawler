package jdbc.MySQL.control;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class JdbcConnection {
	public static int infoInsertNum = 0;
	public static int infoUpdateNum = 0;
	public Connection getConnection(){
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/sinadata?"
                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 将用户uid与数据Map=>json 存入userInfo表中
	 * @author whp
	 * @param uid 用户uid 对应数据库userInfo表中uid字段
	 * @param json 用户数据，转为json数据 对应数据库userInfo表中userInfoJson字段
	 */
	public void userInfo(String uid, String json){
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			String sqlSelect = "select * from userInfo where uid = '"+ uid +"'";
			ResultSet result = stmt.executeQuery(sqlSelect);
			if(result.next()){
				String sqlUpdate = "UPDATE userInfo SET userInfoJson = '"+ json +"' WHERE uid = '"+ uid +"'";
				stmt.executeUpdate(sqlUpdate);
				System.out.println("update"+ infoUpdateNum++);
			}
			else{
				String sqlInsert = "INSERT INTO userInfo (uid, userInfoJson) VALUES ('"+ uid +"','"+ json +"')";
				stmt.executeUpdate(sqlInsert);
				System.out.println("insert"+ infoInsertNum++);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("error emoj字符问题====================================\n"+"uid:"+uid+"   "+json);
		}
	}
	/**
	 * 将用户uid与粉丝列表List=>json 存入fansList表中
	 * @author whp
	 * @param uid 用户uid
	 * @param fansList 该用户粉丝列表
	 */
	public void userFansList(String uid, List<String> fansList){
		String fansString = fansList.toString();
		HashSet<String> set = new HashSet<String>();
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sqlSelect = "select * from fansList where uid = '"+ uid +"'";
			ResultSet result = stmt.executeQuery(sqlSelect);
			if(result.next()){
				String preList = result.getString("fansListJson");
				JSONArray arry = JSONArray.fromObject(preList);
				for(int i=0; i<arry.size(); i++){
					set.add(arry.getString(i));
				}
				for(int i=0; i<fansList.size(); i++){
					set.add(fansList.get(i));
				}
				String sqlUpdate = "UPDATE fansList SET fansListJson = '"+ set.toString() +"' WHERE uid = '"+ uid +"'";
				stmt.executeUpdate(sqlUpdate);
			}
			else{
				String sqlInsert = "INSERT INTO fansList (uid, fansListJson) VALUES ('"+ uid +"','"+ fansString +"')";
				stmt.executeUpdate(sqlInsert);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL执行出错 表fansList中uid:"+ uid + "  " + set.toString());
		}
	}
}
