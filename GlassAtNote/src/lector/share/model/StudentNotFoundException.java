package lector.share.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;

@SuppressWarnings("serial")
public class StudentNotFoundException extends Exception implements Serializable,IsSerializable{
    private String errorMessage;

    public StudentNotFoundException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public StudentNotFoundException() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

   
}
