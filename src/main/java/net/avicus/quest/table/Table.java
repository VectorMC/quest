package net.avicus.quest.table;

import net.avicus.quest.Row;
import net.avicus.quest.database.Database;
import net.avicus.quest.parameter.FieldParameter;
import net.avicus.quest.query.delete.Delete;
import net.avicus.quest.query.insert.Insert;
import net.avicus.quest.query.insert.InsertConfig;
import net.avicus.quest.query.insert.InsertResult;
import net.avicus.quest.query.select.Select;
import net.avicus.quest.query.update.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Table<M extends Model> implements RowMapper<M> {
    private final Database database;
    private final FieldParameter table;

    public Table(Database database, FieldParameter table) {
        this.database = database;
        this.table = table;
    }

    public Table(Database database, String table) {
        this(database, new FieldParameter(table));
    }

    public Delete delete() {
        return new Delete(this.database, this.table);
    }

    public Insert insert() {
        return new Insert(this.database, this.table);
    }

    public Select select() {
        return new Select(this.database, this.table);
    }

    public Insert insert(M model) {
        Insert insert = new Insert(this.database, this.table);
        insert = insert.plus(model.toInsertion());
        return insert;
    }

    public void insert(M model, Consumer<Row> handler) {
        Insert insert = new Insert(this.database, this.table);
        insert = insert.plus(model.toInsertion());

        InsertResult result = insert.execute();
        if (result.getGenerated().isPresent()) {
            handler.accept(result.getGenerated().get());
        }
    }

    public Update update() {
        return new Update(this.database, this.table);
    }

    public Update update(M model, String... excludes) {
        return update(model, Arrays.asList(excludes));
    }

    public Update update(M model, List<String> excludes) {
        Update update = update().where(model.toFilter());
        Map<String, Object> row = model.toRow().toMap();
        row.keySet().removeAll(excludes);
        for (String column : row.keySet()) {
            update = update.set(column, row.get(column));
        }
        return update;
    }

    public Update updateFields(M model, String... columns) {
        return updateFields(model, Arrays.asList(columns));
    }

    public Update updateFields(M model, List<String> columns) {
        Update update = update().where(model.toFilter());
        Map<String, Object> row = model.toRow().toMap();
        for (String column : columns) {
            update = update.set(column, row.get(column));
        }
        return update;
    }

    public Database getDatabase() {
        return this.database;
    }

    public FieldParameter getTableParameter() {
        return this.table;
    }
}