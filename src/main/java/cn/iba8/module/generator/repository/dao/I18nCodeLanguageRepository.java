package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nCodeLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface I18nCodeLanguageRepository extends JpaRepository<I18nCodeLanguage, Long>, JpaSpecificationExecutor<I18nCodeLanguage> {

}