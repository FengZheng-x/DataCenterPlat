package commons.utils;

import commons.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class HDFSUtils {
    private final URI hdfsPath;
    private final Configuration conf;
    private final FileSystem fs;
    private String formatLine = ""; // 用于格式化输出文件信息

    private static Logger logger;
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

    /**
     * 设置日志信息
     */
    private void setLogger() {
        try {
            logger = Logger.getLogger("HDFS API");
            fileHandler = new FileHandler(Constants.HDFS_LOG_FILE, true);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        } finally {
            fileHandler.setFormatter(new LogFormatter()); // 格式化输出日志信息
            logger.addHandler(fileHandler); // 日志输出到文件中
        }
    }

    /**
     * 设置HDFS属性信息
     *
     * @param name  属性名
     * @param value 属性值
     */
    public void setConf(String name, String value) {
        this.conf.set(name, value);
    }

    /**
     * 获取HDFS属性信息
     *
     * @param name 属性名
     * @return 属性值
     */
    public String getConf(String name) {
        return this.conf.get(name);
    }

    /**
     * 获取标准HDFS命令格式
     *
     * @param action 执行的动作及相应的参数
     * @param suffix 该动作的操作对象
     * @return Hadoop fs命令
     */
    private String getCommand(String action, String suffix) {
        String fsCommand = "hadoop fs -";
        return "\"" + fsCommand + action + " " + suffix + "\"";
    }


    /**
     * 将字节转换为人类可读的方式
     * 如 1024->1k
     *
     * @param bytes 字节大小
     * @return 人类可读的大小
     */
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

    /**
     * 将文件或目录信息格式化
     *
     * @param infoList 文件信息列表
     * @return 格式化后的文件信息列表
     */
    private String[] processPath(ArrayList<String[]> infoList) {
        String[] lines = new String[infoList.size()];
        for (int i = 0; i < infoList.size(); i++) {
            lines[i] = String.format(this.formatLine, infoList.get(i));
        }
        return lines;
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
            logger.info(getCommand("chmod", permission));
            fs.setPermission(path, new FsPermission(permission));
        } catch (IllegalArgumentException e) {
            logger.warning("Illeagal permission " + e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 将文件或目录复制到目的文件或目的文件或目录
     *
     * @param fileOrDir 原文件或目录
     * @param dest      目的文件目录
     */
    public void cp(String fileOrDir, String dest) {
        try {
            Path src = new Path(fileOrDir);
            Path destPath = new Path(dest);
            logger.info(getCommand("cp", fileOrDir + " " + dest));
            fs.rename(src, destPath);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 显示可用空间信息
     * 总容量 size
     * 已使用 used
     * 可用   available
     *
     * @return HDFS可用空间的 {@link HashMap<String, Long>}
     */
    public HashMap<String, Long> df() {
        try {
            Path path = new Path("/");
            logger.info(getCommand("df", "/"));
            long size = fs.getStatus(path).getCapacity();
            long used = fs.getStatus(path).getUsed();
            long available = fs.getStatus(path).getRemaining();

            HashMap<String, Long> map = new HashMap<>(3);
            map.put("size", size);
            map.put("used", used);
            map.put("available", available);
            return map;
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
        return new HashMap<>();
    }

    /**
     * 获取文件或目录至本地文件系统
     *
     * @param delSrc 是否删除源文件或目录
     * @param src    HDFS文件或目录路径
     * @param dest   本地路径
     */
    public void get(boolean delSrc, String src, String dest) {
        try {
            logger.info(getCommand("get", src + " " + dest));
            fs.copyToLocalFile(delSrc, new Path(src), new Path(dest));
            logger.info(src + " to " + dest);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 获取文件或目录至本地文件系统
     * 保留原文件或目录
     *
     * @param src  HDFS文件或目录路径
     * @param dest 本地路径
     */
    public void get(String src, String dest) {
        get(false, src, dest);
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
        logger.info(getCommand(command, dir));
        try {
            Path path = new Path(dir);
            FileStatus[] fileList = fs.listStatus(path);
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
     * 递归显示目录下的所有文件或目录
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
    public void mkdir(String dir) {
        try {
            Path path = new Path(dir);
            logger.info(getCommand("mkdir", dir));
            if (fs.exists(path))
                logger.warning("Path " + dir + " already exits");
            fs.mkdirs(path);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 移动文件或目录
     *
     * @param src 原文件或目录
     * @param dest 目的文件或目录
     * @param delSrc 是否删除原文件或目录
     */
    public void mv(String src, String dest, boolean delSrc) {
        try {
            Path srcPath = new Path(src);
            Path destPath = new Path(dest);
            FileUtil.copy(fs, srcPath, fs, destPath, delSrc, this.conf);
        } catch (IOException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void mv(String src, String dest) {
        mv(src, dest, true);
    }

    /**
     * 获取文件或目录至本地文件系统
     *
     * @param delSrc 是否删除原文件或目录
     * @param src    HDFS文件或目录路径
     * @param dest   本地路径
     */
    public void put(boolean delSrc, String src, String dest) {
        try {
            fs.copyFromLocalFile(delSrc, new Path(src), new Path(dest));
            logger.info(getCommand("put", src + " " + dest));
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 获取文件或目录至本地文件系统
     *
     * @param src  HDFS文件或目录路径
     * @param dest 本地路径
     */
    public void put(String src, String dest) {
        put(false, src, dest);
    }

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
            logger.info(getCommand("rm", fileOrDir));
            FileStatus stat = fs.getFileStatus(path);
            if (fs.exists(path)) {
                if (!isEmptyDir(stat, fs.listStatus(path).length)) { // 判断是否为空目录
                    throw new PathIsNotEmptyDirectoryException(fileOrDir);
                }
                fs.delete(path, false);
            } else {
                logger.warning("Path " + fileOrDir + " does not exist");
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
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
            logger.info(getCommand("rmr", fileOrDir));
            if (fs.exists(path)) {
                fs.delete(path, true); // 递归删除目录
            } else {
                logger.warning("Path " + fileOrDir + " does not exist");
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * 路径测试
     * <code>mode</code>的值可以为 d e f s z
     *
     * @param fileOrDir 文件或目录路径
     * @param mode      测试模式
     * @return {@code mode}=d 时若路径为目录则返回 {@code true}，否则返回 {@code false}
     * {@code mode=e} 时若路径存在则返回 {@code true}，否则返回 {@code false}
     * {@code mode=f} 时若路径为文件则返回 {@code true}，否则返回 {@code false}
     * {@code mode=s} 时若路径为空目录则返回 {@code true}，否则返回 {@code false}
     * {@code mode=z} 时若文件大小为0则返回 {@code true}，否则返回 {@code false}
     * 发生异常时返回 {@code false}
     */
    public boolean test(String fileOrDir, String mode) {
        try {
            Path path = new Path(fileOrDir);
            FileStatus stat = fs.getFileStatus(path);
            switch (mode) {
                case "d":
                    logger.info(getCommand("test -d", ""));
                    return stat.isDirectory();
                case "e":
                    logger.info(getCommand("test -e", ""));
                    return fs.exists(path);
                case "f":
                    logger.info(getCommand("test -f", ""));
                    return stat.isFile();
                case "s":
                    logger.info(getCommand("test -s", ""));
                    return isEmptyDir(stat, fs.listStatus(path).length);
                case "z":
                    logger.info(getCommand("test -z", ""));
                    return stat.getLen() == 0;
                default:
                    logger.warning("Arg `mode` can only be set to d, e, f, s or z");
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
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

    /**
     * 创建一个空文件
     *
     * @param file 文件路径
     */
    public void touchz(String file) {
        try {
            Path filePath = new Path(file);
            logger.info(getCommand("touchz", file));
            if (fs.exists(filePath)) {
                logger.warning("File " + file + " already exits");
            } else {
                fs.create(filePath);
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    /**
     * @return HDFS命令的使用帮助
     */
    public String usage() {
        return "Usage: hadoop fs" + "\n\t\t" +
               "[-cat <src>]" + "\n\t\t" +
               "[-chmod <MODE | OCTALMODE> PATH]" + "\n\t\t" +
               "[-copyFromLocal <localsrc> <dst>]" + "\n\t\t" +
               "[-copyToLocal <src> <localdst>]" + "\n\t\t" +
               "[-cp <src> <dst>]" + "\n\t\t" +
               "[-df]" + "\n\t\t" +
               "[-get <src> <localdst>]" + "\n\t\t" +
               "[-ls [-d] [-h] [-R] [<path>]]" + "\n\t\t" +
               "[-mkdir <path>" + "\n\t\t" +
               "[-mv <src> <dst>]" + "\n\t\t" +
               "[-put <localsrc> <dst>]" + "\n\t\t" +
               "[-rm [-r|-R] <src>]" + "\n\t\t" +
               "[-test -[defsz] <path>]" + "\n\t\t" +
               "[-touchz <path>]" + "\n\t\t" +
               "[-usage]";
    }
}
