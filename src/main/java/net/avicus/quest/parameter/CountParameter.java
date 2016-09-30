package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class CountParameter implements Parameter {
    private final Parameter expression;

    public CountParameter(Parameter expression) {
        this.expression = expression;
    }

    public CountParameter(String column) {
        this(new ColumnParameter(column));
    }

    @Override
    public String getKey() {
        return "COUNT(" + this.expression.getKey() + ")";
    }

    @Override
    public List<Object> getValues() {
        return this.expression.getValues();
    }

    @Override
    public String toString() {
        return "CountParameter(expression=" + this.expression + ")";
    }
}
