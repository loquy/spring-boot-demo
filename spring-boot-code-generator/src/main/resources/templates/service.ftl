package ${package_name}.service;

import com.example.codegenerator.common.Page;
import com.example.codegenerator.common.ResultModel;
import ${package_name}.entity.${table_name};

/**
* <#if table_annotation??>${table_annotation}</#if>Service
* @author ${author}
* @date ${date}
*/
public interface ${table_name}Service {

    /**
    * 查询所有
    *
    * @param page the page
    * @return the list
    */
    ResultModel<Page<${table_name}>> list(Page<${table_name}> page);

    /**
    * 查询一个
    *
    * @param id the id
    * @return the by id
    */
    ResultModel<${table_name}> getById(Object id);

    /**
    * 新增
    *
    * @param ${table_name_small} the ${table_name_small}
    * @return the boolean
    */
    ResultModel<Object> save(${table_name} ${table_name_small});

    /**
    * 修改
    *
    * @param ${table_name_small} the ${table_name_small}
    * @return the boolean
    */
    ResultModel<Object> updateById(${table_name} ${table_name_small});

    /**
    * 删除
    *
    * @param id the id
    * @return the boolean
    */
    ResultModel<Object> removeById(Object id);
}
