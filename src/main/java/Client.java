import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;

public class Client implements Serializable{
    public static final long serialVersionUID = 42L;

    public static void main(String[] args) throws IOException, ParseException {
        try (Socket clientSocket = new Socket("127.0.0.1", 8989);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

            JSONObject root = new JSONObject();
            root.put("title", "булка");
            root.put("date", "2022.02.08");
            root.put("sum", 200);

            objectOutputStream.writeObject(root);
            String s = in.readLine();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(s);
            System.out.println(json);
        }
    }
}
