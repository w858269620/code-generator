package cn.iba8.module.generator.service;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.request.MetaDatabaseDdlRequest;
import cn.iba8.module.generator.common.request.MetaDatabaseGenerateDdlRequest;
import cn.iba8.module.generator.common.response.MetaDatabaseDdlResponse;
import cn.iba8.module.generator.common.response.MetaDatabaseGenerateDdlResponse;

import java.util.List;
import java.util.Map;

public interface MetaDatabaseService {

    List<MetaDatabaseTableDefinition> metaTables(String code);

    String getXml(String codes);

    String getTables(String code);

    List<MetaDatabaseDdlResponse> getTableDdl(MetaDatabaseDdlRequest request);

    List<MetaDatabaseGenerateDdlResponse> doTableDdl(MetaDatabaseGenerateDdlRequest request);

}
