package net.avicus.quest.model;

import lombok.Getter;
import net.avicus.quest.database.DatabaseException;
import net.avicus.quest.query.Row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelList<M extends Model> extends ArrayList<M> {
    @Getter private final Table<M> table;

    public ModelList(Table<M> table, ResultSet resultSet) {
        this.table = table;
        addAll(createRows(resultSet));
    }

    public M first() {
        return get(0);
    }

    public M last() {
        return get(size() - 1);
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
    public String toString() {
        return super.toString();
    }
}
