package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.util.MD5;
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
    private String fileKeyword;
    private String fileNote;
    private String packageSuffix;

    public String md5() {
        return MD5.getMD5Str(toString());
    }

    public boolean valid() {
        return StringUtils.isNotBlank(filePath)
                && StringUtils.isNotBlank(fileType)
                && StringUtils.isNotBlank(fileOpType)
                && StringUtils.isNotBlank(fileTypeGroup);
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
