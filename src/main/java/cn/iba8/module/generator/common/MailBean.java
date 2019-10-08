package cn.iba8.module.generator.common;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Data
public class MailBean implements Serializable {
    private String recipients;   //邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容

    public boolean hasRecipients() {
        return StringUtils.isNotBlank(recipients);
    }

    public String[] receivers() {
        return recipients.split(",");
    }

}
