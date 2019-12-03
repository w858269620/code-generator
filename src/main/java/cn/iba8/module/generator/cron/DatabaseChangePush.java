package cn.iba8.module.generator.cron;

import cn.iba8.module.generator.common.enums.MetaDatabaseChangeLogTypeEnum;
import cn.iba8.module.generator.common.util.MetaDatabaseChangeLogTopushUtil;
import cn.iba8.module.generator.repository.dao.MetaDatabaseChangeLogTopushRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseReceiverRelationRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseReceiverRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import cn.iba8.module.generator.repository.entity.MetaDatabaseChangeLogTopush;
import cn.iba8.module.generator.repository.entity.MetaDatabaseReceiver;
import cn.iba8.module.generator.repository.entity.MetaDatabaseReceiverRelation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DatabaseChangePush {

    private final MetaDatabaseChangeLogTopushRepository metaDatabaseChangeLogTopushRepository;

    private final MetaDatabaseReceiverRepository metaDatabaseReceiverRepository;

    private final MetaDatabaseReceiverRelationRepository metaDatabaseReceiverRelationRepository;

    private final MetaDatabaseRepository metaDatabaseRepository;

//    @Scheduled(cron = "${cn.iba8.module.cron.database-change-push}")
    @Transactional(rollbackFor = Exception.class)
    public void pushDatabase() {
        //数据量不会太大，一次性处理
        Set<Integer> types = new HashSet<>();
        types.add(MetaDatabaseChangeLogTypeEnum.DATABASE.getCode());
        List<MetaDatabaseChangeLogTopush> topushes = metaDatabaseChangeLogTopushRepository.findAllByTypeIn(types);
        push(topushes, "开发数据库变更");
    }

//    @Scheduled(cron = "${cn.iba8.module.cron.table-change-push}")
    @Transactional(rollbackFor = Exception.class)
    public void pushTableAndField() {
        //数据量不会太大，一次性处理
        Set<Integer> types = new HashSet<>();
        types.add(MetaDatabaseChangeLogTypeEnum.TABLE.getCode());
        types.add(MetaDatabaseChangeLogTypeEnum.FIELD.getCode());
        List<MetaDatabaseChangeLogTopush> topushes = metaDatabaseChangeLogTopushRepository.findAllByTypeIn(types);
        push(topushes, "开发数据库表或字段变更");
    }

    @Transactional(rollbackFor = Exception.class)
    public void push(List<MetaDatabaseChangeLogTopush> logTopushes, String subject) {
        if (CollectionUtils.isEmpty(logTopushes)) {
            return;
        }
        Set<String> databaseCodes = logTopushes.stream().map(MetaDatabaseChangeLogTopush::getCode).collect(Collectors.toSet());
        List<MetaDatabase> metaDatabases = metaDatabaseRepository.findFirstByCodeIn(databaseCodes);
        //数据库信息
        Set<Long> databaseIds = metaDatabases.stream().map(MetaDatabase::getId).collect(Collectors.toSet());
        List<MetaDatabaseReceiverRelation> receiverRelations = metaDatabaseReceiverRelationRepository.findAllByMetaDatabaseIdIn(databaseIds);
        if (CollectionUtils.isEmpty(receiverRelations)) {
            return;
        }
        Set<Long> receiverIds = receiverRelations.stream().map(MetaDatabaseReceiverRelation::getMetaDatabaseReceiverId).collect(Collectors.toSet());
        Map<Long, List<MetaDatabaseReceiverRelation>> databaseReceiverIdMap = receiverRelations.stream().collect(Collectors.groupingBy(MetaDatabaseReceiverRelation::getMetaDatabaseId));

        //收件人
        List<MetaDatabaseReceiver> databaseReceivers = metaDatabaseReceiverRepository.findAllById(receiverIds);
        if (CollectionUtils.isEmpty(databaseReceivers)) {
            return;
        }
        Map<Long, MetaDatabaseReceiver> receiverMap = databaseReceivers.stream().collect(Collectors.toMap(MetaDatabaseReceiver::getId, s -> s, (k1, k2) -> k2));
        List<MetaDatabaseChangeLogTopush> toDeletes = new ArrayList<>();
        MetaDatabaseChangeLogTopushUtil.push(logTopushes, metaDatabases, databaseReceiverIdMap, receiverMap, toDeletes, subject);

        if (!CollectionUtils.isEmpty(toDeletes)) {
            //删除已经推送的信息
            metaDatabaseChangeLogTopushRepository.deleteAll(logTopushes);
        }
    }

}
