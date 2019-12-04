package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.ModuleMetaDatabaseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleMetaDatabaseTableRepository extends JpaRepository<ModuleMetaDatabaseTable, Long>, JpaSpecificationExecutor<ModuleMetaDatabaseTable> {

    List<ModuleMetaDatabaseTable> findAllByModuleCode(String moduleCode);

}
