package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface I18nCodeRepository extends JpaRepository<I18nCode, Long>, JpaSpecificationExecutor<I18nCode> {

}
