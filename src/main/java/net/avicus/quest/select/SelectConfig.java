package net.avicus.quest.select;

import lombok.Builder;
import lombok.Data;
import net.avicus.quest.QueryConfig;
import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Builder
@Data
public class SelectConfig implements QueryConfig {
    public static SelectConfig DEFAULT = SelectConfig.builder().build();

    private final boolean iterable;
    private final int timeout;
    private final boolean poolable;

    public PreparedStatement createStatement(Database database, String sql) throws DatabaseException {
        try {
            PreparedStatement statement = database.createQueryStatement(sql, this.iterable);
            statement.setQueryTimeout(this.timeout);
            statement.setPoolable(this.poolable);
            return statement;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
