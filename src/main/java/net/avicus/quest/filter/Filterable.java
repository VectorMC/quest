package net.avicus.quest.filter;

import net.avicus.quest.Parameter;
import net.avicus.quest.Query;
import net.avicus.quest.filter.Comparison;
import net.avicus.quest.filter.Filter;
import net.avicus.quest.parameter.ColumnParameter;
import net.avicus.quest.parameter.ObjectParameter;

/**
 * Something that can be where'd of course.
 * @param <C> The resulting object.
 */
public interface Filterable<C extends Query> {
    C where(Filter filter);

    default C where(String column, Object value) {
        return where(new ColumnParameter(column), value);
    }

    default C where(String column, Parameter value) {
        return where(new ColumnParameter(column), value);
    }

    default C where(String column, Object value, Comparison comparison) {
        return where(new ColumnParameter(column), value, comparison);
    }

    default C where(String column, Parameter value, Comparison comparison) {
        return where(new ColumnParameter(column), value, comparison);
    }

    default C where(Parameter column, Object value) {
        return where(column, new ObjectParameter(value));
    }

    default C where(Parameter column, Parameter value) {
        return where(column, value, Comparison.EQUAL);
    }

    default C where(Parameter column, Object value, Comparison comparison) {
        return where(new Filter(column, new ObjectParameter(value), comparison));
    }

    default C where(Parameter column, Parameter value, Comparison comparison) {
        return where(new Filter(column, value, comparison));
    }
}
