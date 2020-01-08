package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseResourceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDatabaseResourceTableRepository extends JpaRepository<MetaDatabaseResourceTable, Long>, JpaSpecificationExecutor<MetaDatabaseResourceTable> {

    MetaDatabaseResourceTable findFirstBySystem(String system);

}