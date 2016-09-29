package net.avicus.quest;

import java.util.Collection;
import java.util.List;

public class ParameterizedString {
    private final String sql;
    private final List<Parameter> parameters;

    public ParameterizedString(String sql, List<Parameter> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    public String getSql() {
        return this.sql;
    }

    public Collection<Parameter> getParameters() {
        return this.parameters;
    }

    public String toString() {
        return "ParameterizedString(sql=" + this.sql + ", parameters=" + this.parameters + ")";
    }
}
