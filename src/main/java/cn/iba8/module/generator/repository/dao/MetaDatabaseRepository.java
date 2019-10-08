package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MetaDatabaseRepository extends JpaRepository<MetaDatabase, Long>, JpaSpecificationExecutor<MetaDatabase> {

    MetaDatabase findFirstByCode(String code);

    List<MetaDatabase> findFirstByCodeIn(Set<String> codes);

}
