package Middleware.infrastructureLayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by thayo on 20/09/2016.
 */
public class ServerRequestHandlerUdp implements IServerRequestHandler {

    private DatagramSocket serverSocket = null;
    private InetAddress IPAddress = null;
    private int port;

    public ServerRequestHandlerUdp(int port) throws SocketException {
        this.port = port;

    }


    @Override
    public byte[] receive() throws IOException, InterruptedException {
        DatagramPacket receivedPacket = new DatagramPacket(new byte[570], 570);
        this.serverSocket = new DatagramSocket(this.port);
        this.serverSocket.receive(receivedPacket);
        this.IPAddress = receivedPacket.getAddress();
        this.port = receivedPacket.getPort();

        byte[] newLength = new byte[receivedPacket.getLength()];
        System.arraycopy(receivedPacket.getData(), 0, newLength, 0, receivedPacket.getLength());

        return newLength;

    }

    @Override
    public void send(byte[] msg) throws IOException, InterruptedException, ClassNotFoundException {

        DatagramPacket sendPacket = new DatagramPacket(msg, msg.length, this.IPAddress, this.port);
        serverSocket.send(sendPacket);
        this.serverSocket.close();

    }
}
