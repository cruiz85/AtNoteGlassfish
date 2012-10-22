package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RelationNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public RelationNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RelationNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
