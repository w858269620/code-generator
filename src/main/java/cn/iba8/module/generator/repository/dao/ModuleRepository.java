package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long>, JpaSpecificationExecutor<Module> {

    List<Module> findAllByCode(String code);

    List<Module> findAllByCodeIn(Set<String> codes);

    Module findFirstByCodeAndVersion(String code, String version);

}
