package simpleHTTPServer;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


class Request{
	protected String path;
	protected String[] deconstructedPath;
	protected HashMap<String,String> params;
	protected CaseInsensitiveHashMap<String> headers;
	protected String uri;
	protected BufferedReader bf;
	public Socket socket;
	Request(String URI, BufferedReader bf, Socket socket) throws BadRequest{
		uri = URI;
		this.bf = bf;
		this.socket = socket;
		parsePath();
		parseHeaders();
		parseQuery();
	}
	protected void parsePath(){
		String[] array = uri.split("\\?")[0].split("/");
		for(int i =0;i<array.length;i++){
			array[i] = URLDecoder.decode(array[i], StandardCharsets.UTF_8);
		}
		path = URLDecoder.decode(uri.split("\\?")[0], StandardCharsets.UTF_8);
		deconstructedPath = array;

	}
	protected void parseHeaders() throws BadRequest{
		headers = new CaseInsensitiveHashMap<String>();
		try{
			String input = bf.readLine();
			while(!input.equals("")) {
				headers.put(input.split(":",2)[0],input.split(":",2)[1]);
				input = bf.readLine();
			}
		}catch (IOException e) {
			e.printStackTrace();
			throw new BadRequest("Error Parsing Headers");
		}
	}
	protected void parseQuery() throws BadRequest{
		if(uri.split("\\?").length>1){
			try{
				params = HTTPEncodingParser.parse(uri.split("\\?")[1],"urlencoded");
			}catch (Exception e){
				e.printStackTrace();
				throw new BadRequest("Can't Parse Query String");
			}
		}else{
			params = new HashMap<String,String>();
		}



	}
	public String getPath()  {
		return path;
	}
	public String[] getDeconstructedPath(){
		return deconstructedPath;
	}
	public String getParameter(String key){
		return params.getOrDefault(key,null);
	}
	public String getHeaderValue(String key){
		return headers.getOrDefault(key,null);
	}
	public String postParameter(String key){
		return null;
	}
}



class RequestFactory{
	private  BufferedReader bf;
	private String requestLine;
	public Request fetchRequest(Socket s) throws BadRequest,NotAllowed{
		try {
			bf = new  BufferedReader(new InputStreamReader(s.getInputStream()));
			String method = getMethod();
			String requestURI = getURI();
			return resolveRequest(method,requestURI,bf,s);
				
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new BadRequest("Error reading the buffer");
		}
	}
	private String getMethod() throws BadRequest{
		if(requestLine == null) {
			try {
				requestLine = bf.readLine();
			}catch(IOException e){
				e.printStackTrace();
				throw new BadRequest("Error parsing the request line");
			}
			
		}
		return requestLine.split(" ",3)[0];
	}
	private String getURI() throws BadRequest{
		if(requestLine == null) {
			try {
				requestLine = bf.readLine();
			}catch(IOException e){
				e.printStackTrace();
				throw new BadRequest("Error parsing the request line");
			}
			
		}
		return requestLine.split(" ",3)[1];
	}
	private Request resolveRequest(String type,String requestURI,BufferedReader bf,Socket socket) throws NotAllowed,BadRequest {
		
		if(type.toLowerCase().equals("get")) {
			return new GetRequest(requestURI,bf,socket);
		}else {
			try {
				String input = bf.readLine();
				while(input!=null){
					System.out.println(input);
					input = bf.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			throw new NotAllowed("Not allowed as not supported");

		}
		
	}
}

class BadRequest extends Exception{
	private static final long serialVersionUID = 589048716764482718L;
	public BadRequest(String message) {
		System.out.println(message);
	}
}
class NotAllowed extends Exception{
	private static final long serialVersionUID = 589048716764482719L;
	public NotAllowed(String message) {
		System.out.println(message);
	}
}





/* All request types come here*/
class GetRequest extends Request {
	GetRequest(String URI, BufferedReader bf,Socket socket) throws BadRequest{
		super(URI,bf,socket);
	}
}

