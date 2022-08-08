package utils;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class HDFSUtilsTest {
    private final HDFSUtils hdfs = new HDFSUtils();

    public HDFSUtilsTest() throws URISyntaxException, IOException {
    }

    @Test
    public void byteReadableTest() {
        assertEquals("1000", hdfs.byteReadable(1000));
        assertEquals("1k", hdfs.byteReadable(1024));
        assertEquals("9.42m", hdfs.byteReadable(9874321));
        //        assertEquals("9.31g", hdfs.byteReadable(10000000000));
    }

    @Test
    public void catTest() throws IOException, URISyntaxException {
        hdfs.cat("/test/heword.txt");
    }

    @Test
    public void chmodTest() {
        hdfs.chmod("/test", "777");
        hdfs.chmod("/test/test.txt", "751");
        hdfs.chmod("/test/test.txt", "ugo+r");
//        hdfs.chmod("/test/test.txt", "abc");
    }

    @Test
    public void cpTest() throws IOException {
        // TODO hdfs.cp();
    }

    @Test
    public void duTest() throws IOException {
        // TODO hdfs.du();
    }

    @Test
    public void dusTest() throws IOException {
        // TODO hdfs.dus();
    }

    @Test
    public void getTest() throws IOException {
        hdfs.get("/test/heword.txt", "D:/");
    }

    @Test
    public void lsTest(){
        String[] infoList = hdfs.ls("/", false, false);
        for (String info: infoList) {
            System.out.println(info);
        }
    }

    @Test
    public void lsrTest(){
        System.out.println("humanReadable=true, directory=false");
        String[] infoList = hdfs.lsr("/", true, false);
        for (String info: infoList) {
            System.out.println(info);
        }
        System.out.println("---------------");
        System.out.println("humanReadable=false, directory=true");
        String[] infoList2 = hdfs.lsr("/", false, true);
        for (String info: infoList2) {
            System.out.println(info);
        }
    }

    @Test
    public void mkdirTest() throws IOException {
        hdfs.mkdir("/test/test");
    }

    @Test
    public void mvTest() throws IOException {
        // TODO
        // hdfs.mv();
    }

    @Test
    public void putTest() throws IOException {
        hdfs.put("src/main/resources/test.txt", "/test");
    }


    @Test
    public void rmTest() throws IOException {
        hdfs.rm("/test/test");
    }

    @Test
    public void rmrTest() throws IOException {
        hdfs.rmr("/test");
    }

    @Test
    public void isEmptyDirTest() {
        //        FileStatus fst = new FileSystem().getFileStatus();
        //        assert hdfs.isEmptyDir("")==true;
    }
}
