package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

@SuppressWarnings("serial")
public class AnnotationThreadNotFoundException extends Exception implements Serializable,IsSerializable{
    private String errorMessage;

    public AnnotationThreadNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AnnotationThreadNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
