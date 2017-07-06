package jonathon.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jonathon.dao.ConnectJDBC;

public class LoginCheck {

    public static boolean check(String name,String password)
    {
        //处理业务逻辑
        try
        {
            Connection conn = ConnectJDBC.getConnection();
            PreparedStatement  p = conn.prepareStatement("select * from Administrator where username = ? and password = ?");
            
            p.setString(1, name);
            p.setString(2, password);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	ConnectJDBC.close(rs, p, conn);
                return true;
            }
            ConnectJDBC.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return false;
    }
}