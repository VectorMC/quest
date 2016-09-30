package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class SumParameter implements Parameter {
    private final Parameter expression;

    public SumParameter(Parameter expression) {
        this.expression = expression;
    }

    public SumParameter(String column) {
        this(new ColumnParameter(column));
    }

    @Override
    public String getKey() {
        return "SUM(" + this.expression.getKey() + ")";
    }

    @Override
    public List<Object> getValues() {
        return this.expression.getValues();
    }

    @Override
    public String toString() {
        return "SumParameter(expression=" + this.expression + ")";
    }
}
