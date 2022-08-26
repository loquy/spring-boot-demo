package com.example.crossdatabase.utils;

import cn.hutool.db.Db;
import cn.hutool.db.ds.DSFactory;

import javax.sql.DataSource;
import java.util.HashMap;


/**
 * @author loquy
 */
public class CrossDatabaseUtils {

    private static final HashMap<String, Db> DBS = new HashMap<>();

    /**
     * 初始化数据源
     *
     */
    public static Db initDb(String dataBase) {
        Db db;
        if (DBS.containsKey(dataBase)) {
            db = DBS.get(dataBase);
        } else {
            DataSource ds = DSFactory.get(dataBase);
            db = Db.use(ds);
            DBS.put(dataBase,db);
        }
        return db;
    }
}
