package dev.jasonhk.minecraft.time.temporal;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import static java.time.temporal.ChronoUnit.DAYS;

import static dev.jasonhk.minecraft.time.temporal.MinecraftUnit.TICKS;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public enum MinecraftField implements TemporalField
{
    TICK_OF_DAY("TickOfDay", FieldType.TIME_BASED, TICKS, DAYS, ValueRange.of(0, 23_999));

    private final String       name;
    private final FieldType    type;
    private final TemporalUnit baseUnit;
    private final TemporalUnit rangeUnit;
    private final ValueRange   range;
    private final String       displayNameKey;

    MinecraftField(
            String name,
            FieldType type,
            TemporalUnit baseUnit,
            TemporalUnit rangeUnit,
            ValueRange range)
    {
        this(name, type, baseUnit, rangeUnit, range, null);
    }

    MinecraftField(
            String name,
            FieldType type,
            TemporalUnit baseUnit,
            TemporalUnit rangeUnit,
            ValueRange range,
            String displayNameKey)
    {
        this.name = name;
        this.type = type;
        this.baseUnit = baseUnit;
        this.rangeUnit = rangeUnit;
        this.range = range;
        this.displayNameKey = displayNameKey;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Temporal> R adjustInto(R temporal, long newValue)
    {
        return (R) temporal.with(this, newValue);
    }

    @Override
    public long getFrom(TemporalAccessor temporal)
    {
        return temporal.getLong(this);
    }

    @Override
    public boolean isSupportedBy(TemporalAccessor temporal)
    {
        return temporal.isSupported(this);
    }

    @Override
    public ValueRange rangeRefinedBy(TemporalAccessor temporal)
    {
        return temporal.range(this);
    }

    @Override
    public ValueRange range()
    {
        return range;
    }

    public long checkValidValue(long value)
    {
        return range.checkValidValue(value, this);
    }

    public int checkValidIntValue(long value)
    {
        return range.checkValidIntValue(value, this);
    }

    @Override
    public boolean isDateBased()
    {
        return (type == FieldType.DATE_BASED);
    }

    @Override
    public boolean isTimeBased()
    {
        return (type == FieldType.TIME_BASED);
    }

    @Override
    public TemporalUnit getBaseUnit()
    {
        return baseUnit;
    }

    @Override
    public TemporalUnit getRangeUnit()
    {
        return rangeUnit;
    }

    @Override
    public String toString()
    {
        return name;
    }

    private enum FieldType
    {
        TIME_BASED,
        DATE_BASED,
    }
}
