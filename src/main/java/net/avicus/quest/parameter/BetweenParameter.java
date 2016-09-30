package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.ArrayList;
import java.util.List;

public class BetweenParameter implements Parameter {
    private final Parameter low;
    private final Parameter high;
    private final List<Object> values;

    public BetweenParameter(Parameter low, Parameter high) {
        this.low = low;
        this.high = high;
        this.values = new ArrayList<>();
        this.values.addAll(low.getValues());
        this.values.addAll(high.getValues());
    }

    public BetweenParameter(int low, int high) {
        this(new ObjectParameter(low), new ObjectParameter(high));
    }

    @Override
    public String getKey() {
        return "? AND ?";
    }

    @Override
    public List<Object> getValues() {
        return this.values;
    }

    @Override
    public String toString() {
        return "BetweenParameter(low=" + this.low + ", high=" + this.high + ")";
    }
}
