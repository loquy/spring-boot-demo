package com.example.crossdatabase;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.example.crossdatabase.utils.CrossDatabaseUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class SpringBootCrossdatabaseApplicationTests {

    @Test
    void contextLoads() {
        try {
            Db testDb = CrossDatabaseUtils.initDb("test");
            Db testDb1 = CrossDatabaseUtils.initDb("test_1");
            System.out.println("============test=============");
            crud(testDb);
            System.out.println("============test_1=============");
            crud(testDb1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crud(Db db) throws SQLException {
        // 删除所有
        int deleteAll = db.execute("delete from user");
        // 新增
        int insert = db.execute("insert into user values (?, ?, ?)", 1, "张三", 123123);
        int insert1 = db.execute("insert into user values (?, ?, ?)", 2, "李四", 123123);
        // 更新
        int update = db.execute("update user set pwd = ? where name = ?", 123456, "张三");
        // 删除
        int delete = db.execute("delete from user where name = ?", "张三");
        // 查询
        List<Entity> query = db.query("select * from user");
        System.out.println(deleteAll);
        System.out.println(insert);
        System.out.println(insert1);
        System.out.println(update);
        System.out.println(delete);
        System.out.println(query);
    }
}
