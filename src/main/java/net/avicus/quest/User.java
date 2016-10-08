package net.avicus.quest;

import net.avicus.quest.query.insert.Insertion;
import net.avicus.quest.table.Column;
import net.avicus.quest.table.MappedColumn;
import net.avicus.quest.table.Model;

import java.util.UUID;
import java.util.function.Function;

public class User implements Model {
    public static Column<Integer> ID = new Column<>("id");
    public static Column<String> NAME = new Column<>("username");
    public static MappedColumn<String, UUID> UNIQUE_ID = new MappedColumn<>("uuid", new Function<String, java.util.UUID>() {
        @Override
        public UUID apply(String text) {
            return UUID.fromString(text);
        }
    });

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public Insertion toInsertion() {
        return null;
    }

    @Override
    public Row toRow() {
        return null;
    }
}
