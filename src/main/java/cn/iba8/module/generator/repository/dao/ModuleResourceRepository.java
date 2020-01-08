package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.ModuleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleResourceRepository extends JpaRepository<ModuleResource, Long>, JpaSpecificationExecutor<ModuleResource> {

    List<ModuleResource> findAllByModuleCode(String moduleCode);

}