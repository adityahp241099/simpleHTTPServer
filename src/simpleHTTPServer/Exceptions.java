package simpleHTTPServer;

class HTTPException extends Exception {
    public String message;
    public int code;
    public Class<?> defaultResponse;//The response to dispatch when throw.
    HTTPException(String message,int code){
        this.message = message;
        this.code = code;

        System.out.println(message);
        defaultResponse = NotFoundResponse.class;
    }
    public Response getResponse(Request request){
        return new NotFoundResponse(request.socket);
    }

}


class NotFound extends HTTPException{
    NotFound(String message){
        super(message,404);
    }

}
