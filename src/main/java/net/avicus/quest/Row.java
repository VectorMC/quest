package net.avicus.quest;

import net.avicus.quest.database.DatabaseException;
import net.avicus.quest.query.select.SelectResult;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A row from a result set. ResultSet's are silly because they
 * go away. We create a row to keep data/metadata from a single row.
 */
public class Row {
    private final List<String> columnNames;
    private final List<RowValue> values;

    public Row(List<String> columnNames, List<RowValue> values) {
        this.columnNames = columnNames;
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
        int number = this.columnNames.indexOf(column) + 1;
        return getValue(number);
    }

    public static Row fromSelectResultSet(SelectResult result, ResultSet set) {
        List<RowValue> values = new ArrayList<>();
        for (int i = 1; i <= result.getColumnCount(); i++) {
            try {
                values.add(new RowValue(set.getObject(i)));
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        return new Row(result.getColumnNames(), values);
    }

    public static Row fromResultSet(ResultSet set) {
        List<String> columnNames = new ArrayList<>();
        List<RowValue> values = new ArrayList<>();

        try {
            for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
                columnNames.add(set.getMetaData().getColumnName(i));
                values.add(new RowValue(set.getObject(i)));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

        return new Row(columnNames, values);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.columnNames.size(); i++) {
            sb.append(this.columnNames.get(i));
            sb.append(": ");
            sb.append(this.values.get(i));
            if (i != this.columnNames.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
