package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.MailBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@AllArgsConstructor
@Slf4j
public class MailUtil {

    private final MailProperties mailProperties;

    private final JavaMailSender javaMailSender;

    /**
     * 发送一个简单格式的邮件
     *
     * @param mailBean
     */
    public boolean sendSimpleMail(MailBean mailBean) {
        if (!mailBean.hasRecipients()) {
            return false;
        }
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(mailProperties.getUsername());
            //邮件接收人
            simpleMailMessage.setTo(mailBean.receivers());
            //邮件主题
            simpleMailMessage.setSubject(mailBean.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailBean.getContent());
            javaMailSender.send(simpleMailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送失败", e.getMessage());
        }
        return false;
    }

    /**
     * 发送一个HTML格式的邮件
     *
     * @param mailBean
     */
    public boolean sendHTMLMail(MailBean mailBean) throws Exception {
        if (!mailBean.hasRecipients()) {
            return false;
        }
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(mailBean.receivers());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            StringBuilder sb = new StringBuilder();
            sb.append(mailBean.getContent());
            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送失败", e.getMessage());
        }
        return false;
    }

}
