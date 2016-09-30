package net.avicus.quest.query.insert;

import net.avicus.quest.Parameter;
import net.avicus.quest.parameter.NullParameter;
import net.avicus.quest.parameter.ObjectParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Insertion {
    private final Map<String, Parameter> values;

    public Insertion(Map<String, Parameter> values) {
        this.values = values;
    }

    public Insertion(String col1, Parameter val1) {
        this(new HashMap<>());
        this.values.put(col1, val1);
    }

    public Insertion(String col1, Object val1) {
        this(col1, new ObjectParameter(val1));
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
}
