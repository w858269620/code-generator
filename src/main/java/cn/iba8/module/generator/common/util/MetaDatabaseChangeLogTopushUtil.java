package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.MailBean;
import cn.iba8.module.generator.common.enums.MetaDatabaseChangeLogTypeEnum;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.repository.entity.MetaDatabaseChangeLogTopush;
import cn.iba8.module.generator.repository.entity.MetaDatabaseReceiver;
import cn.iba8.module.generator.repository.entity.MetaDatabaseReceiverRelation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public abstract class MetaDatabaseChangeLogTopushUtil {

    public static String buildHtml(List<MetaDatabaseChangeLogTopush> topushes) {
        StringBuffer sb = new StringBuffer("<style type=\"text/css\">\n" +
                ".tg  {border-collapse:collapse;border-spacing:0;}\n" +
                ".tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}\n" +
                ".tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:black;}\n" +
                ".tg .tg-c3ow{border-color:inherit;text-align:center;vertical-align:top}\n" +
                ".tg .tg-0pky{border-color:inherit;text-align:left;vertical-align:top}\n" +
                "</style>\n" +
                "<table class=\"tg\" style=\"undefined;table-layout: fixed; width: 741px\">\n" +
                "<colgroup>\n" +
                "<col style=\"width: 105px\">\n" +
                "<col style=\"width: 115px\">\n" +
                "<col style=\"width: 115px\">\n" +
                "<col style=\"width: 127px\">\n" +
                "<col style=\"width: 178px\">\n" +
                "<col style=\"width: 216px\">\n" +
                "</colgroup>\n" +
                "  <tr>\n" +
                "    <th class=\"tg-c3ow\">表名</th>\n" +
                "    <th class=\"tg-c3ow\">变更类型</th>\n" +
                "    <th class=\"tg-c3ow\">日志</th>\n" +
                "    <th class=\"tg-c3ow\">原数据</th>\n" +
                "    <th class=\"tg-c3ow\">新数据</th>\n" +
                "    <th class=\"tg-c3ow\">时间</th>\n" +
                "  </tr>\n"

                );
        for (MetaDatabaseChangeLogTopush logTopush : topushes) {
            sb.append( "  <tr>\n" +
                    "    <td class=\"tg-c3ow\">" + logTopush.getTableName() + "</td>\n" +
                    "    <td class=\"tg-c3ow\">" + MetaDatabaseChangeLogTypeEnum.code2name(logTopush.getType()) + "</td>\n" +
                    "    <td class=\"tg-c3ow\">" + logTopush.getLog() + "</td>\n" +
                    "    <td class=\"tg-c3ow\">" + logTopush.getOriginData() + "</td>\n" +
                    "    <td class=\"tg-c3ow\">" + logTopush.getNowData() + "</td>\n" +
                    "    <td class=\"tg-c3ow\">" + DateUtil.dateToString("yyyy-MM-dd HH:mm:ss", logTopush.getCreateTime()) + "</td>\n" +
                    "  </tr>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public static MailBean mailBean(List<MetaDatabaseChangeLogTopush> topushes, MetaDatabase metaDatabase, Set<String> receivers, String subject) {
        MailBean mailBean = new MailBean();
        mailBean.setRecipients(StringUtils.join(receivers, ","));
        mailBean.setSubject(subject + "(" + metaDatabase.getCode() + ")");
        mailBean.setContent(buildHtml(topushes));
        return mailBean;
    }

    public static void push(
            List<MetaDatabaseChangeLogTopush> logTopushes,
            List<MetaDatabase> metaDatabases,
            Map<Long, List<MetaDatabaseReceiverRelation>> databaseReceiverIdMap,
            Map<Long, MetaDatabaseReceiver> receiverMap,
            List<MetaDatabaseChangeLogTopush> toDeletes,
            String subject
    ) {
        Map<String, MetaDatabase> codeMetaDatabaseMap = metaDatabases.stream().collect(Collectors.toMap(MetaDatabase::getCode, s -> s, (k1, k2) -> k2));
        Map<String, List<MetaDatabaseChangeLogTopush>> databaseLogMap = logTopushes.stream().collect(Collectors.groupingBy(MetaDatabaseChangeLogTopush::getCode));
        databaseLogMap.keySet().forEach(r -> {
            List<MetaDatabaseChangeLogTopush> topushes = databaseLogMap.get(r);
            MetaDatabase metaDatabase = codeMetaDatabaseMap.get(r);
            Set<String> emails = new HashSet<>();
            List<MetaDatabaseReceiverRelation> receiverRelations = databaseReceiverIdMap.get(metaDatabase.getId());
            if (!CollectionUtils.isEmpty(receiverRelations)) {
                receiverRelations.forEach(s -> {
                    Long metaDatabaseReceiverId = s.getMetaDatabaseReceiverId();
                    MetaDatabaseReceiver metaDatabaseReceiver = receiverMap.get(metaDatabaseReceiverId);
                    if (null != metaDatabaseReceiver) {
                        if (StringUtils.isNotBlank(metaDatabaseReceiver.getEmail())) {
                            emails.add(metaDatabaseReceiver.getEmail());
                        }
                    }
                });
            }
            if (!CollectionUtils.isEmpty(emails)) {
                try {
                    //push
                    MailBean mailBean = mailBean(topushes, metaDatabase, emails, subject);
                    MailUtil mailUtil = SpringUtils.getBean(MailUtil.class);
                    boolean b = mailUtil.sendHTMLMail(mailBean);
                    if (b) {
                        toDeletes.addAll(topushes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("发送邮件失败 topush {}, emails {}", topushes, emails);
                }
            }
        });
    }

}
