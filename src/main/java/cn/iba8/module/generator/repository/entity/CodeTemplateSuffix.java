package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "code_template_suffix")
public class CodeTemplateSuffix implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "type_group")
    private String typeGroup;

    @Column(name = "file_suffix")
    private String fileSuffix;

    @Column(name = "package_suffix")
    private String packageSuffix;

    @Column(name = "md5")
    private String md5;

}
