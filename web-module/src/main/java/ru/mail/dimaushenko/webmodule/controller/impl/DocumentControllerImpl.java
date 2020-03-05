package ru.mail.dimaushenko.webmodule.controller.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mail.dimaushenko.repository.model.Document;
import ru.mail.dimaushenko.service.DocumentService;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;
import ru.mail.dimaushenko.webmodule.controller.DocumentController;

@Controller
public class DocumentControllerImpl implements DocumentController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    private static final String DEFAULT_CURRENT_PAGE = "1";
    private static final String DEFAULT_DOCUMENTS_PER_PAGE = "5";
    
    private static final String REQUEST_PARAMETER_CURRENT_PAGE = "currentPage";
    private static final String REQUEST_PARAMETER_DOCUMENTS_PER_PAGE = "documentsPerPage";
    private static final String REQUEST_PARAMETER_DOCUMENTS = "documents";
    
    private final DocumentService documentService;

    public DocumentControllerImpl(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String getDocuments(
            @RequestParam(name = REQUEST_PARAMETER_CURRENT_PAGE, defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPage,
            @RequestParam(name = REQUEST_PARAMETER_DOCUMENTS_PER_PAGE, defaultValue = DEFAULT_DOCUMENTS_PER_PAGE) Integer documentsPerPage,
            Model model
    ) {
        List<DocumentDTO> documentDTOs = documentService.getPackDocuments(currentPage, documentsPerPage);

        model.addAttribute(REQUEST_PARAMETER_DOCUMENTS, documentDTOs);

        Integer amountDocuments = documentService.getAmountDocuments();
        Integer amountPages = amountDocuments / documentsPerPage;
        if (amountPages % documentsPerPage > 0) {
            amountPages++;
        }
        if (amountPages == 0) {
            amountPages++;
        }
        model.addAttribute("amountPages", amountPages);
        model.addAttribute(REQUEST_PARAMETER_CURRENT_PAGE, currentPage);
        model.addAttribute(REQUEST_PARAMETER_DOCUMENTS_PER_PAGE, documentsPerPage);

        return "documents";
    }

    @PostMapping("/document")
    public String getDocument(@RequestParam(name = "id") Long id,
            Model model
    ) {
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        model.addAttribute("document", documentDTO);
        return "view_document";
    }

    @GetMapping("/document/add")
    public String addDocument(
            @ModelAttribute(name = "document") AddDocumentDTO addDocumentDTO
    ) {
        return "add_document";
    }

    @PostMapping("/document/add")
    public String addDocument(
            @Valid @ModelAttribute(name = "document") AddDocumentDTO addDocumentDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "add_document";
        }
        documentService.addDocument(addDocumentDTO);
        return "redirect:/";
    }

    @PostMapping("/document/remove")
    public String removeDocument() {
        return "remove_document";
    }
//
//    @GetMapping("/document/update")
//    public String updateDocument(
//            @RequestParam(name = "id") Long id,
//            @ModelAttribute(name = "document") Document document,
//            BindingResult bindingResult
//    ) {
//        return "redirect:/";
//    }

    @PostMapping("/document/edit")
    public String updateDocument(@RequestParam(name = "id") Long id,
            @RequestParam(name = "update", defaultValue = "") String isUpdate,
            Model model
    ) {
        logger.info(isUpdate);
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        model.addAttribute("document", documentDTO);
        return "update_document";
    }
}
