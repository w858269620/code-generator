package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nFileOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface I18nFileTargetRepository extends JpaRepository<I18nFileOrigin, Long>, JpaSpecificationExecutor<I18nFileOrigin> {

}
