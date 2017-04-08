package tests;

import Middleware.distributionLayer.Marshaller;
import Middleware.infrastructureLayer.*;

import java.io.IOException;

/**
 * Created by thayo on 21/09/2016.
 */
public class EchoServer {

    //set this string to change the protocol

    static String protocol = "udp";
    static int port = 60000;


    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        IServerRequestHandler serverRequestHandler = null;
        Marshaller marshaller = new Marshaller();
        switch (protocol){
            case "tcp":
                serverRequestHandler = new ServerRequestHandlerTcp(port);
                break;
            case "udp":
                serverRequestHandler = new ServerRequestHandlerUdp(port);
                break;
            case "http":
                serverRequestHandler = new ServerRequestHandlerHttp(port);
                break;
        }

        //String echo = "Oi";

        //for (int i = 0; i < 6; i++) {
            String receivedFromClient = (String) marshaller.unmarshall(serverRequestHandler.receive());
            System.out.println(receivedFromClient);
            serverRequestHandler.send(marshaller.marshall(receivedFromClient));

        //}



    }
}
