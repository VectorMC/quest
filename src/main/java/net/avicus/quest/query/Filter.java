package net.avicus.quest.query;

import net.avicus.quest.Parameter;
import net.avicus.quest.ParameterizedString;
import net.avicus.quest.parameter.FieldParameter;
import net.avicus.quest.parameter.ObjectParameter;

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

    public Filter(Parameter key, Parameter value, Comparison comparison) {
        this(key, value, comparison, Collections.emptyList(), Collections.emptyList());
    }

    public Filter(Parameter key, Parameter value) {
        this(key, value, Comparison.EQUAL, Collections.emptyList(), Collections.emptyList());
    }

    public Filter(Parameter key, Object value, Comparison comparison) {
        this(key, new ObjectParameter(value), comparison);
    }

    public Filter(Parameter key, Object value) {
        this(key, new ObjectParameter(value));
    }

    public Filter(String field, Object value, Comparison comparison) {
        this(new FieldParameter(field), new ObjectParameter(value), comparison);
    }

    public Filter(String field, Object value) {
        this(new FieldParameter(field), new ObjectParameter(value));
    }

    public Filter(String field, Parameter value, Comparison comparison) {
        this(new FieldParameter(field), value, comparison);
    }

    public Filter(String field, Parameter value) {
        this(new FieldParameter(field), value);
    }

    public Filter duplicate() {
        Filter filter = new Filter(this.key, this.value, this.comparison);
        filter.ands.addAll(this.ands);
        filter.ors.addAll(this.ors);
        return filter;
    }

    public Filter and(Filter filter) {
        return duplicate().and(filter);
    }

    public Filter or(Filter filter) {
        return duplicate().or(filter);
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
