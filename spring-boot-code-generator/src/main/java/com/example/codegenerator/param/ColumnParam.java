package com.example.codegenerator.param;

import lombok.Data;

/**
 * @author loquy
 */
@Data
public class ColumnParam {

    /**
     * 数据库字段名称
     */
    private String columnName;

    /**
     * 数据库字段类型
     */
    private String columnType;

    /**
     * 数据库字段首字母小写且去掉下划线字符串
     */
    private String changeColumnName;

    /**
     * 数据库字段注释
     */
    private String columnComment;

    /**
     * 主键
     */
    private String primaryKey;

    /**
     * java 类型
     */
    private String javaType;
}
