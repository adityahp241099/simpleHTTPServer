package simpleHTTPServer;
import simpleHTTPServer.exceptions.*;
import simpleHTTPServer.request.Request;
import simpleHTTPServer.request.RequestFactory;
import simpleHTTPServer.response.Response;
import simpleHTTPServer.response.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
//import simpleHTTPServer.Request.Request.*;
public class Server {
	private String host;
	private int port;
	private ServerSocket socket;
	public boolean active = false;
	protected URLMapper urlMapper = new URLMapper();
	public Server() {
		this.host = "0.0.0.0";
		this.port = 8000;
		try {
			this.socket = new ServerSocket();
			SocketAddress sa = new InetSocketAddress(this.host,this.port);
			this.socket.bind(sa);
			System.out.println("Server started on "+ this.host +":"+this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void start() {
		if(!this.active) {
			this.active = true;
			this.serverWorker();
		}
	}
	public void stop(){
		this.active = false;
	}
	public void serverWorker(){
		while(this.active){
			try {
				Socket clientSocket = this.socket.accept();
				System.out.println("New Connection");
				new SocketHandler(clientSocket,urlMapper).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void register(String pattern ,View view){
		urlMapper.register(pattern,view);
	}
	public static void main(String[] args) {
		Server s = new Server();
		View root = (request)->{
			return new StringResponse(request,"This Works");
		};
		s.register("/",root);
		s.start();
	}

}





class SocketHandler extends Thread{
	Socket socket;
	Request request;
	Response response;
	URLMapper urlMapper;
	public SocketHandler(Socket socket,URLMapper urlMapper){
		this.socket = socket;
		this.urlMapper = urlMapper;
	}
	public void run(){
		try {

			this.request = new RequestFactory().fetchRequest(socket);//try accepting a request class into response constructor
			View view = this.urlMapper.resolve(request.getPath());
			this.response = view.call(this.request);
			response.send();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ResponseDispatchException e) {
			e.printStackTrace();

		}catch(BadRequest e){
			e.printStackTrace();
			try {
				new BadRequestResponse(socket).send();
				System.out.println("Dispatched automated response");
			} catch (ResponseDispatchException responseDispatchException) {
				responseDispatchException.printStackTrace();
			}
		}catch (HTTPException e) {
			e.printStackTrace();
			try {
				e.getResponse(request).send();
				System.out.println("Dispatched automated response");
			} catch (ResponseDispatchException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException responseDispatchException) {
				responseDispatchException.printStackTrace();
			}
		}
	}
}



