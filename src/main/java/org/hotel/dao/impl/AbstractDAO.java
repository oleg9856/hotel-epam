package org.hotel.dao.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.dao.DAOHelper;
import org.hotel.dao.api.DAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.Entity;
import org.hotel.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class AbstractDAO<T extends Entity> implements DAO<T> {


    private static final String FIND_BY_ID_QUERY = "SELECT * FROM %s WHERE id=?;";
    private static final String GET_ALL_QUERY = """
            SELECT * FROM %s LIMIT ?, ?
            """;
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM %s WHERE id = ?;";

    private final Builder<T> builder;
    private final Connection connection;

    private static DAOHelper daoHelper;

    protected AbstractDAO(Builder<T> builder, Connection connection) {
        this.builder = builder;
        this.connection = connection;
    }

    @Override
    public Optional<T> getById(Integer id) throws DAOException {
        String tableName = getTableName();
        String query = String.format(FIND_BY_ID_QUERY, tableName);
        String idParameter = id.toString();
        return executeForSingleResult(query, idParameter);
    }

    @Override
    public List<T> getAll(int lim1, int lim2) throws DAOException {
        String tableName = getTableName();
        String query = String.format(GET_ALL_QUERY, tableName);
        return executeQuery(query, lim1, lim2);
    }

    @Override
    public void deleteById(Integer id) throws DAOException {
        String tableName = getTableName();
        String query = String.format(DELETE_BY_ID_QUERY, tableName);
        executeUpdate(query, id);
    }

    protected abstract String getTableName();

    protected List<T> executeQuery(String query, Object... parameters) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, parameters);
            ResultSet resultSet = statement.executeQuery();
            List<T> entities = new ArrayList<>();
            while (resultSet.next()) {
                T entity = builder.build(resultSet);
                entities.add(entity);
            }
            return entities;
        } catch (SQLException e) {
            log.error("Execute query is failed --> {}", e.getMessage());
            throw new DAOException(e);
        }
    }

    protected Optional<T> executeForSingleResult(String query, Object... parameters) throws DAOException {
        List<T> items = executeQuery(query, parameters);
        return (items.size() == 1) ?
                Optional.of(items.get(0)) : Optional.empty();
    }

    protected void executeUpdate(String query, Object... parameters) throws DAOException {
        daoHelper = new DAOHelper(connection);
        daoHelper.startTransaction();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, parameters);
            statement.executeUpdate();
            daoHelper.endTransaction();
        } catch (SQLException e) {
            log.error("Execute update is failed --> {}", e.getMessage());
            daoHelper.cancelTransaction();
            throw new DAOException(e);
        }
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws DAOException {
        for (int i = 0; i < parameters.length; i++) {
            int parameterIndex = i + 1;
            try {
                if (parameters[i] != null) {
                    statement.setObject(parameterIndex, parameters[i]);
                } else {
                    statement.setNull(parameterIndex, Types.NULL);
                }
            } catch (SQLException e) {
                log.error("Unable to set parameters --> {}", e.getMessage());
                throw new DAOException(e.getMessage(), e);
            }
        }
    }

}
