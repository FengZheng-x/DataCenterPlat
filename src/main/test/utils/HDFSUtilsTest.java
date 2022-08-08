package utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class HDFSUtilsTest {
    private final HDFSUtils hdfs = new HDFSUtils();

    public HDFSUtilsTest() throws URISyntaxException {
    }


    //    @Test
    //    public void catTest() throws IOException, URISyntaxException {
    //        hdfs.cat("/test/heword.txt");
    //    }

    @Test
    public void cpTest() {
        // TODO hdfs.cp();
    }

    @Test
    public void duTest() {
        // TODO hdfs.du();
    }

    @Test
    public void dusTest() {
        // TODO hdfs.dus();
    }

    @Test
    public void getTest() {
        hdfs.get("/test/heword.txt", "resources/");
    }

    @Test
    public void mkdirTest() {
        hdfs.mkdir("/test/mkdirtest");
    }

    @Test
    public void rmTest() {
        hdfs.rm("/test/mkdirtest");
    }

    @Test
    public void lsTest() {
        hdfs.ls("/");
    }

    @Test
    public void lsrTest() {
        hdfs.lsr("/");
    }
}
