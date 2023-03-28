package org.hotel.entity.builder;

import org.hotel.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *<P>An interface, which describes creating objects from {@link ResultSet}
 *</P>
 * Those objects must be an instances of a class, which implements {@link Entity}
 *
 * @param <T> a class, which implements {@link Entity}
 * @see ResultSet
 * @see Entity
 */
public interface Builder <T extends Entity> {

    /**
     * Creates and returns an instance of T from {@link ResultSet}.
     *
     * @param resultSet an instance of {@link ResultSet}, containing information about T
     * @return an instance of a class, which implements {@link Entity}
     * @throws SQLException if exception was thrown in any {@link ResultSet} method
     */
    T build(ResultSet resultSet) throws SQLException;

}
