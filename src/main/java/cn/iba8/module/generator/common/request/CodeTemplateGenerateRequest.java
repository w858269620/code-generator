package cn.iba8.module.generator.common.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeTemplateGenerateRequest implements Serializable {

    private String moduleCode;
    private String version;
    private String typeGroup;
    private String templateGroup;
    private boolean withMethod;
    private boolean withMethodExcel;

}
