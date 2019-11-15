package cn.iba8.module.generator.repository.dao;

import cn.iba8.module.generator.repository.entity.MetaDatabaseDdlHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaDatabaseDdlHistoryRepository extends JpaRepository<MetaDatabaseDdlHistory, Long>, JpaSpecificationExecutor<MetaDatabaseDdlHistory> {

    MetaDatabaseDdlHistory findFirstByMetaDatabaseIdOrderByVersionDesc(Long metaDatabaseId);

    MetaDatabaseDdlHistory findFirstByMetaDatabaseIdAndFilterNoteOrderByVersionDesc(Long metaDatabaseId, String filterNote);

}
