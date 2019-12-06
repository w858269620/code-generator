package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateLevelEnum {

    MODULE("module"),
    TABLE("table"),
    ;

    private String name;

}
