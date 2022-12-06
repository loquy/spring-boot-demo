package com.example.codegenerator.param;

import lombok.Data;

/**
 * @author loquy
 */
@Data
public class TableParam {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 日期时间格式化模式
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 是否存在日期
     */
    private boolean exitDate;

    /**
     * 是否存在浮点型
     */
    private boolean exitBigDecimal;
}
