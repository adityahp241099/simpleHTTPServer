package simpleHTTPServer;


import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/* <Default Responses> */

class NotFoundResponse extends StringResponse{
    public NotFoundResponse(Socket socket){
        super(socket,"The resource you are looking for is not available");
        code = 404;
        message = "Not Found";
    }
}

/* <Default Responses/> */


public interface Response {
    int code = 200;
    String status = "OK";
    void send() throws ResponseDispatchException;
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