package ru.mail.dimaushenko.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.dimaushenko.repository.ConnectionPoolRepository;
import ru.mail.dimaushenko.repository.DocumentRepository;
import ru.mail.dimaushenko.repository.model.Document;
import ru.mail.dimaushenko.service.DocumentConvertService;
import ru.mail.dimaushenko.service.DocumentService;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionPoolRepository connectionPoolRepository;
    private final DocumentRepository documentRepository;
    private final DocumentConvertService documentConvertService;

    public DocumentServiceImpl(
            ConnectionPoolRepository connectionPoolRepository,
            DocumentRepository documentRepository,
            DocumentConvertService documentConvertService
    ) {
        this.connectionPoolRepository = connectionPoolRepository;
        this.documentRepository = documentRepository;
        this.documentConvertService = documentConvertService;
    }

    @Override
    public DocumentDTO addDocument(AddDocumentDTO addDocumentDTO) {
        try (Connection connection = connectionPoolRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = documentConvertService.getObjectFromAddDTO(addDocumentDTO);
                document = documentRepository.addEntity(connection, document);
                DocumentDTO documentDTO = documentConvertService.getDTOFromObject(document);
                connection.commit();
                return documentDTO;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public List<DocumentDTO> getAllDocument() {
        try (Connection connection = connectionPoolRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Document> documents = documentRepository.getAllEntities(connection);
                List<DocumentDTO> documentsDTO = documentConvertService.getDTOFromObject(documents);
                connection.commit();
                return documentsDTO;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return Collections.emptyList();
    }

    @Override
    public DocumentDTO getDocumentById(Long documentId) {
        try (Connection connection = connectionPoolRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = documentRepository.getElementById(connection, documentId);
                DocumentDTO documentDTO = documentConvertService.getDTOFromObject(document);
                connection.commit();
                return documentDTO;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public boolean updateDocument(DocumentDTO documentDTO) {
        try (Connection connection = connectionPoolRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = documentConvertService.getObjectFromDTO(documentDTO);
                documentRepository.updateEntity(connection, document);
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

    @Override
    public boolean removeDocument(DocumentDTO documentDTO) {
        try (Connection connection = connectionPoolRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = documentConvertService.getObjectFromDTO(documentDTO);
                documentRepository.removeEntity(connection, document);
                connection.commit();
                return true;
            } catch (SQLException ex) {
                connection.rollback();
                logger.error(ex.getMessage(), ex);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return false;
    }

}
