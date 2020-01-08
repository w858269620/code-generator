package cn.iba8.module.generator.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceResponse implements Serializable {

    private Long id;

    private String note;

    private String code;

    private String sign;

    private String icon;

    private Integer index;

    private Long metaDatabaseId;

    private String enabled;

    private String target;

    private String bgColor;

    private Long parentId;

    private String name;

    private Long resourceId;

    private String perms;

    private String href;

    private String category;

}