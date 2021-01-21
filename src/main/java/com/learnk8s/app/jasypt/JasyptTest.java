package com.learnk8s.app.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("xlgPPrpsT6mxIwZ");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("Root@123");
        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }


}
