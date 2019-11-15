package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.CodeGenerateConstants;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.request.MetaDatabaseGenerateDdlRequest;
import cn.iba8.module.generator.common.response.MetaDatabaseGenerateDdlResponse;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.repository.dao.MetaDatabaseDdlHistoryRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseTableRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.repository.entity.MetaDatabaseDdlHistory;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class MetaDatabaseDdlHistoryBizService {

    private final MetaDatabaseTableRepository metaDatabaseTableRepository;

    private final MetaDatabaseDdlHistoryRepository metaDatabaseDdlHistoryRepository;

    private final MetaDatabaseBizService metaDatabaseBizService;

    public MetaDatabaseDdlHistory generateDdl(MetaDatabase metaDatabase, MetaDatabaseGenerateDdlRequest request) {
        if (null == metaDatabase) {
            throw BaseException.of(ResponseCode.META_DATABASE_NOT_EXIST);
        }
        List<MetaDatabaseTable> metaDatabaseTableList;
        if (StringUtils.isNotBlank(request.getFilterNote()) && !CodeGenerateConstants.GENERATE_DDL_ALL.equals(request.getFilterNote())) {
            metaDatabaseTableList = metaDatabaseTableRepository.findAllByMetaDatabaseIdAndTableNameIsLikeOrderByTableNameAsc(metaDatabase.getId(), "%" + request.getFilterNote() + "%");
        } else {
            metaDatabaseTableList = metaDatabaseTableRepository.findAllByMetaDatabaseIdOrderByTableNameAsc(metaDatabase.getId());
        }
        StringBuffer createTable = new StringBuffer();
        StringBuffer createTriggers = new StringBuffer();
        StringBuffer createDdl = new StringBuffer();
        if (CollectionUtils.isEmpty(metaDatabaseTableList)) {
            return null;
        }
        Set<String> excludeTables = new HashSet<>();
        if (StringUtils.isNotBlank(request.getFilterExcludeNote())) {
            String[] split = request.getFilterExcludeNote().trim().split(",");
            for (String s : split) {
                if (StringUtils.isNotBlank(s)) {
                    excludeTables.add(s.trim());
                }
            }
        }
        for (MetaDatabaseTable metaDatabaseTable : metaDatabaseTableList) {
            if (!CollectionUtils.isEmpty(excludeTables)) {
                boolean exclude = false;
                for (String e : excludeTables) {
                    boolean contains = metaDatabaseTable.getTableName().contains(e);
                    if (contains) {
                        exclude = true;
                        break;
                    }
                }
                if (exclude) {
                    continue;
                }
            }
            String tableDdl = metaDatabaseTable.getTableDdl();
            String tableTriggers = metaDatabaseTable.getTableTriggers();
            if (StringUtils.isNotBlank(tableDdl)) {
                String targetTableDdl = tableDdl;
                String targetTableTriggers = null;
                if (StringUtils.isNotBlank(tableTriggers)) {
                    targetTableTriggers = tableTriggers;
                }
                if (null != targetTableTriggers) {
                    createTriggers.append(targetTableTriggers + ";\n\n");
                }
                createTable.append(targetTableDdl + ";\n\n");
                createDdl.append(targetTableDdl + ";\n\n" + (null != targetTableTriggers ? targetTableTriggers + "\n" : ""));
            }
        }
        MetaDatabaseDdlHistory origin = metaDatabaseDdlHistoryRepository.findFirstByMetaDatabaseIdAndFilterNoteOrderByVersionDesc(metaDatabase.getId(), request.getFilterNote());
        if (StringUtils.isNotBlank(request.getReplacement())) {
            return origin;
        }
        Long version = 1L;
        if (null != origin) {
            version = origin.getVersion() + 1;
        }
        MetaDatabaseDdlHistory metaDatabaseDdlHistory = new MetaDatabaseDdlHistory();
        metaDatabaseDdlHistory.setFilterNote(StringUtils.isNotBlank(request.getFilterNote()) ? request.getFilterNote() : CodeGenerateConstants.GENERATE_DDL_ALL);
        metaDatabaseDdlHistory.setCreateTime(new Date());
        metaDatabaseDdlHistory.setMetaDatabaseId(metaDatabase.getId());
        metaDatabaseDdlHistory.setVersion(version);
        metaDatabaseDdlHistory.setTableCreate(createTable.toString());
        metaDatabaseDdlHistory.setTableTriggers(StringUtils.isNotBlank(createTriggers.toString()) ? createTriggers.toString() : null);
        metaDatabaseDdlHistory.setTableDdl(createDdl.toString());
        return metaDatabaseDdlHistory;
    }

    public MetaDatabaseDdlHistory copyFrom(MetaDatabase metaDatabase, MetaDatabaseGenerateDdlRequest request) {
        MetaDatabaseDdlHistory metaDatabaseDdlHistoryFilterNote = metaDatabaseDdlHistoryRepository.findFirstByMetaDatabaseIdAndFilterNoteOrderByVersionDesc(metaDatabase.getId(), request.getFilterNote());
        MetaDatabaseDdlHistory metaDatabaseDdlHistoryReplacement = metaDatabaseDdlHistoryRepository.findFirstByMetaDatabaseIdAndFilterNoteOrderByVersionDesc(metaDatabase.getId(), request.getReplacement());
        if (null != metaDatabaseDdlHistoryFilterNote) {
            MetaDatabaseDdlHistory metaDatabaseDdlHistory = new MetaDatabaseDdlHistory();
            Long version = 1L;
            if (null != metaDatabaseDdlHistoryReplacement) {
                version = metaDatabaseDdlHistoryReplacement.getVersion() + 1;
            }
            metaDatabaseDdlHistory.setFilterNote(request.getReplacement());
            metaDatabaseDdlHistory.setCreateTime(new Date());
            metaDatabaseDdlHistory.setVersion(version);
            metaDatabaseDdlHistory.setMetaDatabaseId(metaDatabase.getId());

            if (null != metaDatabaseDdlHistoryFilterNote.getTableCreate()) {
                metaDatabaseDdlHistory.setTableCreate(metaDatabaseDdlHistoryFilterNote.getTableCreate().replaceAll(request.getFilterNote(), request.getReplacement()));
            }
            if (null != metaDatabaseDdlHistoryFilterNote.getTableTriggers()) {
                metaDatabaseDdlHistory.setTableTriggers(metaDatabaseDdlHistoryFilterNote.getTableTriggers().replaceAll(request.getFilterNote(), request.getReplacement()));
            }
            if (null != metaDatabaseDdlHistoryFilterNote.getTableDdl()) {
                metaDatabaseDdlHistory.setTableDdl(metaDatabaseDdlHistoryFilterNote.getTableDdl().replaceAll(request.getFilterNote(), request.getReplacement()));
            }
            return metaDatabaseDdlHistory;
        }
        return null;
    }

    public List<MetaDatabaseGenerateDdlResponse> doTableDdl(MetaDatabaseGenerateDdlRequest request) {
        List<MetaDatabase> all = metaDatabaseBizService.findByCodesOr(request.getCodes());
        List<MetaDatabaseGenerateDdlResponse> target = new ArrayList<>();
        if (!CollectionUtils.isEmpty(all)) {
            List<MetaDatabaseDdlHistory> ddlHistories = new ArrayList<>();
            for (MetaDatabase metaDatabase : all) {
                MetaDatabaseDdlHistory metaDatabaseDdlHistory = null;
                if (StringUtils.isNotBlank(request.getFilterNote())) {
                    metaDatabaseDdlHistory = generateDdl(metaDatabase, request);
                }
                if (StringUtils.isNotBlank(request.getReplacement())) {
                    metaDatabaseDdlHistory = copyFrom(metaDatabase, request);
                }
                if (null != metaDatabaseDdlHistory) {
                    ddlHistories.add(metaDatabaseDdlHistory);
                    target.add(CopyUtil.copy(metaDatabaseDdlHistory, MetaDatabaseGenerateDdlResponse.class));
                }
            }
            if (!CollectionUtils.isEmpty(ddlHistories)) {
                metaDatabaseDdlHistoryRepository.saveAll(ddlHistories);
            }
        }
        return target;
    }

}
