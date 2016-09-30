package net.avicus.quest.select;

import net.avicus.quest.database.DatabaseException;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class RowValue {
    private final Object data;

    public RowValue(Object data) {
        this.data = data;
    }

    public <T> T as(Class<T> type) {
        T data = asNullable(type).orElse(null);
        if (data == null) {
            throw new DatabaseException("Unexpected null value.");
        }
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> asNullable(Class<T> type) {
        if (this.data == null)
            return Optional.empty();

        T data = (T) this.data;
        return Optional.of(data);
    }

    public Optional<String> asNullableString() {
        return asNullable(String.class);
    }

    public String asString() {
        return as(String.class);
    }

    public Optional<Date> asNullableDate() {
        return asNullable(Date.class);
    }

    public Date asDate() {
        return as(Date.class);
    }

    public Optional<Time> asNullableTime() {
        return asNullable(Time.class);
    }

    public Date asTime() {
        return as(Time.class);
    }

    public Optional<Timestamp> asNullableTimestamp() {
        return asNullable(Timestamp.class);
    }

    public Timestamp asTimestamp() {
        return as(Timestamp.class);
    }

    public Optional<Boolean> asNullableBoolean() {
        return asNullable(Object.class).map(RowValue::booleanValue);
    }

    public boolean asBoolean() {
        return booleanValue(as(Object.class));
    }

    public Optional<Integer> asNullableInteger() {
        return asNullable(Integer.class);
    }

    public int asInteger() {
        return as(Integer.class);
    }

    public Optional<Long> asNullableLong() {
        return asNullable(Long.class);
    }

    public long asLong() {
        return as(Long.class);
    }

    public Optional<Float> asNullableFloat() {
        return asNullable(Float.class);
    }

    public float asFloat() {
        return as(Float.class);
    }

    public Optional<Double> asNullableDouble() {
        return asNullable(Double.class);
    }

    public double asDouble() {
        return as(Double.class);
    }

    public Optional<Short> asNullableShort() {
        return asNullable(Short.class);
    }

    public short asShort() {
        return as(Short.class);
    }

    public Optional<Byte> asNullableByte() {
        return asNullable(Byte.class);
    }

    public byte asByte() {
        return as(Byte.class);
    }

    public Optional<BigDecimal> asNullableBigDecimal() {
        return asNullable(BigDecimal.class);
    }

    public BigDecimal asBigDecimal() {
        return as(BigDecimal.class);
    }

    public Optional<Number> asNullableNumber() {
        return asNullable(Number.class);
    }

    public Number asNumber() {
        return as(Number.class);
    }

    @Override
    public String toString() {
        return "RowValue(" + this.data + ")";
    }

    private static boolean booleanValue(Object data) {
        return Objects.equals(data, true) || Objects.equals(data, (byte) 1);
    }
}
