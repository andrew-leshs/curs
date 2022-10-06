import org.json.simple.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main implements Serializable{

    public static final long serialVersionUID = 42L;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {

                    JSONObject jsonObject = (JSONObject) in.readObject();
                    jsonObject = JSONProcessing.processing(jsonObject);

                    out.println(jsonObject);

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}