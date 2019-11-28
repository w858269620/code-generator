package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface I18nLanguageRepository extends JpaRepository<I18nLanguage, Long>, JpaSpecificationExecutor<I18nLanguage> {

    I18nLanguage findFirstByCode(String code);

    List<I18nLanguage> findAllByCodeIn(Set<String> codes);

    List<I18nLanguage> findAllByModuleCode(String moduleCode);
}
