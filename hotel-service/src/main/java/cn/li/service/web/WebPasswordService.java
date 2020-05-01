package cn.li.service.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: Mr.Wang
 * @date: 2020/02/11
 * @time: 12:46
 * @comment: WebPassword服务层
 */
@Service
public class WebPasswordService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 向邮箱发送验证码
     * @param email     收件人邮箱
     * @param title     标题
     * @param text      发送的内容
     */
    public boolean emailSendVerifyCode(String email, String title, String text){

        //创建邮件对象
        MimeMessage mMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mMessageHelper;
        Properties prop = new Properties();
        String from;
        try {
            //从配置文件中拿到发件人邮箱地址
            prop.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
            from = prop.get("mail.smtp.username")+"";
            mMessageHelper=new MimeMessageHelper(mMessage,true);
            //发件人邮箱
            mMessageHelper.setFrom(from);
            //收件人邮箱
            mMessageHelper.setTo(email);
            //邮件的主题
            mMessageHelper.setSubject(title);
            //邮件的文本内容，true表示文本以html格式打开
            mMessageHelper.setText(text,true);
            //发送邮件
            javaMailSender.send(mMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
