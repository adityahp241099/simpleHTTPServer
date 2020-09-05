package simpleHTTPServer.utils;

import java.util.HashMap;

public class CaseInsensitiveHashMap<V> extends HashMap<Object,Object> {
    @Override
    @SuppressWarnings("unchecked")
    public V put(Object key, Object value) {

        return (V) super.put(((String)key).toLowerCase(), value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public V  get(Object key) {

        return (V) super.get(((String)key).toLowerCase());
    }

    @Override
    @SuppressWarnings("unchecked")
    public V getOrDefault(Object key, Object defaultValue) {

        return (V) super.getOrDefault(key, defaultValue);
    }
}

