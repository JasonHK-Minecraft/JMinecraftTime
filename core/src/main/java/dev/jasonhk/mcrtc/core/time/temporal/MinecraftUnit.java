package dev.jasonhk.mcrtc.core.time.temporal;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public enum MinecraftUnit implements TemporalUnit
{
    TICKS("Ticks", UnitType.TIME_BASED, Duration.ofSeconds(3, 600_000_000));

    private final String   name;
    private final UnitType type;
    private final Duration duration;
    private final boolean  durationEstimated;

    MinecraftUnit(String name, UnitType type, Duration duration)
    {
        this(name, type, duration, false);
    }

    MinecraftUnit(String name, UnitType type, Duration duration, boolean durationEstimated)
    {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.durationEstimated = durationEstimated;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Temporal> R addTo(R temporal, long amount)
    {
        return (R) temporal.plus(amount, this);
    }

    @Override
    public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive)
    {
        return temporal1Inclusive.until(temporal2Exclusive, this);
    }

    @Override
    public boolean isDateBased()
    {
        return (this.type == UnitType.DATE_BASED);
    }

    @Override
    public boolean isTimeBased()
    {
        return (this.type == UnitType.TIME_BASED);
    }

    @Override
    public boolean isDurationEstimated()
    {
        return this.durationEstimated;
    }

    @Override
    public Duration getDuration()
    {
        return this.duration;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    private enum UnitType
    {
        TIME_BASED,
        DATE_BASED
    }
}
