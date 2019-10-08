package cn.iba8.module.generator.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author wansc
 * @Title: ReflectUtil
 * @ProjectName ChiticBankV3.5
 * @Description:
 * @date 2018/10/3013:28
 */
public abstract class ReflectUtil {

    public static Object getFieldValue(Field field, Object object) throws Exception {
        Method m = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
        return m.invoke(object);
    }

    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
