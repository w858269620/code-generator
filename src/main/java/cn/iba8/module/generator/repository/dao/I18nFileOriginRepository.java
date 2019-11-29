package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.I18nFileOrigin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface I18nFileOriginRepository extends JpaRepository<I18nFileOrigin, Long>, JpaSpecificationExecutor<I18nFileOrigin> {

    List<I18nFileOrigin> findAllByProcessed(Integer processed);

    long countAllByMd5(String md5);

}
