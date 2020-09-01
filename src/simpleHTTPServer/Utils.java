package simpleHTTPServer;

import com.sun.jdi.InterfaceType;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;




class CaseInsensitiveHashMap<V> extends HashMap <Object,Object>{
    @Override
    public V put(Object key, Object value) {
        return (V) super.put(((String)key).toLowerCase(), value);
    }

    @Override
    public V  get(Object key) {
        return (V) super.get(((String)key).toLowerCase());
    }

    @Override
    public V getOrDefault(Object key, Object defaultValue) {
        return (V) super.getOrDefault(key, defaultValue);
    }
}


class HTTPEncodingParser {
    private static boolean initialized = false;
    public static HashMap<String,Class> decoderMapping;

    public static  HashMap parse(BufferedReader bf,String encType,int lines)throws UnsupportedEncType, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, BadRequest {

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
            decoderMapping.put("urlencoded",URLEncodedDecoder.class);
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

interface Decoder{
    public HashMap toHashMap() throws BadRequest;
}
class URLEncodedDecoder implements Decoder{
    private HashMap<String,String> params;
    private String text;
    public URLEncodedDecoder(String text){
        params = new HashMap<>();
        this.text = text;
    }
    @Override
    public HashMap toHashMap() throws BadRequest {
        String[] queryBlocks = text.split("&");
        for (String queryBlock : queryBlocks) {
            try {
                String key = URLDecoder.decode(queryBlock.split("=", 2)[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(queryBlock.split("=", 2)[1], StandardCharsets.UTF_8);
                if (params.containsKey(key)) {
                    params.put(key, params.get(key) + ", " + value);//To handle multiple values for same key
                } else {
                    params.put(key, value);
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                throw new BadRequest("Malformed query parameter");
            }
        }
        return params;
    }
}