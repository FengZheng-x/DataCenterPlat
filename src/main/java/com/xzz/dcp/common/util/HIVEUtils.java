package com.xzz.dcp.common.util;

import com.xzz.dcp.common.Constants;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


@Slf4j
public class HIVEUtils {
    private Connection connection;
    private Statement stmt ;
    private static ResultSet rs = null;
    Logger logger;
    FileHandler fileHandler;

    public HIVEUtils() throws SQLException, ClassNotFoundException {
        Class.forName(Constants.HIVE_JDBC_DRIVER);
        this.connection=DriverManager.getConnection(Constants.HIVE_JDBC_URL,Constants.USER,"");
        this.stmt = connection.createStatement();
    }


    /**
     * 封装获取info信息
     * @param action
     * @param suffix
     * @return
     */
    private String getCommand(String action, String suffix) {
        String hiveCommand = "Hive --";
        return hiveCommand + action + " " + suffix;
    }

    /**
     * 1.用处是：加载驱动、创建连接
     * 2.hive用户名root,没有密码
     * @throws Exception
     */
    public void init() {
        try {
            Class.forName(Constants.HIVE_JDBC_DRIVER);
            connection = DriverManager.getConnection(Constants.HIVE_JDBC_URL,Constants.USER,"");
            stmt = connection.createStatement();
            log.info("初始化成功！");
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    /**
     *  创建操作,传入参数sql语句,包括创建数据库，表等。
     *  需要注意create database后面有个空格，要充分考虑sql语句规则，同时还要考虑数据库的存在以及命名合法
     * @throws Exception
     */
    public void create(String sql) throws Exception {
        try {
            log.info(getCommand("Create操作:",sql));
            System.out.println("Running: " + sql);
            stmt.execute(sql);
        }catch (Exception e){
//            logger.severe("create thing has already exists! or create failed!");
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    /**
     * 查询数据库和表操作  特别注意查询表的时候一定要注意先使用某一数据库，这里要做处理
     * @param sql
     * @return
     */

    public List<String> show(String sql){
        List<String> list = new ArrayList<>();
        try {
            log.info(getCommand("Show操作:",sql));
            System.out.println("Running: "+sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                list.add(rs.getString(1));
            }
        }catch (Exception e){
            log.warn(e.getMessage());
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * drop 操作用于删除数据库数据表
     * @param sql
     */

    public void drop(String sql) {
        try {
            log.info(getCommand("Drop操作:",sql));
            System.out.println("Running: " + sql);
            stmt.execute(sql);
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
    }


    /**
     * desc表操作,可以用desc databasename.tablename
     * @param sql
     * @return
     */
    public List<Map<String, Object>> descTable(String sql) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            log.info(getCommand("Desc表操作:",sql));
            System.out.println("Running: " + sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                map = new HashMap<>();
                map.put("colName", rs.getString(1));
                map.put("dataType", rs.getString(2));
                list.add(map);
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * LOAD DATA 用于上传数据，但由于业务限制，一般不用
     * @param sql
     */
    public void loadData(String sql) {
        try {
            log.info(getCommand("Load Data操作:", sql));
            System.out.println("Running: " + sql);
            stmt.execute(sql);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * insert 数据
     * @param sql
     */
    public void insert(String sql){
        try{
            log.info(getCommand("insert操纵:",sql));
            stmt.execute(sql);
            System.out.println("running"+sql);
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }



    /**
     * 查询操作是经常使用的操作,由于getString中需要传入columnindex参数，所以借助前面的desc操作查询有多少个属性
     *
     * @param sql
     * @return
     */
    public List<String> selectData(String sql) {
        List<String> info_list = new ArrayList<>();
        try {
            String[] split_sql = sql.split(" ");
            String tablename = split_sql[split_sql.length-1];
            List<Map<String, Object>> mapList = descTable("desc " + tablename);
            log.info(getCommand("Select查询操作:",sql));
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
//                Iterator<Map<String, Object>> iterator_map=mapList.iterator();
//                while (iterator_map.hasNext()) {
                for (int i = 0; i < mapList.size(); i++) {
                    info_list.add(rs.getString(i+1));
                }
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return info_list;
    }


    private  void destory() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
            log.info("回收成功！");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    public void execute(String sql){
        try{
            log.info(getCommand("其他操作:",sql));
            stmt.execute(sql);
            System.out.println("running"+sql);
        }catch (SQLException e) {
            log.error(e.getMessage());
        }
    }



//    public void split_execute(String split_sql){
//        if (split_sql.contains("insert"))
//    }
//


    /**
     * 考虑传入的string sql是一大串字符串,并且考虑shell交互的命令，都会有个";" 所以就决定以此为分割切割字符串！
     * @param sql
     * @return
     */
    public LinkedList<String> executeString(String sql){
        String[] sql_string = sql.split(";");
        LinkedList<String> sql_list = new LinkedList<String>();
        Collections.addAll(sql_list, sql_string);
        return sql_list;
    }

    /**
     *  executeQuery和exectue一次只能传一次sql
     * @param sql_list
     */
    public void excuteSql(LinkedList<String> sql_list){
        try {
            for (String sql : sql_list) {
                if (sql.toLowerCase().contains("show".toLowerCase())){
                    List<String> show_rs = show(sql);
                    System.out.println("---show ---");
                    for (String list:show_rs) {
                        System.out.println(list);
                    }
                }else if (sql.toLowerCase().contains("desc".toLowerCase())){
                    List<Map<String, Object>> desc_rs = descTable(sql);
                    System.out.println("---desc ---");
                    for (Map<String, Object> list:desc_rs) {
                        System.out.println(list);}
                }else if (sql.toLowerCase().contains("create".toLowerCase())){
                    create(sql);
                }else if (sql.toLowerCase().contains("select".toLowerCase())){
                    List<String> select_rs = selectData(sql + "limit 200");
                    System.out.println("---select ---");
                    for (String list:select_rs) {
                        System.out.println(list);}
                }else if (sql.toLowerCase().contains("drop".toLowerCase())){
                    drop(sql);
                }else if (sql.toLowerCase().contains("load".toLowerCase())){
                    loadData(sql);
                } else if (sql.toLowerCase().contains("insert".toLowerCase())) {
                    insert(sql);
                }else {
                    execute(sql);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
