package cn.iba8.module.generator.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@AllArgsConstructor
public class I18nCommandLine implements CommandLineRunner {

    private final CodeGeneratorProperties codeGeneratorProperties;

    @Override
    public void run(String... args) throws Exception {
        String inputDir = codeGeneratorProperties.getInputDir();
        String outputDir = codeGeneratorProperties.getOutputDir();
        String inputHistory = codeGeneratorProperties.getInputHistory();
        File inputFile = new File(inputDir);
        File outputFile = new File(outputDir);
        File inputHistoryFile = new File(inputHistory);
        if (!inputFile.exists()) {
            inputFile.mkdirs();
        }
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        if (!inputHistoryFile.exists()) {
            inputHistoryFile.mkdirs();
        }
    }
}
