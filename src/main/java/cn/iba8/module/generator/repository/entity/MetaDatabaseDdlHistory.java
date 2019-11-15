package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "meta_database_ddl_history")
public class MetaDatabaseDdlHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "meta_database_id")
    private Long metaDatabaseId;

    @Column(name = "filter_note")
    private String filterNote;

    @Column(name = "table_create")
    private String tableCreate;

    @Column(name = "table_triggers")
    private String tableTriggers;

    @Column(name = "table_ddl")
    private String tableDdl;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "version")
    private Long version;

}
