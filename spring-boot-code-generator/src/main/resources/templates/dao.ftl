package ${package_name}.dao;

import com.example.codegenerator.common.BaseDaoImpl;
import ${package_name}.entity.${table_name};
import org.springframework.stereotype.Repository;

/**
* <#if table_annotation??>${table_annotation}</#if>Dao
* @author ${author}
* @date ${date}
*/
@Repository
public class ${table_name}Dao extends BaseDaoImpl<${table_name}> {
    public ${table_name}Dao() {
        super(${table_name}.class);
    }
}
