import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client implements Serializable{
    public static final long serialVersionUID = 42L;

    public static void main(String[] args) throws IOException {
        try (Socket clientSocket = new Socket("127.0.0.1", 8989);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {

            JSONObject root = new JSONObject();
            root.put("title", "булка");
            root.put("date", "2022.02.08");
            root.put("sum", 200);

            objectOutputStream.writeObject(root);
            root = (JSONObject) in.readObject();
            System.out.println(root);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
