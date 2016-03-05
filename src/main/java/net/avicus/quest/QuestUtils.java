package net.avicus.quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestUtils {
    public static String getField(String field) {
        return "`" + field + "`";
    }

    public static String getValue(Object value) {
        if (value instanceof String)
            return "'" + value.toString() + "'";
        if (value instanceof Character)
            return getValue(value.toString());
        else if (value instanceof Number)
            return value.toString();
        else if (value instanceof List) {
            List<String> values = new ArrayList<>(((List) value).size());
            for (Object object : (List) value)
                values.add(getValue(object));
            return "(" + String.join(",", values) + ")";
        }
        else if (value.getClass().isArray())
            return getValue(Arrays.asList((Object[]) value));
        throw new IllegalArgumentException("Unknown SQL type: \"" + value.getClass().getSimpleName() + "\".");
    }
}
