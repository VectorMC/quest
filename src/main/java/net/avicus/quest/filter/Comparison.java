package net.avicus.quest.filter;

public interface Comparison {
    Comparison EQUAL = () -> "=";
    Comparison LESS_THAN = () -> "<";
    Comparison LESS_THAN_EQUAL = () -> "<=";
    Comparison GREATER_THAN = () -> ">";
    Comparison GREATER_THAN_EQUAL = () -> ">=";
    Comparison IN = () -> "IN";
    Comparison LIKE = () -> "LIKE";

    String toSql();
}
