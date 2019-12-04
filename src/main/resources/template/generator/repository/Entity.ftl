package ${packagePrefix}.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Entity
@Data
@Table(name="${tableName}")
public class ${clazzName} implements Serializable {

<#if columns??>
    <#list columns as column>
        <#if column.columnComment?? && column.columnComment != ''>
    /** ${column.columnComment} */
        </#if>
        <#if column.primaryKey = true>
    @Id
            <#if idStrategy??>
    @GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = ${data.idStrategyClazz})
            <#else >
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            </#if>
        </#if>
    @Column(name = "${column.columnName}"<#if column.length &gt; 0>, length = ${column.length}</#if>)
    private ${column.javaType} ${column.javaName};
    </#list>
</#if>

}