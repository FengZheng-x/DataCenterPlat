package connectTest;

import java.sql.*;

import commons.Constants;

public class MySQL {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(Constants.MYSQL_JDBC_DRIVER);

            conn = DriverManager.getConnection(Constants.MYSQL_JDBC_URL,
                    Constants.USER, Constants.MYSQL_SERVER_PASSWORD);

            stmt = conn.createStatement();
            String sql;
            sql = "show databases";
            ResultSet rs = stmt.executeQuery(sql);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception se) {
            se.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
