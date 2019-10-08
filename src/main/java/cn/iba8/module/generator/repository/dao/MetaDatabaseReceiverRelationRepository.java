package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseReceiverRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MetaDatabaseReceiverRelationRepository extends JpaRepository<MetaDatabaseReceiverRelation, Long>, JpaSpecificationExecutor<MetaDatabaseReceiverRelation> {

    List<MetaDatabaseReceiverRelation> findAllByMetaDatabaseIdIn(Set<Long> metaDatabaseIds);
}
