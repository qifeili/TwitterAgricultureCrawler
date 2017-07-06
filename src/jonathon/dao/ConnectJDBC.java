package jonathon.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectJDBC {

    public static Connection getConnection()
    {
        Connection conn = null;
      //  String url = "jdbc:mysql://localhost/mysqltest?useUnicode=true&characterEncoding=utf8mb4";
        String url = "jdbc:mysql://localhost/mysqltest";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url,"root","qifei");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();

        }
        catch(SQLException e)
        {
            e.printStackTrace();
         
        }
        return conn;
    }

    public static void close(ResultSet rs,PreparedStatement p,Connection conn)
    {
        try
        {
            rs.close();
            p.close();
            conn.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
       
        }
    }
}
