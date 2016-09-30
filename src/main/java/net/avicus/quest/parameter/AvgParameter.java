package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class AvgParameter implements Parameter {
    private final Parameter expression;

    public AvgParameter(Parameter expression) {
        this.expression = expression;
    }

    public AvgParameter(String column) {
        this(new ColumnParameter(column));
    }

    @Override
    public String getKey() {
        return "AVG(" + this.expression.getKey() + ")";
    }

    @Override
    public List<Object> getValues() {
        return this.expression.getValues();
    }

    @Override
    public String toString() {
        return "AvgParameter(expression=" + this.expression + ")";
    }
}
