package connect;

import commons.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyHive {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        try {
            Connection connection = DriverManager.getConnection(Constants.HIVE_JDBC_URL + "/default");
            PreparedStatement ps = connection.prepareStatement("show tables ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "-------" + rs.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            JDBCUtils.disconnect(connection, rs, ps);
        }
    }
}
