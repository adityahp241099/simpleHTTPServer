package simpleHTTPServer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
//import simpleHTTPServer.Request.*;
public class Server {
	private String host;
	private int port;
	private ServerSocket socket;
	public boolean active = false; 
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
				new SocketHandler(clientSocket).start();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
	}

}





class SocketHandler extends Thread{
	Socket socket;
	Request request;
	Response response;
	public SocketHandler(Socket socket){
		this.socket = socket;
	}
	public void run(){
		try {
			this.request = new RequestFactory().fetchRequest(socket);//try accepting a request class into response constructor
			this.response = new StringResponse(socket,"<!DOCTYPE html>\n" +
					"<html>\n" +
					"  <head>\n" +
					"    <title>This is a demo page</title>\n" +
					"  </head>\n" +
					"  <body>\n" +
					"    <div>\n" +
					"        <p>If you are seeing this, that means server is up!</p>\n" +
					"    </div>\n" +
					"  </body>\n" +
					"</html>");
			response.send();
			socket.close();

		}catch(BadRequest e) {
			e.printStackTrace();
			//Respond with 400
		}catch(NotAllowed e) {
			e.printStackTrace();
			//Respond with 405
		}catch(IOException e){

		} catch (ResponseDispatchException e) {
			//Respond with 500;
			e.printStackTrace();
		}

	}
}
