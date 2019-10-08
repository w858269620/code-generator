package cn.iba8.module.generator.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public abstract class DigitUtil {

    public static BigDecimal toScale(String data, int scale) {
        if (StringUtils.isBlank(data)) {
            data = "0";
        }
        BigDecimal bigDecimal = new BigDecimal(data);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal toScale(BigDecimal data, int scale) {
        if (null == data) {
            data = BigDecimal.ZERO;
        }
        BigDecimal bigDecimal = data;
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal toScale(Double data, int scale) {
        if (null == data) {
            data = 0D;
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(data);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal toScale(Float data, int scale) {
        if (null == data) {
            data = 0F;
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(data);
        return bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

}
