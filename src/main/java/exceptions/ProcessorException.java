package exceptions;

public class ProcessorException extends Exception {

    public ProcessorException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}
