package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class MinParameter implements Parameter {
    private final Parameter expression;

    public MinParameter(Parameter expression) {
        this.expression = expression;
    }

    public MinParameter(String column) {
        this(new ColumnParameter(column));
    }

    @Override
    public String getKey() {
        return "MIN(" + this.expression.getKey() + ")";
    }

    @Override
    public List<Object> getValues() {
        return this.expression.getValues();
    }

    @Override
    public String toString() {
        return "MinParameter(expression=" + this.expression + ")";
    }
}
