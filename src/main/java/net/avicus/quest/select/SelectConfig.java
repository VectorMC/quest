package net.avicus.quest.select;

import lombok.Builder;
import lombok.Data;
import net.avicus.quest.QueryConfig;
import net.avicus.quest.database.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Builder
@Data
public class SelectConfig implements QueryConfig {
    private final int timeout;
    private final boolean poolable;

    public void apply(PreparedStatement statement) throws DatabaseException {
        try {
            statement.setQueryTimeout(this.timeout);
            statement.setPoolable(this.poolable);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
