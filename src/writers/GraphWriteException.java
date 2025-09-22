package writers;

public class GraphWriteException extends RuntimeException {
    public GraphWriteException(String message, Throwable cause) {super(message,cause);}
    public GraphWriteException(String message){super(message);}
}
