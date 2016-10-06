package net.avicus.quest;

import org.junit.Test;

import java.util.Arrays;

public class FilterTest {
    @Test
    public void test() {
        Row row = new Row(Arrays.asList("id", "name"), Arrays.asList(new RowValue(1), new RowValue("funky")));
        System.out.println(row.toString());
    }
}
