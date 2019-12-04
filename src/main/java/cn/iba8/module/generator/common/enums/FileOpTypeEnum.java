package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileOpTypeEnum {

    LOAD("load"),
    ;

    private String name;

    public static boolean contains(String name) {
        for (FileOpTypeEnum fileOpTypeEnum : FileOpTypeEnum.values()) {
            if (fileOpTypeEnum.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
