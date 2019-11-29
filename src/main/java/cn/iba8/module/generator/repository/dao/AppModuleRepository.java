package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.AppModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppModuleRepository extends JpaRepository<AppModule, Long>, JpaSpecificationExecutor<AppModule> {

    List<AppModule> findAllByAppCode(String appCode);

}
