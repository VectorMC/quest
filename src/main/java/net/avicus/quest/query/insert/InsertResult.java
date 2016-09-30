package net.avicus.quest.query.insert;

import net.avicus.quest.QueryResult;
import net.avicus.quest.Row;
import net.avicus.quest.database.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertResult implements QueryResult {
    private final int result;
    private final Row generated;

    public InsertResult(int result, Row generated) {
        this.result = result;
        this.generated = generated;
    }

    public int getResult() {
        return result;
    }

    public Row getGenerated() {
        return this.generated;
    }

    public static InsertResult execute(PreparedStatement statement) throws DatabaseException {
        try {
            int result = statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            set.next();
            Row generated = Row.fromResultSet(set);
            return new InsertResult(result, generated);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
