package cn.iba8.module.generator.common.ftl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "mycat")
public class XmlForMyCatSchema implements Serializable {

    @XmlElement(name = "schema")
    private MyCatSchema schema;

    @XmlElement(name = "dataNode")
    private List<MyCatDataNode>  dataNodes;

    @XmlElement(name = "dataHost")
    private List<MyCatDataHost>  dataHosts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlRootElement(name = "schema")
    public static class MyCatSchema implements Serializable {

        @XmlAttribute
        private String name;

        @XmlAttribute
        private String checkSQLschema = "true";

        @XmlAttribute
        private String sqlMaxLimit = "100";

        @XmlAttribute
        private String dataNode;

        @XmlElement(name = "table")
        private List<MyCatTable>  tables;


    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlRootElement(name = "table")
    public static class MyCatTable implements Serializable {

        @XmlAttribute
        private String name;

        @XmlAttribute
        private String dataNode;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlRootElement(name = "dataNode")
    public static class MyCatDataNode implements Serializable {

        @XmlAttribute
        private String name;

        @XmlAttribute
        private String dataHost;

        @XmlAttribute
        private String database;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @XmlAccessorType(XmlAccessType.NONE)
    @XmlRootElement(name = "dataHost")
    public static class MyCatDataHost implements Serializable {

        @XmlAttribute
        private String name;

        @XmlAttribute
        private String writeType = "0";

        @XmlAttribute
        private String switchType="1";

        @XmlAttribute
        private String slaveThreshold="100";

        @XmlAttribute
        private String balance="1";

        @XmlAttribute
        private String dbType="mysql";

        @XmlAttribute
        private String minCon="1";

        @XmlAttribute
        private String maxCon="1000";

        @XmlAttribute
        private String dbDriver="native";

        @XmlElement(name = "heartbeat")
        private String heartbeat = "select user()";

        @XmlElement(name = "writeHost")
        private MyCatDataHostWriteHost writeHost;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @XmlAccessorType(XmlAccessType.NONE)
        @XmlRootElement(name = "writeHost")
        public static class MyCatDataHostWriteHost implements Serializable {

            @XmlAttribute
            private String host="hostM1";

            @XmlAttribute
            private String url = "localhost";
            @XmlAttribute
            private String user="root";

            @XmlAttribute
            private String password = "123456";

        }

    }

    public String toXml() throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(XmlForMyCatSchema.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);//格式化输出
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");//编码格式
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);//去掉默认报文头
        StringWriter writer = new StringWriter();
        marshaller.marshal(this, writer);
        return writer.toString();
    }

}
