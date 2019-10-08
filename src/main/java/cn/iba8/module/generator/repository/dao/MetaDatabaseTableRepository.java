package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDatabaseTableRepository extends JpaRepository<MetaDatabaseTable, Long>, JpaSpecificationExecutor<MetaDatabaseTable> {

    List<MetaDatabaseTable> findAllByMetaDatabaseId(Long metaDatabaseId);
}
