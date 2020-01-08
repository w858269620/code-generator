package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.util.MataDatabaseUtil;
import cn.iba8.module.generator.id.core.IdService;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseResourceRepository;
import cn.iba8.module.generator.repository.dao.ModuleResourceRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.repository.entity.MetaDatabaseResource;
import cn.iba8.module.generator.repository.entity.MetaDatabaseResourceTable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class ResourceBizService {

    private final ModuleResourceRepository moduleResourceRepository;

    private final MetaDatabaseRepository metaDatabaseRepository;

    private final MetaDatabaseResourceRepository metaDatabaseResourceRepository;

    private final IdService idService;

    public void syncWithDatabase(MetaDatabaseResourceTable metaDatabaseResourceTable) {
        Optional<MetaDatabase> optionalMetaDatabase = metaDatabaseRepository.findById(metaDatabaseResourceTable.getMetaDatabaseId());
        if (!optionalMetaDatabase.isPresent()) {
            log.warn("database does not exit");
            return;
        }
        List<MetaDatabaseResource> metaDatabaseResources = metaDatabaseResourceRepository.findAllByMetaDatabaseId(metaDatabaseResourceTable.getMetaDatabaseId());
        Map<String, MetaDatabaseResource> metaDatabaseResourceMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(metaDatabaseResources)) {
            metaDatabaseResources.forEach(r -> metaDatabaseResourceMap.put(r.getSystem() + r.getSign(), r));
        }
        MetaDatabase metaDatabase = optionalMetaDatabase.get();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<MetaDatabaseResource> target = new ArrayList<>();
        try {
            connection = MataDatabaseUtil.getConnection(metaDatabase);
            preparedStatement = connection.prepareStatement("select * from " + metaDatabaseResourceTable.getTableName());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String sign = resultSet.getString("sign");
                String system = resultSet.getString("system");
                boolean b = null != metaDatabaseResourceMap.get(system + sign);
                MetaDatabaseResource resource = b ? metaDatabaseResourceMap.get(system + sign) : new MetaDatabaseResource();
                resource.setBgColor(resultSet.getString("bg_color"));
                resource.setSystem(system);
                resource.setCategory(resultSet.getInt("category"));
                resource.setEnabled(resultSet.getInt("enabled"));
                resource.setHref(resultSet.getString("href"));
                resource.setIcon(resultSet.getString("icon"));
                resource.setSort(resultSet.getInt("sort"));
                resource.setName(resultSet.getString("name"));
                resource.setNote(resultSet.getString("note"));
                resource.setParentId(resultSet.getLong("parent_id"));
                resource.setPerms(resultSet.getString("perms"));
                resource.setResourceId(resultSet.getLong("id"));
                resource.setSign(sign);
                resource.setTarget(resultSet.getString("target"));
                if (!b) {
                    long id = idService.genId(1);
                    resource.setId(id);
                }
                resource.setMetaDatabaseId(metaDatabaseResourceTable.getMetaDatabaseId());
                target.add(resource);
            }
        } catch (Exception e) {
            log.error("fail to sync resource. {}", metaDatabaseResourceTable);
        } finally {
            if (null != resultSet) {
                try {
                    resultSet.close();
                } catch (Exception e) {

                }
            }
            if (null != preparedStatement) {
                try {
                    preparedStatement.close();
                } catch (Exception e) {

                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (Exception e) {

                }
            }
        }
        if (!CollectionUtils.isEmpty(target)) {
            metaDatabaseResourceRepository.saveAll(target);
        }
    }

    public void loadModuleResource() {

    }

    public void loadAppResource() {

    }

    public void resourceToHistory() {

    }

}
