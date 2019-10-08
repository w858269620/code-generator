package cn.iba8.module.generator.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeTemplateSaveResponse implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String note;

    private String template;

    private Integer type;

}
