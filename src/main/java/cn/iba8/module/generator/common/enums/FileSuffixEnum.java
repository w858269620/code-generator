package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileSuffixEnum {

    JSON("json"),
    PROPERTIES("properties"),
    YML("yml"),
    ;

    private String name;

}
