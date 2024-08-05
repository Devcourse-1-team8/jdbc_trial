package org.health.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static final String url = "jdbc:mariadb://svc.sel5.cloudtype.app:30739/workshop";
    public static final String user = "root";
    public static final String pwd = "1234";


    public static Connection getConnection() throws SQLException {
        Connection con  = null;

        try {
            con = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            System.out.println("커넥션 오류");
            e.printStackTrace();
        }

        return con;
    }

    public static void close (AutoCloseable... closeable){
        for (AutoCloseable c : closeable) {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
