package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.CodeTemplateCodeClass;
import cn.iba8.module.generator.repository.entity.CodeTemplateSuffix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeTemplateCodeClassRepository extends JpaRepository<CodeTemplateCodeClass, Long>, JpaSpecificationExecutor<CodeTemplateCodeClass> {

    CodeTemplateCodeClass findFirstByTypeAndTypeGroup(String type, String typeGroup);

    List<CodeTemplateCodeClass> findAllByTypeGroup(String typeGroup);

    List<CodeTemplateCodeClass> findAllByTypeGroupAndTemplateGroup(String typeGroup, String templateGroup);

}
