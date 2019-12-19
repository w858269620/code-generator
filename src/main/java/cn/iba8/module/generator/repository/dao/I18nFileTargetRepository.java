package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nFileTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface I18nFileTargetRepository extends JpaRepository<I18nFileTarget, Long>, JpaSpecificationExecutor<I18nFileTarget> {

    List<I18nFileTarget> findAllByAppCodeOrderByCreateTsDesc(String appCode);

    I18nFileTarget findFirstByAppCodeAndLanguageAndTypeOrderByCreateTsDesc(String appCode, String language, Integer type);

}
