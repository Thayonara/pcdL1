package Middleware.infrastructureLayer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by thayo on 21/09/2016.
 */
public class ServerRequestHandlerHttp implements IServerRequestHandler {
    private HttpServer httpServer;
    private int port;
    private AtomicBoolean connect;
    private HttpExchange exchange;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;


    public ServerRequestHandlerHttp(int port) {
        connect = new AtomicBoolean(false);
        this.port = port;
    }

    @Override
    public synchronized byte[] receive() throws IOException, InterruptedException {

        httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        ServerRequestHandlerHttp instance = this;
        httpServer.createContext("/connect", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                exchange = httpExchange;
                connect.set(true);
                instance.notifyAll();
            }
        });
        httpServer.setExecutor(null);
        httpServer.start();

        while(!connect.get()){
            wait();
        }

        dataInputStream = new DataInputStream(exchange.getRequestBody());
        byte[] receivedData = new byte[dataInputStream.readInt()];
        dataInputStream.read(receivedData, 0 , receivedData.length);

        return receivedData;
    }

    @Override
    public void send(byte[] msg) throws IOException, InterruptedException, ClassNotFoundException {
        exchange.sendResponseHeaders(200, msg.length+4);
        dataOutputStream = new DataOutputStream(exchange.getResponseBody());
        dataOutputStream.write(msg, 0,  msg.length);
        dataOutputStream.flush();
        dataInputStream.close();
        exchange.close();
        httpServer.stop(0);
        connect.set(false);
    }
}
