package cn.iba8.module.generator.common;

import cn.iba8.module.generator.common.enums.MetaDatabaseChangeLogTypeEnum;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.common.util.JacksonUtil;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.repository.entity.MetaDatabaseChangeLog;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTableColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

 /*
  * @Author sc.wan
  * @Description 数据库原始信息
  * @Date 12:42 2019/7/4
  **/
@Data
public class MetaDatabaseTableDefinition implements Serializable {

    private String tableName;

    private String tableComment;

    private String tableDdl;

    private String tableTriggers;

    private List<MetaDatabaseTableDefinitionColumn> columns = new ArrayList<>();

    @Data
    public static class MetaDatabaseTableDefinitionColumn implements Serializable {
        private String columnName;
        private String columnType;
        private String columnComment;
        private int columnSize;
        private int digits;
        private int nullable;
        private boolean primaryKey;
    }

    public static void columDiff(
            MetaDatabase r,
            List<MetaDatabaseTableDefinition> nows,
            List<MetaDatabaseChangeLog> logs,
            List<MetaDatabaseTable> deletes,
            List<MetaDatabaseTable> metaDatabaseTables,
            List<MetaDatabaseTableColumn> metaDatabaseTableColumns,
            List<MetaDatabaseTableColumn> columnAdds,
            List<MetaDatabaseTableColumn> columnDeletes
    ) {
        Map<String, MetaDatabaseTableDefinition> definitionMap = nows.stream().collect(Collectors.toMap(MetaDatabaseTableDefinition::getTableName, s -> s, (k1, k2) -> k2));
        Map<String, MetaDatabaseTable> tableMap = metaDatabaseTables.stream().collect(Collectors.toMap(MetaDatabaseTable::getTableName, s -> s, (k1, k2) -> k2));
        Map<Long, MetaDatabaseTable> tableIdMap = metaDatabaseTables.stream().collect(Collectors.toMap(MetaDatabaseTable::getId, s -> s, (k1, k2) -> k2));

        Set<Long> toDeleteTableIds = deletes.stream().map(MetaDatabaseTable::getId).collect(Collectors.toSet());
        Map<Long, List<MetaDatabaseTableColumn>> columnMap = new HashMap<>();
        for (MetaDatabaseTableColumn metaDatabaseTableColumn : metaDatabaseTableColumns) {
            if (toDeleteTableIds.contains(metaDatabaseTableColumn.getMetaDatabaseTableId())) {
                columnDeletes.add(metaDatabaseTableColumn);
                MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                log.setCode(r.getCode());
                log.setDatabaseName(r.getName());
                log.setCreateTime(new Date());
                log.setLog("数据库表字段删除");
                log.setOriginData(JacksonUtil.toString(metaDatabaseTableColumn));
                log.setNowData(null);
                log.setType(MetaDatabaseChangeLogTypeEnum.FIELD.getCode());
                log.setTableName(tableIdMap.get(metaDatabaseTableColumn.getMetaDatabaseTableId()).getTableName());
                logs.add(log);
                continue;
            }
            List<MetaDatabaseTableColumn> tableColumns = columnMap.get(metaDatabaseTableColumn.getMetaDatabaseTableId());
            if (null == tableColumns) {
                tableColumns = new ArrayList<>();
            }
            tableColumns.add(metaDatabaseTableColumn);
            columnMap.put(metaDatabaseTableColumn.getMetaDatabaseTableId(), tableColumns);
        }

        definitionMap.keySet().forEach(s1 -> {
            MetaDatabaseTableDefinition definition = definitionMap.get(s1);
            MetaDatabaseTable metaDatabaseTable = tableMap.get(definition.getTableName());
            if (null != definition && null != metaDatabaseTable) {
                List<MetaDatabaseTableDefinitionColumn> columns = definition.getColumns();
                List<MetaDatabaseTableColumn> tableColumns = null != columnMap.get(metaDatabaseTable.getId()) ? columnMap.get(metaDatabaseTable.getId()) : new ArrayList<>();
                Map<String, MetaDatabaseTableDefinitionColumn> columnsMap = columns.stream().collect(Collectors.toMap(MetaDatabaseTableDefinitionColumn::getColumnName, s -> s, (k1, k2) -> k2));
                Map<String, MetaDatabaseTableColumn> tableColumnsMap = tableColumns.stream().collect(Collectors.toMap(MetaDatabaseTableColumn::getColumnName, s -> s, (k1, k2) -> k2));
                Set<String> nowsSet = new HashSet<>(columnsMap.keySet());
                Set<String> originsSet = new HashSet<>(tableColumnsMap.keySet());
                nowsSet.forEach(s -> {
                    if (originsSet.contains(s)) {
                        //比较差异
                        MetaDatabaseTableColumn metaDatabaseTableColumn = tableColumnsMap.get(s);
                        MetaDatabaseTableDefinitionColumn metaDatabaseTableDefinitionColumn = columnsMap.get(s);
                        if (!metaDatabaseTableDefinitionColumn.equals(CopyUtil.copy(metaDatabaseTableColumn, MetaDatabaseTableDefinitionColumn.class))) {
                            MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                            log.setCode(r.getCode());
                            log.setDatabaseName(r.getName());
                            log.setCreateTime(new Date());
                            log.setLog("数据库表字段变化");
                            log.setOriginData(JacksonUtil.toString(metaDatabaseTableColumn));
                            log.setNowData(JacksonUtil.toString(metaDatabaseTableDefinitionColumn));
                            log.setType(MetaDatabaseChangeLogTypeEnum.FIELD.getCode());
                            log.setTableName(metaDatabaseTable.getTableName());
                            logs.add(log);
                            MetaDatabaseTableColumn c = CopyUtil.copy(metaDatabaseTableDefinitionColumn, MetaDatabaseTableColumn.class);
                            c.setId(metaDatabaseTableColumn.getId());
                            c.setMetaDatabaseTableId(metaDatabaseTable.getId());
                            columnAdds.add(c);
                        }
                    } else {
                        //新增
                        MetaDatabaseTableDefinitionColumn metaDatabaseTableColumn = columnsMap.get(s);
                        MetaDatabaseTableDefinitionColumn metaDatabaseTableDefinitionColumn = columnsMap.get(s);
                        MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                        log.setCode(r.getCode());
                        log.setDatabaseName(r.getName());
                        log.setCreateTime(new Date());
                        log.setLog("数据库表字段新增");
                        log.setOriginData(null);
                        log.setNowData(JacksonUtil.toString(metaDatabaseTableColumn));
                        log.setType(MetaDatabaseChangeLogTypeEnum.FIELD.getCode());
                        log.setTableName(metaDatabaseTable.getTableName());
                        logs.add(log);
                        MetaDatabaseTableColumn c = CopyUtil.copy(metaDatabaseTableDefinitionColumn, MetaDatabaseTableColumn.class);
                        c.setMetaDatabaseTableId(metaDatabaseTable.getId());
                        columnAdds.add(c);
                    }
                });
                originsSet.forEach(s -> {
                    if (!nowsSet.contains(s)) {
                        //删除
                        MetaDatabaseTableColumn metaDatabaseTableColumn = tableColumnsMap.get(s);
                        if (null != metaDatabaseTableColumn) {
                            MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                            log.setCode(r.getCode());
                            log.setDatabaseName(r.getName());
                            log.setCreateTime(new Date());
                            log.setLog("数据库表字段删除");
                            log.setType(MetaDatabaseChangeLogTypeEnum.FIELD.getCode());
                            log.setOriginData(JacksonUtil.toString(metaDatabaseTableColumn));
                            log.setNowData(null);
                            log.setTableName(metaDatabaseTable.getTableName());
                            logs.add(log);
                            MetaDatabaseTableColumn c = CopyUtil.copy(metaDatabaseTableColumn, MetaDatabaseTableColumn.class);
                            columnDeletes.add(c);
                        }

                    }
                });
            }
        });
    }

