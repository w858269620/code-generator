package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseChangeLogTopush;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MetaDatabaseChangeLogTopushRepository extends JpaRepository<MetaDatabaseChangeLogTopush, Long>, JpaSpecificationExecutor<MetaDatabaseChangeLogTopush> {

    List<MetaDatabaseChangeLogTopush> findAllByTypeIn(Set<Integer> typs);
}
