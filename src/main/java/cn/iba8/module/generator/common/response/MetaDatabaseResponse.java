package cn.iba8.module.generator.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaDatabaseResponse implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String host;

    private Integer port;
}
