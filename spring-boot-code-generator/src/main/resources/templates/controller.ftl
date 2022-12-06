package ${package_name}.controller;

import com.example.codegenerator.common.Page;
import com.example.codegenerator.common.ResultModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import ${package_name}.entity.${table_name};
import ${package_name}.param.${table_name}Param;
import ${package_name}.service.${table_name}Service;

/**
* <#if table_annotation??>${table_annotation}</#if>Controller
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("/${table_name_small}")
public class ${table_name}Controller {

    private final ${table_name}Service ${table_name_small}Service;

    public ${table_name}Controller(${table_name}Service ${table_name_small}Service) {
        this.${table_name_small}Service = ${table_name_small}Service;
    }

    /**
    * 查询所有
    */
    @GetMapping("/list")
    public ResultModel<Page<${table_name}>> list(@RequestParam(value = "currentPage") Integer currentPage,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        try {
            Page<${table_name}> page = new Page<>(currentPage, pageSize);
            return ${table_name_small}Service.list(page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail(e.getMessage());
        }
    }

    /**
    * 查询一个
    */
    @GetMapping("read/{id}")
    public ResultModel<${table_name}> get(@PathVariable Long id) {
        try {
            return ${table_name_small}Service.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail(e.getMessage());
        }
    }

    /**
    * 新增
    */
    @PostMapping("/create")
    public ResultModel<Object> save(@Validated @RequestBody ${table_name}Param ${table_name_small}Param) {
        try {
            ${table_name} ${table_name_small} = new ${table_name}();
            BeanUtils.copyProperties(${table_name_small}Param, ${table_name_small});
            return ${table_name_small}Service.save(${table_name_small});
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail(e.getMessage());
        }
    }

    /**
    * 修改
    */
    @PostMapping("/update")
    public ResultModel<Object> update(@Validated @RequestBody ${table_name}Param ${table_name_small}Param) {
        try {
            ${table_name} ${table_name_small} = new ${table_name}();
            BeanUtils.copyProperties(${table_name_small}Param, ${table_name_small});
            return ${table_name_small}Service.updateById(${table_name_small});
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail(e.getMessage());
        }
    }

    /**
    * 删除
    */
    @PostMapping("/delete/{id}")
    public ResultModel<Object> delete(@PathVariable Long id) {
        try {
            return ${table_name_small}Service.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail(e.getMessage());
        }
    }
}
