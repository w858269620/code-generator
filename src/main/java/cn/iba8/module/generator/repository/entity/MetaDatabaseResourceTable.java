package cn.iba8.module.generator.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name="meta_database_resource_table")
public class MetaDatabaseResourceTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 19)
    private Long id;

    @Column(name = "table_name", length = 255)
    private String tableName;

    @Column(name = "system", length = 255)
    private String system;

    @Column(name = "meta_database_id", length = 19)
    private Long metaDatabaseId;


}