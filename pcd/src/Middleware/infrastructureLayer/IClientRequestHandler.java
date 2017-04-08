package Middleware.infrastructureLayer;

import java.io.IOException;

/**
 * Created by thayo on 20/09/2016.
 */
public interface IClientRequestHandler {

    public void send(byte[] msg) throws IOException, InterruptedException;
    public byte[] receive() throws IOException, InterruptedException, ClassNotFoundException;
}
