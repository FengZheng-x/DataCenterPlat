package com.xzz.common;

public class Constants {
    // 集群信息
    public final static String MASTER_HOST = "112.74.56.191"; // master节点host
    public final static String SLAVE1_HOST = "119.23.224.219"; // slave1节点host
    public final static String SLAVE2_HOST = "112.74.164.126"; // slave2节点host
    public final static String USER = "root";

    // Hive
    public final static String HIVE_SERVER_HOST = SLAVE1_HOST; // Hive服务host
    public final static int HIVE_SERVER_PORT = 10000; // Hive服务端口号
    public final static String HIVE_JDBC_URL = "jdbc:hive2://" + HIVE_SERVER_HOST
            + ":" + HIVE_SERVER_PORT;
    public final static String HIVE_JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";

    // Redis
    public final static String REDIS_SERVER_HOST = SLAVE1_HOST; // Redis服务host
    public final static int REDIS_SERVER_PORT = 6379; // Redis服务端口号
    public final static String REDIS_SERVER_PASSWORD = "123456";  // Redis服务登陆密码

    // MySQL
    public final static String MYSQL_SERVER_HOST = SLAVE2_HOST; // Mysql服务host
    public final static int MYSQL_SERVER_PORT = 3306; // Mysql服务端口号
    public final static String MYSQL_SERVER_PASSWORD = "123456"; // Mysql服务登录密码
    public final static String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public final static String MYSQL_JDBC_URL = "jdbc:mysql://" + MYSQL_SERVER_HOST
            + ":" + MYSQL_SERVER_PORT;

    // Hadoop
    public final static String HADOOP_MASTER_HOST = MASTER_HOST; // Hadoop master host
    public final static int HDFS_PORT = 9000; // Hive服务端口号
    public final static String HDFS_URL = "hdfs://" + HADOOP_MASTER_HOST
            + ":" + HDFS_PORT;
    public final static String HDFS_LOG_FILE = "src/main/logs/hdfs_log";
}
