package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TemplateCategoryNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public TemplateCategoryNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TemplateCategoryNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
