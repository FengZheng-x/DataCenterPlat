package utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class HDFSUtilsTest {
    private final HDFSUtils hdfs = new HDFSUtils();

    public HDFSUtilsTest() throws URISyntaxException, IOException {
    }

    @Test
    public void byteReadableTest() {
        assertEquals("1000", hdfs.byteReadable(1000));
        assertEquals("1k", hdfs.byteReadable(1024));
        assertEquals("9.4m", hdfs.byteReadable(9874321));
        //        assertEquals("9.31g", hdfs.byteReadable(10000000000));
    }

    @Test
    public void chmodTest() {
        hdfs.chmod("/test", "777");
        hdfs.chmod("/test/test.txt", "751");
        hdfs.chmod("/test/test.txt", "ugo+r");
        hdfs.chmod("/test/test.txt", "abc");
    }

    @Test
    public void cpTest() {
        hdfs.cp("/test/dir/warehousedir", "/");
    }

    @Test
    public void dfTest() {
        HashMap<String, Long> map = hdfs.df();
        System.out.println("size=" + map.get("size"));
        System.out.println("used" + map.get("used"));
        System.out.println("available" + map.get("available"));
    }


    @Test
    public void getTest() {
        hdfs.get("/test/heword.txt", "src/main/resources");
        hdfs.get("/test/book.txt", "src/main/resources");
    }

    @Test
    public void lsTest() {
        String[] infoList = hdfs.ls("/", false, false);
        for (String info : infoList) {
            System.out.println(info);
        }
    }

    @Test
    public void lsrTest() {
        System.out.println("humanReadable=true, directory=false");
        String[] infoList = hdfs.lsr("/", true, false);
        for (String info : infoList) {
            System.out.println(info);
        }
        System.out.println("---------------");
        System.out.println("humanReadable=false, directory=true");
        String[] infoList2 = hdfs.lsr("/", false, true);
        for (String info : infoList2) {
            System.out.println(info);
        }
    }

    @Test
    public void mkdirTest() {
        hdfs.mkdir("/test/dir");
    }

    @Test
    public void mvTest() {
        hdfs.mv("/test/book.txt", "/test/dir/book.txt");
    }

    @Test
    public void putTest() {
        hdfs.put("src/main/resources/test.txt", "/test");
    }


    @Test
    public void rmTest() {
        hdfs.rm("/test/test");
    }

    @Test
    public void rmrTest() {
        hdfs.rmr("/test");
    }

    @Test
    public void isEmptyDirTest() {
        assertFalse(hdfs.isEmptyDir("/test"));
    }

    @Test
    public void touchzTest() {
        hdfs.touchz("/test/touzhTest.txt");
        hdfs.touchz("/test/book.txt");
    }

    @Test
    public void usageTest() {
        System.out.println(hdfs.usage());
    }
}
