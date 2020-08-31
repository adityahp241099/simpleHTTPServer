package simpleHTTPServer;

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


