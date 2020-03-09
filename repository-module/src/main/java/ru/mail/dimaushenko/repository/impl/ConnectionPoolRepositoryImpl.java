package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import ru.mail.dimaushenko.repository.ConnectionPoolRepository;

@Repository
public class ConnectionPoolRepositoryImpl implements ConnectionPoolRepository {

    private final DataSource dataSource;

    public ConnectionPoolRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
