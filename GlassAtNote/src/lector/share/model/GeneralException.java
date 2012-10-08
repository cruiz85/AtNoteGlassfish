package lector.share.model;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class GeneralException extends Exception implements Serializable, IsSerializable{
   

    public GeneralException(String errorMessage) {
        super(errorMessage);
    }

    public GeneralException() {
    }

    public GeneralException(String message, StackTraceElement[] stackTraces){
    	super(message);
    	setStackTrace(stackTraces);
    }

   
}
