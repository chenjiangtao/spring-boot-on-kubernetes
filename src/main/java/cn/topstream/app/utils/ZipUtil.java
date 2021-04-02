package cn.topstream.app.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * @author Jason Chen
 */
public class ZipUtil {

    /**
     * 带密码解压
     *
     * @param zipFile
     * @param pwd
     * @param extraDir
     * @throws ZipException
     */
    public static void unZip(String zipFile, String pwd, String extraDir) throws ZipException {
        new ZipFile(zipFile, pwd.toCharArray()).extractAll(extraDir);
    }

    /**
     * 不带密码解压
     *
     * @param zipFile
     * @param extraDir
     * @throws ZipException
     */
    public static void unZip(String zipFile, String extraDir) throws ZipException {
        new ZipFile(zipFile).extractAll(extraDir);
    }
}
