package demoServer;


import simpleHTTPServer.Server;
import simpleHTTPServer.View;
import simpleHTTPServer.response.StringResponse;

class DemoServer{
    public static void main(String[] args){
        Server s = new Server();

        //Writing views for our server
        View mainPage = (request)->{
            return new StringResponse(request,"<h1>This is our home page</h1>");
        };
        s.register("/",mainPage);
        s.start();
    }
}