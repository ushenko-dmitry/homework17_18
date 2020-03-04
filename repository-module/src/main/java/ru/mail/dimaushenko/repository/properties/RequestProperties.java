package ru.mail.dimaushenko.repository.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestProperties {

    @Value("${sql.request.insert.document}")
    private String sqlRequestInsertDocument;

    @Value("${sql.request.select.document.all}")
    private String sqlRequestSelectAllDocuments;

    @Value("${sql.request.select.document.by_id}")
    private String sqlRequestSelectDocumentById;

    @Value("${sql.request.update.document}")
    private String sqlRequestUpdateDocument;

    @Value("${sql.request.delete.document}")
    private String sqlRequestDeleteDocument;

    public String getSqlRequestInsertDocument() {
        return sqlRequestInsertDocument;
    }

    public String getSqlRequestSelectAllDocuments() {
        return sqlRequestSelectAllDocuments;
    }

    public String getSqlRequestSelectDocumentById() {
        return sqlRequestSelectDocumentById;
    }

    public String getSqlRequestUpdateDocument() {
        return sqlRequestUpdateDocument;
    }

    public String getSqlRequestDeleteDocument() {
        return sqlRequestDeleteDocument;
    }

}
