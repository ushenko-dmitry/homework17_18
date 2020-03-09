package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPoolRepository {

    Connection getConnection() throws SQLException;

}
