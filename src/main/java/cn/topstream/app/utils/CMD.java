package cn.topstream.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by chenjt@seehoo.cn on 2020/3/21.
 *
 * @author Jason Chen
 */
public class CMD {
    private final static Logger logger = LoggerFactory.getLogger(CMD.class);

    public static boolean runCMD(String cmd) throws IOException, InterruptedException {
        final String METHOD_NAME = "runCMD";
        logger.debug("【CMD执行命令】{}", cmd);
        Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String readLine = br.readLine();
            StringBuilder builder = new StringBuilder();
            while (readLine != null) {
                readLine = br.readLine();
                builder.append(readLine + "\n");
            }
            logger.debug(METHOD_NAME + "执行信息: " + builder.toString());

            //说明： p.waitFor()表示当前线程等待process这个线程执行完毕，再开始往下执行。该方法返回0时表示正常终止。
            p.waitFor();
            int i = p.exitValue();
            logger.debug("【CMD命令执行结果】" + i);
            if (i == 0) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            logger.error(METHOD_NAME + "【CMD命令执行失败】{}", cmd, e);
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }
}
