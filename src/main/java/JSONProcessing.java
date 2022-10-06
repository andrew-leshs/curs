import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JSONProcessing {

    private static HashMap<String, Integer> listOfCategories = new HashMap<>();
    private static HashMap<String, String> tsv = new HashMap<>();

    public static JSONObject processing(JSONObject jsonObject) {

        try (BufferedReader br = new BufferedReader(new FileReader("categories.tsv"))) {

            String s;
            List<String> list;
            while ((s = br.readLine()) != null) {
                list = List.of(s.split("\t"));
                tsv.put(list.get(0), list.get(1));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int a = 0;
        for (var entry : tsv.entrySet()) {
            if (jsonObject.get("title").equals(entry.getKey())) {
                listOfCategories.put(entry.getValue(), (Integer) jsonObject.get("sum") + listOfCategories.get(entry.getValue()));
                a = 1;
            }
        }
        if (a == 0) {
            listOfCategories.put("другое", (Integer) jsonObject.get("sum") + listOfCategories.get("другое"));
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

        JSONObject root = new JSONObject();
        JSONObject max = new JSONObject();
        max.put("category", listMaxEntry.getKey());
        max.put("sum", listMaxEntry.getValue());

        root.put("maxCategory", max);
        return root;
    }
}
