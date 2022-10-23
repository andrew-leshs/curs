import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JSONProcessing {

    private HashMap<String, Integer> listOfCategories = new HashMap<>();
    public HashMap<String, String> tsv = new HashMap<>();

    public void parsing(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String s;
            List<String> list;
            while ((s = br.readLine()) != null) {
                list = List.of(s.split("\t"));
                tsv.put(list.get(0), list.get(1));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String processing(String jsonObject) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ClientJSON clientJSON = gson.fromJson(jsonObject, ClientJSON.class);


        int a = 0;
        for (var entry : tsv.entrySet()) {
            if (clientJSON.getTitle().equals(entry.getKey())) {
                listOfCategories.merge(entry.getValue(), clientJSON.getSum(), Integer::sum);
                a = 1;
            }
        }
        if (a == 0) {
            listOfCategories.merge("другое", clientJSON.getSum(), Integer::sum);
        }

        Map.Entry<String, Integer> listMaxEntry = null;
        for (var entry : listOfCategories.entrySet()) {
            if (listMaxEntry == null) {
                listMaxEntry = entry;
            } else {
                if (entry.getValue() > listMaxEntry.getValue()) {
                    listMaxEntry = entry;
                }
            }
        }
        GsonBuilder gsonBuilder1 = new GsonBuilder();
        Gson gson1 = gsonBuilder1.create();

        JSONProcessingJSON json = new JSONProcessingJSON();
        json.setCategory(listMaxEntry.getKey());
        json.setSum(listMaxEntry.getValue());
        Category category = new Category();
        category.setJsonProcessingJSON(json);


        return gson1.toJson(category, Category.class);
    }
}
