package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileTypeGroupEnum {

    JAVA("java"),
    ;

    private String name;

    public static boolean contains(String name) {
        for (FileTypeGroupEnum fileOpTypeEnum : FileTypeGroupEnum.values()) {
            if (fileOpTypeEnum.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
