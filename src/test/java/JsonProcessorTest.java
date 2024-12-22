import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JsonProcessorTest {
    private ClassLoader cl = JsonProcessorTest.class.getClassLoader();

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
     void processJsonFile() throws Exception {
        try (InputStream is = cl.getResourceAsStream("JsonFile.json")) {
            assert is != null;
            try (InputStreamReader isr = new InputStreamReader(is)) {
                JsonFile data;
                data = objectMapper.readValue(isr, JsonFile.class);
                Assertions.assertEquals("Darya Melgunova", data.getName());
                Assertions.assertEquals("28", data.getAge());
                Assertions.assertEquals(List.of("Reading",
                        "Traveling",
                        "Gaming"), data.getHobbies());
            }

        }
    }

}
