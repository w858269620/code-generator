package cn.iba8.module.generator.service.converter;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.ftl.XmlForMyCatSchema;
import cn.iba8.module.generator.repository.entity.MetaDatabase;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MetaDatabaseConverter {

    public static String toMyCatXml(List<MetaDatabase> metaDatabases, Map<String, List<MetaDatabaseTableDefinition>> codeDatabaseDefinitionMap) {
        try {
            MetaDatabase main = metaDatabases.get(0);
            XmlForMyCatSchema myCatSchema = new XmlForMyCatSchema();
            XmlForMyCatSchema.MyCatSchema schema = new XmlForMyCatSchema.MyCatSchema();
            List<XmlForMyCatSchema.MyCatTable> tables = new ArrayList<>();
            List<XmlForMyCatSchema.MyCatDataNode> dataNodes = new ArrayList<>();
            List<XmlForMyCatSchema.MyCatDataHost> dataHosts = new ArrayList<>();
            schema.setTables(tables);
            schema.setDataNode(main.getName());
            schema.setName(main.getName());
            myCatSchema.setSchema(schema);
            myCatSchema.setDataNodes(dataNodes);
            myCatSchema.setDataHosts(dataHosts);
            for (int i = 0; i < metaDatabases.size(); i++) {
                MetaDatabase metaDatabase = metaDatabases.get(i);
                XmlForMyCatSchema.MyCatDataNode myCatDataNode = new XmlForMyCatSchema.MyCatDataNode();
                myCatDataNode.setDataHost(metaDatabase.getName());
                myCatDataNode.setDatabase(metaDatabase.getName());
                myCatDataNode.setName(metaDatabase.getName());
                dataNodes.add(myCatDataNode);

                XmlForMyCatSchema.MyCatDataHost myCatDataHost = new XmlForMyCatSchema.MyCatDataHost();
                XmlForMyCatSchema.MyCatDataHost.MyCatDataHostWriteHost writeHost = new XmlForMyCatSchema.MyCatDataHost.MyCatDataHostWriteHost();
                writeHost.setHost(metaDatabase.getHost());
                writeHost.setPassword(metaDatabase.getPassword());
                writeHost.setUrl(metaDatabase.getHost() + ":" + metaDatabase.getPort());
                myCatDataHost.setWriteHost(writeHost);
                myCatDataHost.setName(metaDatabase.getName());
                dataHosts.add(myCatDataHost);

                List<MetaDatabaseTableDefinition> definitions = codeDatabaseDefinitionMap.get(metaDatabase.getCode());
                if (!CollectionUtils.isEmpty(definitions)) {
                    for (MetaDatabaseTableDefinition definition : definitions) {
                        XmlForMyCatSchema.MyCatTable myCatTable = new XmlForMyCatSchema.MyCatTable();
                        myCatTable.setDataNode(metaDatabase.getName());
                        myCatTable.setName(definition.getTableName());
                        tables.add(myCatTable);
                    }
                }
            }
            return myCatSchema.toXml();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "出错了";
    }

}
