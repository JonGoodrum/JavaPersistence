package com.grapedrink.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.grapedrink.persistence.entities.AbstractEntity;

public abstract class AbstractDAO {

    private EntityManager em;

    public AbstractDAO(EntityManager entityManager) {
        this.em = entityManager;
    }

    /**
     * Begins a transaction in the underlying EntityManager.
     */
    public void beginTransaction() {
        em.getTransaction().begin();
    }

    /**
     * Commits a transaction in the underlying EntityManager.
     */
    public void commitTransaction() {
        em.getTransaction().commit();
    }

    /**
     * Returns the underlying EntityManager object.
     * 
     * @return EntityManager contained in this AbstractDAO
     */
    public EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Closes the underlying EntityManager.
     */
    public void closeEntityManager() {
        em.clear();
        em.close();
    }

    /**
     * Creates a persisted entity in the database Functionally equivalent to this.update(AbstractEntity entity)
     * 
     * @param entity
     *            entity to create
     */
    protected void create(AbstractEntity entity) {
        this.update(entity);
    }

    /**
     * Deletes an entity from the database
     * 
     * @param entity
     *            entity to delete
     */
    protected void delete(AbstractEntity entity) {
        if (entity != null) {
            em.remove(entity);
        }
    }

    /**
     * Deletes an entity from the database
     * 
     * @param entityClass
     *            class of entity to find
     * @param key
     *            primary key
     */
    protected void delete(Class<? extends AbstractEntity> entityClass, Object key) {
        this.delete(em.find(entityClass, key));
    }

    /**
     * Executes a query on the given table
     * 
     * @param query
     *            SQL Query to run
     * @param queryArgs
     *            positional arguments in the query, specified as :argument (ex: "SELECT s FROM STUDENTS s WHERE NAME = :name")
     * @return List of AbstractEntity objects
     */
    @SuppressWarnings("unchecked")
    protected List<? extends AbstractEntity> executeQuery(String query, Object... queryArgs) {
        Query q = em.createQuery(query);
        for (int i = 0; i < queryArgs.length; ++i) {
            q.setParameter(i, queryArgs[i]);
        }
        return q.getResultList();
    }

    /**
     * Returns the entity with the given primary key
     * 
     * @param entityClass
     *            class of entity to find
     * @param key
     *            primary key
     * @return Entity corresponding to the primary key
     */
    protected AbstractEntity get(Class<? extends AbstractEntity> entityClass, Object key) {
        return em.find(entityClass, key);
    }

    /**
     * Updates a persisted entity in the database. Functionally equivalent to this.create(AbstractEntity entity)
     * 
     * @param entity
     *            entity to update
     */
    protected void update(AbstractEntity entity) {
        em.persist(entity);
    }
}