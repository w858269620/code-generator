package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseTableColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MetaDatabaseTableColumnRepository extends JpaRepository<MetaDatabaseTableColumn, Long>, JpaSpecificationExecutor<MetaDatabaseTableColumn> {

    List<MetaDatabaseTableColumn> findAllByMetaDatabaseTableIdIn(Set<Long> metaDatabaseTableIds);
}
