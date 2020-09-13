package simpleHTTPServer.exceptions;

import simpleHTTPServer.response.NotAllowedResponse;


public class NotAllowed extends HTTPException{
    public NotAllowed(String message) {
        super(message, 405);
        defaultResponse = NotAllowedResponse.class;
    }
}
