package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.CodeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeTemplateRepository extends JpaRepository<CodeTemplate, Long>, JpaSpecificationExecutor<CodeTemplate> {

    CodeTemplate findFirstByCodeOrderByVersionDesc(String code);

    List<CodeTemplate> findAllByTypeGroupAndLatest(String typeGroup, Integer latest);

}
