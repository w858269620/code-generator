package cn.iba8.module.generator.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class JsonToJsonUtil {

    public static String jsonMapping(String originJson, String propMapper) {
        Map<String, Object> jsonToMap = Json2Map.jsonToMap(originJson);
        Map<String, String> mappings = Properties2Map.converter(propMapper);
        Map<String, Object> target = new HashMap<>();
        jsonToMap.keySet().forEach(r -> {
            if (r.contains("[") && r.contains("]")) {
                String[] nodes = r.split("\\.");
                List<Integer> indexes = new ArrayList<>();
                for (int i = 0; i < nodes.length; i++) {
                    String node = nodes[i].trim();
                    if (node.contains("[") && node.contains("]")) {
                        int i1 = node.lastIndexOf("[");
                        int index = Integer.valueOf(node.substring(i1 + 1, node.length() - 1));
                        nodes[i] = node.substring(0, i1) + "[i]";
                        indexes.add(index);
                    }
                }
                String k = StringUtils.join(nodes, ".");
                String v = mappings.get(k);
                if (StringUtils.isNotBlank(v)) {
                    String[] targetNodes = v.split("\\.");
                    int j = 0;
                    for (int i = 0; i < targetNodes.length; i++) {
                        String node = targetNodes[i].trim();
                        if (node.contains("[") && node.contains("]")) {
                            int i1 = node.lastIndexOf("[");
                            targetNodes[i] = node.substring(0, i1) + "[" + indexes.get(j) + "]";
                            j++;
                        }
                    }
                    String targetK = StringUtils.join(targetNodes, ".");
                    target.put(targetK, jsonToMap.get(r));
                } else {
                    log.warn("data is not mapped. key={}", r);
                }
            } else {
                String v = mappings.get(r);
                if (StringUtils.isNotBlank(v)) {
                    target.put(v, jsonToMap.get(r));
                } else {
                    log.warn("data is not mapped. key={}", r);
                }
            }
        });
        String prop = Map2Properties.convert(target);
        return Properties2Json.convert(prop);
    }

    public static void main(String[] args) throws Exception {
        InputStream originJsonIs = new FileInputStream(new File("D:\\tmp\\test\\origin.json"));
        InputStream propIs = new FileInputStream(new File("D:\\tmp\\test\\propMapper.properties"));
        byte[] originJsonBytes = new byte[originJsonIs.available()];
        originJsonIs.read(originJsonBytes);
        byte[] propBytes = new byte[propIs.available()];
        propIs.read(propBytes);
        String originJson = new String(originJsonBytes);
        String propMapper = new String(propBytes);
        String s = jsonMapping(originJson, propMapper);
        System.out.println(s);
    }

}
