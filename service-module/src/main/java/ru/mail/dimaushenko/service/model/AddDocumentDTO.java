package ru.mail.dimaushenko.service.model;

import javax.validation.constraints.Size;

public class AddDocumentDTO {

    private String uniqueNumber;
    
    @Size (max = 100, message = "is required")
    private String description;

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
