package simpleHTTPServer;

import simpleHTTPServer.request.Request;
import simpleHTTPServer.response.Response;

public interface View{
    public Response call(Request request);
}