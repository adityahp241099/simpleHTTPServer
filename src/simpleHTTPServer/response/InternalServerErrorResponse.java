package simpleHTTPServer.response;

import simpleHTTPServer.request.Request;

public class InternalServerErrorResponse extends StringResponse{
    public InternalServerErrorResponse(Request request){
        super(request,"Internal Server Error");
        code = 500;
        message = "Internal Server Error";
    }
}
