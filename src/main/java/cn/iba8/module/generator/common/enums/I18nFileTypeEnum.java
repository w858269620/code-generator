package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum I18nFileTypeEnum {

    ALL(1),
    INCR(2),
    ;

    private int code;

}
