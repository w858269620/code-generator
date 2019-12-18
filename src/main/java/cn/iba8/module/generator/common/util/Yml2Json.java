package cn.iba8.module.generator.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;

public abstract class Yml2Json {

    public static String converter(String yml) {
        Properties p = new Properties();
        InputStream is = null;
        Reader reader = null;
        try {
            is = new ByteArrayInputStream(yml.getBytes());
            reader = new InputStreamReader(is);
            Gson gs = new GsonBuilder().disableHtmlEscaping().create();
            Yaml yaml = new Yaml();
            Map<String, Object> loaded = yaml.load(reader);
            String s = gs.toJson(loaded);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
