package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EntryNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public EntryNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public EntryNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
