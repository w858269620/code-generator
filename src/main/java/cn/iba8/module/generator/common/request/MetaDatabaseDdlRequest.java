package cn.iba8.module.generator.common.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaDatabaseDdlRequest implements Serializable {

    private String codes;

    private String replacement;

}
