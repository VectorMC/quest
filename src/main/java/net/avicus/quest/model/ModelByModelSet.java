package net.avicus.quest.model;

import lombok.ToString;
import net.avicus.quest.database.DatabaseException;
import net.avicus.quest.query.RowByRowSet;

import java.sql.ResultSet;
import java.util.Iterator;

@ToString
public class ModelByModelSet<M extends Model> implements Iterator<M> {
    private final Table<M> table;
    private final RowByRowSet rowByRowSet;

    public ModelByModelSet(Table<M> table, ResultSet resultSet) {
        this.table = table;
        this.rowByRowSet = new RowByRowSet(resultSet);
    }

    @Override
    public boolean hasNext() {
        return this.rowByRowSet.hasNext();
    }

    @Override
    public M next() throws DatabaseException {
        return this.table.newInstance(this.rowByRowSet.next());
    }
}
