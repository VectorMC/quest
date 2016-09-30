package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Arrays;
import java.util.List;

public class CustomParameter implements Parameter {
    private final String key;
    private final List<Object> values;

    public CustomParameter(String key, List<Object> values) {
        this.key = key;
        this.values = values;
    }

    public CustomParameter(String key, Object... values) {
        this(key, Arrays.asList(values));
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public List<Object> getValues() {
        return this.values;
    }
}
