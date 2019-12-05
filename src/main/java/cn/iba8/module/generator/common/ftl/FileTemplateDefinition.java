package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.enums.FileOpTypeEnum;
import cn.iba8.module.generator.common.enums.FileTypeGroupEnum;
import cn.iba8.module.generator.common.enums.TemplateTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

@Data
public class FileTemplateDefinition implements Serializable {

    private String fileTypeGroup;
    private String fileType;
    private String filePath;
    private String fileOpType;
    private String fileSuffix;
    private String packageSuffix;

    public boolean valid() {
        return StringUtils.isNotBlank(filePath) && TemplateTypeEnum.contains(fileType) && FileOpTypeEnum.contains(fileOpType) && FileTypeGroupEnum.contains(fileTypeGroup);
    }

    public static List<FileTemplateDefinition> ofJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<FileTemplateDefinition> list = objectMapper.readValue(json, new TypeReference<List<FileTemplateDefinition>>() {});
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
