package ${package_name}.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
<#if exitBigDecimal>
import java.math.BigDecimal;
</#if>
<#if exitDate>
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
</#if>
import java.io.Serializable;

/**
* <#if table_annotation??>${table_annotation}</#if>Entity
* @author ${author}
* @date ${date}
*/
@Entity
@Table(name = "${table}")
public class ${table_name} implements Serializable {
<#if model_column?exists>
    <#list model_column as model>
    /**
    * ${model.columnComment!}
    */
    <#if (model.primaryKey! = model.columnName)>
    @Id
    @Column(name = "${model.columnName?uncap_first}")
    private ${model.javaType} ${model.changeColumnName?uncap_first};

    <#else>
    @Column(name = "${model.columnName?uncap_first}")
    <#if model.javaType == 'Date'>
    @JsonFormat(pattern = "${datePattern}", timezone = "GMT+8")
    @DateTimeFormat(pattern = "${datePattern}")
    </#if>
    private ${model.javaType} ${model.changeColumnName?uncap_first};

    </#if>
    </#list>
</#if>
<#if model_column?exists>
    <#list model_column as model>
    public ${model.javaType} get${model.changeColumnName?cap_first }() {
        return ${model.changeColumnName};
    }

    public void set${model.changeColumnName?cap_first }(${model.javaType} ${model.changeColumnName?uncap_first }) {
        this.${model.changeColumnName} = ${model.changeColumnName};
    }

    </#list>
</#if>
}
