package com.example.hibernate.dao.common;


import java.util.List;

/**
 * The interface Base dao.
 *
 * @author loquy
 */
public interface BaseDao<T> {

    /**
     * Insert.
     *
     * @param model the model
     */
    void insert(Object model);

    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     */
    T update(T entity);

    /**
     * Delete.
     *
     * @param model the model
     */
    void delete(Object model);

    /**
     * Find one t.
     *

     * @param primaryKey  the primary key
     * @return the t
     */
    T findOne(Object primaryKey);

    /**
     * Find all list.
     *

     * @return the list
     */
    List<T> findAll();

    /**
     * 获取查询列表
     *
     * @param queryStr    the query str

     * @return 返回的是list<Entity> 对象
     */
    List<T> getNativeQueryList(final String queryStr);

    /**
     * 获取查询分页列表
     *
     * @param queryStr    the query str
     * @param page        the page

     * @return 返回的是Page<list<Entity>> 对象
     */
    Page<T> getNativeQueryListByPage(String queryStr, Page<T> page);

}
