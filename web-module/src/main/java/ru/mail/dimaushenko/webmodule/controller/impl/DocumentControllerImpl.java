package ru.mail.dimaushenko.webmodule.controller.impl;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mail.dimaushenko.webmodule.controller.DocumentController;

@Controller
public class DocumentControllerImpl implements DocumentController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    @GetMapping("/")
    public String getDocuments() {
        return "documents";
    }
    
    @GetMapping("/document")
    public String getDocument() {
        return "view_document";
    }
    
    @GetMapping("/add_document")
    public String addDocument() {
        return "add_document";
    }
    
    @PostMapping("/add_document")
    public String addDocument_() {
        return "redirect:/";
    }
    
    @PostMapping("/remove_document")
    public String removeDocument() {
        return "";
    }
}
