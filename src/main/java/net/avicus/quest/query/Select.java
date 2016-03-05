package net.avicus.quest.query;

import lombok.Getter;
import lombok.ToString;
import net.avicus.quest.QuestUtils;
import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ToString
public class Select implements Filterable {
    @Getter private final Database database;
    @Getter private final String table;
    private final List<String> columns;
    private Optional<Filter> filter;
    private Optional<String> order;

    public Select(Database database, String table) {
        this.database = database;
        this.table = table;
        this.columns = new ArrayList<>();
        this.filter = Optional.empty();
        this.order = Optional.empty();
    }

    @Override
    public Select where(String field, Object value) {
        return this.where(field, value, Operator.EQUALS);
    }

    @Override
    public Select where(String field, Object value, Operator operator) {
        return this.where(new Filter(field, value, operator));
    }

    @Override
    public Select where(Filter filter) {
        if (this.filter.isPresent())
            this.filter.get().and(filter);
        else
            this.filter = Optional.of(filter);
        return this;
    }

    public Select columns(String... columns) {
        return columns(Arrays.asList(columns));
    }

    public Select columns(List<String> columns) {
        this.columns.clear();
        this.columns.addAll(columns);
        return this;
    }

    public Select order(String field) {
        return this.order(field, "ASC");
    }

    public Select order(String field, String direction) {
        this.order = Optional.of(QuestUtils.getField(field) + " " + direction);
        return this;
    }

    public String build() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(this.getColumnString());
        sql.append(" FROM ");
        sql.append("`");
        sql.append(this.table);
        sql.append("`");
        if (this.filter.isPresent()) {
            Optional<String> where = this.filter.get().build();
            if (where.isPresent()) {
                sql.append(" WHERE ");
                sql.append(where.get());
            }
        }
        if (this.order.isPresent()) {
            sql.append(" ORDER BY ");
            sql.append(this.order.get());
        }
        sql.append(";");
        return sql.toString();
    }

    public RowSet execute() throws DatabaseException {
        String sql = this.build();
        PreparedStatement statement = this.database.createQueryStatement(sql, false);
        try {
            return new RowSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed statement: %s", sql), e);
        }
    }

    public RowByRowSet executeByRow() throws DatabaseException {
        String sql = this.build();
        PreparedStatement statement = this.database.createQueryStatement(sql, true);
        try {
            return new RowByRowSet(statement.executeQuery());
        } catch (SQLException e) {
            throw new DatabaseException(String.format("Failed statement: %s", sql), e);
        }
    }

    private String getColumnString() {
        if (this.columns.size() == 0)
            return "*";
        else {
            List<String> columns = new ArrayList<>(this.columns);
            for (int i = 0; i < columns.size(); i++)
                columns.set(i, "`" + columns.get(i) + "`");
            return String.join(",", columns);
        }
    }
}
