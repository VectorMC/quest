package net.avicus.quest.select;

import net.avicus.quest.Query;
import net.avicus.quest.database.Database;

import java.sql.PreparedStatement;
import java.util.Optional;

public class Select implements Query<SelectResult, SelectConfig> {
    private final Database database;

    public Select(Database database) {
        this.database = database;
    }



    @Override
    public SelectResult execute(Optional<SelectConfig> config) {
        PreparedStatement statement = this.database.createQueryStatement(null, false);

        // Configuration
        if (config.isPresent()) {
            config.get().apply(statement);
        }



        return null;
    }
}
