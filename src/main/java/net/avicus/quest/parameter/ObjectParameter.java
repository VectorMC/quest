package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Collections;
import java.util.List;

public class ObjectParameter implements Parameter {
    private final Object value;

    public ObjectParameter(Object value) {
        this.value = value;
    }

    @Override
    public String getKey() {
        return "?";
    }

    @Override
    public List<Object> getValues() {
        return Collections.singletonList(this.value);
    }

    @Override
    public String toString() {
        return "ObjectParameter(key=" + getKey() + ", values=" + getValues() + ")";
    }
}
