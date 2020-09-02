package simpleHTTPServer;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

interface Response {
    void send() throws ResponseDispatchException;
}



class StringResponse implements Response{
    Socket socket;
    String responseString;
    StringResponse(Socket socket, String response){
        this.socket = socket;
        this.responseString = response;
    }
    public void send() throws ResponseDispatchException {
        ResponseWriter rw = new ResponseWriter(socket);
        rw.write("HTTP/1.1 200 OK\r\n\r\n" );//TODO
        rw.write(responseString);
        rw.close();
    }
}


class ResponseDispatchException extends  Exception{
    ResponseDispatchException(String message){
        System.out.println(message);
    }
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