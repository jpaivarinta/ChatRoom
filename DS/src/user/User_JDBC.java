package user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
public class User_JDBC {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URLSTR = "jdbc:mysql://localhost:3306/chatroom" + "?serverTimezone=GMT%2B8";
    private static final String USERNAME = "root";
    private static final String USERPASSWORD = "123456";
    static Connection con;
    static ResultSet rs;
    public User_JDBC() {
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
    //查看用户
    /*@Override
    public List<User> list() {
        List<User> ls = null;
        try(Connection conn = getConnection();
            Statement s = conn.createStatement()){
            String sql = "select * from user";
            ResultSet rs = s.executeQuery(sql);
            ls = new ArrayList<>();
            while(rs.next()){
                User user = new User(rs.getString(1), rs.getString(2));
                ls.add(user);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ls;
    }*/
    //添加用户
    public void add(String name,String sex,String age,String occupation) {
        String sql = "insert into user values(?,?,?,?)";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, name);
            ps.setString(2, sex);
            ps.setString(3, age);
            ps.setString(4, occupation);
            //执行
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}