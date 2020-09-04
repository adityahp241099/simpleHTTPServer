package simpleHTTPServer.request;

import simpleHTTPServer.exceptions.BadRequest;
import simpleHTTPServer.exceptions.NotAllowed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestFactory{
    private BufferedReader bf;
    private String requestLine;
    public Request fetchRequest(Socket s) throws BadRequest, NotAllowed {
        try {
            bf = new  BufferedReader(new InputStreamReader(s.getInputStream()));
            String method = getMethod();
            String requestURI = getURI();
            return resolveRequest(method,requestURI,bf,s);


        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequest("Error reading the buffer");
        }
    }
    private String getMethod() throws BadRequest{
        if(requestLine == null) {
            try {
                requestLine = bf.readLine();
            }catch(IOException e){
                e.printStackTrace();
                throw new BadRequest("Error parsing the request line");
            }

        }
        return requestLine.split(" ",3)[0];
    }
    private String getURI() throws BadRequest{
        if(requestLine == null) {
            try {
                requestLine = bf.readLine();
            }catch(IOException e){
                e.printStackTrace();
                throw new BadRequest("Error parsing the request line");
            }

        }
        return requestLine.split(" ",3)[1];
    }
    private Request resolveRequest(String type,String requestURI,BufferedReader bf,Socket socket) throws NotAllowed,BadRequest {

        if(type.toLowerCase().equals("get")) {
            return new GetRequest(requestURI,bf,socket);
        }else {
            try {
                String input = bf.readLine();
                while(input!=null){
                    System.out.println(input);
                    input = bf.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new NotAllowed("Not allowed as not supported");

        }

    }
}
