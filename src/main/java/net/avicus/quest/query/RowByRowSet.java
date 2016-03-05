package net.avicus.quest.query;

import lombok.ToString;
import net.avicus.quest.database.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

@ToString
public class RowByRowSet implements Iterator<Row> {
    private final ResultSet resultSet;
    private boolean started = false;

    public RowByRowSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public boolean hasNext() {
        try {
            if (!this.started)
                return this.resultSet.isBeforeFirst() || this.resultSet.getRow() != 0;
            return !this.resultSet.isLast();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Row next() throws DatabaseException {
        try {
            this.started = true;
            this.resultSet.next();
            return new Row(this.resultSet);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
