package lector.share.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GeneralException extends Exception{
   

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
