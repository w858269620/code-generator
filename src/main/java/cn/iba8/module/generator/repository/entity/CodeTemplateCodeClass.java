package cn.iba8.module.generator.repository.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "code_template_code_class")
public class CodeTemplateCodeClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "type_group")
    private String typeGroup;

    @Column(name = "code_class")
    private String codeClass;

}
