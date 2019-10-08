package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.util.MataDatabaseUtil;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MetaDatabaseBizService {

    private final MetaDatabaseRepository metaDatabaseRepository;

    public List<MetaDatabaseTableDefinition> metaTables(String code) {
        MetaDatabase metaDatabase = metaDatabaseRepository.findFirstByCode(code);
        if (null == metaDatabase) {
            throw BaseException.of(ResponseCode.META_DATABASE_NOT_EXIST);
        }
        try {
            Connection connection = MataDatabaseUtil.getConnection(metaDatabase);
            return MataDatabaseUtil.getTableColumns(connection);
        } catch (Exception e) {
            throw BaseException.of(ResponseCode.META_DATABASE_CONNECT_FAIL);
        }
    }

}
