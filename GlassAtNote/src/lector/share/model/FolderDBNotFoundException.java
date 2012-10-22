package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FolderDBNotFoundException extends Exception implements Serializable{
    private String errorMessage;

    public FolderDBNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public FolderDBNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
