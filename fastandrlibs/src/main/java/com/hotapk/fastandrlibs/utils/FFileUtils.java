package com.hotapk.fastandrlibs.utils;

import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author laijian
 * @version 2017/9/11
 * @Copyright (C)下午12:02 , www.hotapk.cn
 * 文件操作工具类
 */
public class FFileUtils {

    /**
     * 获取根目录
     */
    public static String getRootDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * 可创建多个文件夹
     * dirPath 文件路径
     */
    public static boolean mkDir(String dirPath) {
        String[] dirArray = dirPath.split("/");
        String pathTemp = "";
        boolean mkdir = false;
        for (int i = 0; i < dirArray.length; i++) {
            pathTemp = pathTemp + dirArray[i];
            File newF = new File(dirArray[0] + pathTemp);
            if (!newF.exists()) {
                mkdir = newF.mkdir();
            }
        }
        return mkdir;
    }


    /**
     * 创建文件
     * <p>
     * dirpath 文件目录
     * fileName 文件名称
     */
    public static boolean creatFile(String dirPath, String fileName) {
        File file = new File(dirPath, fileName);
        boolean newFile = false;
        if (!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                newFile = false;
            }
        }
        return newFile;
    }

    /**
     * 创建文件
     * filePath 文件路径
     */
    public static boolean creatFile(String filePath) {
        File file = new File(filePath);
        boolean newFile = false;
        if (!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                newFile = false;
            }
        }
        return newFile;
    }

    /**
     * 创建文件
     * file 文件
     */
    public static boolean creatFile(File file) {
        boolean newFile = false;
        if (!file.exists()) {
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                newFile = false;
            }
        }
        return newFile;
    }

    /**
     * 删除文件
     * dirpath 文件目录
     * fileName 文件名称
     */
    public static boolean delFile(String dirpath, String fileName) {
        File file = new File(dirpath, fileName);
        boolean delete = false;
        if (file == null || !file.exists() || file.isDirectory()) {
            delete = false;
        } else {
            delete = file.delete();
        }
        return delete;
    }

    /**
     * 删除文件
     * filepath 文件路径
     */
    public static boolean delFile(String filepath) {
        File file = new File(filepath);
        boolean delete = false;
        if (file == null || !file.exists() || file.isDirectory()) {
            delete = false;
        } else {
            delete = file.delete();
        }
        return delete;
    }

    /**
     * 删除文件
     * filepath 文件路径
     */
    public static boolean delFile(File filepath) {
        boolean delete = false;
        if (filepath == null || !filepath.exists() || filepath.isDirectory()) {
            delete = false;
        } else {
            delete = filepath.delete();
        }
        return delete;
    }

    /**
     * 删除文件夹
     * dirPath 文件路径
     */
    public static boolean delDir(String dirpath) {
        File dir = new File(dirpath);
        return deleteDirWihtFile(dir);
    }

    public static boolean deleteDirWihtFile(File dir) {
        boolean delete = false;
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            delete = false;
        } else {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else if (files[i].isDirectory()) {
                    deleteDirWihtFile(files[i]);
                }
                delete = dir.delete();// 删除目录本身
            }
        }
        return delete;
    }


    /**
     * 修改SD卡上的文件或目录名
     * oldFilePath 旧文件或文件夹路径
     * newFilePath 新文件或文件夹路径
     */
    public static boolean renameFile(String oldFilePath, String newFilePath) {
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);
        return oldFile.renameTo(newFile);
    }

    /**
     * 拷贝一个文件
     * srcFile源文件
     * destFile目标文件
     */
    public static boolean copyFileTo(File srcFile, File destFile) {
        boolean copyFile = false;
        if (!srcFile.exists() || srcFile.isDirectory() || destFile.isDirectory()) {
            copyFile = false;
        } else {
            FileInputStream is = null;
            FileOutputStream os = null;
            try {
                is = new FileInputStream(srcFile);
                os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                copyFile = true;
            } catch (Exception e) {
                copyFile = false;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return copyFile;
    }

    /**
     * 拷贝目录下的所有文件到指定目录
     * srcDir 原目录
     * destDir 目标目录
     */
    public static boolean copyFilesTo(File srcDir, File destDir) {
        if (!srcDir.exists() || !srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }
        File[] srcFiles = srcDir.listFiles();

        for (int i = 0; i < srcFiles.length; i++) {
            if (srcFiles[i].isFile()) {
                File destFile = new File(destDir.getAbsolutePath(), srcFiles[i].getName());
                copyFileTo(srcFiles[i], destFile);
            } else {
                File theDestDir = new File(destDir.getAbsolutePath(), srcFiles[i].getName());
                copyFilesTo(srcFiles[i], theDestDir);
            }

        }
        return true;
    }

    /**
     * 移动一个文件
     * srcFile源文件
     * destFile目标文件
     */
    public static boolean moveFileTo(File srcFile, File destFile) {
        if (!srcFile.exists() || srcFile.isDirectory() || destFile.isDirectory()) {
            return false;
        }
        boolean iscopy = copyFileTo(srcFile, destFile);
        if (!iscopy) {
            return false;
        } else {
            delFile(srcFile);
            return true;
        }
    }

    /**
     * 移动目录下的所有文件到指定目录
     * srcDir 原路径
     * destDir 目标路径
     */
    public static boolean moveFilesTo(File srcDir, File destDir) {
        if (!srcDir.exists() || !srcDir.isDirectory() || !destDir.isDirectory()) {
            return false;
        }

        File[] srcDirFiles = srcDir.listFiles();
        for (int i = 0; i < srcDirFiles.length; i++) {
            if (srcDirFiles[i].isFile()) {
                File oneDestFile = new File(destDir.getAbsolutePath(), srcDirFiles[i].getName());
                moveFileTo(srcDirFiles[i], oneDestFile);
            } else {
                File oneDestFile = new File(destDir.getAbsolutePath(), srcDirFiles[i].getName());
                moveFilesTo(srcDirFiles[i], oneDestFile);
            }
        }
        return true;
    }

    /**
     * 文件转byte数组
     * file 文件路径
     */

    public static byte[] file2byte(File file) throws IOException {
        byte[] bytes = null;
        if (file != null) {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if (length > Integer.MAX_VALUE) {// 当文件的长度超过了int的最大值
                System.out.println("this file is max ");
                is.close();
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            is.close();
            // 如果得到的字节长度和file实际的长度不一致就可能出错了
            if (offset < bytes.length) {
                System.out.println("file length is error");
                return null;
            }
        }
        return bytes;
    }


    /**
     * 文件读取
     * filePath 文件路径
     */
    public static String readFile(File filePath) {

        BufferedReader bufferedReader = null;
        String fileStr = "";
        if (!filePath.exists() || filePath.isDirectory()) {
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String tempFileStr = "";

            while ((tempFileStr = bufferedReader.readLine()) != null) {
                fileStr += tempFileStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileStr;

    }

    /**
     * 文件读取
     * strPath 文件路径
     */
    public static String readFile(String strPath) {
        return readFile(new File(strPath));
    }

    /**
     * InputStream 转字符串
     */
    public static String readInp(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            int len1;
            while ((len1 = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len1);
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException var5) {
        }

        return outputStream.toString();
    }

    /**
     * InputStream转byte数组
     *
     * @param inputStream
     * @return
     */
    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        boolean len = false;

        try {
            int len1;
            while ((len1 = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len1);
            }

            outputStream.close();
            inputStream.close();
        } catch (IOException var5) {
        }

        return outputStream.toByteArray();
    }

    /**
     * BufferedReader 转字符串
     */
    public static String readBuff(BufferedReader bufferedReader) {
        String readerstr = "";
        try {
            String tempstr = "";
            while ((tempstr = bufferedReader.readLine()) != null) {
                readerstr += tempstr;
            }
            return readerstr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * InputStream转文件
     *
     * @param inputStream
     * @param absPath
     */
    public static boolean inputStreamToFile(InputStream inputStream, String absPath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(absPath, false);
            fos.write(inputStreamToByteArray(inputStream));
            return true;
        } catch (IOException var7) {
            var7.printStackTrace();
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 写入数据到文件
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeText(File filePath, String content) {
        creatFile(filePath);
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * byte数组转文件
     *
     * @param content
     * @param file_name
     */
    public static boolean writeByteArrayToFile(byte[] content, String file_name) {
        try {
            File file = new File(file_name);
            FileOutputStream fileW = new FileOutputStream(file.getCanonicalPath());
            fileW.write(content);
            fileW.close();
        } catch (Exception var4) {
            return false;
        }
        return true;
    }

    /**
     * 追加数据
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean appendText(File filePath, String content) {
        creatFile(filePath);
        FileWriter writer = null;
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(filePath, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return true;
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getLeng(File filePath) {
        if (!filePath.exists()) {
            return -1;
        } else {
            return filePath.length();
        }
    }


    /**
     * 按文件时间排序
     *
     * @param fliePath
     * @param desc
     * @return
     */
    public static File[] orderByDate(File fliePath, boolean desc) {
        File[] fs = fliePath.listFiles();
        Arrays.sort(fs, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
        if (desc) {
            File[] nfs = new File[fs.length];
            for (int i = fs.length - 1; i > -1; i--) {
                nfs[fs.length - 1 - i] = fs[i];
            }
            return nfs;
        } else {
            return fs;
        }
    }

    /**
     * 按照文件名称排序
     *
     * @param fliePath
     * @param desc
     * @return
     */
    public static File[] orderByName(File fliePath, boolean desc) {
        File[] files = fliePath.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });

        if (desc) {
            File[] nfs = new File[files.length];
            for (int i = files.length - 1; i > -1; i--) {
                nfs[files.length - 1 - i] = files[i];
            }
            return nfs;
        } else {
            return files;
        }

    }

    /**
     * 按照文件大小排序
     *
     * @param fliePath
     */
    public static File[] orderByLength(File fliePath, boolean desc) {
        File[] files = fliePath.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.length() - f2.length();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;
            }

            public boolean equals(Object obj) {
                return true;
            }
        });

        if (desc) {
            File[] nfs = new File[files.length];
            for (int i = files.length - 1; i > -1; i--) {
                nfs[files.length - 1 - i] = files[i];
            }
            return nfs;
        } else {
            return files;
        }
    }


    /**
     * 文件筛选
     *
     * @param files
     * @param filter
     * @return
     */
    public static List<File> filter(File[] files, String filter) {
        List<File> filels = new ArrayList<>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().contains(filter)) {
                    filels.add(files[i]);
                }
            }
        }
        return filels;
    }



}