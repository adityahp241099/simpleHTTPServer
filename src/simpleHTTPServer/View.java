package simpleHTTPServer;

public interface View{
    public Response call(Request request);
}