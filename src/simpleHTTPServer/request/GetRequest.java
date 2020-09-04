package simpleHTTPServer.request;

import simpleHTTPServer.exceptions.BadRequest;

import java.io.BufferedReader;
import java.net.Socket;

public class GetRequest extends Request {
    GetRequest(String URI, BufferedReader bf, Socket socket) throws BadRequest {
        super(URI,bf,socket);
    }
}
