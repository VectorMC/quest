package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class MaxParameter implements Parameter {
    private final Parameter expression;

    public MaxParameter(Parameter expression) {
        this.expression = expression;
    }

    public MaxParameter(String column) {
        this(new ColumnParameter(column));
    }

    @Override
    public String getKey() {
        return "MAX(" + this.expression.getKey() + ")";
    }

    @Override
    public List<Object> getValues() {
        return this.expression.getValues();
    }

    @Override
    public String toString() {
        return "MaxParameter(expression=" + this.expression + ")";
    }
}
