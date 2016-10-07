package net.avicus.quest.query.select;

import net.avicus.quest.Parameter;
import net.avicus.quest.ParameterizedString;
import net.avicus.quest.query.Query;
import net.avicus.quest.database.Database;
import net.avicus.quest.database.DatabaseException;
import net.avicus.quest.query.Filter;
import net.avicus.quest.query.Filterable;
import net.avicus.quest.parameter.*;

import java.sql.PreparedStatement;
import java.util.*;

public class Select implements Query<SelectResult, SelectConfig>, Filterable<Select> {
    private final Database database;
    private final FieldParameter table;
    private Filter filter;
    private List<Parameter> columns;
    private Parameter offset;
    private Parameter limit;
    private Parameter groupBy;
    private List<Parameter> order;
    private CustomParameter join;

    public Select(Database database, FieldParameter table) {
        this.database = database;
        this.table = table;
    }

    public Select duplicate() {
        Select copy = new Select(this.database, this.table);
        copy.filter = this.filter;
        copy.columns = this.columns == null ? null : new ArrayList<>(this.columns);
        copy.offset = this.offset;
        copy.limit = this.limit;
        copy.groupBy = this.groupBy;
        copy.order = this.order;
        copy.join = this.join;
        return copy;
    }

    public Select where(Filter filter) {
        if (this.filter != null) {
            return where(this.filter, filter);
        }
        else {
            Select select = duplicate();
            select.filter = filter;
            return select;
        }
    }

    public Select where(Filter filter, Filter and, Filter... ands) {
        // And the ands
        Filter result = filter.and(and);
        for (Filter additional : ands) {
            result = result.and(additional);
        }

        Select select = duplicate();
        select.filter = result;
        return select;
    }

    public Select select(List<Parameter> columns) {
        Select select = duplicate();
        select.columns = columns;
        return select;
    }

    public Select select(String... columns) {
        List<Parameter> parameters = new ArrayList<>();
        for (String column : columns) {
            parameters.add(new FieldParameter(column));
        }
        return select(parameters);
    }

    public Select select(Parameter... columns) {
        return select(Arrays.asList(columns));
    }

    public Select groupBy(Parameter groupBy) {
        Select select = duplicate();
        select.groupBy = groupBy;
        return select;
    }

    public Select offset(int offset) {
        return offset(new ObjectParameter(offset));
    }

    public Select offset(Parameter offset) {
        Select select = duplicate();
        select.offset = offset;
        return select;
    }

    public Select limit(int limit) {
        return limit(new ObjectParameter(limit));
    }

    public Select limit(Parameter limit) {
        Select select = duplicate();
        select.limit = limit;
        return select;
    }

    public Select order(Parameter... order) {
        return order(Arrays.asList(order));
    }

    public Select order(List<Parameter> order) {
        Select select = duplicate();
        select.order = order;
        return select;
    }

    public Select join(CustomParameter join) {
        Select select = duplicate();
        select.join = join;
        return select;
    }

    public Select join(String sql) {
        return join(new CustomParameter(sql));
    }

    public Select join(String sql, Parameter... params) {
        return join(new CustomParameter(sql, params));
    }

    public ParameterizedString build() {
        StringBuilder sb = new StringBuilder();
        List<Parameter> parameters = new ArrayList<>();

        sb.append("SELECT ");

        // Columns to select
        List<Parameter> columns = this.columns;
        if (columns == null || columns.isEmpty()) {
            columns = Collections.singletonList(new WildcardParameter());
        }
        for (Parameter column : columns) {
            sb.append(column.getKey());
            parameters.add(column);

            if (!columns.get(columns.size() - 1).equals(column)) {
                sb.append(", ");
            }
        }

        sb.append(" FROM ");

        sb.append(this.table.getKey());
        parameters.add(this.table);

        if (this.join != null) {
            sb.append(" ");
            sb.append(this.join.getKey());
            parameters.add(this.join);
        }

        if (this.filter != null) {
            sb.append(" WHERE ");
            ParameterizedString filterString = this.filter.build();
            sb.append(filterString.getSql());
            parameters.addAll(filterString.getParameters());
        }

        if (this.groupBy != null) {
            sb.append(" GROUP BY ");
            sb.append(this.groupBy.getKey());
            parameters.add(this.groupBy);
        }

        if (this.order != null) {
            sb.append(" ORDER BY ");
            for (Parameter order : this.order) {
                sb.append(order.getKey());
                parameters.add(order);
            }
        }

        if (this.limit != null) {
            sb.append(" LIMIT ");
            sb.append(this.limit.getKey());
            parameters.add(this.limit);

            if (this.offset != null) {
                sb.append(" OFFSET ");
                sb.append(this.offset.getKey());
                parameters.add(this.limit);
            }
        }

        return new ParameterizedString(sb.toString(), parameters);
    }

    @Override
    public SelectResult execute(Optional<SelectConfig> config) throws DatabaseException {
        // The query
        ParameterizedString query = build();

        // Create statement
        PreparedStatement statement = config.orElse(SelectConfig.DEFAULT).createStatement(this.database, query.getSql());

        // Add variables (?, ?)
        query.apply(statement, 1);

        return SelectResult.execute(statement);
    }

    @Override
    public String toString() {
        return "Select(" + build() + ")";
    }
}
