package cn.iba8.module.generator.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class ValidatorUtil {

    private static final  Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Map<String, StringBuffer> validate(T obj) {
        Map<String, StringBuffer> errorMap = null;
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && set.size() > 0) {
            errorMap = new HashMap<>();
            String property;
            for (ConstraintViolation<T> cv : set) {
                property = cv.getPropertyPath().toString();
                if (errorMap.get(property) != null) {
                    errorMap.get(property).append("," + cv.getMessage());
                } else {
                    StringBuffer sb = new StringBuffer();
                    sb.append(cv.getMessage());
                    errorMap.put(property, sb);
                }
            }
        }
        return errorMap;
    }

    public static <T> StringBuffer validate2Msg(T obj) {

        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        StringBuffer sb = new StringBuffer();
        if (set != null && set.size() > 0) {
            for (ConstraintViolation<T> cv : set) {
                sb.append(cv.getMessage());
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            sb.append("\r\n");
        }
        return sb;
    }

}
