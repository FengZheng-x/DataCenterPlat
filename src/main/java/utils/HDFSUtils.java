package utils;

import commons.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSUtils {
    private final URI hdfsPath;
    private final Configuration conf;

    public HDFSUtils() throws URISyntaxException {
        this.hdfsPath = new URI(Constants.HDFS_URL);
        this.conf = new Configuration();
    }

    public HDFSUtils(String hdfs, Configuration conf) throws URISyntaxException {
        this.hdfsPath = new URI(hdfs);
        this.conf = conf;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {

    }

    private String getCommand(String action, String suffix) {
        String fsCommand = "hadoop fs -";
        return fsCommand + action + " " + suffix;
    }

    private String complete() {
        return "Complete!";
    }

    private String failure(String msg) {
        return "Failure, " + msg + "!";
    }

    /**
     * 如果是文件，则按照如下格式返回文件信息：
     * 文件名 <副本数> 文件大小 修改日期 修改时间 权限 用户ID 组ID
     * 如果是目录，则返回它直接子文件的一个列表，就像在Unix中一样。目录返回列表的信息如下：
     * 目录名 <dir> 修改日期 修改时间 权限 用户ID 组ID
     *
     * @param fst
     * @return
     */
    private String showFileInfo(FileStatus fst) {
        if (fst.isFile())
            return fst.getPath() + "\t" +
                   fst.getReplication() + "-reps\t" +
                   fst.getLen() + "\t" +
                   TimeUtils.getDateTime(fst.getModificationTime()) + "\t" +
                   fst.getPermission() + "\t" +
                   fst.getOwner() + "\t" +
                   fst.getGroup();
        else
            return fst.getPath() + "\t" + "-" + "\t" +
                   TimeUtils.getDateTime(fst.getModificationTime()) + "\t" +
                   fst.getPermission() + "\t" +
                   fst.getOwner() + "\t" +
                   fst.getGroup();
    }

    public void cat(String file) throws IOException, URISyntaxException {
        // 与hdfs建立联系
        try (FileSystem fs = FileSystem.get(this.hdfsPath, this.conf)) {
            Path path = new Path(file);
            System.out.println(getCommand("cat", file));
            FSDataInputStream in = fs.open(path);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;


            while (br.readLine() != null) {
                line = br.readLine();
                System.out.println(line);
            }
            br.close();

        }

    }

    public void cp(String file, String dest) {
        // TODO
    }

    /**
     * 总文件大小，目录下所有文件在集群上的总存储大小，路径
     *
     * @param fileOrFolder
     */
    public void du(String fileOrFolder) {
        // TODO
    }

    public void dus(String file) {
        // TODO
    }

    public void get(String src, String dest) {
        try {
            FileSystem fs = FileSystem.get(this.hdfsPath, this.conf);
            src = "hdfs:" + src;
            fs.copyToLocalFile(new Path(src), new Path(dest));
            System.out.println(complete());
            System.out.println(src + " to " + dest);
        } catch (Exception e) {
//            e.getStackTrace();
        }
    }

    public void mkdir(String folder) {
        try {
            FileSystem fs = FileSystem.get(this.hdfsPath, this.conf);
            Path path = new Path(folder);
            System.out.println(getCommand("mkdir", folder));
            if (!fs.exists(path)) {
                fs.mkdirs(path);
                System.out.println(complete());
            } else {
                System.out.println(failure("the folder \"" + folder + "\" exists!"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void rm(String fileOrFolder) {
        try {
            FileSystem fs = FileSystem.get(this.hdfsPath, this.conf);
            Path path = new Path(fileOrFolder);
            System.out.println(getCommand("rm", fileOrFolder));
            boolean deleted = fs.deleteOnExit(path);
            if (deleted)
                System.out.println(complete());
            else
                System.out.println(failure("the file or folder \"" + fileOrFolder + "\" not exists"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void ls(String folder) {
        try {
            FileSystem fs = FileSystem.get(this.hdfsPath, this.conf);
            Path path = new Path(folder);
            System.out.println(getCommand("ls", folder));
            FileStatus[] fileList = fs.listStatus(path);
            for (FileStatus f : fileList) {
                System.out.println(showFileInfo(f));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void lsr(String folder) {
        try {
            FileSystem fs = FileSystem.get(this.hdfsPath, this.conf);
            Path path = new Path(folder);
            System.out.println(getCommand("ls -R", folder));
            FileStatus[] fileList = fs.listStatus(path);
            for (FileStatus f : fileList) {
                System.out.println(showFileInfo(f));

                FileStatus[] fileListR = fs.listStatus(f.getPath());
                for (FileStatus fr : fileListR) {
                    System.out.println(showFileInfo(fr));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    //上传文件
    public void put(String local, String remote) throws IOException, URISyntaxException {
        // 建立联系
        FileSystem fs = FileSystem.get(this.hdfsPath, new Configuration());
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        System.out.println("Put :" + local + "   To : " + remote);
        fs.close();
    }


    //删除文件夹
    public void rmr(String folder) throws IOException, URISyntaxException {
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(this.hdfsPath, new Configuration());
        Path path = new Path(folder);
        fs.delete(path);
        System.out.println("delete:" + folder);
        fs.close();
    }


    //判断某个文件夹是否存在
    private boolean isExist(String folder) throws IOException, URISyntaxException {
        //与hdfs建立联系
        FileSystem fs = FileSystem.get(this.hdfsPath, new Configuration());
        Path path = new Path(folder);
        if (fs.exists(path)) {
            System.out.println("it is have exist:" + folder);
        } else {
            System.out.println("it is not exist:" + folder);
        }
        fs.close();
        return false;
    }
}
