package net.avicus.quest.query;

public interface Comparison {
    Comparison EQUAL = () -> " = ";
    Comparison LESS_THAN = () -> " < ";
    Comparison LESS_THAN_EQUAL = () -> " <= ";
    Comparison GREATER_THAN = () -> " > ";
    Comparison GREATER_THAN_EQUAL = () -> " >= ";
    Comparison IN = () -> " IN ";
    Comparison LIKE = () -> " LIKE ";
    Comparison BETWEEN = () -> " BETWEEN ";

    /**
     * The SQL representation of the comparison method.
     * @return
     */
    String toSql();
}
