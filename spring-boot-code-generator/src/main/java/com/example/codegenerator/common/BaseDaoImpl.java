package com.example.codegenerator.common;

import cn.hutool.core.bean.BeanUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author loquy
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public BaseDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public void insert(Object model) {
        this.entityManager.persist(model);
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(Object model) {
        this.entityManager.remove(model);
    }

    @Override
    public T findOne(Object primaryKey) {
        return entityManager.find(entityClass, primaryKey);
    }

    @Override
    @SuppressWarnings("unchecked")
    public  List<T> findAll() {
        return this.entityManager.createQuery("select obj from " + entityClass.getName() + " obj").getResultList();
    }

    /**
     * 获取查询列表
     *
     * @return 返回的是list<Entity>对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public  List<T> getNativeQueryList(final String queryStr) {
        Query query = createNativeQuery(queryStr);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<T> tmpResult = query.getResultList();
        List<T> result = new ArrayList<>();
        for (Object object : tmpResult) {
            result.add(BeanUtil.toBean(object, entityClass));
        }
        return result;
    }

    /**
     * 获取查询分页列表
     *
     * @return 返回的是Page<list<Entity>> 对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public  Page<T> getNativeQueryListByPage(String queryStr, Page<T> page) {
        queryStr += page.getOrderString();

        Query query = createNativeQuery(queryStr);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        int totalCount = countSqlResult(queryStr).intValue();
        page.setTotalCount(totalCount);
        if (totalCount == 0) {
            return page;
        }
        setPageParameter(query, page);
        List<T> tmpResult = query.getResultList();
        List<T> result = new ArrayList<>();
        for (Object object : tmpResult) {
            result.add(BeanUtil.toBean(object, entityClass));
        }
        page.setResult(result);
        return page;
    }

    /**
     * 统计条数
     *
     */
    protected Long countSqlResult(final String sql) {
        String countHql = "select count(*) from (" + sql + ") count";
        try {
            String valueStr = createNativeQuery(countHql).getSingleResult().toString();
            return new BigDecimal(valueStr.trim()).longValue();
        } catch (NoResultException e) {
            return 0L;
        } catch (Exception e) {
            throw new RuntimeException("sql can't be auto count, sql is:" + countHql, e);
        }
    }


    private Query createNativeQuery(final String queryString, Object... params) {
        Assert.hasText(queryString, "queryString can not empty");
        Query query;
        query = entityManager.createNativeQuery(queryString);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i + 1, params[i]);
            }
        }
        return query;
    }

    /**
     * 设置分页参数
     *
     */
    protected  void setPageParameter(final Query q, final Page<T> page) {
        q.setFirstResult(page.getOffset());
        q.setMaxResults(page.getLimit());
    }
}
