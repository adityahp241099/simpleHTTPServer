package simpleHTTPServer.response;

import simpleHTTPServer.request.Request;

public class NotAllowedResponse extends StringResponse {
    public NotAllowedResponse(Request request){
        super(request,"The method you requested for is not allowed.");
        code = 405;
        message = "Method not allowed";
    }
}