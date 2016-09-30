package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Collections;
import java.util.List;

public class ColumnParameter implements Parameter {
    private final String name;

    public ColumnParameter(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return "`" + this.name + "`";
    }

    @Override
    public List<Object> getValues() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "ColumnParameter(name=" + this.name + ")";
    }
}
