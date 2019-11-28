package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nCodeLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface I18nCodeLanguageRepository extends JpaRepository<I18nCodeLanguage, Long>, JpaSpecificationExecutor<I18nCodeLanguage> {

    List<I18nCodeLanguage> findAllByModuleCodeAndLanguage(String moduleCode, String language);

    List<I18nCodeLanguage> findAllByModuleCode(String moduleCode);

}
