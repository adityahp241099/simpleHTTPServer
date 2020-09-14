package simpleHTTPServer.response;


import simpleHTTPServer.exceptions.InternalServerError;
import simpleHTTPServer.exceptions.ResponseDispatchException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/* <Default Responses> */



/* <Default Responses/> */


public interface Response {
    int code = 200;
    String status = "OK";
    void send() throws ResponseDispatchException, InternalServerError;
    void submitHeader(String key, String value) throws InternalServerError;
}











class ResponseWriter{
    OutputStream os;
    ResponseWriter(Socket socket) throws ResponseDispatchException{
        try {
            this.os = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseDispatchException("Error fetching Output Stream");
        }
    }

    public void write(String string) throws ResponseDispatchException {
        try{
            this.os.write(string.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        e.printStackTrace();
            throw new ResponseDispatchException("Can't Encode Response Message");
        }

    }
    public void close() throws ResponseDispatchException {

        try {
            this.os.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseDispatchException("Error closing stream");
        }
    }
}