package readers;

public class GraphReadException extends RuntimeException {
    public GraphReadException(String message, Throwable cause) {
        super(message, cause);
    }
    public GraphReadException(String message){super(message);}
}