    /*
     * @Author sc.wan
     * @Description 数据库结构差异
     * @Date 11:48 2019/7/3
     * @Param
     * @return
     **/
    public static void databaseDiff(
            MetaDatabase r,
            List<MetaDatabaseTable> origins, List<MetaDatabaseTableDefinition> nows,
            List<MetaDatabaseChangeLog> logs,
            List<MetaDatabaseTable> adds, List<MetaDatabaseTable> deletes

    ) {
        Map<String, MetaDatabaseTableDefinition> nowsMap = nows.stream().collect(Collectors.toMap(MetaDatabaseTableDefinition::getTableName, s -> s, (k1, k2) -> k2));
        Map<String, MetaDatabaseTable> originsMap = origins.stream().collect(Collectors.toMap(MetaDatabaseTable::getTableName, s -> s, (k1, k2) -> k2));
        Set<String> nowsSet = new HashSet<>(nowsMap.keySet());
        Set<String> originsSet = new HashSet<>(originsMap.keySet());
        nowsSet.forEach(k -> {
            if (originsSet.contains(k)) {
                //比较差异
                TableDiff tableDiff = TableDiff.of(nowsMap.get(k));
                TableDiff diff = TableDiff.of(originsMap.get(k));
                if (!tableDiff.equals(diff)) {
                    MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                    log.setCode(r.getCode());
                    log.setDatabaseName(r.getName());
                    log.setCreateTime(new Date());
                    log.setLog("数据库表信息发生变化");
                    log.setOriginData(JacksonUtil.toString(diff));
                    log.setNowData(JacksonUtil.toString(tableDiff));
                    log.setType(MetaDatabaseChangeLogTypeEnum.TABLE.getCode());
                    log.setTableName(tableDiff.getTableName());
                    logs.add(log);
                    MetaDatabaseTable metaDatabaseTable = CopyUtil.copy(originsMap.get(k), MetaDatabaseTable.class);
                    metaDatabaseTable.setTableName(tableDiff.getTableName());
                    metaDatabaseTable.setTableComment(tableDiff.getTableComment());
                    metaDatabaseTable.setTableDdl(tableDiff.getTableDdl());
                    metaDatabaseTable.setTableTriggers(tableDiff.getTableTriggers());
                    adds.add(metaDatabaseTable);
                }
            } else {
                //新增
                TableDiff tableDiff = TableDiff.of(nowsMap.get(k));
                MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                log.setCode(r.getCode());
                log.setDatabaseName(r.getName());
                log.setCreateTime(new Date());
                log.setLog("数据库表新增");
                log.setOriginData(null);
                log.setNowData(JacksonUtil.toString(tableDiff));
                log.setType(MetaDatabaseChangeLogTypeEnum.TABLE.getCode());
                log.setTableName(tableDiff.getTableName());
                logs.add(log);
                MetaDatabaseTable metaDatabaseTable = new MetaDatabaseTable();
                metaDatabaseTable.setTableName(tableDiff.getTableName());
                metaDatabaseTable.setTableComment(tableDiff.getTableComment());
                metaDatabaseTable.setMetaDatabaseId(r.getId());
                metaDatabaseTable.setTableDdl(tableDiff.getTableDdl());
                metaDatabaseTable.setTableTriggers(tableDiff.getTableTriggers());
                adds.add(metaDatabaseTable);
            }
        });
        originsSet.forEach(k -> {
            if (!nowsSet.contains(k)) {
                //删除
                TableDiff diff = TableDiff.of(originsMap.get(k));
                MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
                log.setCode(r.getCode());
                log.setDatabaseName(r.getName());
                log.setCreateTime(new Date());
                log.setLog("数据库表删除");
                log.setOriginData(JacksonUtil.toString(diff));
                log.setNowData(null);
                log.setType(MetaDatabaseChangeLogTypeEnum.TABLE.getCode());
                log.setTableName(diff.getTableName());
                logs.add(log);
                deletes.add(originsMap.get(k));
            }
        });
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    static class TableDiff implements Serializable {
        private String tableName;
        private String tableComment;
        private String tableDdl;
        private String tableTriggers;

        public static TableDiff of(MetaDatabaseTableDefinition definition) {
            TableDiff tableDiff = new TableDiff();
            tableDiff.setTableComment(definition.getTableComment());
            tableDiff.setTableName(definition.getTableName());
            tableDiff.setTableDdl(definition.getTableDdl());
            tableDiff.setTableTriggers(definition.getTableTriggers());
            return tableDiff;
        }

        public static TableDiff of(MetaDatabaseTable table) {
            TableDiff tableDiff = new TableDiff();
            tableDiff.setTableComment(table.getTableComment());
            tableDiff.setTableName(table.getTableName());
            tableDiff.setTableDdl(table.getTableDdl());
            tableDiff.setTableTriggers(table.getTableTriggers());
            return tableDiff;
        }

    }

}
