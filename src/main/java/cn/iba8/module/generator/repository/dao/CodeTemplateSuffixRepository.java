package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.CodeTemplateSuffix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeTemplateSuffixRepository extends JpaRepository<CodeTemplateSuffix, Long>, JpaSpecificationExecutor<CodeTemplateSuffix> {

    CodeTemplateSuffix findFirstByTypeAndTypeGroup(String type, String typeGroup);

    List<CodeTemplateSuffix> findAllByTypeGroup(String typeGroup);

}
