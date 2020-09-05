package simpleHTTPServer.utils.httpEncodingParser;

import simpleHTTPServer.exceptions.BadRequest;

import java.util.HashMap;

public interface Decoder{
    public HashMap toHashMap() throws BadRequest;
}