package net.avicus.quest;

import net.avicus.quest.filter.Comparison;
import net.avicus.quest.filter.Filter;
import net.avicus.quest.parameter.NowParameter;
import net.avicus.quest.parameter.NullParameter;
import net.avicus.quest.parameter.ObjectParameter;
import org.junit.Test;

import java.util.Date;

public class FilterTest {
    @Test
    public void filter() {
        Filter filter = new Filter(new NowParameter(), new ObjectParameter(new Date()), Comparison.EQUAL);
        Filter anded = filter.and(new Filter(new NowParameter(), new NowParameter(), Comparison.EQUAL));
        Filter ored = anded.or(new Filter(new NullParameter(), new ObjectParameter(null), Comparison.EQUAL));

        System.out.println(filter.build());
        System.out.println(anded.build());
        System.out.println(ored.build());
    }
}
