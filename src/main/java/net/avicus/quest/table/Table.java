package net.avicus.quest.table;

import net.avicus.quest.Row;
import net.avicus.quest.database.Database;
import net.avicus.quest.parameter.FieldParameter;
import net.avicus.quest.query.select.Select;
import net.avicus.quest.query.update.Update;

public abstract class Table<T> {
    private final Database database;
    private final FieldParameter table;

    public Table(Database database, FieldParameter table) {
        this.database = database;
        this.table = table;
    }

    public Update update() {
        return new Update(this.database, this.table);
    }

    public Select select() {
        return new Select(this.database, this.table);
    }

    public Database getDatabase() {
        return this.database;
    }

    public FieldParameter getTableParameter() {
        return this.table;
    }

    protected abstract Row toRow();

    protected abstract T fromRow(Row row);
}
