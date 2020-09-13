package simpleHTTPServer.response;

import simpleHTTPServer.request.Request;

import java.net.Socket;

public class BadRequestResponse extends StringResponse {
    public BadRequestResponse(Request request){
        super(request,"The request made was malformed or was found corrupt while receiving.");
        code = 400;
        message = "Bad Request";
    }
    public BadRequestResponse(Socket socket){
        super(socket,"The request made was malformed or was found corrupt while receiving.");
        code = 400;
        message = "Bad Request";
    }
}
