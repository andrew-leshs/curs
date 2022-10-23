import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException, ParseException {
        try (Socket clientSocket = new Socket("127.0.0.1", 8989);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {

            ClientJSON root = new ClientJSON();
            root.setTitle("мыло");
            root.setSum(300);
            root.setDate("2022.02.08");
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            out.println(gson.toJson(root));
            System.out.println(in.readLine());
        }
    }
}
