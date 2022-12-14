package com.example.codegenerator.utils;

import com.example.codegenerator.constant.TemplateConstant;
import com.example.codegenerator.param.ColumnParam;
import com.example.codegenerator.param.TableParam;
import com.example.codegenerator.param.TemplatePathParam;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器入口
 * @author loquy
 */
public class CodeGenerateUtils {

    private TemplatePathParam templatePathParam = null;

    public static void main(String[] args) throws Exception {
        CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
        codeGenerateUtils.templatePathParam = new TemplatePathParam("com.example.codegenerator.modules.test",
                "D:\\study\\spring-boot-demo\\spring-boot-code-generator",
                "",
                "loquy");
        codeGenerateUtils.generate("user");
    }

    public void generate(String tableName) throws Exception {
        //数据库相关
        DbUtils dbUtil = new DbUtils();
        List<TableParam> allTables = dbUtil.getAllTables(tableName);
        if (allTables.get(0).getTableName() == null) {
            //获取所有表
            List<TableParam> allTable = dbUtil.getAllTables("");
            for (TableParam tableClass : allTable) {
                generateOne(dbUtil, tableClass);
            }
        } else {
            generateOne(dbUtil, allTables.get(0));
        }

        dbUtil.closeConnection();
    }

    public void generateOne(DbUtils dbUtil, TableParam tableClass) throws Exception {
        //表名
        String table = tableClass.getTableName();
        //获取所有列
        List<ColumnParam> allColumns = dbUtil.getAllColumns(table);

        boolean date = allColumns.stream().anyMatch(m -> "Date".equals(m.getJavaType()));
        boolean bigDecimal = allColumns.stream().anyMatch(m -> "BigDecimal".equals(m.getJavaType()));
        ColumnParam columnParam = allColumns.stream().filter(m -> m.getColumnName().equals(m.getPrimaryKey())).findFirst().orElse(null);

        tableClass.setExitDate(date);
        tableClass.setExitBigDecimal(bigDecimal);
        tableClass.setPrimaryKeyType("String");
        if (columnParam != null) {
            tableClass.setPrimaryKeyType(columnParam.getJavaType());
        }

        System.out.println("============正在生成 " + table + " 表相关文件============");

        //生成实体类
        System.out.println("生成 entity 类");
        generateEntityFile(tableClass, allColumns);

        //生成dao层文件
        System.out.println("生成 dao 类");
        generateDaoFile(tableClass);

        //生成param参数类
        System.out.println("生成 param 类");
        generateParamFile(tableClass, allColumns);

        //生成service接口
        System.out.println("生成 service 接口");
        generateServiceFile(tableClass);

        //生成service实现类
        System.out.println("生成 service 实现类");
        generateServiceImplFile(tableClass);

        //生成Controller层文件
        System.out.println("生成 controller 类");
        generateControllerFile(tableClass);

        System.out.println("============ 全部生成完成！ =============");
    }


    /**
     * 生成实体文件
     */
    private void generateEntityFile(TableParam tableClass, List<ColumnParam> allColumns) throws Exception {
        String suffix = ".java";
        String filePath = templatePathParam.getEntityPath();
        String file = templatePathParam.getEntityPath() + "\\" + StrUtils.changeTableStr(tableClass.getTableName()) + suffix;

        Map<String, Object> dataMap = new HashMap<>(6);
        dataMap.put("model_column", allColumns);
        dataMap = getCommonModel(dataMap, tableClass);
        generateFileByTemplate(TemplateConstant.ENTITY_TEMPLATE, filePath, file, dataMap);
    }

    /**
     * 生成dao层文件
     */
    private void generateDaoFile(TableParam tableClass) throws Exception {
        String suffix = "Dao.java";
        String filePath = templatePathParam.getDaoPath();
        String file = templatePathParam.getDaoPath() + "\\" + StrUtils.changeTableStr(tableClass.getTableName()) + suffix;

        Map<String, Object> dataMap = new HashMap<>(6);
        dataMap = getCommonModel(dataMap, tableClass);
        generateFileByTemplate(TemplateConstant.DAO_TEMPLATE, filePath, file, dataMap);
    }

