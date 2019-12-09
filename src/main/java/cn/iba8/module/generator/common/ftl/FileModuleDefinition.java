package cn.iba8.module.generator.common.ftl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FileModuleDefinition implements Serializable {

    private List<FileModuleModuleDefinition> modules = new ArrayList<>();
    private List<FileModuleTemplateDefinition> templates = new ArrayList<>();

    public static FileModuleDefinition ofJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileModuleDefinition fileModuleDefinition = objectMapper.readValue(json, FileModuleDefinition.class);
            return fileModuleDefinition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    public static class FileModuleModuleDefinition implements Serializable {
        private String code;
        private String name;
        private String version;
        private String basePackage;
        private String restClient;
        private String restClientRoute;
    }

    @Data
    public static class FileModuleTemplateDefinition implements Serializable {
        private String templateGroup;
        private String templateRoot;
    }

}
