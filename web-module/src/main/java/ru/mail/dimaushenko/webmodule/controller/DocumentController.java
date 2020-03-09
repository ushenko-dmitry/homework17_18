package ru.mail.dimaushenko.webmodule.controller;

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
import ru.mail.dimaushenko.service.DocumentService;
import ru.mail.dimaushenko.service.model.AddDocumentDTO;
import ru.mail.dimaushenko.service.model.DocumentDTO;
import static ru.mail.dimaushenko.webmodule.controller.constants.RequestParemeter.REQUEST_PARAMETER_CURRENT_PAGE;
import static ru.mail.dimaushenko.webmodule.controller.constants.RequestParemeter.REQUEST_PARAMETER_DOCUMENTS_PER_PAGE;
import static ru.mail.dimaushenko.webmodule.controller.constants.URL.URL_ADD_DOCUMENT;
import static ru.mail.dimaushenko.webmodule.controller.constants.ViewPages.VIEW_PAGE_ADD_DOCUMENT;
import static ru.mail.dimaushenko.webmodule.controller.constants.ViewPages.VIEW_PAGE_UPDATE_DOCUMENT;

@Controller
public class DocumentController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/")
    public String getDocuments(
            @RequestParam(name = REQUEST_PARAMETER_CURRENT_PAGE, defaultValue = "1") Integer currentPage,
            @RequestParam(name = REQUEST_PARAMETER_DOCUMENTS_PER_PAGE, defaultValue = "5") Integer documentsPerPage,
            Model model
    ) {
        List<DocumentDTO> documentDTOs = documentService.getPackDocuments(currentPage, documentsPerPage);
        model.addAttribute("documents", documentDTOs);

        Integer amountDocuments = documentService.getAmountDocuments();
        Integer amountPages = amountDocuments / documentsPerPage;
        if (amountDocuments % documentsPerPage > 0) {
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

    @GetMapping(URL_ADD_DOCUMENT)
    public String addDocument(
            @ModelAttribute(name = "document") AddDocumentDTO addDocumentDTO
    ) {
        return VIEW_PAGE_ADD_DOCUMENT;
    }

    @PostMapping(URL_ADD_DOCUMENT)
    public String addDocument(
            @Valid @ModelAttribute(name = "document") AddDocumentDTO addDocumentDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return VIEW_PAGE_ADD_DOCUMENT;
        }
        documentService.addDocument(addDocumentDTO);
        return "redirect:/";
    }

    @PostMapping("/document/edit")
    public String updateDocument(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "update", defaultValue = "") String isUpdate,
            @Valid @ModelAttribute(name = "document") DocumentDTO updatedDocument,
            BindingResult bindingResult,
            Model model
    ) {
        if (isUpdate.isBlank()) {
            DocumentDTO documentDTO = documentService.getDocumentById(id);
            model.addAttribute("document", documentDTO);
        } else {
            if (bindingResult.hasErrors()) {
                return VIEW_PAGE_UPDATE_DOCUMENT;
            }
            boolean isDocumentUpdate = documentService.updateDocument(updatedDocument);
            model.addAttribute("isUpdated", isDocumentUpdate);
        }
        return VIEW_PAGE_UPDATE_DOCUMENT;
    }

    @PostMapping("/document/remove")
    public String removeDocument(
            @RequestParam(name = "id") Long id,
            Model model
    ) {
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        boolean isDocumentRemoved = documentService.removeDocument(documentDTO);
        model.addAttribute("isRemoved", isDocumentRemoved);
        return "redirect:/";
    }

}
