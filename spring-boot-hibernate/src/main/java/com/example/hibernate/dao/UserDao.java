package com.example.hibernate.dao;

import com.example.hibernate.dao.common.BaseDaoImpl;
import com.example.hibernate.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author loquy
 */
@Repository
public class UserDao extends BaseDaoImpl<User> {
    public UserDao() {
        super(User.class);
    }
}
