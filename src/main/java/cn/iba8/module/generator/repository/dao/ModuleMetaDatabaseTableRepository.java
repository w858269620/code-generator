package cn.iba8.module.generator.repository.entity;

import cn.iba8.module.generator.repository.entity.ModuleMetaDatabaseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleMetaDatabaseTableRepository extends JpaRepository<ModuleMetaDatabaseTable, Long>, JpaSpecificationExecutor<ModuleMetaDatabaseTable> {

}
