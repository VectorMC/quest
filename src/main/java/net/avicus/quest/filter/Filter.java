package net.avicus.quest.filter;

import net.avicus.quest.Parameter;
import net.avicus.quest.ParameterizedString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Filter {
    private final Parameter key;
    private final Parameter value;
    private final Comparison comparison;
    private final List<Filter> ands;
    private final List<Filter> ors;

    private Filter(Parameter key, Parameter value, Comparison comparison, List<Filter> ands, List<Filter> ors) {
        this.key = key;
        this.value = value;
        this.comparison = comparison;
        this.ands = ands;
        this.ors = ors;
    }

    /**
     * A filter (key compared to value with provided comparison method).
     * @param key
     * @param value
     * @param comparison
     */
    public Filter(Parameter key, Parameter value, Comparison comparison) {
        this(key, value, comparison, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * A filter, defaulting to a comparison of EQUAL.
     * @param key
     * @param value
     */
    public Filter(Parameter key, Parameter value) {
        this(key, value, Comparison.EQUAL, Collections.emptyList(), Collections.emptyList());
    }

    public Filter and(Filter filter) {
        List<Filter> ands = new ArrayList<>(this.ands.size() + 1);
        ands.addAll(this.ands);
        ands.add(filter);
        return new Filter(this.key, this.value, this.comparison, ands, this.ors);
    }

    public Filter or(Filter filter) {
        List<Filter> ors = new ArrayList<>(this.ors.size() + 1);
        ors.addAll(this.ors);
        ors.add(filter);
        return new Filter(this.key, this.value, this.comparison, this.ands, ors);
    }

    public ParameterizedString build() {
        StringBuilder sb = new StringBuilder();
        List<Parameter> parameters = new ArrayList<>();

        sb.append("(");
        sb.append(this.key.getKey());
        sb.append(this.comparison.toSql());
        sb.append(this.value.getKey());
        sb.append(")");

        parameters.add(this.key);
        parameters.add(this.value);

        if (!this.ands.isEmpty()) {
            sb.append(" AND ");
            if (this.ands.size() > 1)
                sb.append("(");
            for (Filter filter : this.ands) {
                ParameterizedString built = filter.build();
                sb.append(built.getSql());
                parameters.addAll(built.getParameters());

                if (!this.ands.get(this.ands.size() - 1).equals(filter)) {
                    sb.append(" AND ");
                }
            }
            if (this.ands.size() > 1)
                sb.append(")");
        }

        if (!this.ors.isEmpty()) {
            sb.append(" OR ");
            if (this.ors.size() > 1)
                sb.append("(");
            for (Filter filter : this.ors) {
                ParameterizedString built = filter.build();
                sb.append(built.getSql());
                parameters.addAll(built.getParameters());

                if (!this.ors.get(this.ors.size() - 1).equals(filter)) {
                    sb.append(" OR ");
                }
            }
            if (this.ors.size() > 1)
                sb.append(")");
        }

        return new ParameterizedString(sb.toString(), parameters);
    }
}
