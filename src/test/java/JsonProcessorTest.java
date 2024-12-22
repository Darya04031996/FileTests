import com.fasterxml.jackson.databind.ObjectMapper;
import model.JsonFile;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonProcessorTest {
    private final ClassLoader cl = JsonProcessorTest.class.getClassLoader();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void processJsonFile() throws Exception {
        try (InputStream is = cl.getResourceAsStream("JsonFile.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            JsonFile data = objectMapper.readValue(isr, JsonFile.class);
            assertThat(data.getName()).isEqualTo("Darya Melgunova");
            assertThat(data.getAge()).isEqualTo("28");
            assertThat(data.getHobbies()).containsExactly("Reading", "Traveling", "Gaming");
        }

    }
}
