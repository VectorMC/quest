package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class AvgParameter implements Parameter {
    private final Parameter expression;

    public AvgParameter(Parameter expression) {
        this.expression = expression;
    }

    public AvgParameter(String column) {
        this(new FieldParameter(column));
    }

    @Override
    public String getKey() {
        return "AVG(" + this.expression.getKey() + ")";
    }

    public List<Object> getObjects() {
        return this.expression.getObjects();
    }

    @Override
    public String toString() {
        return "AvgParameter(expression=" + this.expression + ")";
    }
}
