package net.avicus.quest;

import lombok.Data;
import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseConfig;
import net.avicus.quest.filter.Comparison;
import net.avicus.quest.filter.Filter;
import net.avicus.quest.parameter.*;
import net.avicus.quest.parameter.OrderParameter.Direction;
import net.avicus.quest.select.Select;
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
        Select select = new Select(db, new TableParameter("users"));
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

        Select select = new Select(db, new TableParameter("users"));
        select = select.select(new WildcardParameter(), new CustomParameter("@curr := @curr + 1 AS rank"));
        select = select.order(new OrderParameter("date", Direction.DESC));
        select = select.limit(5);

        System.out.println(select.execute());
    }

    @Test
    public void sum() {
        ColumnParameter amount = new ColumnParameter("amount");
        SumParameter sum = new SumParameter(amount);
        System.out.println(sum.toString());
    }
}
