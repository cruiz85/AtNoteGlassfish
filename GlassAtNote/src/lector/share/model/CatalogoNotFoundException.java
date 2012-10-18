package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CatalogoNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public CatalogoNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CatalogoNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
