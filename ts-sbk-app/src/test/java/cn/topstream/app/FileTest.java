package cn.topstream.app;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by jiangtao on 2016/10/21.
 */
@SpringBootTest
public class FileTest {


    @Test
    public void create() throws Exception {


        int sms_num = 10000 * 1;
        Long numMobile = 835150000L;

        StringBuffer mobiles = new StringBuffer();
//        String mobiles = "";

        System.out.println("wait ...");
        Thread.sleep(10000);
        System.out.println("start ...");

        long now = System.currentTimeMillis();

        for (int i = 0; i < sms_num; i++) {
            mobiles.append(numMobile + i).append("\r\n");
//            mobiles += numMobile + "\r\n";
//            System.out.println("长度：" + mobiles.length()/13);
        }
        System.out.println("生成文件时间：" + (System.currentTimeMillis() - now));

        System.out.println("长度：" + (mobiles.length() / 13));

        File file = new File("sms-" + sms_num + ".txt");

//        Files.write(mobiles.toString().getBytes(),file);
        PrintWriter writer = new PrintWriter(file);
        writer.print(mobiles);
        writer.flush();
        writer.close();


        System.out.println("写完文件时间：" + (System.currentTimeMillis() - now));

    }
}