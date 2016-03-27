package net.avicus.quest.query;

import lombok.ToString;
import net.avicus.quest.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ToString
public class RowList extends ArrayList<Row> {
    public RowList(ResultSet resultSet) {
        addAll(createRows(resultSet));
    }

    public Row first() {
        return get(0);
    }

    public Row last() {
        return get(size() - 1);
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
}
