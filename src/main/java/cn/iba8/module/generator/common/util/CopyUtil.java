package cn.iba8.module.generator.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtil {

    public static <T> T copy(Object source, Class<T> c) {
        if (source == null) {
            return null;
        }
        try {
            T instance = c.newInstance();
            BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <E, T> List<T> copyList(List<E> sources, Class<T> c) {
        if (sources.isEmpty()) {
            return new ArrayList<T>();
        }
        List<T> list = new ArrayList<T>();
        for (E source : sources) {
            list.add(copy(source, c));
        }
        return list;
    }

}
