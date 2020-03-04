package ru.mail.dimaushenko.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import ru.mail.dimaushenko.repository.DocumentRepository;
import ru.mail.dimaushenko.repository.model.Document;
import ru.mail.dimaushenko.repository.properties.RequestProperties;

@Repository
public class DocumentRepositoryImpl extends GeneralRepositoryImpl<Document> implements DocumentRepository {

    private static final String id = "id";
    private static final String uniqueNumber = "unique_number";
    private static final String description = "description";

    private final RequestProperties requestProperties;

    public DocumentRepositoryImpl(
            RequestProperties requestProperties
    ) {
        this.requestProperties = requestProperties;
    }

    @Override
    public Document addEntity(Connection connection, Document document) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestInsertDocument())) {
            preparedStatement.setString(1, document.getUniqueNumber());
            preparedStatement.setString(2, document.getDescription());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        document.setId((long) resultSet.getInt(1));
                        return document;
                    } else {
                        throw new SQLException("Add `document` in DB was failed, no generated id");
                    }
                }
            } else {
                throw new SQLException("Add `document` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public List<Document> getAllEntities(Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectAllDocuments())) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Document> documents = new ArrayList<>();
                while (resultSet.next()) {
                    Document document = getDocument(resultSet);
                    documents.add(document);
                }
                return documents;
            }
        }
    }

    @Override
    public Document getElementById(Connection connection, Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestSelectDocumentById())) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Document document = null;
                if (resultSet.next()) {
                    document = getDocument(resultSet);
                }
                return document;
            }
        }
    }

    @Override
    public void updateEntity(Connection connection, Document document) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestUpdateDocument())) {
            preparedStatement.setString(1, document.getUniqueNumber());
            preparedStatement.setString(2, document.getDescription());
            preparedStatement.setLong(3, document.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update `document` in DB was failed, no affected rows");
            }
        }
    }

    @Override
    public void removeEntity(Connection connection, Document document) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareCall(requestProperties.getSqlRequestDeleteDocument())) {
            preparedStatement.setLong(1, document.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Remove `document` in DB was failed, no affected rows");
            }
        }
    }

    private Document getDocument(final ResultSet resultSet) throws SQLException {
        Document document = new Document();
        document.setId((long) resultSet.getInt(id));
        document.setUniqueNumber(resultSet.getString(uniqueNumber));
        document.setDescription(resultSet.getString(description));
        return document;
    }
}
