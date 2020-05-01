package cn.li.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * @author: Mr.Wang
 * @date: 2020/02/11
 * @time: 12:47
 * @comment: 公共工具类
 */
public class PublicUtil {

    /**
     * 发送邮箱提示
     * @throws EmailException
     */
    public static boolean doEmail(String sendUser, String userEmail, String password, String tips, String content) {
        boolean flag = false;
        try {
            //创建Email实例
            HtmlEmail email = new HtmlEmail();
            //设置SMTP服务器
            email.setHostName("smtp.qq.com");
            //设置发送的字符集
            email.setCharset("UTF-8");
            email.addTo(sendUser);//设置收件人
            email.setFrom(userEmail,"[Mr Hotels]");//发送人的邮箱为自己的，用户名可以随便填
            email.setAuthentication(userEmail,password);//设置发送人的邮箱用户名和授权码
            email.setSubject(tips);//设置发送主题
            email.setMsg(content);//设置发送内容
            email.send();//进行发送
            flag = true;
        }catch (EmailException e){
            flag = false;
        }
        return flag;
    }

}
