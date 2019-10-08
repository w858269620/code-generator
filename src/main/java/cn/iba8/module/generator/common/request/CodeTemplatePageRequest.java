package cn.iba8.module.generator.common.request;

import lombok.Data;

@Data
public class CodeTemplatePageRequest extends PageRequest {

    private String codeLike;

    private String nameLike;

}
