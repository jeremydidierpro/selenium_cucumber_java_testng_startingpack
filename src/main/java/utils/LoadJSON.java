package utils;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;


import java.io.InputStream;
import java.util.Objects;

public class LoadJSON {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private LoadJSON() {
    }

    static public <T> T load(String jsonPath, Class<T> clazz){
        try(InputStream is = LoadJSON.class.getClassLoader().getResourceAsStream(jsonPath)){
            if (Objects.isNull(is)){
                throw new RuntimeException("JSON file not found: " + jsonPath);
            }
            return MAPPER.readValue(is, clazz);
        }catch (Exception e) {
            throw new RuntimeException("Failed to load JSON: " + jsonPath, e);
        }
    }


}

