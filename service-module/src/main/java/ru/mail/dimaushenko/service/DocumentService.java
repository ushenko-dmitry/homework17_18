package ru.mail.dimaushenko.service;

import java.util.List;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;

public interface DocumentService {

    DocumentDTO addDocument(AddDocumentDTO addDocumentDTO);

    List<DocumentDTO> getAllDocument();

    DocumentDTO getDocumentById(Long documentId);

    boolean updateDocument(DocumentDTO documentDTO);

    boolean removeDocument(DocumentDTO documentDTO);

}
