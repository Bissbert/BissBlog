package ch.bissbert.bissblog.dao;

import java.io.Serializable;
import java.util.List;

/**
 * A DAO interface to service the persistence of entities.
 *
 * It contains all CRUD operations.
 *
 * @author Bissbert
 * @param <T> The class of the entity to persist.
 * @author Bissbert
 * @since 1.0.0
 * @version 1.0.0
 * @see DaoException
 */
public interface Dao <T> {

    /**
     * Creates a new entity.
     *
     * @param entity The entity to create.
     * @return The created entity.
     * @throws DaoException If an error occurs.
     */
    public T create(T entity) throws DaoException;

    /**
     * Reads an entity.
     *
     * @param id The id of the entity to read.
     * @return The entity.
     * @throws DaoException If an error occurs.
     */
    public T read(Serializable id) throws DaoException;

    /**
     * Updates an entity.
     *
     * @param entity The entity to update.
     * @return The updated entity.
     * @throws DaoException If an error occurs.
     */
    public T update(T entity) throws DaoException;

    /**
     * Deletes an entity.
     *
     * @param entity The entity to delete.
     * @throws DaoException If an error occurs.
     */
    public void delete(T entity) throws DaoException;

    /**
     * Reads all entities.
     *
     * @return The entities.
     * @throws DaoException If an error occurs.
     */
    public List<T> readAll() throws DaoException;

    /**
     * Exception thrown by the DAO.
     *
     * @author Bissbert
     * @since 1.0.0
     * @version 1.0.0
     * @see Dao
     */
    class DaoException extends RuntimeException {
    }
}
