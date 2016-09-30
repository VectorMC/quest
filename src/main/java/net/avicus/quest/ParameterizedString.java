package net.avicus.quest;

import net.avicus.quest.database.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ParameterizedString {
    private final String sql;
    private final List<Parameter> parameters;

    public ParameterizedString(String sql, List<Parameter> parameters) {
        this.sql = sql;
        this.parameters = parameters;
    }

    /**
     * Applies parameter objects to a statement, given the starting parameter number.
     * @param statement
     * @param startingParameter
     * @return The next parameter number.
     */
    public int apply(PreparedStatement statement, int startingParameter) {
        int next = startingParameter;

        for (Parameter parameter : this.parameters) {
            for (Object value : parameter.getObjects()) {
                try {
                    statement.setObject(next, value);
                } catch (SQLException e) {
                    throw new DatabaseException(e);
                }
                next++;
            }
        }

        return next;
    }

    public String getSql() {
        return this.sql;
    }

    public Collection<Parameter> getParameters() {
        return this.parameters;
    }

    @Override
    public String toString() {
        return "ParameterizedString(sql=" + this.sql + ", parameters=" + this.parameters + ")";
    }
}
