package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.ftl.FileModuleDefinition;
import cn.iba8.module.generator.repository.dao.ModuleRepository;
import cn.iba8.module.generator.repository.entity.Module;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ModuleBizService {

    private final ModuleRepository moduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public void load(FileModuleDefinition.FileModuleModuleDefinition fileModuleModuleDefinition) {
        if (null == fileModuleModuleDefinition) {
            return;
        }
        Module module = moduleRepository.findFirstByCodeAndVersion(fileModuleModuleDefinition.getCode(), fileModuleModuleDefinition.getVersion());
        if (null == module) {
            log.info("模块不存在，自动创建 {}:{}", fileModuleModuleDefinition.getCode(), fileModuleModuleDefinition.getVersion());
            module = new Module();
            module.setCode(fileModuleModuleDefinition.getCode());
        }
        if (null != fileModuleModuleDefinition.getBasePackage()) {
            module.setPackageName(fileModuleModuleDefinition.getBasePackage());
        }
        if (null != fileModuleModuleDefinition.getName()) {
            module.setName(fileModuleModuleDefinition.getName());
        }
        if (null != fileModuleModuleDefinition.getRestClient()) {
            module.setRestClient(fileModuleModuleDefinition.getRestClient());
        }
        if (null != fileModuleModuleDefinition.getRestClientRoute()) {
            module.setRestClientRoute(fileModuleModuleDefinition.getRestClientRoute());
        }
        if (null != fileModuleModuleDefinition.getVersion()) {
            module.setVersion(fileModuleModuleDefinition.getVersion());
        }
        moduleRepository.save(module);
    }

}
