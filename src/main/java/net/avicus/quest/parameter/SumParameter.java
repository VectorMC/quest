package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class SumParameter implements Parameter {
    private final Parameter expression;

    public SumParameter(Parameter expression) {
        this.expression = expression;
    }

    public SumParameter(String column) {
        this(new FieldParameter(column));
    }

    @Override
    public String getKey() {
        return "SUM(" + this.expression.getKey() + ")";
    }

    public List<Object> getObjects() {
        return this.expression.getObjects();
    }

    @Override
    public String toString() {
        return "SumParameter(expression=" + this.expression + ")";
    }
}
