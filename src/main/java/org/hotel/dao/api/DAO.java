package org.hotel.dao.api;

import org.hotel.entity.Entity;
import org.hotel.exception.DAOException;

import java.util.List;
import java.util.Optional;

/**
 * Basic interface of all DAO.
 * It describes basic data operations with an instances of a class, which extends {@link Entity}
 *
 * @param <T> generic
 */
public interface DAO<T extends Entity>{

    Optional<T> getById(Integer id) throws DAOException;

    List<T> getAll(int lim1, int lim2) throws DAOException;

    /**
     * Saves an entity to the storage, if there is no entry with such id or id is null.
     * If the entry with such id already exists, then the information of this object is updated.
     *
     * @param entity an instance of a class, which implements {@link Entity}
     * @throws DAOException if the object couldn't be saved to the dao.
     */
    void save(T entity) throws DAOException;

    void deleteById(Integer id) throws DAOException;

}
