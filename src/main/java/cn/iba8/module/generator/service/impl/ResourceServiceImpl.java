package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.excel.ResourceExcelDefinition;
import cn.iba8.module.generator.common.response.ResourceResponse;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.repository.dao.MetaDatabaseRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseResourceRepository;
import cn.iba8.module.generator.repository.dao.MetaDatabaseResourceTableRepository;
import cn.iba8.module.generator.repository.dao.ModuleResourceRepository;
import cn.iba8.module.generator.repository.entity.MetaDatabaseResourceTable;
import cn.iba8.module.generator.repository.entity.ModuleResource;
import cn.iba8.module.generator.service.ResourceService;
import cn.iba8.module.generator.service.biz.ResourceBizService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final MetaDatabaseResourceRepository metaDatabaseResourceRepository;

    private final MetaDatabaseResourceTableRepository metaDatabaseResourceTableRepository;

    private final MetaDatabaseRepository metaDatabaseRepository;

    private final ModuleResourceRepository moduleResourceRepository;

    private final ResourceBizService resourceBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ResourceResponse> importExcel(List<ResourceExcelDefinition> request) {
        Map<String, List<ResourceExcelDefinition>> listMap = request.stream().collect(Collectors.groupingBy(ResourceExcelDefinition::getSystem));
        Map<String, ModuleResource> map = new HashMap<>();
        listMap.keySet().forEach(r -> {
            List<ModuleResource> moduleResources = moduleResourceRepository.findAllByModuleCode(r);
            if (!CollectionUtils.isEmpty(moduleResources)) {
                moduleResources.forEach(s -> map.put(s.getModuleCode() + s.getSign(), s));
            }
        });
        List<ModuleResource> target = new ArrayList<>();
        listMap.keySet().forEach(r -> {
            List<ResourceExcelDefinition> resourceExcelDefinitions = listMap.get(r);
            resourceExcelDefinitions.forEach(s -> {
                List<ResourceExcelDefinition.ResourcePoolExcelRequest> list = s.getList();
                list.forEach(k -> {
                    ModuleResource moduleResource = map.get(r + k.getSign());

                });
            });
        });
        return CopyUtil.copyList(target, ResourceResponse.class);
    }

    @Override
    public void copyFromDatabase() {
        List<MetaDatabaseResourceTable> metaDatabaseResourceTables = metaDatabaseResourceTableRepository.findAll();
        if (CollectionUtils.isEmpty(metaDatabaseResourceTables)) {
            return;
        }
        metaDatabaseResourceTables.forEach(r -> {
            resourceBizService.syncWithDatabase(r);
        });
    }
}
