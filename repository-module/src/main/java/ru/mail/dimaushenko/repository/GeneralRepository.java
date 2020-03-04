package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GeneralRepository<T> {

    T addEntity(Connection connection, T t) throws SQLException;

    List<T> getAllEntities(Connection connection) throws SQLException;

    void updateEntity(Connection connection, T t) throws SQLException;

    void removeEntity(Connection connection, T t) throws SQLException;

}