    /**
     * 生成参数类
     */
    private void generateParamFile(TableParam tableClass, List<ColumnParam> allColumns) throws Exception {
        String suffix = "Param.java";
        String filePath = templatePathParam.getParamPath();
        String file = templatePathParam.getParamPath() + "\\" + StrUtils.changeTableStr(tableClass.getTableName()) + suffix;

        Map<String, Object> dataMap = new HashMap<>(6);
        dataMap.put("model_column", allColumns);
        dataMap = getCommonModel(dataMap, tableClass);
        generateFileByTemplate(TemplateConstant.PARAM_TEMPLATE, filePath, file, dataMap);
    }

    /**
     * 生成业务接口层
     */
    private void generateServiceFile(TableParam tableClass) throws Exception {
        String suffix = "Service.java";
        String filePath = templatePathParam.getServicePath();
        String file = templatePathParam.getServicePath() + "\\" + StrUtils.changeTableStr(tableClass.getTableName()) + suffix;

        Map<String, Object> dataMap = new HashMap<>(6);
        dataMap = getCommonModel(dataMap, tableClass);
        generateFileByTemplate(TemplateConstant.SERVICE_TEMPLATE, filePath, file, dataMap);
    }

    /**
     * 生成业务实现层
     */
    private void generateServiceImplFile(TableParam tableClass) throws Exception {
        String suffix = "ServiceImpl.java";
        String filePath = templatePathParam.getServiceImplPath();
        String file = templatePathParam.getServiceImplPath() + "\\" + StrUtils.changeTableStr(tableClass.getTableName()) + suffix;

        Map<String, Object> dataMap = new HashMap<>(6);
        dataMap = getCommonModel(dataMap, tableClass);
        generateFileByTemplate(TemplateConstant.SERVICE_IMPL_TEMPLATE, filePath, file, dataMap);
    }

    /**
     * 生成控制层
     */
    private void generateControllerFile(TableParam tableClass) throws Exception {
        String suffix = "Controller.java";
        String filePath = templatePathParam.getControllerPath();
        String file = templatePathParam.getControllerPath() + "\\" + StrUtils.changeTableStr(tableClass.getTableName()) + suffix;

        Map<String, Object> dataMap = new HashMap<>(6);
        dataMap = getCommonModel(dataMap, tableClass);
        generateFileByTemplate(TemplateConstant.CONTROLLER_TEMPLATE, filePath, file, dataMap);
    }

    /**
     * 模版通用参数
     *
     * @param dataMap    模型map
     * @param tableClass 表名和表注释参数
     */
    public Map<String, Object> getCommonModel(Map<String, Object> dataMap, TableParam tableClass) {
        dataMap.put("table_name", StrUtils.changeTableStr(tableClass.getTableName()));
        dataMap.put("table_name_small", StrUtils.changeColumnStr(tableClass.getTableName()));
        dataMap.put("table", tableClass.getTableName());
        dataMap.put("datePattern", tableClass.getDatePattern());
        dataMap.put("exitDate", tableClass.isExitDate());
        dataMap.put("exitBigDecimal", tableClass.isExitBigDecimal());
        dataMap.put("primaryKeyType", tableClass.getPrimaryKeyType());
        dataMap.put("author", templatePathParam.getAuthor());
        dataMap.put("date", templatePathParam.getCurrentDate());
        dataMap.put("package_name", templatePathParam.getPackageName());
        dataMap.put("project_name", templatePathParam.getProjectName());
        dataMap.put("table_annotation", StringUtils.isNotBlank(tableClass.getTableComment()) ? tableClass.getTableComment() : null);
        return dataMap;
    }

    /**
     * 静态化方法
     *
     * @param templateName  模版名称
     * @param filePathParam 文件所在目录 绝对路径
     * @param fileParam     文件 绝对路径
     * @param dataMap       数据模型
     */
    private void generateFileByTemplate(final String templateName,
                                        String filePathParam,
                                        String fileParam,
                                        Map<String, Object> dataMap) throws Exception {
        Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
        System.out.println(fileParam);
        //文件夹不存在创建文件夹
        File filePath = new File(filePathParam);
        if (!filePath.exists() && !filePath.isDirectory()) {
            boolean mkdirs = filePath.mkdirs();
            if (!mkdirs) {
                System.out.println(filePathParam + "创建失败！");
            }
        }
        //文件不存在创建文件夹
        File file = new File(fileParam);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    System.out.println(fileParam + "创建失败！");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8), 10240);
        template.process(dataMap, out);
    }

}
