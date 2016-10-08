package net.avicus.quest.query.insert;

import net.avicus.quest.Param;
import net.avicus.quest.ParamString;
import net.avicus.quest.parameter.FieldParam;
import net.avicus.quest.query.Query;
import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseException;

import java.sql.PreparedStatement;
import java.util.*;

public class Insert implements Query<InsertResult, InsertConfig> {
    private final Database database;
    private final FieldParam table;
    private final List<Insertion> insertions;

    public Insert(Database database, FieldParam table) {
        this.database = database;
        this.table = table;
        this.insertions = new ArrayList<>();
    }

    public Insert duplicate() {
        Insert copy = new Insert(this.database, this.table);
        copy.insertions.addAll(this.insertions);
        return copy;
    }

    public Insert plus(Insertion insertion) {
        Insert query = duplicate();
        query.insertions.add(insertion);
        return query;
    }

    public ParamString build() {
        if (this.insertions.isEmpty()) {
            throw new DatabaseException("No insertions to be made.");
        }

        StringBuilder sb = new StringBuilder();
        List<Param> parameters = new ArrayList<>();

        sb.append("INSERT INTO ");

        sb.append(this.table.getKey());
        parameters.add(this.table);

        sb.append(" (");
        Set<String> columns = new HashSet<>();
        for (Insertion insertion : this.insertions) {
            for (String column : insertion.getColumns()) {
                if (!columns.contains(column)) {
                    columns.add(column);
                    sb.append("`").append(column).append("`");
                    sb.append(", ");
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
        sb.append(")");

        sb.append(" VALUES ");
        for (Insertion insertion : this.insertions) {
            sb.append("(");
            for (String column : columns) {
                Param value = insertion.getValue(column);
                sb.append(value.getKey());
                parameters.add(value);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
            sb.append(")");
        }

        return new ParamString(sb.toString(), parameters);
    }

    @Override
    public InsertResult execute(Optional<InsertConfig> optConfig) throws DatabaseException {
        InsertConfig config = optConfig.orElse(InsertConfig.DEFAULT);

        // The query
        ParamString query = build();

        // Create statement
        PreparedStatement statement = config.createStatement(this.database, query.getSql());

        // Add variables (?, ?)
        query.apply(statement, 1);

        return InsertResult.execute(statement);
    }

    @Override
    public String toString() {
        return "Update(" + build() + ")";
    }
}
