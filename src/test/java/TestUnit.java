import com.google.gson.Gson;
import sun.misc.IOUtils;
import org.junit.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.fail;

public class TestUnit {
    @Test
    public void aNewUserShouldBeCreated() {
        TestResponse res = request("GET", "/Fibonnaci/4");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
        assertEquals("john", json.get("name"));
        assertEquals("john@foobar.com", json.get("email"));
        assertNotNull(json.get("id"));
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:8080" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.readFully(connection.getInputStream(), 1, true).toString();
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}
