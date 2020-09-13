package simpleHTTPServer.exceptions;

import simpleHTTPServer.response.BadRequestResponse;

public class BadRequest extends HTTPException{
    public BadRequest(String message) {
        super(message,400);
        defaultResponse = BadRequestResponse.class;
    }
}
