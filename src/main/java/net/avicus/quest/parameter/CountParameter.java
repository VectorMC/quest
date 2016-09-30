package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class CountParameter implements Parameter {
    private final Parameter expression;

    public CountParameter(Parameter expression) {
        this.expression = expression;
    }

    public CountParameter(String column) {
        this(new FieldParameter(column));
    }

    @Override
    public String getKey() {
        return "COUNT(" + this.expression.getKey() + ")";
    }

    public List<Object> getObjects() {
        return this.expression.getObjects();
    }

    @Override
    public String toString() {
        return "CountParameter(expression=" + this.expression + ")";
    }
}
