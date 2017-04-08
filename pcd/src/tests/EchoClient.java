package tests;

import Middleware.distributionLayer.Marshaller;
import Middleware.infrastructureLayer.ClientRequestHandlerHttp;
import Middleware.infrastructureLayer.ClientRequestHandlerTcp;
import Middleware.infrastructureLayer.ClientRequestHandlerUdp;
import Middleware.infrastructureLayer.IClientRequestHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by thayo on 21/09/2016.
 */
public class EchoClient {
    //set this string to change the protocol
    static String protocol = "udp";
    static int port = 60000;
    static InetAddress address = null;

    static {
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        IClientRequestHandler clientRequestHandler = null;
        Marshaller marshaller = new Marshaller();
        switch (protocol){
            case "tcp":
                clientRequestHandler = new ClientRequestHandlerTcp(address, port);
                break;
            case "udp":
                clientRequestHandler = new ClientRequestHandlerUdp(port, address);
                break;
            case "http":
                clientRequestHandler = new ClientRequestHandlerHttp(address, port);
                break;
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Digite algo");
        String echo = in.nextLine();

      //  for (int i = 0; i < 6; i++) {
            clientRequestHandler.send(marshaller.marshall(echo));
            String receivedFromServer = (String) marshaller.unmarshall(clientRequestHandler.receive());
            System.out.println(receivedFromServer);

           // Thread.sleep(1000);
       // }



    }

}
