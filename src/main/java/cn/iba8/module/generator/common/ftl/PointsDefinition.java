package cn.iba8.module.generator.common.ftl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Data
@Slf4j
public class PointsDefinition implements Serializable {

    private String filepath;

    private String dataType;

    public static List<PointsDefinition> ofList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<PointsDefinition>>() {});
        } catch (Exception e) {
            log.error("解析json失败 {}", json);
        }
        return null;
    }

}
