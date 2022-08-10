package com.example.hibernate;

import com.example.hibernate.dao.UserDao;
import com.example.hibernate.dao.common.Page;
import com.example.hibernate.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
class SpringBootHibernateApplicationTests {

    @Autowired
    public UserDao userDao;

    @Test
    void contextLoads() {
        User one = userDao.findOne(1);

        System.out.println("========");
        System.out.println(one);
        if (one != null) {
            userDao.delete(one);
        }

        User user = new User(1, "insert", "insert");
        userDao.insert(user);
        List<User> all = userDao.findAll();

        System.out.println("========");
        for (User user1 : all) {
            System.out.println(user1.toString());
        }

        user.setName("update");
        user.setPwd("update");
        User update = userDao.update(user);

        System.out.println("========");
        System.out.println(update);

        List<User> nativeQueryList = userDao.getNativeQueryList("select * from user");
        System.out.println("========");
        System.out.println(nativeQueryList);

        Page<User> page = new Page<>(1, 10);
        page.setSortField("id");
        Page<User> nativeQueryListByPage = userDao.getNativeQueryListByPage("select * from user", page);
        System.out.println("========");
        System.out.println(nativeQueryListByPage.getResult());
        System.out.println(nativeQueryListByPage.getTotalCount());
        System.out.println(nativeQueryListByPage.getTotalPage());
        System.out.println(nativeQueryListByPage.getOrderString());
    }

}
