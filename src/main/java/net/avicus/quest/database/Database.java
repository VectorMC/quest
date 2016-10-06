package net.avicus.quest.database;

import net.avicus.quest.parameter.FieldParameter;
import net.avicus.quest.query.insert.Insert;
import net.avicus.quest.query.select.Select;
import net.avicus.quest.database.url.DatabaseUrl;
import net.avicus.quest.query.update.Update;

import java.sql.*;
import java.util.Optional;

public class Database {
    private final DatabaseUrl url;
    private Connection connection;

    public Database(DatabaseUrl url) {
        this.url = url;
    }

    public void open() throws DatabaseException {
        this.connection = this.url.establishConnection();
    }

    public void close() throws DatabaseException {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public Optional<Connection> getConnection() {
        return Optional.ofNullable(this.connection);
    }

    public Insert insert(FieldParameter table) {
        return new Insert(this, table);
    }

    public Insert insert(String table) {
        return insert(new FieldParameter(table));
    }

    public Update update(FieldParameter table) {
        return new Update(this, table);
    }

    public Update update(String table) {
        return new Update(this, new FieldParameter(table));
    }

    public Select select(FieldParameter table) {
        return new Select(this, table);
    }

    public Select select(String table) {
        return select(new FieldParameter(table));
    }

    public PreparedStatement createUpdateStatement(String sql) {
        try {
            return this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement createQueryStatement(String sql, boolean iterate) throws DatabaseException {
        try {
            PreparedStatement statement;

            if (iterate) {
                statement = this.connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                statement.setFetchSize(Integer.MIN_VALUE);
            }
            else {
                statement = this.connection.prepareStatement(sql);
            }

            return statement;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
