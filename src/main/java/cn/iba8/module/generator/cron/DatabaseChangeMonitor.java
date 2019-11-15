package cn.iba8.module.generator.cron;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.enums.MetaDatabaseChangeLogTypeEnum;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.common.util.MataDatabaseUtil;
import cn.iba8.module.generator.repository.dao.*;
import cn.iba8.module.generator.repository.entity.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

 /*
  * @Author sc.wan
  * @Description 定时监控数据库变化情况
  * @Date 12:42 2019/7/4
  **/
@Service
@AllArgsConstructor
@Slf4j
public class DatabaseChangeMonitor {

    private final MetaDatabaseRepository metaDatabaseRepository;

    private final MetaDatabaseTableRepository metaDatabaseTableRepository;

    private final MetaDatabaseTableColumnRepository metaDatabaseTableColumnRepository;

    private final MetaDatabaseChangeLogRepository metaDatabaseChangeLogRepository;

    private final MetaDatabaseChangeLogTopushRepository metaDatabaseChangeLogTopushRepository;

//    @Scheduled(cron = "0 0/3 * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void monitor() {
        //数据连接信息
        List<MetaDatabase> metaDatabases = metaDatabaseRepository.findAll();
        if (CollectionUtils.isEmpty(metaDatabases)) {
            return;
        }
        //表结构变化记录
        List<MetaDatabaseChangeLog> logs = new ArrayList<>();
        metaDatabases.forEach(r -> {
            if (null != r.getEnabled() && r.getEnabled() == 1) {
                //数据库连接，如果连接不上，加入日志
                Connection connection = doConnect(r, logs);
                if (null != connection) {
                    //获取数据库表原始信息信息
                    List<MetaDatabaseTableDefinition> metaDatabaseTableDefinition = doMata(r, logs, connection);
                    //获取已经存在的数据表及字段信息
                    List<MetaDatabaseTable> metaDatabaseTables = metaDatabaseTableRepository.findAllByMetaDatabaseId(r.getId());
                    //数据库表变化
                    List<MetaDatabaseTableColumn> metaDatabaseTableColumns = new ArrayList<>();
                    List<MetaDatabaseTable> adds = new ArrayList<>();
                    List<MetaDatabaseTable> deletes = new ArrayList<>();
                    List<MetaDatabaseTableColumn> columnAdds = new ArrayList<>();
                    List<MetaDatabaseTableColumn> columnDeletes = new ArrayList<>();

                    if (!CollectionUtils.isEmpty(metaDatabaseTables)) {
                        Set<Long> ids = metaDatabaseTables.stream().map(MetaDatabaseTable::getId).collect(Collectors.toSet());
                        metaDatabaseTableColumns = metaDatabaseTableColumnRepository.findAllByMetaDatabaseTableIdIn(ids);
                    }

                    //找出差异
                    doDiff(r, metaDatabaseTableDefinition, metaDatabaseTables, metaDatabaseTableColumns, logs, adds, deletes, columnAdds, columnDeletes);

                    if (!CollectionUtils.isEmpty(logs)) {
                        List<MetaDatabaseChangeLog> metaDatabaseChangeLogs = metaDatabaseChangeLogRepository.saveAll(logs);
                        metaDatabaseChangeLogTopushRepository.saveAll(CopyUtil.copyList(metaDatabaseChangeLogs, MetaDatabaseChangeLogTopush.class));
                    }

                    if (!CollectionUtils.isEmpty(adds)) {
                        metaDatabaseTableRepository.saveAll(adds);
                    }
                    if (!CollectionUtils.isEmpty(deletes)) {
                        metaDatabaseTableRepository.deleteAll(deletes);
                    }

                    if (!CollectionUtils.isEmpty(columnAdds)) {
                        metaDatabaseTableColumnRepository.saveAll(columnAdds);
                    }
                    if (!CollectionUtils.isEmpty(columnDeletes)) {
                        metaDatabaseTableColumnRepository.deleteAll(columnDeletes);
                    }
                }
            }
        });
        //表结构变化通知
    }

    private void doDiff(
            MetaDatabase r, List<MetaDatabaseTableDefinition> metaDatabaseTableDefinition,
            List<MetaDatabaseTable> metaDatabaseTables,
            List<MetaDatabaseTableColumn> metaDatabaseTableColumns,
            List<MetaDatabaseChangeLog> logs,
            List<MetaDatabaseTable> adds,
            List<MetaDatabaseTable> deletes,
            List<MetaDatabaseTableColumn> columnAdds,
            List<MetaDatabaseTableColumn> columnDeletes
    ) {

        //表结构差异
        MetaDatabaseTableDefinition.databaseDiff(r, metaDatabaseTables, metaDatabaseTableDefinition, logs, adds, deletes);

        //表字段差异
        MetaDatabaseTableDefinition.columDiff(r, metaDatabaseTableDefinition, logs, deletes, metaDatabaseTables, metaDatabaseTableColumns, columnAdds, columnDeletes);

    }


    private Connection doConnect(MetaDatabase r, List<MetaDatabaseChangeLog> logs) {
        Connection connection = null;
        boolean hasError = false;
        try {
            connection = MataDatabaseUtil.getConnection(r);
        } catch (Exception e) {
            hasError = true;
            e.printStackTrace();
            log.error("获取数据库连接信息失败 metadatabase {}", r);
        }
        if (hasError) {
            MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
            log.setCode(r.getCode());
            log.setDatabaseName(r.getName());
            log.setCreateTime(new Date());
            log.setLog("数据库连接失败");
            log.setType(MetaDatabaseChangeLogTypeEnum.DATABASE.getCode());
            logs.add(log);
        }
        return connection;
    }

    private List<MetaDatabaseTableDefinition> doMata(MetaDatabase r, List<MetaDatabaseChangeLog> logs, Connection connection) {
        List<MetaDatabaseTableDefinition> definitions = new ArrayList<>();
        boolean hasError;
        try {
            definitions = MataDatabaseUtil.getTableColumns(connection);
            hasError = false;
        } catch (Exception e) {
            hasError = true;
            log.error("获取数据库表信息失败 metadatabase {}", r);
        }

        if (hasError) {
            MetaDatabaseChangeLog log = new MetaDatabaseChangeLog();
            log.setCode(r.getCode());
            log.setDatabaseName(r.getName());
            log.setCreateTime(new Date());
            log.setLog("数据库表信息读取失败");
            log.setType(MetaDatabaseChangeLogTypeEnum.DATABASE.getCode());
            logs.add(log);
        }
        return definitions;
    }

}
