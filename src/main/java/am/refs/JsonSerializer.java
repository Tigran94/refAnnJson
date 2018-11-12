import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.*;

public class JsonSerializer {

    public String serialize(Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            Map<String,String> jsonElements = new HashMap<>();
            for (Field field: fields) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(Transient.class)) {
                    if(field.getGenericType().getTypeName().contains("String"))
                         jsonElements.put(field.getName(), (String) field.get(object));
                    else if(field.getGenericType().getTypeName().contains("int")){
                        Integer value = (Integer)field.get(object);
                        String value1  = Integer.toString(value);
                        jsonElements.put(field.getName(), value1);                    
                    }
                }
            }
            System.out.println(toJsonString(jsonElements));
            return toJsonString(jsonElements);
        }
        catch (IllegalAccessException e) {
            return null;
        }
    }
    private String toJsonString(Map<String,String> l) {
        StringBuilder builder = new StringBuilder("{\n");
        for ( Map.Entry<String, String> entry : l.entrySet()) {
             builder.append("\t\"")
                    .append(entry.getKey())
                    .append("\" : \"")
                    .append(entry.getValue())
                    .append("\",\n");
            }
        builder.setLength(builder.length()-2);
        builder.append("\n}");
        return builder.toString();
    }
}
