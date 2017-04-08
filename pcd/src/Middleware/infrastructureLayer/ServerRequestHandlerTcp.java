package Middleware.infrastructureLayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by thayo on 20/09/2016.
 */
public class ServerRequestHandlerTcp implements IServerRequestHandler{

    private int port;
    private ServerSocket welcomeSocket = null;
    private Socket connectionSocket = null;

    private int sentMsgSize;
    private int receiveMsgSize;
    private DataOutputStream outToClient = null;
    private DataInputStream inFromClient = null;


    public ServerRequestHandlerTcp(int port){
        this.port = port;
    }

    @Override
    public byte[] receive() throws IOException, InterruptedException{

        byte[] receiveMsg = null;

        this.welcomeSocket = new ServerSocket(this.port);
        this.connectionSocket = this.welcomeSocket.accept();
        welcomeSocket.close();

        this.inFromClient = new DataInputStream(this.connectionSocket.getInputStream());
        this.outToClient = new DataOutputStream(this.connectionSocket.getOutputStream());

        this.receiveMsgSize = inFromClient.readInt();
        receiveMsg = new byte[receiveMsgSize];

        this.inFromClient.read(receiveMsg, 0 , this.receiveMsgSize);

        return receiveMsg;
    }

    @Override
    public void send(byte[] msg) throws IOException, InterruptedException, ClassNotFoundException{
        this.sentMsgSize = msg.length;
        this.outToClient.writeInt(this.sentMsgSize);
        this.outToClient.write(msg);
        this.outToClient.flush();

        this.outToClient.close();
        this.inFromClient.close();
        this.connectionSocket.close();
    }

}
