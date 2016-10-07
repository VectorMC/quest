package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.List;

public class DirectionalParameter implements Parameter {
    private final Parameter parameter;
    private final Direction direction;

    public DirectionalParameter(String column) {
        this(column, Direction.ASC);
    }

    public DirectionalParameter(String column, Direction direction) {
        this(new FieldParameter(column), direction);
    }

    public DirectionalParameter(Parameter parameter) {
        this(parameter, Direction.ASC);
    }

    public DirectionalParameter(Parameter parameter, Direction direction) {
        this.parameter = parameter;
        this.direction = direction;
    }

    @Override
    public String getKey() {
        return this.parameter.getKey() + " " + this.direction.name();
    }

    public List<Object> getObjects() {
        return this.parameter.getObjects();
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
