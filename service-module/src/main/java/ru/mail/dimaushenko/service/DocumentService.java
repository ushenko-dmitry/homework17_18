package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;

public interface DocumentService {

    DocumentDTO addDocument(AddDocumentDTO addDocumentDTO);

    List<DocumentDTO> getAllDocuments();
    
    List<DocumentDTO> getPackDocuments(Integer currentPage, Integer documentsPerPage);

    DocumentDTO getDocumentById(Long documentId);
    
    Integer getAmountDocuments();

    boolean updateDocument(DocumentDTO documentDTO);

    boolean removeDocument(DocumentDTO documentDTO);

}
