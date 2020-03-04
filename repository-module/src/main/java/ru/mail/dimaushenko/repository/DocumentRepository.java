package ru.mail.dimaushenko.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import ru.mail.dimaushenko.repository.model.Document;

public interface DocumentRepository extends GeneralRepository<Document> {

    Document getDocumentById(Connection connection, Long id) throws SQLException;

    List<Document> getDocuments(Connection connection, Integer currentPage, Integer documentsPerPage) throws SQLException;

}
