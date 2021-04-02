package cn.topstream.app.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


/**
 * @author Jason Chen
 */
@Component
public class SendEmail {
    //基础配置
    private static String mailFrom = null;// 指明邮件的发件人
    private static String password_mailFrom = null;// 指明邮件的发件人登陆密码
    private static String mailTo = null;    // 指明邮件的收件人
    private static String mail_host = null;    // 邮件的服务器域名

    //本地配置文件
    private static SystemPropertiesConfig config;
    private static Properties prop = new Properties();

    private final static Logger logger = LoggerFactory.getLogger(SendEmail.class);


    @Autowired
    public void init(SystemPropertiesConfig systemPropertiesConfig) {

        //获取基础信息
        SendEmail.config = systemPropertiesConfig;
        SendEmail.mail_host = config.getMail_host();
        SendEmail.password_mailFrom = config.getPassword_mailFrom();

        //基础邮件发送、接收人
        SendEmail.mailTo = config.getMailTo();
        SendEmail.mailFrom = config.getMailFrom();

        //邮件基础配置
        prop.setProperty("mail.debug", "true");
        prop.setProperty("mail.host", mail_host);
        prop.setProperty("mail.smtp.host",mail_host);
        //prop.setProperty("mail.smtp.ehlo", "false");
        prop.setProperty("mail.smtp.auth", "true");
        prop.setProperty("mail.transport.protocol", "smtp");



        //加密
        //prop.put("mail.smtp.starttls.enable", "true");
        //prop.put("mail.smtp.socketFactory.port", "465");
        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //prop.put("mail.smtp.port", "465");

        //等待时间
        //prop.setProperty("mail.smtp.connectiontimeout", "5000");
        //prop.setProperty("mail.smtp.timeout", "5000");
        //prop.setProperty("mail.smtp.writetimeout", "5000");

    }

    public static void sendMail(String mailTo, String mailTittle, String mailText, String attFile) throws Exception {

        if (mailTo == null || mailTo.isEmpty()) {
            mailTo = SendEmail.mailTo;
            logger.info("sendMail：收件人为空，使用默认收件人：{}", mailTo);
        }


        // 1、创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2、通过session得到transport对象
        Transport ts = session.getTransport();
        // 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(SendEmail.mail_host, SendEmail.mailFrom, SendEmail.password_mailFrom);
        // 4、创建邮件
        Message message;

        //判断有无附件
        if (attFile == null || attFile.isEmpty()) {//无附件
            message = createSimpleMail(session, mailTo, mailTittle, mailText);
        } else {
            message = createAttachMail(session, mailTo, mailTittle, mailText, attFile);
        }
        // 5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();

    }


    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String mailTo, String mailTittle, String mailText) throws Exception {

        MimeMessage message = createAttachMail(session, mailTo, mailTittle, mailText, null);
        return message;
    }

    /**
     * @Method: createAttachMail
     * @Description: 创建一封带附件的邮件
     */
    public static MimeMessage createAttachMail(Session session, String mailTo, String mailTittle, String mailText, String attFile) throws Exception {

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SendEmail.mailFrom));    // 发件人
        //message.setRecipients(Message.RecipientType.TO, new InternetAddress(mailTo));// 收件人
        message.setRecipients(Message.RecipientType.TO, mailTo);// 收件人
        message.setSubject(mailTittle, "UTF-8");        // 邮件标题

        if (attFile != null && !attFile.isEmpty()) {//有附件
            // 创建容器描述数据关系
            MimeMultipart mp = new MimeMultipart();

            // 创建邮件正文，为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(mailText, "text/html;charset=UTF-8");
            mp.addBodyPart(text);            //加入body 1
            // 创建邮件附件
            MimeBodyPart attach = new MimeBodyPart();
            String[] atts = attFile.split(",");
            for (int i = 0; i < atts.length; i++) {
                DataHandler dh = new DataHandler(new FileDataSource(atts[i]));// 需要修改
                attach.setDataHandler(dh);
                //attach.setFileName(MimeUtility.encodeWord(dh.getName()));
                attach.setFileName(dh.getName());

                mp.addBodyPart(attach);//加入body 2
            }
            mp.setSubType("mixed");//设置 3

            message.setContent(mp);
            message.saveChanges();
            // 将创建的Email写入到/tmp/存储
            //message.writeTo(new FileOutputStream("/tmp/ImageMail.eml"));// 需要修改
        } else {
            // 邮件的文本内容
            message.setContent(mailText, "text/html;charset=UTF-8");
        }

        // 返回生成的邮件
        return message;
    }
}
