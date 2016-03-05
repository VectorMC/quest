package net.avicus.quest.query;

import lombok.ToString;
import net.avicus.quest.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@ToString
public class RowSet implements Iterable<Row> {
    private final List<Row> rows;

    public RowSet(ResultSet resultSet) {
        this.rows = createRows(resultSet);
    }

    public Row get(int index) {
        return this.rows.get(index);
    }

    public Row first() {
        return this.rows.get(0);
    }

    public Row last() {
        return this.rows.get(this.rows.size() - 1);
    }

    public int size() {
        return this.rows.size();
    }

    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    public List<Row> subList(int fromIndex, int toIndex) {
        return this.rows.subList(fromIndex, toIndex);
    }

    private List<Row> createRows(ResultSet resultSet) {
        List<Row> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(new Row(resultSet));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return list;
    }

    @Override
    public Iterator<Row> iterator() {
        return this.rows.iterator();
    }
}
