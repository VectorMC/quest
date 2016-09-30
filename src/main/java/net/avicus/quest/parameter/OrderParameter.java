package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class OrderParameter implements Parameter {
    private final Parameter parameter;
    private final Direction direction;

    public OrderParameter(String column) {
        this(column, Direction.ASC);
    }

    public OrderParameter(String column, Direction direction) {
        this(new ColumnParameter(column), direction);
    }

    public OrderParameter(Parameter parameter) {
        this(parameter, Direction.ASC);
    }

    public OrderParameter(Parameter parameter, Direction direction) {
        this.parameter = parameter;
        this.direction = direction;
    }

    @Override
    public String getKey() {
        return this.parameter.getKey() + " " + this.direction.name();
    }

    @Override
    public List<Object> getValues() {
        return this.parameter.getValues();
    }

    public enum Direction {
        /**
         * Ascending
         */
        ASC,

        /**
         * Descending
         */
        DESC
    }
}
