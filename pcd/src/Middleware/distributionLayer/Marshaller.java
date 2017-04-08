package Middleware.distributionLayer;

import java.io.*;

/**
 * Created by thayo on 20/09/2016.
 */
public class Marshaller {
    public byte[] marshall(Serializable msgToBeMarshalled) throws IOException, InterruptedException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(msgToBeMarshalled);

        return byteStream.toByteArray();
    }

    public Serializable unmarshall(byte[] msgToBeUnMarshalled) throws IOException, InterruptedException, ClassNotFoundException{
        ByteArrayInputStream byteStream = new ByteArrayInputStream(msgToBeUnMarshalled);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);

        return (Serializable) objectStream.readObject();

    }
}

