package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.repository.dao.CodeTemplateRepository;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class CodeGenerateBizService {

    private final CodeTemplateBizService codeTemplateBizService;

    private final CodeTemplateRepository codeTemplateRepository;

    public CodeTemplate loadByCode(String code) {
        return codeTemplateRepository.findFirstByCodeOrderByVersionDesc(code);
    }

    private String generate(CodeTemplate codeTemplate) {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        String template = "${owner}：您的${type}:${bill}在${date}日已支付成功";
        VelocityContext context = new VelocityContext();
        context.put("owner", "nassir");
        context.put("bill", "201203221000029763");
        context.put("type", "订单");
        context.put("date",
                new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date()));
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", template);
        System.out.println(writer.toString());
        return null;
    }

    public static void main(String[] args) {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        String template = "${owner}：您的${type}:${bill}在${date}日已支付成功";
        VelocityContext context = new VelocityContext();
        context.put("owner", "nassir");
        context.put("bill", "201203221000029763");
        context.put("type", "订单");
        context.put("date", new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").format(new Date()));
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", template);
        System.out.println(writer.toString());
    }

}
