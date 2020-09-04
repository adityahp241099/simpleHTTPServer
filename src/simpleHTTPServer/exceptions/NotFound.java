package simpleHTTPServer.exceptions;

import simpleHTTPServer.response.NotFoundResponse;

public class NotFound extends HTTPException {
    public NotFound(String message) {
        super(message, 404);
        defaultResponse = NotFoundResponse.class;
    }
}
