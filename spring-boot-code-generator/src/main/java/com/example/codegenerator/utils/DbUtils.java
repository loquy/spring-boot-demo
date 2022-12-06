package com.example.codegenerator.utils;

import cn.hutool.setting.dialect.Props;
import com.example.codegenerator.constant.DbConstant;
import com.example.codegenerator.param.ColumnParam;
import com.example.codegenerator.param.TableParam;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 数据库工具类
 *
 * @author loquy
 */
public class DbUtils {

    private final Props props = new Props("application.properties");

    private final String url = getProps().getProperty("spring.datasource.url");

    private final String username = getProps().getProperty("spring.datasource.username");

    private final String password = getProps().getProperty("spring.datasource.password");

    private final String driver = getProps().getProperty("spring.datasource.driver-class-name");

    private Connection connection = null;

    /**
     * 获取jdbc链接
     */
    public Connection getConnection() throws Exception {
        Properties pro = new Properties();
        pro.setProperty("user", username);
        pro.setProperty("password", password);
        String mysql = "mysql";
        String oracle = "oracle";
        if (url.contains(mysql)) {
            //获取mysql表注释
            pro.setProperty("useInformationSchema", "true");
        } else if (url.contains(oracle)) {
            //获取oracle表注释
            pro.setProperty("remarksReporting", "true");
        }

        Class.forName(driver);
        connection = DriverManager.getConnection(url, pro);
        return connection;
    }

    /**
     * 获取当前数据库下的所有表名称及注释
     */
    public List<TableParam> getAllTables(String table) throws Exception {
        String tableName = StringUtils.isNotEmpty(table) ? table : "%";
        List<TableParam> list = new ArrayList<>();
        //获取链接
        Connection conn = getConnection();
        //获取元数据
        DatabaseMetaData metaData = conn.getMetaData();
        //获取所有的数据库表信息
        ResultSet rs = metaData.getTables(conn.getCatalog(), "%", tableName, new String[]{"TABLE"});
        while (rs.next()) {
            TableParam tableClass = new TableParam();
            tableClass.setTableName(rs.getString(3));
            tableClass.setTableComment(rs.getString(5));
            list.add(tableClass);
        }

        if (list.size() == 0) {
            throw new Exception(tableName + "表不存在！");
        }
        return list;
    }

    /**
     * 获取某张表的所有列
     */
    public List<ColumnParam> getAllColumns(String tableName) throws Exception {
        List<ColumnParam> list = new ArrayList<>();
        //获取链接
        Connection conn = getConnection();
        //获取元数据
        DatabaseMetaData metaData = conn.getMetaData();
        //获取所有的数据库某张表所有列信息
        ResultSet rs = metaData.getColumns(conn.getCatalog(), "%", tableName, "%");
        //获取主键字段
        ResultSet rsPk = metaData.getPrimaryKeys(conn.getCatalog(), null, tableName);
        String primaryKey = "";
        while (rsPk.next()) {
            primaryKey = rsPk.getString("COLUMN_NAME");
        }
        while (rs.next()) {
            ColumnParam columnClass = new ColumnParam();
            columnClass.setPrimaryKey(primaryKey);
            columnClass.setColumnName(rs.getString("COLUMN_NAME"));
            columnClass.setColumnType(rs.getString("TYPE_NAME"));
            columnClass.setJavaType(getJavaType(rs.getString("TYPE_NAME")));
            columnClass.setColumnComment(rs.getString("REMARKS"));
            columnClass.setChangeColumnName(StrUtils.changeColumnStr(rs.getString("COLUMN_NAME")));
            list.add(columnClass);
        }

        if (list.size() == 0) {
            throw new Exception(tableName + "表不存在！");
        }
        return list;
    }

    /**
     * 获取数据库表字段类型对应的java类型
     */
    public String getJavaType(String columnType) {
        columnType = columnType.toLowerCase();
        String dataType = getDbType(columnType);
        if (arraysContains(DbConstant.COLUMNTYPE_TIME, dataType)) {
            // 时间类型
            return DbConstant.TYPE_DATE;
        } else if (arraysContains(DbConstant.COLUMNTYPE_NUMBER, dataType)) {
            String[] str = StringUtils.split(StringUtils.substringBetween(columnType, "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                // 如果是浮点型 统一用BigDecimal
                return DbConstant.TYPE_BIGDECIMAL;
            }else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                // 如果是整形
                return DbConstant.TYPE_INTEGER;
            }else {
                // 长整形
                return DbConstant.TYPE_LONG;
            }
        } else {
            // 字符串
            return DbConstant.TYPE_STRING;
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType)
    {
        if (StringUtils.indexOf(columnType, "(") > 0)
        {
            return StringUtils.substringBefore(columnType, "(");
        }
        else
        {
            return columnType;
        }
    }

    /**
     * 关闭链接
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Props getProps() {
        return props;
    }
}
