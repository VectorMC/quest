package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class FieldParameter implements Parameter {
    private final List<String> fields;

    public FieldParameter(List<String> fields) {
        this.fields = fields;
    }

    public FieldParameter(String... fields) {
        this(Arrays.asList(fields));
    }

    @Override
    public String getKey() {
        StringBuilder sb = new StringBuilder();
        for (String field : this.fields) {
            sb.append("`").append(field).append("`");
            if (!this.fields.get(this.fields.size() - 1).equals(field)) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    public List<Object> getObjects() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "FieldParameter(fields=" + this.fields + ")";
    }
}
