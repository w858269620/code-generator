package cn.iba8.module.generator.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MetaDatabaseGenerateDdlResponse implements Serializable {

    private Long id;

    private Long metaDatabaseId;

    private String filterNote;

    private String tableCreate;

    private String tableTriggers;

    private String tableDdl;

    private Date createTime;

    private Long version;

}
