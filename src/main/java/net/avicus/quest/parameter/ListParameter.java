package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list in SQL, for example:
 *
 * SELECT * FROM `users` WHERE `id` IN (?, ?, ?);
 */
public class ListParameter implements Parameter {
    private final List<Parameter> parameters;

    public ListParameter(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getKey() {
        StringBuilder sb = new StringBuilder("(");
        for (Parameter parameter : this.parameters) {
            sb.append(parameter.getKey());
        }
        sb.append(")");
        return sb.toString();
    }

    public List<Object> getObjects() {
        List<Object> values = new ArrayList<>();
        for (Parameter parameter : this.parameters) {
            values.addAll(parameter.getObjects());
        }
        return values;
    }

    @Override
    public String toString() {
        return "ListParameter(key=" + getKey() + ", values=" + getObjects() + ")";
    }
}
