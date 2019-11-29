package cn.iba8.module.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cn.iba8.module.generator")
public class CodeGeneratorProperties {

    private String inputDir;

    private String outputDir;

    private String inputHistory;

}
