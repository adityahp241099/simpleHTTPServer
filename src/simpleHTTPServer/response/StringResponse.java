package simpleHTTPServer.response;

import simpleHTTPServer.exceptions.InternalServerError;
import simpleHTTPServer.exceptions.ResponseDispatchException;
import simpleHTTPServer.request.Request;


import java.net.Socket;
import java.util.HashMap;

public class StringResponse implements Response {
    Socket socket;
    String responseString;
    private HashMap<String,String> headers = new HashMap<String,String>();
    protected int code = 200;
    protected String message = "OK";
    StringResponse(Socket socket, String response){
        this.socket = socket;
        this.responseString = response;
    }
    public StringResponse(Request request, String response){
        this(request.socket,response);
    }
    public void send() throws  ResponseDispatchException {
        ResponseWriter rw = new ResponseWriter(socket);
        rw.write("HTTP/1.1 +"+code+" "+message+"\r\n\r\n" );
        rw.write(responseString);
        rw.close();
    }

    @Override
    public void submitHeader(String key,String value) throws InternalServerError {
        headers.put(key,value);
    }

}