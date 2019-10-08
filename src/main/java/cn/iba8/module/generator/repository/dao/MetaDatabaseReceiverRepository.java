package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDatabaseReceiverRepository extends JpaRepository<MetaDatabaseReceiver, Long>, JpaSpecificationExecutor<MetaDatabaseReceiver> {

}
