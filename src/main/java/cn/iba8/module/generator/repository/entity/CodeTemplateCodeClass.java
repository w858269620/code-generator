package cn.iba8.module.generator.repository.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    @Column(name = "template_group")
    private String templateGroup;

    @Column(name = "type")
    private String type;

    @Column(name = "type_group")
    private String typeGroup;

    @Column(name = "code_class")
    private String codeClass;

    public String getCodeClassName() {
        if (StringUtils.isNotBlank(codeClass)) {
            int i = codeClass.lastIndexOf(".");
            if (i > 0) {
                return codeClass.substring(i + 1);
            }
        }
        return "";
    }

}
