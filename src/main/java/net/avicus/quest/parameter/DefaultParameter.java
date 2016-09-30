package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Collections;
import java.util.List;

public class DefaultParameter implements Parameter {
    @Override
    public String getKey() {
        return "DEFAULT";
    }

    @Override
    public List<Object> getValues() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "DefaultParameter()";
    }
}
