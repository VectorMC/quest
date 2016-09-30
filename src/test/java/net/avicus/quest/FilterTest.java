package net.avicus.quest;

import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseConfig;
import net.avicus.quest.filter.Comparison;
import net.avicus.quest.filter.Filter;
import net.avicus.quest.parameter.*;
import net.avicus.quest.parameter.OrderParameter.Direction;
import net.avicus.quest.query.delete.Delete;
import net.avicus.quest.query.insert.Insert;
import net.avicus.quest.query.insert.Insertion;
import net.avicus.quest.query.select.Select;
import net.avicus.quest.query.select.SelectConfig;
import net.avicus.quest.query.select.SelectResult;
import net.avicus.quest.query.update.Update;
import org.junit.Test;

import java.util.Date;

public class FilterTest {
    private static final Database db;

    static {
        db = new Database(DatabaseConfig.builder("localhost", "testing", "root", "").build());
        db.connect();
    }

    @Test
    public void andAndOr() {
        Filter filter = new Filter(new NowParameter(), new ObjectParameter(new Date()), Comparison.EQUAL);
        Filter anded = filter.and(new Filter(new NowParameter(), new NowParameter(), Comparison.EQUAL));
        Filter ored = anded.or(new Filter(new NullParameter(), new ObjectParameter(null), Comparison.EQUAL));

        System.out.println(filter.build());
        System.out.println(anded.build());
        System.out.println(ored.build());
    }

    @Test
    public void select() {
        Select select = new Select(db, new FieldParameter("users"));
        select = select.where("id", 5);
        select = select.where("key", 50, Comparison.LESS_THAN);
        select = select.where("dob", new Date(), Comparison.GREATER_THAN_EQUAL);
        select = select.limit(1).offset(5);
        select = select.limit(5);

        System.out.println(select.build());
        select.execute();
    }


    @Test
    public void customSelect() {
        Select select = new Select(db, new FieldParameter("users"));
        select = select.select(new MinParameter("age"));
        select = select.order(new OrderParameter("dob", Direction.DESC));
        select = select.limit(5);

        SelectResult result = select.execute(SelectConfig.builder().iterable(true).build());
        while (result.next()) {
            Row row = result.getCurrent();
            System.out.println(result.getColumnNames());
            System.out.println(row);
        }
    }

    @Test
    public void update() {
        Update update = new Update(db, new FieldParameter("testing", "users"));
        update = update.set("name", "Keenan");
        update = update.set("age", 19);
        update = update.where("name", "Keenan");

        System.out.println(update.execute().getResult());
    }

    @Test
    public void delete() {
        Delete delete = new Delete(db, new FieldParameter("testing", "users"));
        delete = delete.where("id", 5, Comparison.GREATER_THAN_EQUAL);

        System.out.println(delete.execute().getResult());
    }

    @Test
    public void insert() {
        Insert insert = new Insert(db, new FieldParameter("users"));
        insert = insert.add(new Insertion("name", "Adam").with("age", 19).with("nickname", "Adamster"));

        System.out.println(insert.build());

        System.out.println(insert.execute().getGenerated());
    }

    @Test
    public void join() {
        Select select = new Select(db, new FieldParameter("users"));
        select = select.select(new FieldParameter("users", "id"), new FieldParameter("purchases", "product_name"), new FieldParameter("purchases", "amount"));
        select = select.join("JOIN `purchases` ON `users`.`id`=`purchases`.`user_id`");

        System.out.println(select.build());
        for (Row row : select.execute().toList()) {
            System.out.print(row.getValue(1));
            System.out.print(row.getValue(2));
            System.out.print(row.getValue(3));
            System.out.println();
        }
    }
}
