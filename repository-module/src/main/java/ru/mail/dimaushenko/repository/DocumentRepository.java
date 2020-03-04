package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import ru.mail.dimaushenko.repository.model.Document;

public interface DocumentRepository extends GeneralRepository<Document> {

    Document getElementById(Connection connection, Long id) throws SQLException;

}
