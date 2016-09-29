package net.avicus.quest.database;

import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    @Getter private final DatabaseConfig config;
    @Getter private final DatabaseConnection connection;

    public Database(DatabaseConfig config) {
        this.config = config;
        this.connection = new DatabaseConnection(this);
    }

    public PreparedStatement createUpdateStatement(String sql) {
        try {
            return this.connection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public PreparedStatement createQueryStatement(String sql, boolean iterate) throws DatabaseException {
        try {
            PreparedStatement statement;

            if (iterate) {
                statement = this.connection.getConnection().prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                statement.setFetchSize(Integer.MIN_VALUE);
            }
            else {
                statement = this.connection.getConnection().prepareStatement(sql);
            }

            return statement;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public DatabaseConnection connect() throws DatabaseException {
        this.connection.open();
        return this.connection;
    }

    public DatabaseConnection disconnect() throws DatabaseException {
        this.connection.close();
        return this.connection;
    }
}
