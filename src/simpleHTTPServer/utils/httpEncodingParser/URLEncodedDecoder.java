package simpleHTTPServer.utils.httpEncodingParser;

import simpleHTTPServer.exceptions.BadRequest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class URLEncodedDecoder implements Decoder {
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
