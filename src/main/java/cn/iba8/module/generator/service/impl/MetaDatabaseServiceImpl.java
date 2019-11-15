package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.request.MetaDatabaseDdlRequest;
import cn.iba8.module.generator.common.request.MetaDatabaseGenerateDdlRequest;
import cn.iba8.module.generator.common.response.MetaDatabaseDdlResponse;
import cn.iba8.module.generator.common.response.MetaDatabaseGenerateDdlResponse;
import cn.iba8.module.generator.repository.dao.MetaDatabaseDdlHistoryRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.repository.entity.MetaDatabaseDdlHistory;
import cn.iba8.module.generator.service.MetaDatabaseService;
import cn.iba8.module.generator.service.biz.MetaDatabaseBizService;
import cn.iba8.module.generator.service.biz.MetaDatabaseDdlHistoryBizService;
import cn.iba8.module.generator.service.converter.MetaDatabaseConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MetaDatabaseServiceImpl implements MetaDatabaseService {

    private final MetaDatabaseRepository metaDatabaseRepository;

    private final MetaDatabaseBizService metaDatabaseBizService;

    private final MetaDatabaseDdlHistoryRepository metaDatabaseDdlHistoryRepository;

    private final MetaDatabaseDdlHistoryBizService metaDatabaseDdlHistoryBizService;

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

    @Override
    public String getTables(String code) {
        MetaDatabase metaDatabase = metaDatabaseRepository.findFirstByCode(code);
        if (null == metaDatabase) {
            throw BaseException.of(ResponseCode.META_DATABASE_NOT_EXIST);
        }
        List<MetaDatabaseTableDefinition> definitions = metaDatabaseBizService.metaTables(code);
        StringBuffer sb = new StringBuffer();
        definitions.forEach(r -> {
            sb.append("," + metaDatabase.getName() + "." + r.getTableName());
        });
        return sb.toString();
    }

    @Override
    public List<MetaDatabaseDdlResponse> getTableDdl(MetaDatabaseDdlRequest request) {
        List<MetaDatabase> all = metaDatabaseBizService.findByCodesOr(request.getCodes());
        List<MetaDatabaseDdlResponse> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(all)) {
            Map<Long, MetaDatabase> metaDatabaseMap = all.stream().collect(Collectors.toMap(MetaDatabase::getId, s -> s, (k1, k2) -> k2));
            List<MetaDatabaseDdlHistory> metaDatabaseDdlHistories = new ArrayList<>();
            metaDatabaseMap.keySet().forEach(r -> {
                MetaDatabaseDdlHistory filterNoteOrderByVersionDesc = metaDatabaseDdlHistoryRepository.findFirstByMetaDatabaseIdAndFilterNoteOrderByVersionDesc(r, request.getReplacement());
                if (null != filterNoteOrderByVersionDesc) {
                    metaDatabaseDdlHistories.add(filterNoteOrderByVersionDesc);
                }
            });
            if (!CollectionUtils.isEmpty(metaDatabaseDdlHistories)) {
                metaDatabaseDdlHistories.forEach(r -> {
                    MetaDatabase metaDatabase = metaDatabaseMap.get(r.getMetaDatabaseId());
                    MetaDatabaseDdlResponse metaDatabaseDdlResponse = new MetaDatabaseDdlResponse();
                    metaDatabaseDdlResponse.setNote(metaDatabase.getNote());
                    metaDatabaseDdlResponse.setReplacement(request.getReplacement());
                    metaDatabaseDdlResponse.setCreateDdl(r.getTableDdl());
                    metaDatabaseDdlResponse.setCreateTable(r.getTableCreate());
                    metaDatabaseDdlResponse.setCreateTriggers(r.getTableTriggers());
                    target.add(metaDatabaseDdlResponse);
                });
            }
        }
        return target;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MetaDatabaseGenerateDdlResponse> doTableDdl(MetaDatabaseGenerateDdlRequest request) {
        return metaDatabaseDdlHistoryBizService.doTableDdl(request);
    }

}
