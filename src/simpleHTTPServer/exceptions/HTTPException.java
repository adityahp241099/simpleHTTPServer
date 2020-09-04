package simpleHTTPServer.exceptions;

import simpleHTTPServer.response.NotFoundResponse;
import simpleHTTPServer.request.Request;
import simpleHTTPServer.response.Response;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HTTPException extends Exception {
    //TODO Set Code through here
    public String message;
    public int code;
    public Class<?> defaultResponse;//The response to dispatch when throw.
    HTTPException(String message,int code){
        this.message = message;
        this.code = code;
        System.out.println(message);
        defaultResponse = NotFoundResponse.class;
    }
    public Response getResponse(Request request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?> constructor =  defaultResponse.getConstructor(Request.class);
        Response response = (Response) constructor.newInstance(request);
        return response;
    }
}


