package cn.iba8.module.generator.common.jsontojava.tosingleclass;

import cn.iba8.module.generator.common.jsontojava.converter.builder.JavaClassBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

@Data
public class ToSingleJsonMeta implements Serializable {

    String packageName;
    ToSingleJsonClassBuilder rootBean;
    List<ToSingleJsonClassBuilder> innerBean = new ArrayList<>();
    Set<String> imports = new HashSet<>();

    public static ToSingleJsonMeta of(Map<String, ToSingleJsonClassBuilder> map, String rootBeanName, String packageName) {
        ToSingleJsonMeta target = new ToSingleJsonMeta();
        ToSingleJsonClassBuilder rootBean = map.get(rootBeanName);
        List<ToSingleJsonClassBuilder> innerBean = new ArrayList<>();
        Set<String> imports = new HashSet<>();
        map.keySet().forEach(r -> {
            ToSingleJsonClassBuilder toSingleJsonClassBuilder = map.get(r);
            if (!rootBeanName.equals(r)) {
                innerBean.add(toSingleJsonClassBuilder);
            }
            imports.addAll(toSingleJsonClassBuilder.getImportedClasses());
        });
        target.setImports(imports);
        target.setPackageName(packageName);
        target.setInnerBean(innerBean);
        target.setRootBean(rootBean);
        return target;
    }

    public String getBeanText() {
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(packageName).append(JavaClassBuilder.END_STATEMENT)
                .append(JavaClassBuilder.DOUBLE_NEW_LINE);
        imports.forEach(s -> sb.append("import " + s + JavaClassBuilder.END_STATEMENT).append(JavaClassBuilder.NEW_LINE));
        sb.append(JavaClassBuilder.NEW_LINE);

        appendRootBeanPre(sb);

        appendOfMethod(sb);

        if (!CollectionUtils.isEmpty(innerBean)) {
            innerBean.forEach(r -> {
                appendInnerBean(sb, r);
            });
        }
        appendRootBeanSuf(sb);

        return sb.toString();
    }

    public void appendOfMethod(StringBuffer sb) {
        sb.append(JavaClassBuilder.BIG_SPACE + "public static " + rootBean.getClassName() + " of(String json) " + JavaClassBuilder.BLOCK_OPEN + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + "try" + JavaClassBuilder.BLOCK_OPEN + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + JavaClassBuilder.BIG_SPACE + "ObjectMapper om = new ObjectMapper();" + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + JavaClassBuilder.BIG_SPACE + "return om.readValue(json, " + rootBean.getClassName() + ".class);" + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + JavaClassBuilder.BLOCK_CLOSED + " catch (Exception e) " + JavaClassBuilder.BLOCK_OPEN + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + JavaClassBuilder.BIG_SPACE + "log.error(\"json data parse error. data {}, e{}\", json, e);" + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + JavaClassBuilder.BLOCK_CLOSED + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + "return null;" + JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.BIG_SPACE + JavaClassBuilder.BLOCK_CLOSED);
        sb.append(JavaClassBuilder.DOUBLE_NEW_LINE);
    }

    private void appendInnerBean(StringBuffer sb, ToSingleJsonClassBuilder innerBean) {
        sb.append(JavaClassBuilder.BIG_SPACE + "@Data");
        sb.append(JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.BIG_SPACE + "public static class " + innerBean.getClassName() + " implements Serializable " + JavaClassBuilder.BLOCK_OPEN);
        sb.append(JavaClassBuilder.DOUBLE_NEW_LINE);
        if (!CollectionUtils.isEmpty(innerBean.getProperties())) {
            innerBean.getProperties().forEach(r -> {
                if (StringUtils.isNotBlank(r.getJsonAnnotation())) {
                    sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + r.getJsonAnnotation());
                }
                sb.append(JavaClassBuilder.DOUBLE_BIG_SPACE + r.getFieldStatement());
                sb.append(JavaClassBuilder.NEW_LINE);
            });
        }
        sb.append(JavaClassBuilder.BIG_SPACE + JavaClassBuilder.BLOCK_CLOSED);
        sb.append(JavaClassBuilder.DOUBLE_NEW_LINE);
    }

    private void appendRootBeanPre(StringBuffer sb) {
        sb.append("@Data" + JavaClassBuilder.NEW_LINE);
        sb.append("@Slf4j" + JavaClassBuilder.NEW_LINE);
        sb.append("public class " + rootBean.getClassName() + " implements Serializable " + JavaClassBuilder.BLOCK_OPEN);
        sb.append(JavaClassBuilder.DOUBLE_NEW_LINE);
        if (!CollectionUtils.isEmpty(rootBean.getProperties())) {
            rootBean.getProperties().forEach(r -> {
                if (StringUtils.isNotBlank(r.getJsonAnnotation())) {
                    sb.append(JavaClassBuilder.BIG_SPACE + r.getJsonAnnotation());
                }
                sb.append(JavaClassBuilder.BIG_SPACE + r.getFieldStatement());
                sb.append(JavaClassBuilder.NEW_LINE);
            });
        }
    }

    private void appendRootBeanSuf(StringBuffer sb) {
        sb.append(JavaClassBuilder.NEW_LINE);
        sb.append(JavaClassBuilder.BLOCK_CLOSED);
    }

}
