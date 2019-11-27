package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.ApiI18nCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiI18nCodeRepository extends JpaRepository<ApiI18nCode, Long>, JpaSpecificationExecutor<ApiI18nCode> {

}
