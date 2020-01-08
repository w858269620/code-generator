package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetaDatabaseResourceRepository extends JpaRepository<MetaDatabaseResource, Long>, JpaSpecificationExecutor<MetaDatabaseResource> {

    List<MetaDatabaseResource> findAllByMetaDatabaseId(Long metaDatabaseId);

}