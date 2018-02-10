/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.projects.simplegithubintegrator;



import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Set;


/**
 *
 * @author munif
 */
public class Principal {
    
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/github", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("REQUEST FROM:"+t.getRequestMethod()+" "+t.getRemoteAddress());
            Headers requestHeaders = t.getRequestHeaders();
            Set<String> keySet = requestHeaders.keySet();
            for (String s:keySet){
                System.out.println(s+":"+requestHeaders.get(s));
            }
            InputStream requestBody = t.getRequestBody();
            int c=0;
            String body="";
            while((c=requestBody.read())!=-1){
                body+=(char)c;
            }
            System.out.println(body);
            String response = "This is the response";

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("-----------\n\n");
        }
    }
    
}
