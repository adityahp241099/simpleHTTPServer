package simpleHTTPServer.response;

import simpleHTTPServer.exceptions.InternalServerError;
import simpleHTTPServer.exceptions.ResponseDispatchException;
import simpleHTTPServer.request.Request;


import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
    public void send() throws  InternalServerError,ResponseDispatchException {
        submitHeader("Content-Type","text/html; charset=utf-8");
        ResponseWriter rw = new ResponseWriter(socket);
        rw.write("HTTP/1.1 "+code+" "+message+"\r\n" );
        writeHeaders(rw);
        rw.write("\r\n");
        rw.write(responseString);
        rw.close();
    }

    @Override
    public void submitHeader(String key,String value) throws InternalServerError {
        headers.put(key,value);
    }
    private void writeHeaders(ResponseWriter rw)  throws  InternalServerError{
        try{
            for(Map.Entry <String,String> entry: headers.entrySet()){
                rw.write(entry.getKey()+": "+entry.getValue()+"\r\n" );
            }
        }catch (ResponseDispatchException e){
            throw new InternalServerError("Error adding headers");
        }

    }

}