import net.avicus.quest.Row;
import net.avicus.quest.User;
import net.avicus.quest.database.Database;
import net.avicus.quest.parameter.DirectionalParam.Direction;
import net.avicus.quest.parameter.FieldParam;
import net.avicus.quest.query.select.Select;
import net.avicus.quest.table.Table;

public class UserTable extends Table<User> {
    public UserTable(Database database, FieldParam table) {
        super(database, table);
    }

    public void test() {
        Select select = select().where(User.UNIQUE_ID.lt(5000));
        select = select.select(User.ID.sum());
        select = select.order(User.ID.asc());
    }

    @Override
    public User map(Row row) {
        return null;
    }
}
