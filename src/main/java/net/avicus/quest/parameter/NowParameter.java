package net.avicus.quest.parameter;

import net.avicus.quest.Parameter;

import java.util.Collections;
import java.util.List;

public class NowParameter implements Parameter {
    @Override
    public String getKey() {
        return "NOW()";
    }

    public List<Object> getObjects() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "NowParameter()";
    }
}
