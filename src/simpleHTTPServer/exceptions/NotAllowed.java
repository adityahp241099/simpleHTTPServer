package simpleHTTPServer.exceptions;

public class NotAllowed extends Exception{
    private static final long serialVersionUID = 589048716764482719L;
    public NotAllowed(String message) {
        System.out.println(message);
    }
}
