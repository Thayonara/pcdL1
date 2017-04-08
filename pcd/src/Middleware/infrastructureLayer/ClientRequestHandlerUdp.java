package Middleware.infrastructureLayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by thayo on 21/09/2016.
 */
public class ClientRequestHandlerUdp implements IClientRequestHandler {
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    private int port;


    public ClientRequestHandlerUdp(int port, InetAddress address) {
        this.port = port;
        this.IPAddress = address;

    }


    @Override
    public void send(byte[] msg) throws IOException, InterruptedException {

        DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, this.IPAddress, this.port);
        this.clientSocket = new DatagramSocket();
        this.clientSocket.send(sendPacket);

    }


    @Override
    public byte[] receive() throws IOException, InterruptedException, ClassNotFoundException {

        DatagramPacket receivedPacket = new DatagramPacket(new byte[570], 570);
        clientSocket.receive(receivedPacket);
        byte[] newLength = new byte[receivedPacket.getLength()];
        System.arraycopy(receivedPacket.getData(), 0, newLength, 0, receivedPacket.getLength());

        return newLength;

    }
}
