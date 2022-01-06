package ch.bissbert.bissblog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A implementation of the {@link Dao} interface using the hibernate session.
 * <p>
 * it also implements a method to execute a {@link Query} and a method to execute a {@link NamedQuery}
 *
 * @param <T> the type of the entity to be persisted using hibernate
 *            <p>
 *            <b>Note:</b> the type of the entity must be annotated with {@link javax.persistence.Entity}
 *            <p>
 *            <b>Note:</b> the type of the entity must be annotated with {@link javax.persistence.Table}
 *            <p>
 *            <b>Note:</b> the type of the entity must be annotated with {@link javax.persistence.Id}
 * @author Bissbert
 * @version 1.0
 * @see Dao
 * @see javax.persistence.Entity
 * @see javax.persistence.Table
 * @see javax.persistence.Id
 * @see javax.persistence.NamedQuery
 * @see javax.persistence.Query
 * @see <a href="http://www.hibernate.org/">Hibernate</a>
 * @since 1.0
 */
public class GenericDao<T> implements Dao<T> {

    /**
     * the {@link SessionFactory} to be used for the persistence
     */
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    /**
     * the type of the entity to be persisted using hibernate
     */
    private final Class<T> type;

    /**
     * Creates a new instance of the {@link GenericDao} class
     *
     * @param type the type of the entity to be persisted using hibernate
     * @since 1.0.0
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Create a new instance of the entity to be persisted using hibernate session
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    public T create(T entity) throws DaoException {
        //create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            //begin a transaction
            session.beginTransaction();
            //persist the entity
            session.save(entity);
            //commit the transaction
            session.getTransaction().commit();
        }
        return entity;
    }

    /**
     * Read the entity with the given id from the database using hibernate session
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    public T read(Serializable id) throws DaoException {
        // create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            // begin a transaction
            session.beginTransaction();
            // read the entity
            T entity = session.get(type, id);
            // commit the transaction
            session.getTransaction().commit();
            return entity;
        }
    }

    /**
     * Update the entity in the database using hibernate session
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    public T update(T entity) throws DaoException {
        // create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            // begin a transaction
            session.beginTransaction();
            // update the entity
            session.update(entity);
            // commit the transaction
            session.getTransaction().commit();
        }
        return entity;
    }

    /**
     * Delete the entity from the database using hibernate session
     * {@inheritDoc}
     *
     * @since 1.0.0
     */
    @Override
    public void delete(T entity) throws DaoException {
        // create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            // begin a transaction
            session.beginTransaction();
            // delete the entity
            session.delete(entity);
            // commit the transaction
            session.getTransaction().commit();
        }
    }

    /**
     * read all the entities from the database using hibernate session
     * {@inheritDoc}
     *
     * @return the list of entities
     * @throws DaoException if an error occurs
     */
    @Override
    public List<T> readAll() throws DaoException {
        // create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            // begin a transaction
            session.beginTransaction();
            // read all the entities
            List<T> entities = session.createQuery("from " + type.getName()).list();
            // commit the transaction
            session.getTransaction().commit();
            return entities;
        }
    }

    /**
     * Execute a named query in the database using hibernate session
     *
     * @param queryName the name of the query to be executed
     *                  <p>
     *                  <b>Note:</b> the query must be annotated with {@link javax.persistence.NamedQuery}
     * @param parameters the parameters of the query
     * @return the result of the query
     * @throws DaoException if the query cannot be executed
     * @since 1.0.0
     * @see javax.persistence.NamedQuery
     * @see javax.persistence.Query
     * @see javax.persistence.EntityManager#createNamedQuery(String)
     * @see javax.persistence.EntityManager#createNamedQuery(String, Class)
     */
    public List<T> executeNamedQuery(String queryName, Map<String, Object> parameters) throws DaoException {
        // create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            // begin a transaction
            session.beginTransaction();
            // create the query
            Query query = session.createNamedQuery(queryName);
            // set the parameters
            setParameters(query, parameters);
            // execute the query
            List<T> result = query.getResultList();
            // commit the transaction
            session.getTransaction().commit();
            return result;
        }
    }

    /**
     * Execute a Query in the database using hibernate session
     *
     * @param query the query to be executed
     * @param parameters the parameters of the query
     * @return the result of the query
     * @throws DaoException if the query cannot be executed
     * @since 1.0.0
     * @see javax.persistence.Query
     * @see javax.persistence.EntityManager#createQuery(String)
     * @see javax.persistence.EntityManager#createQuery(String, Class)
     */
    public List<T> executeQuery(String query, Map<String, Object> parameters) throws DaoException {
        // create a session from the session factory
        try (Session session = sessionFactory.openSession()) {
            // begin a transaction
            session.beginTransaction();
            // create the query
            Query queryObject = session.createQuery(query);
            // set the parameters
            setParameters(queryObject, parameters);
            // execute the query
            List<T> result = queryObject.getResultList();
            // commit the transaction
            session.getTransaction().commit();
            return result;
        }
    }

    /**
     * method to fill a query with parameters
     *
     * @param query the query to be filled
     * @param parameters the parameters of the query
     * @since 1.0.0
     */
    protected void setParameters(Query query, Map<String, Object> parameters) {
        // set the parameters
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }
}
