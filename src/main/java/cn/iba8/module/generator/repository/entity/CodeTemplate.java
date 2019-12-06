package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "code_template")
public class CodeTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "template")
    private String template;

    @Column(name = "type")
    private String type;

    @Column(name = "type_group")
    private String typeGroup;

    @Column(name = "md5")
    private String md5;

    @Column(name = "version")
    private Long version;

    @Column(name = "latest")
    private Integer latest;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "level")
    private String level;

}
