package net.avicus.quest.select;

import net.avicus.quest.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A row from a result set. ResultSet's are silly because they
 * go away. We create a row to keep data/metadata from a single row.
 */
public class Row {
    private final List<RowValue> values;
    private final SelectResult result;

    public Row(SelectResult result, List<RowValue> values) {
        this.result = result;
        this.values = values;
    }

    public RowValue getValue(int column) throws DatabaseException {
        int index = column - 1;
        if (index < 0 || index >= this.values.size()) {
            throw new DatabaseException("Invalid column number: " + column + ".");
        }
        return this.values.get(index);
    }

    public RowValue getValue(String column) {
        int number = this.result.getColumnNumber(column);
        return getValue(number);
    }

    public static Row fromResultSet(SelectResult result, ResultSet set) {
        List<RowValue> values = new ArrayList<>();
        for (int i = 1; i <= result.getColumnCount(); i++) {
            try {
                values.add(new RowValue(set.getObject(i)));
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        return new Row(result, values);
    }

    @Override
    public String toString() {
        return this.values.toString();
    }
}
