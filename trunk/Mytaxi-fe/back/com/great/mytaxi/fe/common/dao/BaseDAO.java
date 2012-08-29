package com.great.mytaxi.fe.common.dao;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import com.great.mytaxi.fe.common.Page;
import com.great.mytaxi.fe.common.util.StringUtils;

@Repository
public class BaseDAO<T> extends JpaDaoSupport
{
    private Class<T> entityClass;
    
    private String entityClassName;
    
    
    @SuppressWarnings("unchecked")
	public BaseDAO()
    {
        if(getClass().getGenericSuperclass() instanceof ParameterizedType)
        {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            entityClassName = entityClass.getSimpleName();
        }
    }
    
    /**
     * 释放查询管理器
     * @param em
     */
    protected void releaseManager(EntityManager em)
    {
        if(null != em)
        {
            em.clear();
            em.close();
        }
    }

    /**
     * 根据ID查询实体
     */
    public T findById(Integer id)
    {
        T entity = (T) getJpaTemplate().find(entityClass, id);
        return entity;
    }
    

    public <E> E findById(Class<E> cls,Integer id)
    {        
        return (E)getJpaTemplate().find(cls, id);
    }
    
    /**
     * 插入实体
     */
    public void save(T t)
    {
        getJpaTemplate().persist(t);
        
    }
    
    /**
     * 更新实体
     */
    public void update(Object obj)
    {   
        getJpaTemplate().merge(obj);
    }
    
    /**
     * 删除实体
     */
    public void delete(Object obj)
    {        
        getJpaTemplate().remove(obj);
    }
    
    @SuppressWarnings("unchecked")
    public List<T> queryByProperty(String propertyName, Object value)
    {
        String queryString = "select model from " + entityClassName + " as model where model." + propertyName + "= ?";
        return getJpaTemplate().find(queryString, value);
    }
    
    
    /**
     * 根据属性查询，自定义表
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> queryByProperty(Class<E> cls,String propertyName, Object value)
    {
        String queryString = "select model from " + cls.getSimpleName() + " as model where model." + propertyName + "= ?";
        return getJpaTemplate().find(queryString, value);
    }
    
    /**
     * 条件查询
     * @param modelName
     * @param model
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <E> List<E> queryList(final String hql,final Object...args)
    {        
        return (List<E>)getJpaTemplate().execute(
              new JpaCallback()
              {
                  public Object doInJpa(EntityManager em) throws PersistenceException 
                  {
                      Query query = em.createQuery(hql);
                      if(null != args && args.length != 0){
                          for(int i=1;i<=args.length;i++){                              
                              query.setParameter(i,args[i-1]);
                          }
                      }
                      List<E> list = query.getResultList();
                      //System.out.println(list.size()+"[[[[[]]]]]]]]");
                      return list;
                  }
              }                    
         );        
    }
    
    /**
     * 条件查询，含limit
     * @param modelName
     * @param model
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <E> List<E> queryList(final String hql,final Integer start,final Integer rows,final Object...args)
    {        
        return (List<E>)getJpaTemplate().execute(
              new JpaCallback()
              {
                  public Object doInJpa(EntityManager em) throws PersistenceException 
                  {
                      Query query = em.createQuery(hql);
                      if(null != args && args.length != 0){
                          for(int i=1;i<=args.length;i++){                              
                              query.setParameter(i,args[i-1]);
                          }
                      }
                      query.setFirstResult(start);
                      query.setMaxResults(rows);
                      List<E> list = query.getResultList();
                      return list;
                  }
              }                    
         );        
    }
    
    /**
     * 条件查询
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected <E> Page<E> queryPageList(final Page<E> page,final String hql, final String countHql,final Object...args)
    {      
      return (Page)getJpaTemplate().execute(
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {

                     Query query = null;
                     Query queryCount = null;
                     query = em.createQuery(hql);
                     queryCount = em.createQuery(countHql);
                     //System.out.println(args.length);
                     if(null != args && args.length != 0){
                         for(int i=1;i<=args.length;i++){
                             query.setParameter(i,args[i-1]);
                             queryCount.setParameter(i,args[i-1]);
                         }
                     }
                     page.setCount((Long)queryCount.getSingleResult());//这句必须先写
                     query.setFirstResult((page.getTargetPage()-1) * page.getPageSize());
                     query.setMaxResults(page.getPageSize());
                     page.setResultList(query.getResultList());
                     return page;
                 }
             }                    
        );
    }    
    
    
    
    /**
     * 根据属性查询单个实体
     * 不可模糊匹配
     */
    public T findByProperty(String propertyName, Object value)
    {
        List<T> list = queryByProperty(propertyName, value);
        if(list.isEmpty())
        {
            return null;
        }
        else
        {
            return list.get(0);
        }
    }
    
    /**
     * 根据属性查询单个实体，自定义表
     * 不可模糊匹配
     */
    public <E> E findByProperty(Class<E> cls,String propertyName, Object value)
    {
        List<E> list = queryByProperty(cls,propertyName, value);
        if(list.isEmpty())
        {
            return null;
        }
        else
        {
            return list.get(0);
        }
    }
    
    /**
     * 删除一条记录
     * @param id
     * @return int 影响行数
     */
    public int deleteById(final Integer id)
    {
        int rowcount = 0;
        rowcount = (Integer) getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createQuery("DELETE FROM "+ entityClassName+" WHERE id ="+id);
                     return query.executeUpdate();
                 }
             }
         );         
        return rowcount;
    }
    
    /**
     * 删除多条记录
     * @param ids
     * @return
     */
    public int deleteByIds(final Integer[] ids)
    {
        int rowcount = 0;
        rowcount = (Integer) getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createQuery("DELETE FROM "+ entityClassName+" WHERE id in("+StringUtils.combIntString(ids)+")");
                     return query.executeUpdate();
                 }
             }
         );         
        return rowcount;
    }
    
    
    /**
     * 删除多条记录，可自定义表
     */
    @SuppressWarnings("rawtypes")
	public int deleteByIds(final Class cls,final Integer[] ids)
    {
        int rowcount = 0;
        rowcount = (Integer) getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createQuery("DELETE FROM "+ cls.getSimpleName()+" WHERE id in("+StringUtils.combIntString(ids)+")");
                     return query.executeUpdate();
                 }
             }
         );         
        return rowcount;
    }
    
    /**
     * 删除记录
     */
    protected int deleteByParam(final String hql,final Object...args)
    {
        int rowcount = 0;
        rowcount = (Integer) getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createQuery(hql);
                     if(null != args && args.length != 0){
                         for(int i=1;i<=args.length;i++){
                             query.setParameter(i,args[i-1]);
                         }
                     }
                     return query.executeUpdate();
                 }
             }
         );         
        return rowcount;
    }
    
    public Object excuteNativeQuery(final String sql){
        return getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createNativeQuery(sql);
                     return query.getSingleResult();                     
                 }
             }
         );         
    }
    
    public Object excuteNativeForQueryList(final String sql){
        return getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createNativeQuery(sql);
                     return query.getResultList();                     
                 }
             }
         );         
    }
    
    /**
     * 删除
     * @return
     */
    public int delete(final String sql) {
        return (Integer) getJpaTemplate().execute
        (
             new JpaCallback()
             {
                 public Object doInJpa(EntityManager em) throws PersistenceException 
                 {
                     Query query = em.createNativeQuery(sql);
                     return query.executeUpdate();                     
                 }
             }
         ); 
    }
}
