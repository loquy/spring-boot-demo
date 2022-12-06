package com.example.codegenerator.param;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Template path param.
 *
 * @author loquy
 */
@Data
public class TemplatePathParam {

    private String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    /**
     * 包名
     */
    private String packageName;

    /**
     * 代码生成路径
     */
    private String basePath;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 作者
     */
    private String author;

    /**
     * 实体类生成的绝对路径
     */
    private String entityPath;

    /**
     * dao生成的绝对路径
     */
    private String daoPath;

    /**
     * param生成的绝对路径
     */
    private String paramPath;

    /**
     * service接口生成的绝对路径
     */
    private String servicePath;

    /**
     * service实现类生成的绝对路径
     */
    private String serviceImplPath;

    /**
     * controller生成的绝对路径
     */
    private String controllerPath;

    /**
     * @param packageName 包名
     * @param basePath    生成代码的基础路径
     * @param projectName 项目名称
     * @param author      作者
     */
    public TemplatePathParam(String packageName, String basePath, String projectName, String author) {
        if (StringUtils.isBlank(packageName)
                || StringUtils.isBlank(basePath)
                || StringUtils.isBlank(author)) {
            throw new RuntimeException("参数不能为空");
        }
        this.packageName = packageName;
        this.basePath = basePath;
        this.author = author;
        this.projectName = projectName;

        String[] split = packageName.split("\\.");

        StringBuilder javaModelPath;
        if (StringUtils.isBlank(projectName)) {
            javaModelPath = new StringBuilder(basePath + "\\src\\main\\java\\");
        } else {
            javaModelPath = new StringBuilder(basePath + "\\" + projectName + "\\src\\main\\java\\");
        }

        for (String s : split) {
            javaModelPath.append(s);
            javaModelPath.append("\\");
        }
        this.setEntityPath(javaModelPath + "\\entity");
        this.setDaoPath(javaModelPath + "\\dao");
        this.setParamPath(javaModelPath + "\\param");
        this.setServicePath(javaModelPath + "\\service");
        this.setServiceImplPath(javaModelPath + "\\service\\impl");
        this.setControllerPath(javaModelPath + "\\controller");
    }

}
