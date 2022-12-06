package ${package_name}.param;

import java.io.Serializable;
<#if exitBigDecimal>
    import java.math.BigDecimal;
</#if>
<#if exitDate>
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
</#if>

/**
* <#if table_annotation??>${table_annotation}</#if>Param
* @author ${author}
* @date ${date}
*/
public class ${table_name}Param implements Serializable {
<#if model_column?exists>
    <#list model_column as model>
    /**
    * ${model.columnComment!}
    */
    <#if model.javaType == 'Date'>
    @JsonFormat(pattern = "${datePattern}", timezone = "GMT+8")
    @DateTimeFormat(pattern = "${datePattern}")
    </#if>
    private ${model.javaType} ${model.changeColumnName?uncap_first};

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
