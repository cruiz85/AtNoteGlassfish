package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TwinBrotherException extends Exception implements Serializable{
    private String errorMessage;

    public TwinBrotherException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public TwinBrotherException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
