package cn.iba8.module.generator.common.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaDatabaseGenerateDdlRequest implements Serializable {

    private String codes;

    private String filterNote;

    private String replacement;

    private String filterExcludeNote;

}
