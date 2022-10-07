import junit.framework.TestCase;
import org.json.simple.JSONObject;

public class JSONProcessingTest extends TestCase {

    public void testProcessing() {
        JSONObject root = new JSONObject();
        root.put("title", "абракадабра");
        root.put("date", "2022.02.08");
        root.put("sum", 1000);

        JSONObject jsonObject = JSONProcessing.processing(root);
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("maxCategory");
        assertEquals("другое", jsonObject1.get("category"));
        assertEquals(1000, jsonObject1.get("sum"));
    }
}