package chnu.edu.labproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Rout
 * @version 1.0.0
 * @project lab-project
 * @class Utils
 * @since 27.05.2025 - 13.46
 */
public class Utils {
    public static String toJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
