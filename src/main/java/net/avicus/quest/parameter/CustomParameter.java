package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomParameter implements Parameter {
    private final String key;
    private final List<Parameter> params;

    public CustomParameter(String key, List<Parameter> params) {
        this.key = key;
        this.params = params;
    }

    public CustomParameter(String key, Parameter... params) {
        this(key, Arrays.asList(params));
    }

    public CustomParameter(String key, Object... objects) {
        this(key, Arrays.asList(objects).stream().map(ObjectParameter::new).collect(Collectors.toList()));
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public List<Object> getObjects() {
        List<Object> objects = new ArrayList<>();
        for (Parameter param : this.params) {
            objects.addAll(param.getObjects());
        }
        return objects;
    }
}
