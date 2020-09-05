package simpleHTTPServer.exceptions;

public class ResponseDispatchException extends  Exception{
    public ResponseDispatchException(String message){
        System.out.println(message);
    }
}

