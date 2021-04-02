package cn.topstream.app.jasypt;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("8gKZNS6GwpJ2vgq");
        //要加密的数据（数据库的用户名或密码）
        String username = textEncryptor.encrypt("test");
        String password = textEncryptor.encrypt("test");
        System.out.println("username:ENC("+username+")");
        System.out.println("password:ENC("+password+")");
    }
}
