package Middleware.infrastructureLayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by thayo on 20/09/2016.
 */
public class ClientRequestHandlerTcp implements IClientRequestHandler {
    private InetAddress host;
    private int port;
    private int sentMsgSize;
    private int receiveMsgSize;

    private Socket clientSocket = null;
    private DataOutputStream outToServer = null;
    private DataInputStream inFromServer = null;

    public ClientRequestHandlerTcp(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void send(byte[] msg) throws IOException, InterruptedException {
        this.clientSocket = new Socket(this.host, this.port);

        this.inFromServer = new DataInputStream(clientSocket.getInputStream());
        this.outToServer = new DataOutputStream(clientSocket.getOutputStream());
        this.sentMsgSize = msg.length;
        this.outToServer.writeInt(sentMsgSize);
        this.outToServer.write(msg, 0, sentMsgSize);
        outToServer.flush();

    }

    @Override
    public byte[] receive() throws IOException, InterruptedException, ClassNotFoundException {
        byte[] msg = null;

        this.receiveMsgSize = this.inFromServer.readInt();
        msg = new byte[this.receiveMsgSize];
        inFromServer.read(msg, 0, receiveMsgSize);

        this.outToServer.close();
        this.inFromServer.close();
        this.clientSocket.close();

        return msg;
    }
}
