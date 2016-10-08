package net.avicus.quest.parameter;

import net.avicus.quest.Param;

import java.util.List;

public class CountParam implements Param {
    public static final CountParam WILDCARD = new CountParam(WildcardParam.INSTANCE);

    private final Param expression;

    public CountParam(Param expression) {
        this.expression = expression;
    }

    public CountParam(String column) {
        this(new FieldParam(column));
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
        return "CountParam(expression=" + this.expression + ")";
    }
}
