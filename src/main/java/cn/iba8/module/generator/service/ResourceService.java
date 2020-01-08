package cn.iba8.module.generator.service;

import cn.iba8.module.generator.common.excel.ResourceExcelDefinition;
import cn.iba8.module.generator.common.response.ResourceResponse;

import java.util.List;

public interface ResourceService {

    /**
     * @Author sc.wan
     * @Description 导入excel资源文件
     * @Param
     * @return
     * @Date 2020/1/2 16:18
     */
    List<ResourceResponse> importExcel(List<ResourceExcelDefinition> request);

    /**
     * @Author sc.wan
     * @Description 从数据库同步资源信息
     * @Param
     * @return
     * @Date 2020/1/2 16:33
     */
    void copyFromDatabase();

}
