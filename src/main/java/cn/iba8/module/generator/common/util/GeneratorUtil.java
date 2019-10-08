package cn.iba8.module.generator.common.util;

import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;

import java.io.File;

public class GeneratorUtil {

    public static void genRepository(String module) {
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("generator/repository/entity.ftl");
        String ProjectPath = System.getProperty("user.dir") + File.separator + module;
        String packagePath = ProjectPath + File.separator + "src" +File.separator+ "main" + File.separator + "java" + File.separator;
//        template.render()
    }

}
