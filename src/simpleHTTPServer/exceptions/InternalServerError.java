package simpleHTTPServer.exceptions;

import simpleHTTPServer.response.InternalServerErrorResponse;

public class InternalServerError extends HTTPException{
    public InternalServerError(String message){
        super(message,500);
        defaultResponse = InternalServerErrorResponse.class;
    }
}