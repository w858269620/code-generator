package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.util.MD5;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class FileTemplateDefinition implements Serializable {

    private List<FileTemplateClassDefinition> loadCodeTemplate = new ArrayList<>();
    private List<FileTemplateSuffixDefinition> loadCodeSuffix = new ArrayList<>();
    private List<FileTemplateCodeClassDefinition> loadCodeClass = new ArrayList<>();

    public static FileTemplateDefinition ofJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FileTemplateDefinition fileTemplateDefinition = objectMapper.readValue(json, FileTemplateDefinition.class);
            return fileTemplateDefinition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<FileTemplateDefinition> ofJsonList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<FileTemplateDefinition> list = objectMapper.readValue(json, new TypeReference<List<FileTemplateDefinition>>() {});
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    public static class FileTemplateClassDefinition implements Serializable {
        private String fileType;
        private String fileTypeGroup;
        private String filePath;
        private String fileKeyword;
        private String fileNote;
        public String md5() {
            return MD5.getMD5Str(toString());
        }
    }

    @Data
    public static class FileTemplateSuffixDefinition implements Serializable {
        private String fileType;
        private String fileTypeGroup;
        private String fileSuffix;
        private String packageSuffix;
        public String md5() {
            return MD5.getMD5Str(toString());
        }
    }

    @Data
    public static class FileTemplateCodeClassDefinition implements Serializable {
        private String fileType;
        private String fileTypeGroup;
        private String codeClass;
    }

}
