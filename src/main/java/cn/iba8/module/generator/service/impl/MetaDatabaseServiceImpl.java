package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.service.MetaDatabaseService;
import cn.iba8.module.generator.service.biz.MetaDatabaseBizService;
import cn.iba8.module.generator.service.converter.MetaDatabaseConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class MetaDatabaseServiceImpl implements MetaDatabaseService {

    private final MetaDatabaseRepository metaDatabaseRepository;

    private final MetaDatabaseBizService metaDatabaseBizService;

    @Override
    public List<MetaDatabaseTableDefinition> metaTables(String code) {
        return metaDatabaseBizService.metaTables(code);
    }

    @Override
    public String getXml(String codes) {
        String[] databases = codes.split(",");
        if (null == databases || databases.length == 0) {
            return "无";
        }
        Map<String, List<MetaDatabaseTableDefinition>> codeDatabaseDefinitionMap = new HashMap<>();
        List<MetaDatabase> metaDatabases = new ArrayList<>();
        for (int i = 0; i < databases.length; i++) {
            MetaDatabase metaDatabase = metaDatabaseRepository.findFirstByCode(databases[i]);
            metaDatabases.add(metaDatabase);
            List<MetaDatabaseTableDefinition> definitions = metaDatabaseBizService.metaTables(databases[i]);
            codeDatabaseDefinitionMap.put(metaDatabase.getCode(), definitions);
        }
        return MetaDatabaseConverter.toMyCatXml(metaDatabases, codeDatabaseDefinitionMap);
    }

}
