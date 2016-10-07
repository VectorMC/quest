package net.avicus.quest.query.insert;

import net.avicus.quest.Parameter;
import net.avicus.quest.Row;
import net.avicus.quest.parameter.NullParameter;
import net.avicus.quest.parameter.ObjectParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Insertion {
    private final Map<String, Parameter> values;

    public Insertion(Map<String, Parameter> values) {
        this.values = values;
    }

    public Insertion duplicate() {
        return new Insertion(new HashMap<>(this.values));
    }

    public Insertion with(String column, Parameter value) {
        Insertion insertion = duplicate();
        insertion.values.put(column, value);
        return insertion;
    }

    public Insertion with(String column, Object value) {
        return with(column, new ObjectParameter(value));
    }

    public Set<String> getColumns() {
        return this.values.keySet();
    }

    public Parameter getValue(String column) {
        return this.values.containsKey(column) ? this.values.get(column) : new NullParameter();
    }

    public static Insertion fromRow(Row row) {
        Map<String, Object> data = row.toMap();
        Map<String, Parameter> converted = new HashMap<>();
        for (Entry<String, Object> entry : data.entrySet()) {
            converted.put(entry.getKey(), new ObjectParameter(entry.getValue()));
        }
        return new Insertion(converted);
    }

    public static InsertionBuilder builder() {
        return new InsertionBuilder();
    }

    public static class InsertionBuilder {
        private final Map<String, Parameter> values;

        private InsertionBuilder() {
            this.values = new HashMap<>();
        }

        public InsertionBuilder value(String column, Parameter value) {
            this.values.put(column, value);
            return this;
        }

        public InsertionBuilder value(String column, Object value) {
            return value(column, new ObjectParameter(value));
        }

        public InsertionBuilder values(Map<String, Parameter> values) {
            this.values.putAll(values);
            return this;
        }

        public Insertion build() {
            return new Insertion(this.values);
        }
    }
}
