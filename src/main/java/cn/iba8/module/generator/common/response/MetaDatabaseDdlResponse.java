package cn.iba8.module.generator.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaDatabaseDdlResponse implements Serializable {

    private String replacement;

    private String note;

    private String createTable;

    private String createTriggers;

    private String createDdl;

}
