package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ReadingActivityNotFoundException extends Exception implements IsSerializable {

    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public ReadingActivityNotFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ReadingActivityNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
