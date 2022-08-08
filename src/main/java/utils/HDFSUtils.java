package utils;

import commons.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HDFSUtils {
    private final URI hdfsPath;
    private Configuration conf;
    private FileSystem fs;
    // 用于格式化输出文件信息
    private String formatLine = "";

    Logger logger;
    FileHandler fileHandler;


    public HDFSUtils() throws URISyntaxException, IOException {
        this.hdfsPath = new URI(Constants.HDFS_URL);
        this.conf = new Configuration();
        this.fs = FileSystem.get(this.hdfsPath, this.conf);
        setLogger();
    }

    public HDFSUtils(String hdfs, Configuration conf) throws URISyntaxException, IOException {
        this.hdfsPath = new URI(hdfs);
        this.conf = conf;
        this.fs = FileSystem.get(this.hdfsPath, this.conf);
        setLogger();
    }

    private void setLogger() {
        try {
            logger = Logger.getLogger("HDFS API");
            fileHandler = new FileHandler(Constants.HDFS_LOG_FILE, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHandler.setFormatter(new LogFormatter());
    }

    /**
     * 设置 HDFS 属性信息
     *
     * @param name  属性名
     * @param value 属性值
     */
    public void setConf(String name, String value) {
        this.conf.set(name, value);
    }

    /**
     * 获取 HDFS 属性信息
     *
     * @param name 属性名
     * @return 属性值
     */
    public String getConf(String name) {
        return this.conf.get(name);
    }

    private String getCommand(String action, String suffix) {
        String fsCommand = "hadoop fs -";
        return fsCommand + action + " " + suffix;
    }


    public String byteReadable(long bytes) {
        if (bytes == 0)
            return "0";
        double index = Math.floor(Math.log(bytes) / Math.log(1024));
        String[] size = {"", "k", "m", "g", "t", "p", "e", "z", "y"};
        double dec = bytes / Math.pow(1024, index);
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);
        String result = format.format(dec);
        result = result.replaceAll(",", ""); // 去除千分位符
        if (result.indexOf(".") > 0) {
            //  去除小数末尾的0和在末尾的小数点
            result = result.replaceAll("0+?$", "").replaceAll("\\.$", "");
        }
        return result + size[(int) index];
    }


    /**
     * @param stat:         {@link FileStatus} 对象
     * @param humanReadable 以人类可读的方式显示文件大小
     * @return 文件或目录的信息列表
     */
    private String[] getFileInfo(FileStatus stat, boolean humanReadable) {
        String[] list = new String[7];

        list[0] = stat.getPermission().toString();
        list[1] = stat.isFile() ? String.valueOf(stat.getReplication()) : "-";
        list[2] = stat.getOwner();
        list[3] = stat.getGroup();
        String size;
        if (humanReadable) {
            size = byteReadable(stat.getLen());
        } else {
            size = String.valueOf(stat.getLen());
        }
        list[4] = size;
        list[5] = TimeUtils.getDateTime(stat.getModificationTime());
        list[6] = stat.getPath().toUri().getPath();

        return list;
    }

    /**
     * 计算列宽，获取输出格式
     *
     * @param pathInfo 多个路径信息列表的数组
     */
    private void getFormat(ArrayList<String[]> pathInfo) {
        int[] lenList = new int[pathInfo.get(0).length];
        for (String[] list : pathInfo) {
            for (int i = 0; i < lenList.length; i++) {
                lenList[i] = Math.max(lenList[i], list[i].length());
            }
        }
        for (int j = 0; j < lenList.length; j++) {
            if (j == lenList.length - 1) {
                // 最后一个字符串不需要计算宽度
                this.formatLine = String.format("%s%%s", this.formatLine);
            } else {
                this.formatLine = String.format("%s%%-%ds\t", this.formatLine, lenList[j]);
            }
        }
    }

    private String[] processPath(ArrayList<String[]> infoList) {
        String[] lines = new String[infoList.size()];
        for (int i = 0; i < infoList.size(); i++) {
            lines[i] = String.format(this.formatLine, infoList.get(i));
        }
        return lines;
    }

    /**
     * 将本地文件附加到目标文件中
     */
    public void appendToFile(String src, String dest) {
        // TODO
    }

    /**
     * 输出文件内容
     *
     * @param file HDFS文件
     */
    public void cat(String file) throws IOException {
        // 与hdfs建立联系
        //        Path path = new Path(file);
        //        System.out.println(getCommand("cat", file));
        //        FSDataInputStream in = fs.open(path);
        //
        //        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        //        String line;
        //
        //
        //        while (br.readLine() != null) {
        //            line = br.readLine();
        //            System.out.println(line);
        //        }
        //        fs.close();
        //        br.close();
    }

    /**
     * 更改文件或目录的权限
     * 用户必须是文件的所有者或者超级用户
     *
     * @param fileOrDir  需要修改权限的路径
     * @param permission 权限，符号模式或八进制数字模式
     */
    public void chmod(String fileOrDir, String permission) {
        try {
            Path path = new Path(fileOrDir);
            fs.setPermission(path, new FsPermission(permission));
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 将文件或目录复制到目的文件或目的目录
     *
     * @param fileOrDir
     * @param dest
     */
    public void cp(String fileOrDir, String dest, boolean override) {
        // TODO
    }

    /**
     * 显示可用空间
     *
     * @param fileOrDir
     * @param humanReadable 以人类可读的方式格式化文件大小(67108864->64.0m)
     */
    public void df(String fileOrDir, boolean humanReadable) {
        // TODO
    }

    /**
     * 若为文件则显示文件的长度
     * 若为目录则显示其中中包含的文件和目录的大小
     * 总文件大小 目录下所有文件在集群上的总存储大小 路径
     *
     * @param fileOrDir
     */
    public void du(String fileOrDir, boolean humanReadable) {
        // TODO
    }

    /**
     * 显示所有文件的长度汇总
     *
     * @param file
     */
    public void dus(String file) {
        // TODO
    }

    /**
     * 获取文件或目录至本地文件系统
     *
     * @param delScr 是否删除
     * @param src
     * @param dest
     */
    public void get(boolean delScr, String src, String dest, boolean override) throws IOException {
        logger.addHandler(fileHandler);
        logger.info(getCommand("get", src + " " + dest));
        fs.copyToLocalFile(delScr, new Path(src), new Path(dest));
        logger.info(src + " to " + dest);
        fs.close();
    }

    /**
     * 获取文件或目录至本地文件系统
     *
     * @param src
     * @param dest
     */
    public void get(String src, String dest) throws IOException {
        get(false, src, dest, false);
    }

    /**
     * 显示目录下的文件或目录
     * 显示信息：
     * 权限 副本数 用户ID 组ID 修改日期 修改时间 文件名
     *
     * @param dir           查询的目录
     * @param humanReadable 以人类可读方式显示文件大小
     * @param directory     是否只显示目录
     * @return 文件或目录的信息列表
     */
    public String[] ls(String dir, boolean humanReadable, boolean directory) {
        this.formatLine = "";
        String command = "ls -R";
        if (humanReadable)
            command = command + " -h";
        if (directory)
            command = command + " -d";
        logger.addHandler(fileHandler);
        logger.info(getCommand(command, dir));
        try {
            Path path = new Path(dir);
            FileStatus[] fileList = fs.listStatus(path);
            System.out.println("Found " + fileList.length + " items");
            ArrayList<String[]> infoList = new ArrayList<>(fileList.length);
            for (FileStatus stat : fileList) {
                infoList.add(getFileInfo(stat, humanReadable));
            }
            getFormat(infoList);
            return processPath(infoList);
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        return new String[]{};
    }

    /**
     * 显示目录下的文件或目录
     * {@code humanReadable}=false
     * {@code directory}=false
     * <p>
     * 显示信息：
     * 权限 副本数 用户ID 组ID 修改日期 修改时间 文件名
     *
     * @param dir 查询的目录
     * @return 文件或目录的信息列表
     */
    public String[] ls(String dir) {
        return ls(dir, false, false);
    }

    /**
     * 递归调用寻找文件或目录
     *
     * @param filePath  文件或目录路径
     * @param directory 是否只显示目录
     * @return {@code directory}=true 时显示路径下所有的目录；
     * {@code directory}=false 时显示路径下所有的文件和目录；
     */
    private ArrayList<FileStatus> getAllPath(Path filePath, boolean directory) {
        ArrayList<FileStatus> fileList = new ArrayList<>();
        try {
            FileStatus[] fileStatus = fs.listStatus(filePath);
            for (FileStatus fileStat : fileStatus) {
                if (fileStat.isDirectory()) {
                    fileList.add(fileStat);
                    fileList.addAll(getAllPath(fileStat.getPath(), directory));
                } else if (!directory) {
                    fileList.add(fileStat);
                }
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 递归显示目录下的所有文件
     * 显示信息：
     * 权限 副本数 用户ID 组ID 修改日期 修改时间 文件名
     *
     * @param dir           查询的目录
     * @param humanReadable 以人类可读方式显示文件大小
     * @param directory     是否只显示目录
     * @return 文件的信息列表
     */
    public String[] lsr(String dir, boolean humanReadable, boolean directory) {
        this.formatLine = "";
        String command = "ls -R";
        if (humanReadable)
            command = command + " -h";
        if (directory)
            command = command + " -d";
        logger.addHandler(fileHandler);
        logger.info(getCommand(command, dir));
        try {
            Path path = new Path(dir);
            ArrayList<FileStatus> fileList = getAllPath(path, directory);
            ArrayList<String[]> infoList = new ArrayList<>();
            for (FileStatus stat : fileList) {
                infoList.add(getFileInfo(stat, humanReadable));
            }
            getFormat(infoList);
            return processPath(infoList);
        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        return new String[]{};
    }

    /**
     * 递归显示目录下的文件或目录
     * {@code humanReadable}=false
     * {@code directory}=false
     * <p>
     * 显示信息：
     * 权限 副本数 用户ID 组ID 修改日期 修改时间 文件名
     *
     * @param dir 查询的目录
     * @return 文件或目录的信息列表
     */
    public String[] lsr(String dir) {
        return lsr(dir, false, false);
    }

    /**
     * 创建目录
     *
     * @param dir 目录路径
     */
    public void mkdir(String dir) throws IOException {
        Path path = new Path(dir);
        logger.addHandler(fileHandler);
        logger.info(getCommand("mkdir", dir));
        if (fs.exists(path))
            logger.warning("Path " + dir + " already exits");
        fs.mkdirs(path);
    }

    public void mv(String src, String dest) {
        //        try {
        //            Path srcPath = new Path(src);
        //        }catch (IOException e){
        //        logger.severe(e.getMessage());
        //            e.printStackTrace();
        //        }
    }

    public void put(boolean delSrc, String src, String dest) throws IOException {
        System.out.println(getCommand("put", src + " " + dest));
        fs.copyFromLocalFile(delSrc, new Path(src), new Path(dest));
        System.out.println(src + " to " + dest);
        fs.close();
    }

    public void put(String src, String dest) throws IOException {
        put(false, src, dest);
    }

    //    private void delete(Path path) throws IOException {
    //        if (fs.deleteOnExit(path)) {
    //            complete();
    //        } else {
    //            throw new FileExistsException("The file " + path.getName() + " does not exist!");
    //        }
    //    }

    private boolean isEmptyDir(FileStatus stat, int len) {
        return stat.isDirectory() && len == 0;
    }


    /**
     * 删除文件或空目录
     *
     * @param fileOrDir 文件或目录
     */
    public void rm(String fileOrDir) {
        try {
            Path path = new Path(fileOrDir);
            System.out.println(getCommand("rm", fileOrDir));
            FileStatus stat = fs.getFileStatus(path);
            if (fs.exists(path)) {
                if (!isEmptyDir(stat, fs.listStatus(path).length)) { // 判断是否为空目录
                    throw new PathIsNotEmptyDirectoryException(fileOrDir);
                }
                fs.delete(path, false);
            } else {
                throw new FileNotFoundException("Path " + fileOrDir + " does not exist");
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 递归删除文件或目录
     *
     * @param fileOrDir 文件或目录
     */
    public void rmr(String fileOrDir) {
        try {
            Path path = new Path(fileOrDir);
            System.out.println(getCommand("rmr", fileOrDir));
            if (fs.exists(path)) {
                fs.delete(path, true); // 递归删除目录
            } else {
                throw new FileNotFoundException("Path " + fileOrDir + " does not exist");
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 路径测试
     * <code>mode</code>的值可以为 d e f s z
     *
     * @param fileOrDir 文件或目录路径
     * @param mode      测试模式
     * @return {@code mode}=d 时若路径为目录则返回 {@code true}，否则返回 {@code false}
     * {@code mode}=e 时若路径存在则返回 {@code true}，否则返回 {@code false}
     * {@code mode}=f 时若路径为文件则返回 {@code true}，否则返回 {@code false}
     * {@code mode}=s 时若路径为空目录则返回 {@code true}，否则返回 {@code false}
     * {@code mode}=z 时若文件大小为0则返回 {@code true}，否则返回 {@code false}
     * 发生异常时返回 {@code false}
     */
    public boolean test(String fileOrDir, String mode) {
        try {
            Path path = new Path(fileOrDir);
            FileStatus stat = fs.getFileStatus(path);
            switch (mode) {
                case "d":
                    return stat.isDirectory();
                case "e":
                    return fs.exists(path);
                case "f":
                    return stat.isFile();
                case "s":
                    return isEmptyDir(stat, fs.listStatus(path).length);
                case "z":
                    return stat.getLen() == 0;
                default:
                    throw new IllegalArgumentException("Arg `mode` can only be set to d, e, f, s or z");
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 测试路径是否为目录
     *
     * @param dir 文件或目录路径
     * @return 若路径为目录则返回 {@code true}，否则返回 {@code false}
     */
    public boolean isDir(String dir) {
        return test(dir, "d");
    }

    /**
     * 测试文件或目录是否存在
     *
     * @param fileOrDir 文件或目录路径
     * @return 若路径存在则返回 {@code true}，否则返回 {@code false}
     */
    public boolean exists(String fileOrDir) {
        return test(fileOrDir, "e");
    }

    /**
     * 测试路径是否为文件
     *
     * @param file 文件或目录路径
     * @return 若路径为文件则返回 {@code true}，否则返回 {@code false}
     */
    public boolean isFile(String file) {
        return test(file, "d");
    }

    /**
     * 测试路径是否为空目录
     *
     * @param dir 文件或目录路径
     * @return 若路径为空目录则返回 {@code true}，否则返回 {@code false}
     */
    public boolean isEmptyDir(String dir) {
        return test(dir, "s");
    }

    /**
     * 测试文件大小是否为0
     *
     * @param file 文件或目录路径
     * @return 若文件大小为0则返回 {@code true}，否则返回 {@code false}
     */
    public boolean zeroByte(String file) {
        return test(file, "z");
    }


    public void touchz(String file) {

    }
}
