package ${package_name}.service.impl;

import com.example.codegenerator.common.Page;
import com.example.codegenerator.common.ResultModel;
import ${package_name}.entity.${table_name};
import ${package_name}.service.${table_name}Service;
import ${package_name}.dao.${table_name}Dao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* <#if table_annotation??>${table_annotation}</#if>ServiceImpl
* @author ${author}
* @date ${date}
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ${table_name}ServiceImpl implements ${table_name}Service{

    public final ${table_name}Dao ${table_name_small}Dao;

    public ${table_name}ServiceImpl(${table_name}Dao ${table_name_small}Dao) {
        this.${table_name_small}Dao = ${table_name_small}Dao;
    }

    @Override
    public ResultModel<Page<${table_name}>> list(Page<${table_name}> page) {
        String sql = "select * from ${table_name_small}";
        return ResultModel.success(${table_name_small}Dao.getNativeQueryListByPage(sql, page));
    }

    @Override
    public ResultModel<${table_name}> getById(Object id) {
        return ResultModel.success(${table_name_small}Dao.findOne(id));
    }

    @Override
    public ResultModel<Object> save(${table_name} ${table_name_small}) {
        ${table_name} ${table_name_small}Old = ${table_name_small}Dao.findOne(${table_name_small}.getId());
        if (${table_name_small}Old == null) {
            ${table_name_small}Dao.insert(${table_name_small});
            return ResultModel.success("保存成功！");
        }
        return ResultModel.fail("已存在数据，保存失败！");
    }

    @Override
    public ResultModel<Object> updateById(${table_name} ${table_name_small}) {
        ${table_name} ${table_name_small}Old = ${table_name_small}Dao.findOne(${table_name_small}.getId());
        if (${table_name_small}Old != null) {
            ${table_name_small}Dao.update(${table_name_small});
            return ResultModel.success();
        }
        return ResultModel.fail("更新失败！");
    }

    @Override
    public ResultModel<Object> removeById(Object id) {
        ${table_name} ${table_name_small} = ${table_name_small}Dao.findOne(id);
        if (${table_name_small} != null) {
            ${table_name_small}Dao.delete(${table_name_small});
            return ResultModel.success();
        }
        return ResultModel.fail("删除失败！");
    }
}
