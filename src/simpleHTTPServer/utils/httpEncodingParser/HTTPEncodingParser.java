package simpleHTTPServer.utils.httpEncodingParser;


import simpleHTTPServer.exceptions.BadRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class HTTPEncodingParser {
    private static boolean initialized = false;
    public static HashMap<String,Class> decoderMapping;

    public static  HashMap parse(BufferedReader bf, String encType, int lines)throws UnsupportedEncType, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, BadRequest {

        HashMap out;
        String in = "";
        try {
            in = in + "\n" + bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadRequest("Unable to parse Request");
        }
        out = parse(in, encType);
        return out;
    }
    public static HashMap parse(String text,String encType) throws UnsupportedEncType, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, BadRequest {
        if(!initialized){
            decoderMapping = new HashMap<String, Class>();
            decoderMapping.put("urlencoded", URLEncodedDecoder.class);
            initialized = true;
        }
        var constructor = decoderMapping.get(encType).getConstructor(String.class);
        Decoder decoder = (Decoder) constructor.newInstance(text);
        return decoder.toHashMap();

    }
    public static Class<Decoder> encodingQuery(String encType) throws UnsupportedEncType{
        if(decoderMapping.containsKey(encType)){
            return decoderMapping.get(encType);
        }else{
            throw new UnsupportedEncType("encType not recognised");
        }

    }
    static class UnsupportedEncType extends Exception{
        UnsupportedEncType(String message){

        }
    };

}
