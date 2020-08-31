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
			try {
				this.socket.bind(sa);
				System.out.println("Server started on "+ this.host +":"+this.port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	OutputStream out;
	public SocketHandler(Socket socket){
		this.socket = socket;
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		try {
			this.request = new RequestFactory().fetchRequest(socket);
			System.out.println(request.getParameter("host"));
			System.out.println(request.getHeaderValue("host"));
			System.out.print(this.request.getPath());
			standardResponse();
			handoverResponse();
		}catch(BadRequest e) {
			e.printStackTrace();
			//Respond with 400
		}catch(NotAllowed e) {
			e.printStackTrace();
			//Respond with 405
		}
		
	}
	public void standardResponse() {
		
			String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + "<html>Works<form method='POST'><input name='hi' value='1'><input type='submit' value='Interesting'></form></html>";
            
            try {
				out.write(httpResponse.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		
	}
	public void handoverResponse() {
		try {
			out.close();		
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
