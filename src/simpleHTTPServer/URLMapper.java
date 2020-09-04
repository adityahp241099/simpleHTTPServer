package simpleHTTPServer;

import java.lang.reflect.Method;
import java.net.InterfaceAddress;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class URLMapper {
    /* One Server instance must have only one instance of this */
    HashMap<String,View> mapping;
    URLMapper(){
        mapping = new HashMap<String,View>();
    }
    public void register(String pattern,View viewLambda){
            mapping.put(pattern,viewLambda);
    }
    public View resolve(String path) throws NotFound{
        if(mapping.containsKey(path)){
            return mapping.get(path);
        }else{
            throw new NotFound("No View found for the path");
        }

    }
}

class InternalSeverError extends Exception{
    public InternalSeverError(String message){
        System.out.println(message);

    }
}

interface View{
    public Response call(Request request);
}