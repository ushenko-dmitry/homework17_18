package ru.mail.dimaushenko.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.mail.dimaushenko.repository.model.Document;
import ru.mail.dimaushenko.service.DocumentConvertService;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;

@Component
public class DocumentConvertServiceImpl implements DocumentConvertService {

    @Override
    public DocumentDTO getDTOFromObject(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUniqueNumber(document.getUniqueNumber());
        documentDTO.setName(document.getName());
        documentDTO.setDescription(document.getDescription());
        return documentDTO;
    }

    @Override
    public List<DocumentDTO> getDTOFromObject(List<Document> documents) {
        List<DocumentDTO> documentsDTO = new ArrayList<>();
        for (Document document : documents) {
            DocumentDTO documentDTO = getDTOFromObject(document);
            documentsDTO.add(documentDTO);
        }
        return documentsDTO;
    }

    @Override
    public Document getObjectFromDTO(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setId(documentDTO.getId());
        document.setUniqueNumber(documentDTO.getUniqueNumber());
        document.setName(documentDTO.getName());
        document.setDescription(documentDTO.getDescription());
        return document;
    }

    @Override
    public List<Document> getObjectFromDTO(List<DocumentDTO> documentsDTO) {
        List<Document> documents = new ArrayList<>();
        for (DocumentDTO documentDTO : documentsDTO) {
            Document document = getObjectFromDTO(documentDTO);
            documents.add(document);
        }
        return documents;
    }

    @Override
    public Document getObjectFromAddDTO(AddDocumentDTO addDocumentDTO) {
        Document document = new Document();
        document.setUniqueNumber(addDocumentDTO.getUniqueNumber());
        document.setName(addDocumentDTO.getName());
        document.setDescription(addDocumentDTO.getDescription());
        return document;
    }

}
