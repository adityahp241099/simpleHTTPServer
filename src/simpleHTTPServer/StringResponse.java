package simpleHTTPServer;

import java.net.Socket;

public class StringResponse implements Response{
    Socket socket;
    String responseString;
    protected int code = 200;
    protected String message = "OK";
    StringResponse(Socket socket, String response){
        this.socket = socket;
        this.responseString = response;
    }
    public StringResponse(Request request,String response){
        this(request.socket,response);
    }

    public void send() throws ResponseDispatchException {
        ResponseWriter rw = new ResponseWriter(socket);
        rw.write("HTTP/1.1 +"+code+" "+message+"\r\n\r\n" );//TODO
        rw.write(responseString);
        rw.close();
    }
}