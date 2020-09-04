package simpleHTTPServer.exceptions;

public class BadRequest extends Exception{
    public BadRequest(String message) {
        System.out.println(message);
    }
}
