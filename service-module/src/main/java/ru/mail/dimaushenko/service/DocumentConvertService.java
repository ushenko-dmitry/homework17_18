package ru.mail.dimaushenko.service;

import ru.mail.dimaushenko.repository.model.Document;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;

public interface DocumentConvertService extends ConvertService<DocumentDTO, Document> {

    Document getObjectFromAddDTO(AddDocumentDTO addDocumentDTO);

}
