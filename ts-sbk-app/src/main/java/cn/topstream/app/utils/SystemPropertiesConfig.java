package cn.topstream.app.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统信息配置
 * <p>
 * application.properties file
 *
 * mail.mail_host=smtp.ym.163.com
 * mail.mailFrom=sys-alter@top-see.com
 * mail.password_mailFrom=password
 * mail.mailTittle=JavaMail邮件发送测试
 * mail.mailText=使用JavaMail创建的带附件的邮件
 * mail.mailTo=datasynclog@blue-see.com,chenjt@blue-see.com,wunn@seehoo.cn,chenml@seehoo.cn,zhangk@seehoo.cn
 *
 *
 * pom.xml file
 *
 *         <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-configuration-processor</artifactId>
 *         </dependency>
 * </p>
 * @author Jason Chen
 * @date 2021.4.2
 */

@Component
@ConfigurationProperties(prefix = "mail")
public class SystemPropertiesConfig {

    //mail
    private String mailFrom;// 指明邮件的发件人
    private String password_mailFrom;//发件人密码
    private String mailTo;    // 指明邮件的收件人
    private String mail_host;    // 邮件的服务器域名

    //mysql
    private String mysql_host;
    private String mysql_user;
    private String mysql_pwd;
    private String mysql_db;

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMail_host() {
        return mail_host;
    }

    public void setMail_host(String mail_host) {
        this.mail_host = mail_host;
    }

    public String getPassword_mailFrom() {
        return password_mailFrom;
    }

    public void setPassword_mailFrom(String password_mailFrom) {
        this.password_mailFrom = password_mailFrom;
    }

    public String getMysql_host() {
        return mysql_host;
    }

    public void setMysql_host(String mysql_host) {
        this.mysql_host = mysql_host;
    }

    public String getMysql_user() {
        return mysql_user;
    }

    public void setMysql_user(String mysql_user) {
        this.mysql_user = mysql_user;
    }

    public String getMysql_pwd() {
        return mysql_pwd;
    }

    public void setMysql_pwd(String mysql_pwd) {
        this.mysql_pwd = mysql_pwd;
    }

    public String getMysql_db() {
        return mysql_db;
    }

    public void setMysql_db(String mysql_db) {
        this.mysql_db = mysql_db;
    }
}
