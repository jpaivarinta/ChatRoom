package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
public class Server_JDBC {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URLSTR = "jdbc:mysql://localhost:3306/chatroom_server" + "?serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String USERPASSWORD = "123456";
    static Connection con;
    static ResultSet rs;

    public Server_JDBC() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(URLSTR, USERNAME, USERPASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //添加用户
    public void add(String name, String gender) {
        String sql = "insert into user_server values(?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, gender);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void store(String message, Timestamp timestamp) {
        String sql = "insert into message_server values(?,?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, message);
            ps.setTimestamp(2, timestamp);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
