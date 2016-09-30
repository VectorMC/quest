package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Collections;
import java.util.List;

public class TableParameter implements Parameter {
    private final String database;
    private final String name;

    public TableParameter(String name) {
        this(null, name);
    }

    public TableParameter(String database, String name) {
        this.database = database;
        this.name = name;
    }

    @Override
    public String getKey() {
        String result = "`" + this.name + "`";
        if (this.database != null) {
            result = "`" + this.database + "`." + result;
        }
        return result;
    }

    @Override
    public List<Object> getValues() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "TableParameter(database=" + this.database + ", name=" + this.name + ")";
    }
}
