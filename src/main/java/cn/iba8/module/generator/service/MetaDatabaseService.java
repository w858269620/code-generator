package cn.iba8.module.generator.service;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;

import java.util.List;

public interface MetaDatabaseService {

    List<MetaDatabaseTableDefinition> metaTables(String code);

    String getXml(String codes);

}
