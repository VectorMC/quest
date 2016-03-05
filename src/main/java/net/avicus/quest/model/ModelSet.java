package net.avicus.quest.model;

import lombok.ToString;
import net.avicus.quest.database.DatabaseException;
import net.avicus.quest.query.Row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ToString
public class ModelSet<M extends Model> implements Iterable<M> {
    private final Table<M> table;
    private final List<M> rows;

    public ModelSet(Table<M> table, ResultSet resultSet) {
        this.table = table;
        this.rows = createRows(resultSet);
    }

    private List<M> createRows(ResultSet resultSet) {
        List<M> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(this.table.newInstance(new Row(resultSet)));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
        return list;
    }

    @Override
    public Iterator<M> iterator() {
        return this.rows.iterator();
    }
}
