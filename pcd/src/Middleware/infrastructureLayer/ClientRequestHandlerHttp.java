package Middleware.infrastructureLayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by thayo on 21/09/2016.
 */
public class ClientRequestHandlerHttp implements IClientRequestHandler {
    private InetAddress address;
    private int port;
    private URLConnection connection;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public ClientRequestHandlerHttp(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void send(byte[] msg) throws IOException, InterruptedException {
        URL url = new URL("http", address.getHostAddress(), port, "/connect");
        connection = url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);

        dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeInt(msg.length);
        dataOutputStream.flush();
    }

    @Override
    public byte[] receive() throws IOException, InterruptedException, ClassNotFoundException {

        dataInputStream = new DataInputStream(connection.getInputStream());
        byte[] receivedData = new byte[dataInputStream.readInt()];

        dataInputStream.read(receivedData, 0 , receivedData.length);

        dataInputStream.close();
        dataOutputStream.close();

        return receivedData;
    }
}
