package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TemplateScopeEnum {

    CLASS("class"),
    METHOD("method"),
    ;

    private String name;

}
