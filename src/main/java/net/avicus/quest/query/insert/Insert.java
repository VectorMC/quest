package net.avicus.quest.query.insert;

import net.avicus.quest.Parameter;
import net.avicus.quest.ParameterizedString;
import net.avicus.quest.Query;
import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseException;
import net.avicus.quest.parameter.FieldParameter;

import java.sql.PreparedStatement;
import java.util.*;

public class Insert implements Query<InsertResult, InsertConfig> {
    private final Database database;
    private final FieldParameter table;
    private final List<Insertion> insertions;

    public Insert(Database database, FieldParameter table) {
        this.database = database;
        this.table = table;
        this.insertions = new ArrayList<>();
    }

    public Insert duplicate() {
        Insert copy = new Insert(this.database, this.table);
        copy.insertions.addAll(this.insertions);
        return copy;
    }

    public Insert add(Insertion insertion) {
        Insert query = duplicate();
        query.insertions.add(insertion);
        return query;
    }

    public ParameterizedString build() {
        if (this.insertions.isEmpty()) {
            throw new DatabaseException("No insertions to be made.");
        }

        StringBuilder sb = new StringBuilder();
        List<Parameter> parameters = new ArrayList<>();

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
                Parameter value = insertion.getValue(column);
                sb.append(value.getKey());
                parameters.add(value);
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
            sb.append(")");
        }

        return new ParameterizedString(sb.toString(), parameters);
    }

    @Override
    public InsertResult execute(Optional<InsertConfig> config) throws DatabaseException {
        // The query
        ParameterizedString query = build();

        // Create statement
        PreparedStatement statement = config.orElse(InsertConfig.DEFAULT).createStatement(this.database, query.getSql());

        // Add variables (?, ?)
        query.apply(statement, 1);

        return InsertResult.execute(statement);
    }

    @Override
    public String toString() {
        return "Update(" + build() + ")";
    }
}
