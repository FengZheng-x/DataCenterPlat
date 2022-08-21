package utils;

import com.xzz.dcp.common.util.HIVEUtils;
import org.junit.Test;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HIVEUtilsTest {
    private final HIVEUtils hive = new HIVEUtils();
    public HIVEUtilsTest() throws URISyntaxException, SQLException, ClassNotFoundException {
    }

    @Test
    public void testInit() throws Exception {
        hive.init();
    }

    @Test
    public void testCreate() throws Exception {
        hive.create("create database testt");
    }

    @Test
    public void testShow(){
          List<String> list = new ArrayList<>();
          list = hive.show("show databases");
          for (String s : list) {
                System.out.println(s);
            }            
    }

    @Test
    public void test_drop(){
        String sql = "drop database sql_test1 cascade";
        hive.drop(sql);
    }

    @Test
    public void test_desc(){
        String sql = "desc sql_test.emp_test";
        List<Map<String, Object>> list = hive.descTable(sql);
        for (Map<String, Object> s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void test_load(){
//        String sql = "load data local inpath '\" + hdfsPath + \"' insert into table \" + tableName";
        String sql = "";
        hive.loadData(sql);
    }


    @Test
    public void test_insert(){
        String sql = "insert into sql_test.emp_test values (2020,\"zhoufei\",\"student\")";
        hive.insert(sql);
    }

    @Test
    public void test_select(){
        String sql = "select * from sql_test.emp_test";
        List<String> list = hive.selectData(sql);
        for (String s : list) {
            System.out.print(s+"\t");
        }
    }


    
    
    @Test
    public void testSplit(){
        String sql = "select * from zf";
        String[] split = sql.split(" ");
        System.out.println(split[split.length-1]);
    }


    @Test
    public void executeSQLTest(){
        String sql = "create database sql_test1;use sql_test1;create table emp_test1(empno int,ename string,job string)row format delimited fields terminated by '\t';insert into emp_test1 values(2020,\"zhoufei\",\"student\");select * from emp_test1;";
        hive.excuteSql(hive.executeString(sql));
    }
}
