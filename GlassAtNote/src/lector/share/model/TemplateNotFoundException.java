package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TemplateNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public TemplateNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TemplateNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
