package simpleHTTPServer.response;

import simpleHTTPServer.request.Request;

public class NotFoundResponse extends StringResponse {
    public NotFoundResponse(Request request){
        super(request,"The resource you are looking for is not available");
        code = 404;
        message = "Not Found";
    }
}